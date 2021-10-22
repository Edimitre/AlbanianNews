package com.edikorce.albaniannews.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.edikorce.albaniannews.entities.News;


@Database(entities = {News.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();

    private static volatile NewsDatabase INSTANCE;


    public static NewsDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (NewsDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsDatabase.class,"NewsDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
