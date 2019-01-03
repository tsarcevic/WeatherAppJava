package com.tsarcevic.weatherappjava.view.weatherinfo;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.tsarcevic.weatherappjava.model.WeatherResponse;
import com.tsarcevic.weatherappjava.viewmodel.WeatherInfoViewModel;

import butterknife.BindView;
import butterknife.OnClick;

public class WeatherInfoView extends BaseActivity implements ReplaceCityDialogFragment.OnClickListener {

    private WeatherInfoViewModel viewModel;
    private WeatherInfoAdapter adapter;

    DialogFragment replaceCityDialogFragment;

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

    @OnClick(R.id.activity_weather_info_view_replace_city)
    public void onReplaceCityClicked() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag("replace_city");
        if (fragment != null) {
            ft.remove(fragment);
        }
        ft.addToBackStack(null);

        replaceCityDialogFragment = new ReplaceCityDialogFragment();
        ((ReplaceCityDialogFragment) replaceCityDialogFragment).setOnClickListener(this);
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
        viewModel = ViewModelProviders.of(this).get(WeatherInfoViewModel.class);
        observeData();
        viewModel.getWeatherInformation("osijek");
        progressBar.setVisibility(View.VISIBLE);
    }

    private void observeData() {
        viewModel.getWeatherResponse().observe(this, this::showWeatherData);
        viewModel.getWeatherResponseError().observe(this, this::showError);
        viewModel.getLoading().observe(this, this::toggleProgress);
    }

    private void toggleProgress(Boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void showWeatherData(WeatherResponse weatherResponse) {
        layoutFutureWeatherRecycler.setVisibility(View.VISIBLE);

        adapter.setWeatherInfoList(weatherResponse.getWeatherInfoList());
        layoutCurrentWeatherCity.setText(weatherResponse.getCity().getName() + ", " + weatherResponse.getCity().getCountry());
        layoutCurrentWeatherTemperature.setText("Current temperature: " + weatherResponse.getWeatherInfoList().get(0).getCurrentWeatherInfo().getTemperature().toString() + "°C");
        layoutCurrentWeatherPressure.setText("Pressure: " + weatherResponse.getWeatherInfoList().get(0).getCurrentWeatherInfo().getPressure().toString() + " hPa");
        layoutCurrentWeatherHumidity.setText("Humidity: " + weatherResponse.getWeatherInfoList().get(0).getCurrentWeatherInfo().getHumidity() + "%");
        layoutCurrentWeatherMinMaxTemperature.setText(weatherResponse.getWeatherInfoList().get(0).getCurrentWeatherInfo().getMinimumTemperature() + "°C - " + weatherResponse.getWeatherInfoList().get(0).getCurrentWeatherInfo().getMaximumTemperature() + "°C");
        layoutCurrentWeatherDescription.setText("Current condition: " + weatherResponse.getWeatherInfoList().get(0).getWeather().get(0).getDescription());
        Glide.with(this)
                .load(Constants.WEATHER_CODE_URL + weatherResponse.getWeatherInfoList().get(0).getWeather().get(0).getIcon() + ".png")
                .into(layoutCurrentWeatherIcon);

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

    private void showError(Boolean error) {
        Toast.makeText(this, "Something went horribly wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonClicked(String cityName) {
        layoutFutureWeatherRecycler.setVisibility(View.GONE);

        replaceCityDialogFragment.dismiss();
        viewModel.getWeatherInformation(cityName);
    }
}
