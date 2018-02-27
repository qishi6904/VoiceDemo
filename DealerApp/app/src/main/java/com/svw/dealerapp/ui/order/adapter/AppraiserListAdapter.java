package com.svw.dealerapp.ui.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.order.AppraiserEntity;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by qinshi on 11/22/2017.
 */

public class AppraiserListAdapter extends BaseRecyclerViewAdapter<AppraiserEntity.AppraiserInfoEntity> {

    private Holder selectHolder;
    private AppraiserEntity.AppraiserInfoEntity selectEntity;

    public AppraiserListAdapter(Context context, List<AppraiserEntity.AppraiserInfoEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_yellow_card_transfer, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Holder viewHolder = (Holder) holder;
        final AppraiserEntity.AppraiserInfoEntity entity = dataList.get(position);

        viewHolder.tvAppraiserName.setText(entity.getAppraiserName());

        if(entity.isSelect()){
            viewHolder.ivSelectIndicator.setBackgroundResource(R.mipmap.ic_circle_radio_select);
        }else {
            viewHolder.ivSelectIndicator.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.shape_yellow_transfer_unselect_icon_bg));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entity.isSelect()){
                    viewHolder.ivSelectIndicator.setBackgroundDrawable(context.getResources()
                            .getDrawable(R.drawable.shape_yellow_transfer_unselect_icon_bg));
                    if(entity == selectEntity){
                        selectEntity = null;
                        selectHolder = null;
                    }
                }else {
                    viewHolder.ivSelectIndicator.setBackgroundResource(R.mipmap.ic_circle_radio_select);
                    if(null != selectHolder){
                        selectHolder.ivSelectIndicator.setBackgroundDrawable(context.getResources()
                                .getDrawable(R.drawable.shape_yellow_transfer_unselect_icon_bg));
                    }
                    if(null != selectEntity) {
                        selectEntity.setSelect(false);
                    }
                    selectEntity = entity;
                    selectHolder = viewHolder;
                }
                entity.setSelect(!entity.isSelect());
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
        private TextView tvAppraiserName;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            assignViews(itemView);
        }

        private void assignViews(View view) {
            ivSelectIndicator = (ImageView) view.findViewById(R.id.iv_select_indicator);
            tvAppraiserName = (TextView) view.findViewById(R.id.tv_sales_name);
        }
    }

    public AppraiserEntity.AppraiserInfoEntity getAppraiser(){
        return selectEntity;
    }
}
