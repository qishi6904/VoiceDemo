package com.svw.dealerapp.ui.resource.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.FailedCustomerEntity;
import com.svw.dealerapp.ui.adapter.LinearDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.adapter.RelativeDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.widget.DragFrontRelativeLayout;
import com.svw.dealerapp.ui.widget.DragLayout;
import com.svw.dealerapp.ui.widget.RdResourceItemRightBtn;
import com.svw.dealerapp.ui.widget.RdResourceItemView;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.UserInfoUtils;

import java.util.List;

/**
 * Created by qinshi on 1/15/2018.
 */

public class RdFailAdapter extends RelativeDragRecyclerViewAdapter<FailedCustomerEntity.FailedCustomerInfoEntity> {

    private List<FailedCustomerEntity.FailedCustomerInfoEntity> showMonthEntities;
    private String currentUserId;
    private OnBtnClickListener onBtnClickListener;

    public RdFailAdapter(Context context, List dataList,
                         List<FailedCustomerEntity.FailedCustomerInfoEntity> showMonthEntities) {
        super(context, dataList);
        currentUserId = UserInfoUtils.getUserId();
        this.showMonthEntities = showMonthEntities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.rd_item_resource_fail, null);
        Holder holder = new Holder(view);
        holder.rivItemView.showVip(false);
        holder.rivItemView.showFailReason(true);
        holder.rivItemView.showApproveStatus(false);
        holder.rivItemView.showCustomerRank(false);
        holder.rivItemView.showCustomerSrc(false);
        holder.rivItemView.setIsDrive(false);
        holder.rivItemView.showRightDate(false);
        holder.rivItemView.setAction(context.getResources().getString(R.string.resource_deal_cus_failed_action));
        holder.rirBtnOne.setIconById(R.mipmap.rd_ic_resource_follow_up);
        holder.rirBtnOne.setTip(context.getResources().getString(R.string.yellow_back_view_active));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        holder.rivItemView.setCarTypeLayoutParams(params);
        holder.rivItemView.setCustomerNameMaxWidth(DensityUtil.dp2px(context, 160));

        setHolderListener(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder viewHolder = (Holder) holder;
        final FailedCustomerEntity.FailedCustomerInfoEntity entity = dataList.get(position);

        viewHolder.rivItemView.setCustomerName(entity.getCustName());
        viewHolder.rivItemView.setMobileNumber(entity.getCustMobile());
        viewHolder.rivItemView.setSeriesType(entity.getSeriesName());
        viewHolder.rivItemView.setFailReason(entity.getFailureDesc());

        viewHolder.rivItemView.setDay(DateUtils.dateFormat("dd", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getUpdateTime()));
        viewHolder.rivItemView.setTime(DateUtils.dateFormat("HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getUpdateTime()));

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
        if(null == entity.getOppOwner() || !entity.getOppOwner().equals(currentUserId)) {
            viewHolder.rivItemView.setOwner(false, entity.getOppOwnerName());
        }else {
            viewHolder.rivItemView.setOwner(true, null);
        }

        viewHolder.rirBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onBtnClickListener) {
                    onBtnClickListener.onBtnOneClick(v, entity, position);
                }
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
    public interface OnBtnClickListener {
        void onBtnOneClick(View v, FailedCustomerEntity.FailedCustomerInfoEntity entity, int position);
    }

    public void setOnBtnClickListener(OnBtnClickListener listener) {
        this.onBtnClickListener = listener;
    }

}
