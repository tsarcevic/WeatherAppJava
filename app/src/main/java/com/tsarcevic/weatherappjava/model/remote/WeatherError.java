package com.tsarcevic.weatherappjava.model.remote;

import com.google.gson.annotations.Expose;

public class WeatherError {

    @Expose
    private int cod;

    @Expose
    private String message;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
