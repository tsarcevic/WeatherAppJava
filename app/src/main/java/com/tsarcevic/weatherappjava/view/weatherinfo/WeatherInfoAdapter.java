package com.tsarcevic.weatherappjava.view.weatherinfo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsarcevic.weatherappjava.Constants;
import com.tsarcevic.weatherappjava.R;
import com.tsarcevic.weatherappjava.model.WeatherInfo;
import com.tsarcevic.weatherappjava.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherInfoAdapter extends RecyclerView.Adapter<WeatherInfoAdapter.ViewModel> {

    private List<WeatherInfo> weatherInfoList = new ArrayList<>();

    public void setWeatherInfoList(List<WeatherInfo> weatherInfoList) {
        this.weatherInfoList.clear();
        this.weatherInfoList.addAll(weatherInfoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new ViewModel(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        WeatherInfo weatherInfo = weatherInfoList.get(position);
        if (weatherInfo != null) {
            holder.setInfo(weatherInfo);
        }
    }

    @Override
    public int getItemCount() {
        return weatherInfoList.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {

        @BindView(R.id.item_weather_icon)
        ImageView itemWeatherIcon;
        @BindView(R.id.item_weather_date)
        TextView itemWeatherDate;
        @BindView(R.id.item_weather_temperature)
        TextView itemWeatherTemperature;

        public ViewModel(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setInfo(WeatherInfo weatherInfo) {
            itemWeatherDate.setText(DateUtil.showHourMinuteFormat(weatherInfo.getCurrentDate()));
            itemWeatherTemperature.setText("Temp: " + weatherInfo.getCurrentWeatherInfo().getTemperature().toString() + "°C");
            Glide.with(itemView)
                    .load(Constants.WEATHER_CODE_URL + weatherInfo.getWeather().get(0).getIcon() + ".png")
                    .into(itemWeatherIcon);
        }
    }
}
