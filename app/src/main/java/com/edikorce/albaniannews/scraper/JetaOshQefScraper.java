package com.edikorce.albaniannews.scraper;

import android.content.Context;

import com.edikorce.albaniannews.database.Repository;
import com.edikorce.albaniannews.entities.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JetaOshQefScraper {

    Repository repository;

    Context context;

    public JetaOshQefScraper(Context context) {
        this.context = context;
    }

    public void scrapJetaOshQef(){

        Document html_page = null;


        // ngarko faqen html si dokument
        try {
            html_page = Jsoup.connect("https://joq-albania.com/kategori/lajme.html").get();
        } catch (Exception e) {
            System.out.println("faqja nuk u gjend");
        }

        assert html_page != null;

        Elements newsSection = html_page.getElementsByClass("home-category-post");

        for (Element li:newsSection.select("a")){
            String link = "https://joq-albania.com" + li.attr("href");
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


        News news = new News(title, paragraph,  "jeta_osh_qef.al");

        repository = new Repository(context);
        repository.addNews(news);

    }


}
