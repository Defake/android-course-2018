package com.defake.opener;

import com.google.gson.annotations.SerializedName;

public final class Quote {
    @SerializedName(value="value")
    public String text;

    @SerializedName(value="appeared_at")
    public String date;
}
