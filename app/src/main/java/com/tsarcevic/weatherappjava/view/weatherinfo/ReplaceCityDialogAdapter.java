package com.tsarcevic.weatherappjava.view.weatherinfo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tsarcevic.weatherappjava.R;
import com.tsarcevic.weatherappjava.listener.ItemClickListener;
import com.tsarcevic.weatherappjava.model.local.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReplaceCityDialogAdapter extends RecyclerView.Adapter<ReplaceCityDialogAdapter.ViewHolder> {

    private List<City> cityNameList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public ReplaceCityDialogAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setCityName(List<City> cityName) {
        cityNameList.clear();
        cityNameList.addAll(cityName);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = cityNameList.get(position);
        if (city != null) {
            holder.cityName.setText(city.getCityName());
        }
    }

    @Override
    public int getItemCount() {
        return cityNameList.size();
    }

    public String getCity(int position) {
        return cityNameList.get(position).getCityName();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_city_name)
        TextView cityName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onItemClicked() {
            itemClickListener.onItemClicked(getAdapterPosition());
        }
    }
}
