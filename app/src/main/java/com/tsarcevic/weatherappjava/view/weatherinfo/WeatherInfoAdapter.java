package com.tsarcevic.weatherappjava.view.weatherinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsarcevic.weatherappjava.Constants;
import com.tsarcevic.weatherappjava.R;
import com.tsarcevic.weatherappjava.base.BaseAdapter;
import com.tsarcevic.weatherappjava.base.BaseViewHolder;
import com.tsarcevic.weatherappjava.listener.RecyclerItemClickListener;
import com.tsarcevic.weatherappjava.model.remote.WeatherInfo;
import com.tsarcevic.weatherappjava.util.DateUtil;

import butterknife.BindView;

public class WeatherInfoAdapter extends BaseAdapter<WeatherInfo, RecyclerItemClickListener<WeatherInfo>, WeatherInfoAdapter.ViewHolder> {

    public WeatherInfoAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public WeatherInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflate(R.layout.item_weather, parent));
    }

    public class ViewHolder extends BaseViewHolder<WeatherInfo, RecyclerItemClickListener<WeatherInfo>> {

        @BindView(R.id.item_weather_icon)
        ImageView itemWeatherIcon;
        @BindView(R.id.item_weather_date)
        TextView itemWeatherDate;
        @BindView(R.id.item_weather_temperature)
        TextView itemWeatherTemperature;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(WeatherInfo item, RecyclerItemClickListener<WeatherInfo> listener) {
            itemWeatherDate.setText(DateUtil.showHourMinuteFormat(item.getCurrentDate()));
            itemWeatherTemperature.setText("Temp: " + item.getCurrentWeatherInfo().getTemperature().toString() + "°C");
            Glide.with(itemView)
                    .load(Constants.WEATHER_CODE_URL + item.getWeather().get(0).getIcon() + ".png")
                    .into(itemWeatherIcon);
        }
    }
}
