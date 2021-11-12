package com.edikorce.albaniannews.scraper;

import android.content.Context;

import com.edikorce.albaniannews.database.Repository;
import com.edikorce.albaniannews.entities.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LapsiScraper {



    Repository repository;

    Context context;

    public LapsiScraper(Context context) {
        this.context = context;
    }

    public void scrapLapsiAl1(){

        Document html_page = null;


        // ngarko faqen html si dokument
        try {
            html_page = Jsoup.connect("https://lapsi.al/kategoria/lajme/").get();
        } catch (Exception e) {
            System.out.println("faqja nuk u gjend");
        }


        assert html_page != null;

        Elements newsSection = html_page.getElementsByClass("post-preview");
        for (Element links:newsSection.select("a")){
            String link = links.attr("href");

            populateNewsFromLink(link);




        }

    }

    public void scrapLapsiAl2(){

        Document html_page = null;


        // ngarko faqen html si dokument
        try {
            html_page = Jsoup.connect("https://lapsi.al/kategoria/kryesore/").get();
        } catch (Exception e) {
            System.out.println("faqja nuk u gjend");
        }


        assert html_page != null;

        Elements newsSection = html_page.getElementsByClass("post-preview");
        for (Element links:newsSection.select("a")){
            String link = links.attr("href");

            populateNewsFromLink(link);



        }

    }

    public void populateNewsFromLink(String link){

        Document html_page = null;

        // ngarko faqen html si dokument
        try {
            html_page = Jsoup.connect(link).get();
        } catch (Exception e) {
            System.out.println("faqja nuk u gjend");
        }

        assert html_page != null;

        String title = html_page.select("h1").text();
        String paragraph = html_page.select("p").text();
        System.out.println("Title : " + title);
        System.out.println("Paragraph : " + paragraph.replace("identifikohu", ""));

        News news = new News(title, paragraph,  "lapsi.al");

        repository = new Repository(context);
        repository.addNews(news);

    }

}
