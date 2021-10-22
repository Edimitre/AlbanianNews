package com.edikorce.albaniannews.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.edikorce.albaniannews.R;
import com.edikorce.albaniannews.entities.News;

import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {


    private List<News> allNewsList = new ArrayList<>();



    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View newsView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new NewsHolder(newsView);
    }




    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {

        News news = allNewsList.get(position);


        holder.title.setText(news.getTitle());

        holder.paragraph.setText(news.getParagraph());

        holder.source.setText("burimi lajmit : " + news.getSource());


    }



    @Override
    public int getItemCount() {
        return allNewsList.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder{

        private TextView title ,paragraph, source;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_text);
            paragraph = itemView.findViewById(R.id.paragraph_text);
            source = itemView.findViewById(R.id.source_text);

        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void putNews(List<News> newsList){
        this.allNewsList = newsList;
        notifyDataSetChanged();
    }

    public News getNewsByPos(int pos){
        return allNewsList.get(pos);
    }
}
