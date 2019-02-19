package com.tsarcevic.weatherappjava.listener;

import com.tsarcevic.weatherappjava.base.BaseRecyclerListener;

public interface RecyclerItemClickListener<T> extends BaseRecyclerListener {

    void onItemClicked(T item);
}
