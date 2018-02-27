package com.svw.dealerapp.ui.resource.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.resource.entity.CannotCreateReasonItemEntity;

import java.util.List;

/**
 * Created by qinshi on 5/15/2017.
 */

public class CanNotCreateRecyclerAdapter extends RecyclerView.Adapter {

    private final Context context;
    private List<CannotCreateReasonItemEntity> dataList;
    private Holder lastHolder;
    private OnItemClickListener itemClickListener;

    public CanNotCreateRecyclerAdapter(Context context, List<CannotCreateReasonItemEntity> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_dailog_cannot_create, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Holder viewHolder = (Holder) holder;
        final CannotCreateReasonItemEntity entity = dataList.get(position);
        viewHolder.tvInfo.setText(entity.getInfo());
        viewHolder.tvDetail.setText(entity.getDetail());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != lastHolder){
                    if(lastHolder.itemView == v){
                        return;
                    }

                    lastHolder.ivNoInfoSelectIndicator.setImageResource(0);
                    lastHolder.tvDetail.setTextColor(context.getResources().getColor(R.color.resource_assist_text));
                    lastHolder.tvInfo.setTextColor(context.getResources().getColor(R.color.resource_assist_text));
                }

                viewHolder.ivNoInfoSelectIndicator.setImageResource(R.mipmap.ic_radio_select);
                viewHolder.tvDetail.setTextColor(context.getResources().getColor(R.color.resource_main_text));
                viewHolder.tvInfo.setTextColor(context.getResources().getColor(R.color.resource_main_text));

                if(null != itemClickListener){
                    itemClickListener.onItemClick(v, entity);
                }

                lastHolder = viewHolder;
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        private View itemView;
        private ImageView ivNoInfoSelectIndicator;
        private TextView tvInfo;
        private TextView tvDetail;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            assignViews(itemView);
        }

        private void assignViews(View view) {
            ivNoInfoSelectIndicator = (ImageView) view.findViewById(R.id.iv_select_indicator);
            tvInfo = (TextView) view.findViewById(R.id.tv_info);
            tvDetail = (TextView) view.findViewById(R.id.tv_detail);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View v, CannotCreateReasonItemEntity entity);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
