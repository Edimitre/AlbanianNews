package com.edikorce.albaniannews.scraper;

import android.content.Context;

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
        } catch (Exception e) {
            System.out.println("faqja nuk u gjend");
        }

        assert html_page != null;

        String title = html_page.select("h1").text();
        String paragraph = html_page.select("p").text();

        News news = new News(title, paragraph,  "syri.net");

        repository = new Repository(context);
        repository.addNews(news);

    }

}
