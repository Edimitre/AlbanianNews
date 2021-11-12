package com.edikorce.albaniannews.scraper;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.edikorce.albaniannews.database.Repository;
import com.edikorce.albaniannews.entities.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

public class BotaAlScrapper {


    Repository repository;

    Context context;

    public BotaAlScrapper(Context context) {
        this.context = context;
    }






    @RequiresApi(api = Build.VERSION_CODES.N)
    public void scrapBotaAl(){

        Document html_page = null;

        ArrayList<String> linksList = new ArrayList<>();

        // ngarko faqen html si dokument
        try {
            html_page = Jsoup.connect("https://bota.al/").get();
        } catch (Exception e) {
            System.out.println("faqja nuk u gjend");
        }


        assert html_page != null;

        Elements newsSection = html_page.getElementsByClass("background-overlay");

        for (Element links:newsSection.select("a")){
            String link = links.attr("href");
            if (link.length()>40){
                linksList.add(link);
            }
        }

        List<String> links = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            links = linksList.stream().distinct().collect(Collectors.toList());
        }

        for (String link:links){
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
        System.out.println("Paragraph : " + paragraph);

        News news = new News(title, paragraph,  "bota.al");

        repository = new Repository(context);
        repository.addNews(news);


    }
}
