package com.edikorce.albaniannews.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.edikorce.albaniannews.entities.News;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert
    void insert(News news);

    @Delete
    void delete(News news);
    @Query("DELETE FROM news_table")
    void deleteAllNews();

    @Query("SELECT * FROM news_table")
    List<News> getAllNewsList();

    @Query("SELECT * FROM news_table")
    LiveData<List<News>> getAllNewsLiveData();

    @Query("SELECT * FROM news_table WHERE source LIKE :source")
    List<News> getNewsBySourceList(String source);

    @Query("SELECT * FROM news_table WHERE source LIKE :source")
    LiveData<List<News>> getNewsBySourceLiveData(String source);

}
