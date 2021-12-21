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
import com.edikorce.albaniannews.utilities.ScrapService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Repository repository;

    Button btn_botaal, btn_joq,  btn_lapsi, btn_syri;

    SwipeRefreshLayout swipeRefreshLayout;

    Intent serviceIntent;

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

        serviceIntent = new Intent(getApplicationContext(), ScrapService.class);


        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        btn_botaal = findViewById(R.id.btn_bota);

        btn_joq = findViewById(R.id.btn_joq);

        btn_lapsi = findViewById(R.id.btn_lapsi);

        btn_syri = findViewById(R.id.btn_syri);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setListeners(){

        swipeRefreshLayout.setOnRefreshListener(() -> {

           AndroidSystemUtilities.startScrapService(getApplicationContext());
           swipeRefreshLayout.setRefreshing(false);

        });

        btn_botaal.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewsReadActivity.class);
            intent.putExtra("source", "bota.al");
            intent.putExtra("title", "Bota.al");
            startActivity(intent);

        });
        btn_joq.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, NewsReadActivity.class);
            intent.putExtra("source", "jeta_osh_qef.al");
            intent.putExtra("title", "jetaOshQef");
            startActivity(intent);


        });
        btn_lapsi.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, NewsReadActivity.class);
            intent.putExtra("source", "lapsi.al");
            intent.putExtra("title", "Lapsi.al");
            startActivity(intent);

        });
        btn_syri.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, NewsReadActivity.class);
            intent.putExtra("source", "syri.net");
            intent.putExtra("title", "syri.net");
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

            btn_botaal.setEnabled(false);
            btn_syri.setEnabled(false);
        }
    }


}