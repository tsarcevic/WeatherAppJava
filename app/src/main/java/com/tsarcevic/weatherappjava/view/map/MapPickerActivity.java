package com.tsarcevic.weatherappjava.view.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tsarcevic.weatherappjava.Constants;
import com.tsarcevic.weatherappjava.R;
import com.tsarcevic.weatherappjava.base.BaseActivity;
import com.tsarcevic.weatherappjava.view.login.LoginActivity;

import butterknife.BindView;

public class MapPickerActivity extends BaseActivity {

    private int startingActivity;

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
        if (getIntent().hasExtra(Constants.STARTING_ACTIVITY)) {
            startingActivity = getIntent().getIntExtra(Constants.STARTING_ACTIVITY, 0);
        }
    }

    private void showData() {
        mapText.setText(startingActivity == Constants.STARTING_ACTIVITY_LOGIN ? "Text 2" : "Text 1");
    }
}
