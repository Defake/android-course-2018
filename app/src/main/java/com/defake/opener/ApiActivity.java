package com.defake.opener;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiActivity extends AppCompatActivity {

    private QuoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        RecyclerView recyclerView = findViewById(R.id.quotesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new QuoteAdapter();
        recyclerView.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED) {
            enableSearch();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.INTERNET},
                    0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if ((permissions.length > 0) &&
                permissions[0].equals(Manifest.permission.INTERNET)){
            if (grantResults[0] == 0) {
                enableSearch();
            }
        }
    }

    private void findQuote(String searchQuery) {
        TrumpService service = new Retrofit.Builder()
                .baseUrl("https://api.tronalddump.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TrumpService.class);

        service.findQuote(searchQuery).enqueue(new Callback<QuoteSearchResult>() {
            @Override
            public void onResponse(Call<QuoteSearchResult> call, Response<QuoteSearchResult> response) {
                if (response.code() == 200 && response.body() != null) {
                    for (Quote q : response.body().getQuotes()) {
                        adapter.addQuote(q);
                    }
                }
            }

            @Override
            public void onFailure(Call<QuoteSearchResult> call, Throwable t) { }
        });
    }

    private void enableSearch() {
        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearList();
                String searchQuery = ((TextView)findViewById(R.id.searchQuery)).getText().toString();
                findQuote(searchQuery);
            }
        });
    }
}
