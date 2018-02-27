package com.svw.dealerapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svw.dealerapp.R;

import java.util.List;

/**
 * Created by qinshi on 5/31/2017.
 */

public class BottomListDialogAdapter extends RecyclerView.Adapter {

    private List<String> dataList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public BottomListDialogAdapter(Context context, List<String> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_bottom_list_dialog, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder viewHolder = (Holder) holder;
        viewHolder.tvItemText.setText(dataList.get(position));
        viewHolder.tvItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onItemClickListener){
                    onItemClickListener.onItemClick(viewHolder.tvItemText, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class Holder extends RecyclerView.ViewHolder{

        private TextView tvItemText;

        public Holder(View itemView) {
            super(itemView);
            tvItemText = (TextView) itemView.findViewById(R.id.tv_bottom_list_item);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
}
