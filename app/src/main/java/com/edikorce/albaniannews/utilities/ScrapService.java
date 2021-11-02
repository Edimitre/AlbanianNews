package com.edikorce.albaniannews.utilities;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.edikorce.albaniannews.database.Repository;
import com.edikorce.albaniannews.scraper.Scraper;

public class ScrapService extends Service {

    Scraper scraper ;

    Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        scraper = new Scraper(getApplicationContext());
        repository = new Repository(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        repository.deleteAllNews();


        scraper.scrapSyriNet();
        scraper.scrapJoq();
        scraper.scrapLapsi();
        scraper.scrapIdeaNews();

        startForeground(1, AndroidSystemUtilities.serviceNotification(getApplicationContext()));

        stopSelf();

        return START_NOT_STICKY;
    }
}
