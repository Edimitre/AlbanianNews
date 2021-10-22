package com.edikorce.albaniannews.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.edikorce.albaniannews.database.NewsDao;
import com.edikorce.albaniannews.database.NewsDatabase;
import com.edikorce.albaniannews.database.Repository;
import com.edikorce.albaniannews.entities.News;

import java.util.List;


public class NewsViewModel extends AndroidViewModel {

    private NewsDao newsDao;

    private Repository repository = new Repository(getApplication());

    LiveData<List<News>> newsList;

    public NewsViewModel(@NonNull Application application) {
        super(application);

        newsDao = NewsDatabase.getInstance(getApplication()).newsDao();


        newsList = newsDao.getAllNewsLiveData();
    }

    public LiveData<List<News>> getAllNewsBySource(String source) {
        return newsDao.getNewsBySourceLiveData(source);
    }

}

