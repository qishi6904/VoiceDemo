package com.svw.dealerapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svw.dealerapp.R;
import java.util.ArrayList;

/**
 * Created by lijinkui on 2017/4/28.
 */

public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<String> mList;
    private Context context;

    public HomeListAdapter(ArrayList<String> list, Context context){
        this.mList = list;
        this.context = context;
    }

    private class CellViewHolder extends RecyclerView.ViewHolder {

        private TextView mTime;

        public CellViewHolder(View itemView) {
            super(itemView);
            mTime = (TextView) itemView.findViewById(R.id.tv_home_list_item_time);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default: {
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list, parent, false);
                return new CellViewHolder(v1);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            default: {
                CellViewHolder cellViewHolder = (CellViewHolder) holder;
                cellViewHolder.mTime.setText(mList.get(position).toString());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

}
