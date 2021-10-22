package com.edikorce.albaniannews.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.edikorce.albaniannews.entities.News;
import com.edikorce.albaniannews.utilities.AndroidSystemUtilities;

import java.util.ArrayList;
import java.util.List;



public class Repository {

    Context context;

    NewsDao newsDao;

    public AndroidSystemUtilities systemUtilities;

    List<News> newsList = new ArrayList<>();
    List<News> allNewsList = new ArrayList<>();

    public Repository(Context context) {
        this.context = context;
        newsDao = NewsDatabase.getInstance(context).newsDao();
    }

    public void addNews(News news){

        Thread thread = new Thread(()->{
            newsDao.insert(news);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void deleteNews(News news){

        Thread thread = new Thread(()->{
            newsDao.delete(news);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void deleteAllNews(){
        Thread thread = new Thread(()->{
            newsDao.deleteAllNews();

        });
        thread.start();
    }

    public List<News> getNewsBySource(String source){

        Thread thread = new Thread(()->{
            newsList = newsDao.getNewsBySourceList(source);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return newsList;

    }

    public List<News> getAllNews(){

        Thread thread = new Thread(()->{
           allNewsList = newsDao.getAllNewsList();
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allNewsList;

    }


}
