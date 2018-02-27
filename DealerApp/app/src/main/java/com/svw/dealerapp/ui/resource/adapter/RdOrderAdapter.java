package com.svw.dealerapp.ui.resource.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.OrderCustomerEntity;
import com.svw.dealerapp.ui.adapter.LinearDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.adapter.RelativeDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.widget.DragFrontLinearLayout;
import com.svw.dealerapp.ui.widget.DragFrontRelativeLayout;
import com.svw.dealerapp.ui.widget.DragLayout;
import com.svw.dealerapp.ui.widget.RdResourceItemRightBtn;
import com.svw.dealerapp.ui.widget.RdResourceItemView;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.util.List;

/**
 * Created by qinshi on 1/15/2018.
 */

public class RdOrderAdapter extends RelativeDragRecyclerViewAdapter<OrderCustomerEntity.OrderCustomerInfoEntity> {

    private List<OrderCustomerEntity.OrderCustomerInfoEntity> showMonthEntities;
    private String currentUserId;

    public RdOrderAdapter(Context context, List dataList,
                          List<OrderCustomerEntity.OrderCustomerInfoEntity> showMonthEntities) {
        super(context, dataList);
        currentUserId = UserInfoUtils.getUserId();
        this.showMonthEntities = showMonthEntities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.rd_item_resource_order, null);
        Holder holder = new Holder(view);
        holder.rivItemView.showVip(false);
        holder.rivItemView.showFailReason(false);
        holder.rivItemView.showCustomerRank(false);
        holder.rivItemView.showCustomerSrc(false);
        holder.rivItemView.showApproveStatus(false);
        holder.rivItemView.setAction(context.getResources().getString(R.string.order_customer_status_order));
        holder.rirBtnOne.setIconById(R.mipmap.rd_ic_resource_follow_up);
        holder.rirBtnOne.setTip(context.getResources().getString(R.string.traffic_back_view_follow));

        setHolderListener(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Holder viewHolder = (Holder) holder;
        final OrderCustomerEntity.OrderCustomerInfoEntity entity = dataList.get(position);

        viewHolder.rivItemView.setCustomerName(entity.getCustName());
        viewHolder.rivItemView.setMobileNumber(entity.getCustMobile());
        viewHolder.rivItemView.setSeriesType(entity.getSeriesName());

        viewHolder.rivItemView.setDay(DateUtils.dateFormat("dd", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getCreateTime()));
        viewHolder.rivItemView.setTime(DateUtils.dateFormat("HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getCreateTime()));

        //月份设置
        if(null != showMonthEntities && showMonthEntities.contains(entity)) {
            //如果是第一个item不显示月份条
            if(position == 0){
                viewHolder.tvMonthTag.setVisibility(View.GONE);
            }else {
                viewHolder.tvMonthTag.setVisibility(View.VISIBLE);
            }
            String yearMonth = DateUtils.dateFormat("MM月", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                    entity.getUpdateTime());
            viewHolder.tvMonthTag.setText(yearMonth);
            viewHolder.vDivider.setVisibility(View.GONE);
        }else {
            viewHolder.vDivider.setVisibility(View.VISIBLE);
            if(position == 0){
                viewHolder.tvMonthTag.setVisibility(View.INVISIBLE);
            }else {
                viewHolder.tvMonthTag.setVisibility(View.GONE);
            }
        }

        //是否是owner
        if(null == entity.getSalesConsultant() || !entity.getSalesConsultant().equals(currentUserId)) {
            viewHolder.rivItemView.setOwner(false, entity.getSalesConsultantName());
        }else {
            viewHolder.rivItemView.setOwner(true, null);
        }

        //是否是OTD
        if("0".equals(entity.getIsOtdOrder())){
            viewHolder.rivItemView.showOTD(true);
        }else {
            viewHolder.rivItemView.showOTD(false);
        }

        viewHolder.rirBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomerDetailActivity.class);
                intent.putExtra("oppId", entity.getOppId());
                intent.putExtra("leadsId", entity.getLeadsId());
                context.startActivity(intent);
                viewHolder.dragLayout.closeDelay(600);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class Holder extends DragRelativeRecyclerViewHolder {

        private View itemView;
        private TextView tvMonthTag;
        private View vDivider;
        private RdResourceItemView rivItemView;
        public RdResourceItemRightBtn rirBtnOne;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            assignViews(itemView);
        }

        private void assignViews(View view) {
            tvMonthTag = (TextView) view.findViewById(R.id.tv_month_tag);
            vDivider = view.findViewById(R.id.v_divider);
            dragLayout = (DragLayout) view.findViewById(R.id.drag_layout);
            frontLayout = (DragFrontRelativeLayout) view.findViewById(R.id.ll_front_view);
            rivItemView = (RdResourceItemView) view.findViewById(R.id.riv_item_view);
            rirBtnOne = (RdResourceItemRightBtn) view.findViewById(R.id.rir_btn_one);
        }

    }
}
