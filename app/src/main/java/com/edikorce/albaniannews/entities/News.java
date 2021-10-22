package com.edikorce.albaniannews.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table")
public class News {

    @PrimaryKey(autoGenerate = true)
    int id;

    String title;
    String paragraph;
    String source;

    public News(String title, String paragraph, String source) {
        this.title = title;
        this.paragraph = paragraph;
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
