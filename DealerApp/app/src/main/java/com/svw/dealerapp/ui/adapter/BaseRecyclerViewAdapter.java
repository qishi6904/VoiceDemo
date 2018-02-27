package com.svw.dealerapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by qinshi on 4/14/2017.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    protected List<T> dataList;
    protected Context context;

    public BaseRecyclerViewAdapter(Context context, List<T> dataList){
        this.context = context;
        this.dataList = dataList;
    }
}
