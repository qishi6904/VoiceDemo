package com.svw.dealerapp.ui.resource.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.YCTransferSalesEntity;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.resource.entity.SeriesFilterEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 11/21/2017.
 */

public class SeriesFilterListAdapter extends BaseRecyclerViewAdapter<SeriesFilterEntity> {

    private List<SeriesFilterEntity> selectSeries = new ArrayList<>();
    private SeriesFilterEntity allEntity;

    public SeriesFilterListAdapter(Context context, List<SeriesFilterEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_yellow_card_transfer, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Holder viewHolder = (Holder) holder;
        final SeriesFilterEntity entity = dataList.get(position);

        if(!TextUtils.isEmpty(entity.getSeriesName())) {
            viewHolder.tvSalesName.setText(entity.getSeriesName());
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
                if(entity.isAll()){
                    selectSeries.clear();
                    if(!entity.isSelect()){
                        selectSeries.addAll(dataList);
                        selectSeries.remove(allEntity);
                    }
                    setAllSelectStatus(!entity.isSelect());
                    notifyDataSetChanged();
                }else {
                    if(entity.isSelect()){
                        selectSeries.remove(entity);
                        viewHolder.ivSelectIndicator.setBackgroundDrawable(context.getResources()
                                .getDrawable(R.drawable.shape_yellow_transfer_unselect_icon_bg));
                        entity.setSelect(!entity.isSelect());
                        if(allEntity.isSelect()){
                            allEntity.setSelect(false);
                            notifyDataSetChanged();
                        }
                    }else {
                        selectSeries.add(entity);
                        viewHolder.ivSelectIndicator.setBackgroundResource(R.mipmap.ic_circle_radio_select);
                        entity.setSelect(!entity.isSelect());
                        if(selectSeries.size() == dataList.size() - 1){
                            allEntity.setSelect(true);
                            notifyDataSetChanged();
                        }
                    }
                }
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

    private void setAllSelectStatus(boolean isSelect){
        for(int i = 0; i < dataList.size(); i++){
            dataList.get(i).setSelect(isSelect);
        }
    }

    public List<SeriesFilterEntity> getSelectSeries(){
        return selectSeries;
    }

    public void addSelectSeries(SeriesFilterEntity entity) {
        selectSeries.add(entity);
    }

    public void setAllEntity(SeriesFilterEntity allEntity){
        this.allEntity = allEntity;
    }

    public boolean isSelectAll(){
        return allEntity.isSelect();
    }

    public void clearSelectSeries(){
        for(SeriesFilterEntity entity : selectSeries) {
            entity.setSelect(false);
        }
        allEntity.setSelect(false);
        selectSeries.clear();
    }
}
