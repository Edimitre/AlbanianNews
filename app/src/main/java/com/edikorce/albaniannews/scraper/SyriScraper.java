package com.edikorce.albaniannews.scraper;

import android.content.Context;
import android.util.Log;

import com.edikorce.albaniannews.database.Repository;
import com.edikorce.albaniannews.entities.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SyriScraper {

    Repository repository;

    Context context;

    public SyriScraper(Context context) {
        this.context = context;
    }

    public void scrapSyriNet(){

        Document html_page = null;


        // ngarko faqen html si dokument
        try {
            html_page = Jsoup.connect("https://www.syri.net/lajme/").get();
        } catch (Exception e) {
            System.out.println("faqja nuk u gjend");
        }


        assert html_page != null;

        Elements newsSection = html_page.getElementsByClass("container");
        for (Element links:newsSection.select("a")){
            String link = links.attr("href");
            if (link.length()> 45){

                populateNewsFromLink(link);
            }

        }

    }

    public void populateNewsFromLink(String link){

        Document html_page = null;

        // ngarko faqen html si dokument
        try {

            html_page = Jsoup.connect(link).get();

            html_page = Jsoup.connect(link).get();
        } catch (Exception e) {
            System.out.println("faqja nuk u gjend");
        }

        assert html_page != null;



        String title = html_page.select("h1").text().replace("Komentet", "");
        String paragraph = html_page.select("p").text();

        String noNeedPart = "© SYRI.net SYRI.net është agjencia më e madhe e lajmeve në Shqipëri, që brenda një kohe të shkurtër është kthyer në lider të informacionit. Në SYRI.net do të gjeni opinionistë më me emër në vendin tonë, investigime dhe lajme që nuk do ti gjeni diku tjetër. Lajmet në SYRI.net janë prodhim i SYRI.net dhe nuk lejohet marrja e tyre pa një komunikim të mëparshëm me redaksinë. Ju ftojmë të klikoni dhe të na shkruani sa herë që ju mendoni se duhet. Stafi i SYRI.net Për informacione info@syri.net, për reklama marketing@syri.net Merrni lajmet më të fundit nga SYRI.net në çdo moment dhe kudo që të jeni!";


        News news = new News(title, paragraph.replace(noNeedPart, "").replace("Komentet",""),  "syri.net");


        repository = new Repository(context);
        repository.addNews(news);

    }

}
