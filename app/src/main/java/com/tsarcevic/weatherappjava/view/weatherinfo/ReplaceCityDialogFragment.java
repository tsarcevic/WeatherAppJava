package com.tsarcevic.weatherappjava.view.weatherinfo;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.tsarcevic.weatherappjava.R;
import com.tsarcevic.weatherappjava.listener.ItemClickListener;
import com.tsarcevic.weatherappjava.model.local.City;
import com.tsarcevic.weatherappjava.viewmodel.CityViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReplaceCityDialogFragment extends DialogFragment implements ItemClickListener {

    interface ButtonClickListener {
        void onButtonClicked(String cityName);
    }

    private ButtonClickListener buttonClickListener;
    private ReplaceCityDialogAdapter adapter;
    private CityViewModel cityViewModel;

    @BindView(R.id.fragment_dialog_query)
    EditText fragmentDialogQuery;
    @BindView(R.id.fragment_dialog_recycler)
    RecyclerView cityListRecycler;

    @OnClick(R.id.fragment_dialog_button)
    public void onShowCityWeatherButtonClicked() {
        buttonClickListener.onButtonClicked(fragmentDialogQuery.getText().toString().trim());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.replace_city_fragment_dialog, container, false);
        // Do all the stuff to initialize your custom view
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initUI();
        initViewModel();
    }

    private void initUI() {
        adapter = new ReplaceCityDialogAdapter(this);

        LinearLayoutManager linear = new LinearLayoutManager(getActivity());

        cityListRecycler.setLayoutManager(linear);
        cityListRecycler.setAdapter(adapter);
    }

    private void initViewModel() {
        cityViewModel = new CityViewModel(getActivity().getApplication());
        observeData();
        cityViewModel.getAllCities();
    }

    private void observeData() {
        cityViewModel.getCityListResponse().observe(this, this::showCities);
        cityViewModel.getCityListError().observe(this, this::showError);
    }

    private void showCities(List<City> cities) {
        if(cities.size() == 0) {
            cityListRecycler.setVisibility(View.GONE);
        }
        adapter.setCityName(cities);
    }

    private void showError(Boolean aBoolean) {
        cityListRecycler.setVisibility(View.GONE);
    }

    public void setButtonClickListener(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    @Override
    public void onItemClicked(int position) {
        buttonClickListener.onButtonClicked(adapter.getCity(position));
    }
}
