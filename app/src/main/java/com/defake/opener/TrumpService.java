package com.defake.opener;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrumpService {
    @Headers("accept: application/hal+json")
    @GET("random/quote")
    Call<Quote> randomQuote();

    @Headers("accept: application/hal+json")
    @GET("search/quote")
    Call<QuoteSearchResult> findQuote(@Query("query") String query);
}

