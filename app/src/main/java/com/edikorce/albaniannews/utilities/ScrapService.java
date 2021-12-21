package com.edikorce.albaniannews.utilities;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.edikorce.albaniannews.database.Repository;
import com.edikorce.albaniannews.scraper.Scraper;

public class ScrapService extends Service {

    Scraper scraper ;

    Repository repository;



    @Override
    public void onCreate() {

        scraper = new Scraper(getApplicationContext());

        repository = new Repository(getApplicationContext());
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        repository.deleteAllNews();

        scraper.scrapBotaAl();
        scraper.scrapJetaOshQef();
        scraper.scrapLapsiAl();
        scraper.scrapSyriNet();

        startForeground(1, AndroidSystemUtilities.serviceNotification(getApplicationContext()));



        stopSelf();

        return START_NOT_STICKY;
    }

}
