package com.tsarcevic.weatherappjava.view.weatherinfo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tsarcevic.weatherappjava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ReplaceCityDialogFragment extends DialogFragment {

    @BindView(R.id.fragment_dialog_query)
    EditText fragmentDialogQuery;

    interface OnClickListener {
        void onButtonClicked(String cityName);
    }

    private OnClickListener onClickListener;

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
    }

    @OnClick(R.id.fragment_dialog_button)
    public void onShowCityWeatherButtonClicked() {
        onClickListener.onButtonClicked(fragmentDialogQuery.getText().toString());
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
