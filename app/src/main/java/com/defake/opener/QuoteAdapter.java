package com.defake.opener;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder> {
    private ArrayList<Quote> quotesList;

    public QuoteAdapter() {
        quotesList = new ArrayList<>();
    }

    public static class QuoteViewHolder extends RecyclerView.ViewHolder {
        public TextView quoteText;
        public TextView quoteDate;
        QuoteViewHolder(View v) {
            super(v);
            quoteText = v.findViewById(R.id.quote_text);
            quoteDate = v.findViewById(R.id.quote_date);
        }
    }

    public void addQuote(Quote quote) {
        quotesList.add(quote);
        notifyDataSetChanged();
    }

    public void clearList() {
        quotesList.clear();
        notifyDataSetChanged();
    }

    @Override
    public @NonNull QuoteAdapter.QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.quote_item_view, parent, false);
        return new QuoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        Quote quote = quotesList.get(position);
        holder.quoteText.setText(quote.text);
        holder.quoteDate.setText(quote.date.replaceAll("T.*", ""));
    }

    @Override
    public int getItemCount() {
        return quotesList.size();
    }
}
