package com.tsarcevic.weatherappjava.view.weatherinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tsarcevic.weatherappjava.R;
import com.tsarcevic.weatherappjava.base.BaseAdapter;
import com.tsarcevic.weatherappjava.base.BaseViewHolder;
import com.tsarcevic.weatherappjava.listener.RecyclerItemClickListener;
import com.tsarcevic.weatherappjava.model.local.City;

import butterknife.BindView;

public class ReplaceCityDialogAdapter extends BaseAdapter<City, RecyclerItemClickListener<City>, ReplaceCityDialogAdapter.ViewHolder> {

    public ReplaceCityDialogAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflate(R.layout.item_city, parent));
    }

    public class ViewHolder extends BaseViewHolder<City, RecyclerItemClickListener<City>> {

        @BindView(R.id.item_city_name)
        TextView cityName;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(City item, RecyclerItemClickListener<City> listener) {
            cityName.setText(item.getCityName());
            cityName.setOnClickListener(v -> {
                listener.onItemClicked(item);
            });
        }
    }
}
