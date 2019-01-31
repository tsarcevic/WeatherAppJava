package com.tsarcevic.weatherappjava.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("main")
    @Expose
    private String mainDescription;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("icon")
    @Expose
    private String icon;

    public int getId() {
        return id;
    }

    public String getMainDescription() {
        return mainDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
