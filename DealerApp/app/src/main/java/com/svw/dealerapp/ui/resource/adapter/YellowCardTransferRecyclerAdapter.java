package com.svw.dealerapp.ui.resource.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.SMYCTransferSalesEntity;
import com.svw.dealerapp.entity.resource.YCTransferSalesEntity;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by qinshi on 5/18/2017.
 */

public class YellowCardTransferRecyclerAdapter extends BaseRecyclerViewAdapter<SMYCTransferSalesEntity> {

    private OnItemClickListener itemClickListener;
    private SMYCTransferSalesEntity lastSelectEntity;

    public YellowCardTransferRecyclerAdapter(Context context, List<SMYCTransferSalesEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_yellow_card_transfer, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder viewHolder = (Holder) holder;
        final SMYCTransferSalesEntity entity = dataList.get(position);

        if(!TextUtils.isEmpty(dataList.get(position).getDisplayName())) {
            viewHolder.tvSalesName.setText(dataList.get(position).getDisplayName());
        }

        if(entity.isSelect()){
            viewHolder.ivSelectIndicator.setBackgroundResource(R.mipmap.ic_circle_radio_select);
        }else {
            viewHolder.ivSelectIndicator.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.shape_yellow_transfer_unselect_icon_bg));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!entity.isSelect()){
                    viewHolder.ivSelectIndicator.setBackgroundDrawable(context.getResources()
                            .getDrawable(R.drawable.shape_yellow_transfer_unselect_icon_bg));
                    entity.setSelect(true);
                }else {
                    viewHolder.ivSelectIndicator.setBackgroundResource(R.mipmap.ic_circle_radio_select);
                    entity.setSelect(false);
                }
                if(null != lastSelectEntity && entity != lastSelectEntity){
                    lastSelectEntity.setSelect(false);
                }
                if(null != itemClickListener){
                    itemClickListener.onItemClick(v, dataList.get(position), entity.isSelect());
                }

                lastSelectEntity = entity;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class Holder extends RecyclerView.ViewHolder{

        View itemView;
        private ImageView ivSelectIndicator;
        private TextView tvSalesName;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            assignViews(itemView);
        }

        private void assignViews(View view) {
            ivSelectIndicator = (ImageView) view.findViewById(R.id.iv_select_indicator);
            tvSalesName = (TextView) view.findViewById(R.id.tv_sales_name);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, SMYCTransferSalesEntity entity, boolean isSelect);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }
}
