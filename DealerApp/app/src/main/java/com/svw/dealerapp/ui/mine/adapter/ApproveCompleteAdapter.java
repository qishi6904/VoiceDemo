package com.svw.dealerapp.ui.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.mine.ApproveCompleteEntity.ApproveCompleteInfoEntity;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.util.DateUtils;

import java.util.List;

/**
 * Created by qinshi on 6/3/2017.
 */

public class ApproveCompleteAdapter extends BaseRecyclerViewAdapter<ApproveCompleteInfoEntity> {

    private OnIconClickListener onClickListener;

    public ApproveCompleteAdapter(Context context, List<ApproveCompleteInfoEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_mine_approve_complete, null);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Holder viewHolder = (Holder) holder;
        final ApproveCompleteInfoEntity entity = dataList.get(position);
        if(0 == position){
            viewHolder.vUpDivider.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vUpDivider.setVisibility(View.GONE);
        }

        viewHolder.tvDateTime.setText(DateUtils.dateFormat("yyyy.MM.dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getStrApprovalTime()));
        viewHolder.tvDetail.setText(entity.getRemark());
        viewHolder.tvReason.setText(entity.getApplicationDesc());
        viewHolder.tvUserName.setText(entity.getCustName());

        if("1".equals(entity.getApprovalFlag())){
            viewHolder.tvSalesName.setVisibility(View.VISIBLE);
            viewHolder.tvSalesName.setText(context.getResources().getString(R.string.mine_approve_response) +
                    entity.getApplicationOwnerName());
        }else {
            viewHolder.tvSalesName.setVisibility(View.GONE);
        }

        if("18010".equals(entity.getApprovalTypeId())){     //战败
            viewHolder.ivStatus.setVisibility(View.VISIBLE);
            viewHolder.ivStatus.setImageResource(R.mipmap.ic_request_fail);
//            viewHolder.ivDetail.setImageResource(R.mipmap.ic_approve_detail);
//            viewHolder.ivReason.setImageResource(R.mipmap.ic_approve_reason);
        }else if("18020".equals(entity.getApprovalTypeId())){   //休眠
            viewHolder.ivStatus.setVisibility(View.VISIBLE);
            viewHolder.ivStatus.setImageResource(R.mipmap.ic_request_sleep);
//            viewHolder.ivDetail.setImageResource(R.mipmap.ic_approve_detail);
//            viewHolder.ivReason.setImageResource(R.mipmap.ic_approve_reason);
        }else if("18030".equals(entity.getApprovalTypeId())){   //创建订单
            viewHolder.ivStatus.setVisibility(View.VISIBLE);
            viewHolder.ivStatus.setImageResource(R.mipmap.ic_request_order);
//            viewHolder.ivDetail.setImageResource(R.mipmap.ic_approve_delivery_time);
//            viewHolder.ivReason.setImageResource(R.mipmap.ic_approve_order_car_model);
        }else if("18040".equals(entity.getApprovalTypeId())){   //取消订单
            viewHolder.ivStatus.setVisibility(View.VISIBLE);
            viewHolder.ivStatus.setImageResource(R.mipmap.ic_approve_cancel_order);
//            viewHolder.ivDetail.setImageResource(R.mipmap.ic_approve_delivery_time);
//            viewHolder.ivReason.setImageResource(R.mipmap.ic_approve_order_car_model);
        }else {
            viewHolder.ivStatus.setVisibility(View.INVISIBLE);
        }

        if("18520".equals(entity.getApprovalStatusId())){
            viewHolder.ivApproveStamp.setImageResource(R.mipmap.ic_approve_pass);
            viewHolder.tvRejectReason.setVisibility(View.INVISIBLE);
        }else if("18530".equals(entity.getApprovalStatusId())){
            viewHolder.ivApproveStamp.setImageResource(R.mipmap.ic_approve_no_pass);
            viewHolder.tvRejectReason.setVisibility(View.VISIBLE);
            viewHolder.tvRejectReason.setText(context.getResources().getString(R.string.mine_approve_complete_reject_reason) +
                    entity.getApprovalDesc());

        }else {
            viewHolder.ivApproveStamp.setImageResource(0);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onClickListener) {
                    onClickListener.onItemClick(v, entity, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class Holder extends RecyclerView.ViewHolder{

        View vUpDivider;
        ImageView ivStatus;
        TextView tvDateTime;
        ImageView ivUser;
        TextView tvUserName;
        TextView tvReason;
        TextView tvRejectReason;
        TextView tvDetail;
        LinearLayout dealOuter;
        ImageView ivReject;
        ImageView ivSupport;
        ImageView ivApproveStamp;
        TextView tvSalesName;
        ImageView ivReason;
        ImageView ivDetail;
        View itemView;

        public Holder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View view) {
            itemView = view;
            vUpDivider = view.findViewById(R.id.v_up_divider);
            ivStatus = (ImageView) view.findViewById(R.id.iv_status);
            tvDateTime = (TextView) view.findViewById(R.id.tv_date_time);
            ivUser = (ImageView) view.findViewById(R.id.iv_user);
            tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            tvReason = (TextView) view.findViewById(R.id.tv_reason);
            tvDetail = (TextView) view.findViewById(R.id.tv_detail);
            tvRejectReason = (TextView) view.findViewById(R.id.tv_reject_reason);
            dealOuter = (LinearLayout) view.findViewById(R.id.deal_outer);
            ivReject = (ImageView) view.findViewById(R.id.iv_reject);
            ivSupport = (ImageView) view.findViewById(R.id.iv_support);
            ivApproveStamp = (ImageView) view.findViewById(R.id.iv_approve_stamp);
            tvSalesName = (TextView) view.findViewById(R.id.tv_sales_name);
            ivReason = (ImageView) view.findViewById(R.id.iv_reason);
            ivDetail = (ImageView) view.findViewById(R.id.iv_detail);
        }
    }

    public interface OnIconClickListener{
        void onItemClick(View view, ApproveCompleteInfoEntity entity, int position);
    }

    public void setOnIconClickListener(OnIconClickListener listener){
        this.onClickListener = listener;
    }

}
