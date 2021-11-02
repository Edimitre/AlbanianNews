package com.edikorce.albaniannews.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.edikorce.albaniannews.R;
import com.edikorce.albaniannews.database.Repository;
import com.edikorce.albaniannews.entities.News;
import com.edikorce.albaniannews.utilities.AndroidSystemUtilities;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Repository repository;

    Button btn_syri, btn_joq, btn_corruption, btn_lapsi;

    SwipeRefreshLayout swipeRefreshLayout;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadObjects();


        disableIfNoConnection();


        AndroidSystemUtilities.createNotificationChannel(getApplicationContext());


        checkIfNewsEmpty();


        setListeners();
    }

    private void disableIfNoConnection(){

        if (!AndroidSystemUtilities.isNetworkConnected(getApplicationContext())){
            disabled(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkIfNewsEmpty(){
        repository = new Repository(getApplicationContext());
        List<News> allNews = repository.getAllNews();
        if (allNews.isEmpty()){
            AndroidSystemUtilities.startScrapService(getApplicationContext());
        }
    }

    private void loadObjects(){

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);


        btn_syri = findViewById(R.id.btn_syri);
        btn_joq = findViewById(R.id.btn_joq);
        btn_corruption = findViewById(R.id.btn_corruption);
        btn_lapsi = findViewById(R.id.btn_lapsi);
    }

    private void setListeners(){

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onRefresh() {
                AndroidSystemUtilities.startScrapService(getApplicationContext());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        btn_syri.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, NewsReadActivity.class);
            intent.putExtra("source", "syri");
            intent.putExtra("title", "syri.net");
            startActivity(intent);

        });

        btn_joq.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, NewsReadActivity.class);
            intent.putExtra("source", "joq");
            intent.putExtra("title", "jetaOshQef");
            startActivity(intent);


        });

        btn_corruption.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewsReadActivity.class);
            intent.putExtra("source", "ide");
            intent.putExtra("title", "Te ndryshme");
            startActivity(intent);

        });

        btn_lapsi.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, NewsReadActivity.class);
            intent.putExtra("source", "lapsi");
            intent.putExtra("title", "lapsi.al");
            startActivity(intent);

        });

    }

    @SuppressLint("SetTextI18n")
    private void disabled(boolean value){

        // kontrollon nqs pajisja ka internet
        if(value){
            btn_lapsi.setEnabled(false);
            btn_lapsi.setText("Pajisja Nuk Ka Internet");

            btn_joq.setEnabled(false);
            btn_joq.setText("Nuk Mund Te Vazhdojme Pa Internet");

            btn_corruption.setEnabled(false);
            btn_syri.setEnabled(false);
        }
    }

}