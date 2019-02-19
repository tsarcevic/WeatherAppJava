package com.tsarcevic.weatherappjava.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, L extends BaseRecyclerListener, VH extends BaseViewHolder<T, L>> extends RecyclerView.Adapter<VH> {

    private List<T> itemList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private L listener;

    public BaseAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * method that ads new items to empty list
     *
     * @param itemList Generic List of items
     */
    public void setData(List<T> itemList) {
        this.itemList.clear();
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    /**
     * Method that ads new items to already defined list
     *
     * @param moreItems Generic List of items
     */
    public void addData(List<T> moreItems) {
        this.itemList.addAll(moreItems);
        notifyDataSetChanged();
    }

    /**
     * Method that sets listener for adapter
     *
     * @param listener Generic listener
     */
    public void setListener(L listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T item = itemList.get(position);
        if (item != null) {
            holder.onBind(item, listener);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * Method that returns item at picked position
     *
     * @param position Position of item
     * @return Item at picked position
     */
    public T getItem(int position) {
        return itemList.get(position);
    }

    public View inflate(int layout, ViewGroup parent) {
        return layoutInflater.inflate(layout, parent, false);
    }
}
