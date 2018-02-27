package com.svw.dealerapp.ui.report.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.report.ReportHomeEntity;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by qinshi on 8/29/2017.
 */

public class ReportItemAdapter extends BaseRecyclerViewAdapter<ReportHomeEntity.ReportHomeItemEntity> {

    private OnItemClickListener onItemClickListener;

    public ReportItemAdapter(Context context, List<ReportHomeEntity.ReportHomeItemEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_report_home, null);

        return new ReportHomeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ReportHomeHolder reportHomeHolder = (ReportHomeHolder) holder;
        final ReportHomeEntity.ReportHomeItemEntity itemEntity = dataList.get(position);

        if(position < 2){
            reportHomeHolder.vLineTop.setVisibility(View.GONE);
        }else {
            reportHomeHolder.vLineTop.setVisibility(View.VISIBLE);
        }

        if(position % 2 == 0){
            reportHomeHolder.vLineLeft.setVisibility(View.VISIBLE);
        }else {
            reportHomeHolder.vLineLeft.setVisibility(View.GONE);
        }

        reportHomeHolder.tvName.setText(itemEntity.getName());
        reportHomeHolder.tvValue.setText(itemEntity.getValue());
        if(!TextUtils.isEmpty(itemEntity.getUrl())){
            reportHomeHolder.tvName.setTextColor(context.getResources().getColor(R.color.resource_main_text));
            reportHomeHolder.tvValue.setTextColor(context.getResources().getColor(R.color.resource_main_text));
        }else{
            reportHomeHolder.tvName.setTextColor(context.getResources().getColor(R.color.resource_assist_text));
            reportHomeHolder.tvValue.setTextColor(context.getResources().getColor(R.color.resource_assist_text));
        }

        reportHomeHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onItemClickListener){
                    onItemClickListener.onItemClick(reportHomeHolder.itemView, itemEntity, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class ReportHomeHolder extends RecyclerView.ViewHolder{

        private View itemView;
        private TextView tvValue;
        private TextView tvName;
        private View vLineTop;
        private View vLineLeft;

        public ReportHomeHolder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View itemView) {
            this.itemView = itemView;
            tvValue = (TextView) itemView.findViewById(R.id.tv_value);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            vLineTop = itemView.findViewById(R.id.v_line_top);
            vLineLeft = itemView.findViewById(R.id.v_line_left);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, ReportHomeEntity.ReportHomeItemEntity entity, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
