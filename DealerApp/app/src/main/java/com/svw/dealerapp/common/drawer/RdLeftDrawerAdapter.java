package com.svw.dealerapp.common.drawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;

import java.util.List;

/**
 * Created by qinshi on 1/11/2018.
 */

public class RdLeftDrawerAdapter extends RecyclerView.Adapter {

    private List<RdLeftDrawerItemEntity> dataList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RdLeftDrawerAdapter(Context context, List<RdLeftDrawerItemEntity> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.rd_item_left_drawer, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        RdLeftDrawerItemEntity entity = dataList.get(position);
        if(entity.isShowDividerSpace()) {
            viewHolder.rlDividerView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.rlDividerView.setVisibility(View.GONE);
        }
        viewHolder.ivItemIcon.setImageResource(entity.getIconId());
        viewHolder.tvItemTag.setText(entity.getTagText());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onItemClickListener) {
                    onItemClickListener.onItemClick(viewHolder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null != dataList ? dataList.size() : 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public ImageView ivItemIcon;
        public TextView tvItemTag;
        public RelativeLayout rlDividerView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            assignViews(itemView);
        }

        private void assignViews(View view) {
            ivItemIcon = (ImageView) view.findViewById(R.id.iv_item_icon);
            tvItemTag = (TextView) view.findViewById(R.id.tv_item_tag);
            rlDividerView = (RelativeLayout) view.findViewById(R.id.rl_divider_space);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

}
