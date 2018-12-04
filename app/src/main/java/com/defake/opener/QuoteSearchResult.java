package com.defake.opener;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class QuoteSearchResult {
    @SerializedName(value="_embedded")
    public EmbeddedList listWrapper;

    public List<Quote> getQuotes() {
        return listWrapper.quotes;
    }

    public class EmbeddedList {
        public List<Quote> quotes;
    }

}
