package com.edikorce.albaniannews.scraper;

import android.content.Context;
import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;

import com.edikorce.albaniannews.database.Repository;
import com.edikorce.albaniannews.entities.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

    Context context;

    BotaAlScrapper botaAlScrapper;
    JetaOshQefScraper jetaOshQefScraper;
    LapsiScraper lapsiScraper;
    SyriScraper syriScraper;




    public Scraper(Context context) {

        this.context = context;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void scrapBotaAl() {

        Thread thread = new Thread(()->{
            botaAlScrapper = new BotaAlScrapper(context);
            botaAlScrapper.scrapBotaAl();

        });
        thread.start();


    }

    public void scrapJetaOshQef(){
        Thread thread = new Thread(()->{
            jetaOshQefScraper = new JetaOshQefScraper(context);
            jetaOshQefScraper.scrapJetaOshQef();

        });
        thread.start();

    }

    public void scrapLapsiAl(){

        Thread thread = new Thread(()->{
            lapsiScraper = new LapsiScraper(context);
            lapsiScraper.scrapLapsiAl1();
            lapsiScraper.scrapLapsiAl2();
        });
        thread.start();
    }

    public void scrapSyriNet(){

        Thread thread = new Thread(()->{

            syriScraper = new SyriScraper(context);
            syriScraper.scrapSyriNet();
        });
        thread.start();
    }


}
