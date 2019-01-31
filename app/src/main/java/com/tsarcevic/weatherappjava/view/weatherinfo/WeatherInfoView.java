package com.tsarcevic.weatherappjava.view.weatherinfo;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tsarcevic.weatherappjava.Constants;
import com.tsarcevic.weatherappjava.R;
import com.tsarcevic.weatherappjava.base.BaseActivity;
import com.tsarcevic.weatherappjava.model.remote.CurrentTemperatureResponse;
import com.tsarcevic.weatherappjava.model.remote.WeatherError;
import com.tsarcevic.weatherappjava.model.remote.WeatherResponse;
import com.tsarcevic.weatherappjava.viewmodel.CityViewModel;
import com.tsarcevic.weatherappjava.viewmodel.WeatherInfoViewModel;

import butterknife.BindView;
import butterknife.OnClick;

public class WeatherInfoView extends BaseActivity implements ReplaceCityDialogFragment.ButtonClickListener {

    private WeatherInfoViewModel weatherViewModel;
    private WeatherInfoAdapter adapter;
    private CityViewModel cityViewModel;

    private DialogFragment replaceCityDialogFragment;

    @BindView(R.id.layout_current_weather_icon)
    ImageView layoutCurrentWeatherIcon;
    @BindView(R.id.layout_current_weather_description)
    TextView layoutCurrentWeatherDescription;
    @BindView(R.id.layout_current_weather_city)
    TextView layoutCurrentWeatherCity;
    @BindView(R.id.layout_current_weather_temperature)
    TextView layoutCurrentWeatherTemperature;
    @BindView(R.id.layout_current_weather_min_max_temperature)
    TextView layoutCurrentWeatherMinMaxTemperature;
    @BindView(R.id.layout_current_weather_pressure)
    TextView layoutCurrentWeatherPressure;
    @BindView(R.id.layout_current_weather_humidity)
    TextView layoutCurrentWeatherHumidity;
    @BindView(R.id.layout_future_weather_recycler)
    RecyclerView layoutFutureWeatherRecycler;
    @BindView(R.id.weather_info_toolbar)
    Toolbar weatherInfoToolbar;
    @BindView(R.id.weather_info_collapsing_toolbar_layout)
    CollapsingToolbarLayout weatherInfoCollapsingToolbarLayout;
    @BindView(R.id.weather_info_app_bar_layout)
    AppBarLayout weatherInfoAppBarLayout;
    @BindView(R.id.activity_weather_info_view_progress)
    ProgressBar progressBar;
    @BindView(R.id.activity_weather_info_view_error)
    TextView errorMessage;

    @OnClick(R.id.activity_weather_info_view_replace_city)
    public void onReplaceCityClicked() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("replace_city");
        if (fragment != null) {
            ft.remove(fragment);
        }
        ft.addToBackStack(null);

        replaceCityDialogFragment = new ReplaceCityDialogFragment();
        ((ReplaceCityDialogFragment) replaceCityDialogFragment).setButtonClickListener(this);
        replaceCityDialogFragment.show(ft, "replace_city");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_weather_info_view;
    }

    @Override
    protected void initUI() {
        initRecycler();
        initViewModel();
    }

    private void initRecycler() {
        adapter = new WeatherInfoAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutFutureWeatherRecycler.setLayoutManager(layoutManager);
        layoutFutureWeatherRecycler.setAdapter(adapter);
        layoutFutureWeatherRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        layoutFutureWeatherRecycler.setNestedScrollingEnabled(false);
    }

    private void initViewModel() {
        weatherViewModel = ViewModelProviders.of(this).get(WeatherInfoViewModel.class);
        cityViewModel = new CityViewModel(getApplication());
        observeData();
        weatherViewModel.getFullWeatherInformation("osijek");
        progressBar.setVisibility(View.VISIBLE);
    }

    private void observeData() {
        weatherViewModel.getWeatherResponse().observe(this, this::showWeatherData);
        weatherViewModel.getCurrentWeatherResponse().observe(this, this::showCurrentWeatherData);
        weatherViewModel.getWeatherResponseError().observe(this, this::showError);
        weatherViewModel.getLoading().observe(this, this::toggleProgress);
    }

    private void toggleProgress(Boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void showCurrentWeatherData(CurrentTemperatureResponse weatherResponse) {
        cityViewModel.saveCity(weatherResponse.getName());

        weatherInfoAppBarLayout.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.GONE);

        layoutCurrentWeatherCity.setText(weatherResponse.getName());
        layoutCurrentWeatherTemperature.setText("Current temperature: " + weatherResponse.getCurrentWeather().getTemperature().toString() + "°C");
        layoutCurrentWeatherPressure.setText("Pressure: " + weatherResponse.getCurrentWeather().getPressure().toString() + " hPa");
        layoutCurrentWeatherHumidity.setText("Humidity: " + weatherResponse.getCurrentWeather().getHumidity() + "%");
        layoutCurrentWeatherMinMaxTemperature.setText(weatherResponse.getCurrentWeather().getMinimumTemperature().toString() + "°C - "
                + weatherResponse.getCurrentWeather().getMaximumTemperature().toString() + "°C");
        layoutCurrentWeatherDescription.setText("Current condition: " + weatherResponse.getWeather().get(0).getDescription());
        Glide.with(this)
                .load(Constants.WEATHER_CODE_URL + weatherResponse.getWeather().get(0).getIcon() + ".png")
                .into(layoutCurrentWeatherIcon);
    }

    private void showWeatherData(WeatherResponse weatherResponse) {
        cityViewModel.saveCity(weatherResponse.getCity().getName());

        layoutFutureWeatherRecycler.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.GONE);

        adapter.setWeatherInfoList(weatherResponse.getWeatherInfoList());

        weatherInfoAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    weatherInfoCollapsingToolbarLayout.setTitle(weatherResponse.getCity().getName() + ", " + weatherResponse.getCity().getCountry());
                    isShow = true;
                } else if (isShow) {
                    weatherInfoCollapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void showError(WeatherError error) {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(error.getMessage());
    }

    @Override
    public void onButtonClicked(String cityName) {
        layoutFutureWeatherRecycler.setVisibility(View.GONE);
        weatherInfoAppBarLayout.setVisibility(View.GONE);

        replaceCityDialogFragment.dismiss();
        weatherViewModel.getFullWeatherInformation(cityName);
    }
}
