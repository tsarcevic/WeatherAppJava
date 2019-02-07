package com.tsarcevic.weatherappjava.view.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsarcevic.weatherappjava.Constants;
import com.tsarcevic.weatherappjava.R;
import com.tsarcevic.weatherappjava.base.BaseActivity;
import com.tsarcevic.weatherappjava.view.login.LoginActivity;

import butterknife.BindView;

public class MapPickerActivity extends BaseActivity implements OnMapReadyCallback {

    private int startingActivity;
    private double lat, lng;

    @BindView(R.id.maptext)
    TextView mapText;

    @Override
    public void onBackPressed() {
        if (startingActivity == Constants.STARTING_ACTIVITY_LOGIN) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (startingActivity == Constants.STARTING_ACTIVITY_WEATHER_INFO) {
            super.onBackPressed();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_map_picker;
    }

    @Override
    protected void initUI() {
        getExtras();
        showData();
    }

    private void getExtras() {
        if (getIntent().getAction() != null) {
            if (getIntent().getAction().equalsIgnoreCase("Zagreb")) {
                startingActivity = Constants.STARTING_ACTIVITY_LOGIN;
                mapText.setText("1");
            } else if (getIntent().getAction().equalsIgnoreCase("Osijek")) {
                mapText.setText("2");
                startingActivity = Constants.STARTING_ACTIVITY_LOGIN;
            }
        }

        if (getIntent().hasExtra(Constants.STARTING_ACTIVITY)) {
            startingActivity = getIntent().getIntExtra(Constants.STARTING_ACTIVITY, 0);
            lat = 45.00;
            lng = 18.00;
        }
    }

    private void showData() {
        mapText.setText(startingActivity == 1 ? getIntent().getAction() : "Pick city");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng pickedCity = new LatLng(lat, lng);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickedCity, 13));

        googleMap.addMarker(new MarkerOptions()
                .title(getIntent().getAction() != null ? getIntent().getAction() : "Pick city")
                .position(pickedCity));
    }
}
