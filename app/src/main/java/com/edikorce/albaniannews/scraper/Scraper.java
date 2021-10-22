package com.edikorce.albaniannews.scraper;

import android.content.Context;
import android.util.Log;

import com.edikorce.albaniannews.database.Repository;
import com.edikorce.albaniannews.entities.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

    Context context;
    Repository repository;



    public Scraper(Context context) {

        this.context = context;
        repository = new Repository(context);
    }

    public void scrapSyriNet() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {



                // deklaro nje dokument bosh
                Document html_page = null;


                // ngarko faqen html si dokument
                try {
                    html_page = Jsoup.connect("https://www.syri.net/lajme/").get();
                } catch (Exception e) {
                    System.out.println("faqja nuk u gjend");
                }


                assert html_page != null;


                Elements page = html_page.select("div[id=wrapper]");

                Elements linket = page.select("a");


                for (Element link : linket) {
                    String sLinku = link.attr("href");

                    if (sLinku.startsWith("https")) {

                        String syriTitle = sLinku.trim();

                        String sTitle = syriTitle.replace("https://www.syri.net/", "");

                        String title = sTitle.replaceAll("\\P{L}", " ").replace("politike", "");

                        String syriParagraph = scrapTextFromLink(sLinku);


                        String paragraph = syriParagraph.replace("© SYRI.net SYRI.net është agjencia më e madhe e lajmeve në Shqipëri, që brenda një kohe të shkurtër është kthyer në lider të informacionit. Në SYRI.net do të gjeni opinionistë më me emër në vendin tonë, investigime dhe lajme që nuk do ti gjeni diku tjetër. Lajmet në SYRI.net janë prodhim i SYRI.net dhe nuk lejohet marrja e tyre pa një komunikim të mëparshëm me redaksinë. Ju ftojmë të klikoni dhe të na shkruani sa herë që ju mendoni se duhet. Stafi i SYRI.net Për informacione info@syri.net, për reklama marketing@syri.net Merrni lajmet më të fundit nga SYRI.net në çdo moment dhe kudo që të jeni!" , "");


                        News lajmi = new News(title.trim().toUpperCase(), paragraph.trim(),  "syri");


                        repository.addNews(lajmi);


                    }
                }
            }
        });
        thread.start();

    }

    public void scrapJoq() {

        Thread thread  = new Thread(new Runnable() {
            @Override
            public void run() {



                // deklaro nje dokument bosh
                Document html_page = null;


                // ngarko faqen html si dokument
                try {
                    html_page = Jsoup.connect("https://joq-albania.com/kategori/lajme.html").get();
                } catch (Exception e) {
                    System.out.println("faqja nuk u gjend");
                }


                assert html_page != null;

                Elements page = html_page.select("a[href]");

                for (Element link : page) {

                    String title_bruto = link.text().trim();


                    // pastrojme titullin nga budalleqet e faqes
                    if (title_bruto.length() > 25) {

                        String joqTitle = title_bruto.trim();

                        // marim linkun
                        String link_bruto = link.attr("href");

                        if (link_bruto.contains("artikull")) {
                            String linku = "https://joq-albania.com" + link_bruto;

                            String joqParagraph = scrapTextFromLink(linku);

                            News news = new News(joqTitle.toUpperCase(), joqParagraph, "joq");

                            repository.addNews(news);
                        }

                    }
                }
            }
        });
        thread.start();


    }

    public void scrapCorruptionNews() {
        Thread thread  = new Thread(new Runnable() {
            @Override
            public void run() {
                // deklaro nje dokument bosh
                Document html_page = null;

                // ngarko faqen html si dokument
                try {
                    html_page = Jsoup.connect("https://joq-albania.com/kategori/vec-e-jona.html").get();
                } catch (Exception e) {
                    System.out.println("faqja nuk u gjend");
                }

                assert html_page != null;

                Elements page = html_page.select("a[href]");

                for (Element link : page) {

                    String title_bruto = link.text().trim();


                    // pastrojme titullin nga budalleqet e faqes
                    if (title_bruto.length() > 25) {

                        String coruptionTitle = title_bruto.trim();

                        // marim linkun
                        String link_bruto = link.attr("href");

                        if (link_bruto.contains("artikull")) {
                            String linku = "https://joq-albania.com" + link_bruto;

                            String corruptionParagraph = scrapTextFromLink(linku);

                            News news = new News(coruptionTitle.toUpperCase(), corruptionParagraph, "corruption");

                            repository.addNews(news);

                        }

                    }

                }
            }
        });
        thread.start();

    }

    public void scrapLapsi(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Document html_page = null;


                // ngarko faqen html si dokument
                try {
                    html_page = Jsoup.connect("https://lapsi.al/kategoria/ide/").get();
                } catch (Exception e) {
                    System.out.println("faqja nuk u gjend");
                }

                assert html_page != null;

                Elements faqja = html_page.select("div[id=container]");

                Elements links = faqja.select("a[href]");



                for(Element link:links) {
                    String linku = link.attr("href");

                    if (linku.getBytes().length > 55) {
                        String myLink = linku.trim();
                        String titulli = link.text();

                        if (!titulli.isEmpty()) {
                            String par = scrapTextFromLink(myLink);

                            String lapsiTitle = titulli.trim();

                            String lapsiParagraph = par.replace("identifikohu", "");

                            News news = new News(lapsiTitle.toUpperCase(),lapsiParagraph, "lapsi");

                            repository.addNews(news);

                        }

                    }

                }
            }
        });

        thread.start();


    }

    public String scrapTextFromLink(String link) {

        Document html_page = null;

        // ngarko faqen html si dokument
        try {
            html_page = Jsoup.connect(link).get();
        } catch (Exception e) {
            System.out.println("faqja nuk u gjend");
        }

        assert html_page != null;
        return html_page.select("p").text();

    }
}
