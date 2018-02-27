package com.svw.dealerapp.ui.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.mine.ApproveWaitEntity.ApproveWaitInfoEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.dbtools.PrivilegeDBUtils;

import java.util.List;

/**
 * Created by qinshi on 6/3/2017.
 */

public class ApproveWaitAdapter extends BaseRecyclerViewAdapter<ApproveWaitInfoEntity> {

    private OnIconClickListener onIconClickListener;
    private boolean isShowRejectBtn;
    private boolean isShowApproveBtn;
    private boolean isShowSaleName;

    public ApproveWaitAdapter(Context context, List<ApproveWaitInfoEntity> dataList) {
        super(context, dataList);
        isShowSaleName = PrivilegeDBUtils.isCheck(Constants.P_SA_APP_APPROVAL_LIST, Constants.E_APPROVAL_LIST_OWNER_NAME);
        isShowApproveBtn = PrivilegeDBUtils.isCheck(Constants.P_SA_APP_APPROVAL_LIST, Constants.E_APPROVAL_LIST_APPROVAL);
        isShowRejectBtn = PrivilegeDBUtils.isCheck(Constants.P_SA_APP_APPROVAL_LIST, Constants.E_APPROVAL_LIST_REJECT);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_mine_approve_wait, null);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Holder viewHolder = (Holder) holder;
        final ApproveWaitInfoEntity entity = dataList.get(position);
        if(0 == position){
            viewHolder.vUpDivider.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vUpDivider.setVisibility(View.GONE);
        }

        if(isShowRejectBtn){
            viewHolder.llDealOuter.setVisibility(View.VISIBLE);
            viewHolder.ivReject.setVisibility(View.VISIBLE);
        }else {
            viewHolder.ivReject.setVisibility(View.INVISIBLE);
        }

        if(isShowApproveBtn){
            viewHolder.llDealOuter.setVisibility(View.VISIBLE);
            viewHolder.ivSupport.setVisibility(View.VISIBLE);
        }else {
            viewHolder.ivSupport.setVisibility(View.INVISIBLE);
        }

        if(isShowSaleName){
            viewHolder.tvSalesName.setVisibility(View.VISIBLE);
            viewHolder.tvSalesName.setText(context.getResources().getString(R.string.mine_approve_response) +
                    entity.getApplicationOwnerName());
        }else {
            viewHolder.tvSalesName.setVisibility(View.GONE);
        }

        if("18010".equals(entity.getApprovalTypeId())){   //战败
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

        viewHolder.tvUserName.setText(entity.getCustName());
        viewHolder.tvReason.setText(entity.getApplicationDesc());
        viewHolder.tvDetail.setText(entity.getRemark());
        viewHolder.tvDateTime.setText(DateUtils.dateFormat("yyyy.MM.dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getStrCreateTime()));

        viewHolder.ivReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onIconClickListener){
                    onIconClickListener.onRejectApprove(v, entity, position);
                }
            }
        });
        viewHolder.ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onIconClickListener) {
                    onIconClickListener.onSupportApprove(v, entity, position);
                }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onIconClickListener) {
                    onIconClickListener.onItemClick(v, entity, position);
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
        TextView tvSalesName;
        TextView tvReason;
        TextView tvDetail;
        LinearLayout llDealOuter;
        ImageView ivReject;
        ImageView ivSupport;
        ImageView ivApproveStamp;
        ImageView ivReason;
        ImageView ivDetail;
        View itemView;

        public Holder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View view) {
            this.itemView = view;
            vUpDivider = view.findViewById(R.id.v_up_divider);
            ivStatus = (ImageView) view.findViewById(R.id.iv_status);
            tvDateTime = (TextView) view.findViewById(R.id.tv_date_time);
            ivUser = (ImageView) view.findViewById(R.id.iv_user);
            tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            tvSalesName = (TextView) view.findViewById(R.id.tv_sales_name);
            tvReason = (TextView) view.findViewById(R.id.tv_reason);
            tvDetail = (TextView) view.findViewById(R.id.tv_detail);
            llDealOuter = (LinearLayout) view.findViewById(R.id.deal_outer);
            ivReject = (ImageView) view.findViewById(R.id.iv_reject);
            ivSupport = (ImageView) view.findViewById(R.id.iv_support);
            ivApproveStamp = (ImageView) view.findViewById(R.id.iv_approve_stamp);
            ivReason = (ImageView) view.findViewById(R.id.iv_reason);
            ivDetail = (ImageView) view.findViewById(R.id.iv_detail);
        }
    }

    public interface OnIconClickListener{
        void onSupportApprove(View view, ApproveWaitInfoEntity entity, int position);
        void onRejectApprove(View view, ApproveWaitInfoEntity entity, int position);
        void onItemClick(View view, ApproveWaitInfoEntity entity, int position);
    }

    public void setOnIconClickListener(OnIconClickListener listener){
        this.onIconClickListener = listener;
    }

}
