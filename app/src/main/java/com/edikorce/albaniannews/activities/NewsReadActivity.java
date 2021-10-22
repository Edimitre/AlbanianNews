package com.edikorce.albaniannews.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.edikorce.albaniannews.R;
import com.edikorce.albaniannews.adapter.NewsAdapter;
import com.edikorce.albaniannews.database.Repository;
import com.edikorce.albaniannews.entities.News;
import com.edikorce.albaniannews.viewModel.NewsViewModel;

import java.util.List;

public class NewsReadActivity extends AppCompatActivity {

    Repository repository;
    RecyclerView recyclerView;
    NewsAdapter adapter;
    NewsViewModel newsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_read);

        loadObjects();
        loadRecyclerView();
        loadToolbar();
        observeList(getSourceFromIntent());
        addRecyclerViewTouchFunctions();

    }

    private String getSourceFromIntent(){

        Intent getSource = getIntent();
        String source  = getSource.getStringExtra("source");
        return source;
    }

    private String getTitleFromIntent(){

        Intent getTitle = getIntent();
        String title = getTitle.getStringExtra("title");
        return title;
    }

    private void loadObjects(){

        repository = new Repository(getApplicationContext());
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new NewsAdapter();
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

    }

    private void loadToolbar(){

        Toolbar topToolbar = (Toolbar) findViewById(R.id.top_toolbar);


        topToolbar.setTitle("Lajmet nga : " + getTitleFromIntent().toUpperCase());

        setSupportActionBar(topToolbar);
    }

    private void loadRecyclerView(){

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void observeList(String source) {

        newsViewModel.getAllNewsBySource(source).observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> allNews) {

                adapter.putNews(allNews);
            }
        });

    }

    private void addRecyclerViewTouchFunctions() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                repository.deleteNews(adapter.getNewsByPos(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Lajmi u fshi", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);
    }


}