package com.svw.dealerapp.ui.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.entity.KeyValueListItemEntity;

import java.util.List;

/**
 * Created by qinshi on 6/2/2017.
 */

public class ScheduleUpdateAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<KeyValueListItemEntity> dataList;
    private OnItemClickListener onItemClickListener;

    public ScheduleUpdateAdapter(Context context, List<KeyValueListItemEntity> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_schedule_update, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Holder viewHolder = (Holder) holder;
        viewHolder.tvItemTitle.setText(dataList.get(position).getKey());
        viewHolder.tvItemValue.setText(dataList.get(position).getValue());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onItemClickListener){
                    onItemClickListener.onItemClick(dataList.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class Holder extends RecyclerView.ViewHolder{

        private TextView tvItemTitle;
        private TextView tvItemValue;
        private View itemView;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            assignViews(itemView);
        }

        private void assignViews(View view) {
            tvItemTitle = (TextView) view.findViewById(R.id.tv_item_title);
            tvItemValue = (TextView) view.findViewById(R.id.tv_item_number);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(KeyValueListItemEntity entity, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

}
