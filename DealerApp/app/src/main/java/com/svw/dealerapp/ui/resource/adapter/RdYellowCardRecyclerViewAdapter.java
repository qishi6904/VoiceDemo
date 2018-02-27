package com.svw.dealerapp.ui.resource.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.ui.adapter.LinearDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.adapter.RelativeDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.widget.DragFrontLinearLayout;
import com.svw.dealerapp.ui.widget.DragFrontRelativeLayout;
import com.svw.dealerapp.ui.widget.DragLayout;
import com.svw.dealerapp.ui.widget.RdResourceItemRightBtn;
import com.svw.dealerapp.ui.widget.RdResourceItemView;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.util.List;

/**
 * Created by lijinkui on 2018/1/13.
 */

public class RdYellowCardRecyclerViewAdapter extends RelativeDragRecyclerViewAdapter<YellowCardEntity.YellowCardInfoEntity> {
    private List<YellowCardEntity.YellowCardInfoEntity> showMonthEntities;
    private String currentUserId;
    private boolean noLeftDrag;
    private OnItemClickListener onItemClickListener;

    public RdYellowCardRecyclerViewAdapter(Context context, List dataList,
                                           List<YellowCardEntity.YellowCardInfoEntity> showMonthEntities, boolean noLeftDrag) {
        super(context, dataList);

        currentUserId = UserInfoUtils.getUserId();
        this.showMonthEntities = showMonthEntities;
        this.noLeftDrag = noLeftDrag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.rd_item_resource_search, null);
        Holder holder = new Holder(view);

        setHolderListener(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder viewHolder = (Holder) holder;
        final YellowCardEntity.YellowCardInfoEntity entity = dataList.get(position);

        viewHolder.rirBtnOne.setIconById(R.mipmap.rd_ic_resource_follow_up);
        viewHolder.rirBtnOne.setTip(context.getResources().getString(R.string.traffic_back_view_follow));
        viewHolder.rivItemView.setCustomerName(entity.getCustName());
        viewHolder.rivItemView.setMobileNumber(entity.getCustMobile());
        viewHolder.rivItemView.setCustomerRank(entity.getOppLevel());
        viewHolder.rivItemView.setCustomerSrc(entity.getSrcTypeName());

        //需要根据后台返回场景
        viewHolder.rivItemView.showFailReason(false);
        viewHolder.rivItemView.showOTD(false);
        viewHolder.rivItemView.setDate("12.12");
        viewHolder.rivItemView.setIsDrive(true);
        viewHolder.rivItemView.setDay("15");
        viewHolder.rivItemView.setTime("10:30");
        viewHolder.rivItemView.setAction("建卡");

        if (TextUtils.isEmpty(entity.getSeriesName())) {
            viewHolder.rivItemView.setSeriesType(context.getResources().getString(R.string.yellow_car_series_empty));
        } else {
            viewHolder.rivItemView.setSeriesType(entity.getSeriesName());
        }

        //设置是否重点客户
        if ("0".equals(entity.getIsKeyuser())) {
            viewHolder.rivItemView.showVip(true);
        } else {
            viewHolder.rivItemView.showVip(false);
        }

        if (null != showMonthEntities && showMonthEntities.contains(entity)) {
            //如果是第一个item不显示月份条
            if (position == 0) {
                viewHolder.tvMonthTag.setVisibility(View.GONE);
            } else {
                viewHolder.tvMonthTag.setVisibility(View.VISIBLE);
            }
            String yearMonth = DateUtils.dateFormat("MM月", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                    entity.getUpdateTime());
            viewHolder.tvMonthTag.setText(yearMonth);
            viewHolder.vDivider.setVisibility(View.GONE);
        } else {
            viewHolder.vDivider.setVisibility(View.VISIBLE);
            if (position == 0) {
                viewHolder.tvMonthTag.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.tvMonthTag.setVisibility(View.GONE);
            }
        }

        if (null == entity.getOppOwner() || !entity.getOppOwner().equals(currentUserId)) {
            viewHolder.rivItemView.setOwner(false, entity.getOppOwnerName());
        } else {
            viewHolder.rivItemView.setOwner(true, null);
        }

        setLeftDrag(viewHolder, entity.getOppOwner());

        viewHolder.rirBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TalkingDataUtils.onEvent(context, "左划跟进", "资源潜客");
                Intent intent = new Intent(context, CustomerDetailActivity.class);
                intent.putExtra("oppId", entity.getOppId());
                intent.putExtra("leadsId", entity.getLeadsId());
                context.startActivity(intent);
                viewHolder.dragLayout.closeDelay(600);
            }
        });

        viewHolder.frontLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(dataList.get(position), position);
                }
            }
        });
    }

    /**
     * 控制左划
     *
     * @param holder
     * @param oppOwner
     */
    private void setLeftDrag(Holder holder, String oppOwner) {
        LinearLayout.LayoutParams backViewParams = new LinearLayout.LayoutParams(holder.llBtnOuter.getLayoutParams());
        //如果当前用户不是该黄卡的owner，不能左滑 || 从客源跟进查询，不能左划
        if ((noLeftDrag || TextUtils.isEmpty(oppOwner) || !oppOwner.equals(currentUserId))) {
            holder.dragLayout.setRange(0);
            backViewParams.width = 0;
            holder.llBtnOuter.setLayoutParams(backViewParams);
            return;
        }
        backViewParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_one_item_back_layout_width);
        holder.dragLayout.setRange(backViewParams.width);
        holder.llBtnOuter.setLayoutParams(backViewParams);
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(YellowCardEntity.YellowCardInfoEntity entity, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private class Holder extends DragRelativeRecyclerViewHolder {

        public TextView tvMonthTag;
        public View vDivider;
        public RdResourceItemView rivItemView;
        public LinearLayout llBtnOuter;
        public RdResourceItemRightBtn rirBtnOne;
        public View itemView;

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
            llBtnOuter = (LinearLayout) view.findViewById(R.id.ll_btn_outer);
            rirBtnOne = (RdResourceItemRightBtn) view.findViewById(R.id.rir_btn_one);
        }
    }
}
