package com.svw.dealerapp.ui.resource.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.ui.adapter.LinearDragRecyclerViewAdapter;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.ui.widget.DragFrontLinearLayout;
import com.svw.dealerapp.ui.widget.DragLayout;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.UserInfoUtils;
import com.svw.dealerapp.util.dbtools.PrivilegeDBUtils;

import java.util.List;

/**
 * Created by qinshi on 5/5/2017.
 */

@Deprecated
public class YellowCardRecyclerViewAdapter extends LinearDragRecyclerViewAdapter<YellowCardEntity.YellowCardInfoEntity> {

    private final static int NORMAL_STATUS = 10001;
    private final static int SLEEP_STATUS = 10002;
    private final static int FAIL_STATUS = 10003;

    private String currentUserId;
    private OnIconClickListener iconClickListener;
    private boolean noLeftDrag;

    private boolean isFroSearch = false;

    private List<YellowCardEntity.YellowCardInfoEntity> showMonthEntities;
    private boolean isShowSalesName;

    public YellowCardRecyclerViewAdapter(Context context, List<YellowCardEntity.YellowCardInfoEntity> dataList,
                                         List<YellowCardEntity.YellowCardInfoEntity> showMonthEntities, boolean noLeftDrag) {
        super(context, dataList);
        currentUserId = UserInfoUtils.getUserId();
        this.noLeftDrag = noLeftDrag;
        this.showMonthEntities = showMonthEntities;
		isShowSalesName = PrivilegeDBUtils.isCheck(Constants.P_SA_APP_CUSTOMER_LIST, Constants.E_CUSTOMER_LIST_OWNER_NAME);
    }

    public YellowCardRecyclerViewAdapter(Context context, List<YellowCardEntity.YellowCardInfoEntity> dataList,  boolean noLeftDrag, boolean isFroSearch) {
        this(context, dataList, null, noLeftDrag);
        this.isFroSearch = isFroSearch;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_resource_yellow_card, null);
        final YellowRecyclerViewHolder holder = new YellowRecyclerViewHolder(view);
        holder.tvResponsible.setVisibility(View.GONE);

        setHolderListener(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        YellowRecyclerViewHolder yellowHolder = (YellowRecyclerViewHolder) holder;
        YellowCardEntity.YellowCardInfoEntity infoEntity = dataList.get(position);

        yellowHolder.tvCustomerName.setText(infoEntity.getCustName());
//        yellowHolder.tvCarType.setText(infoEntity.getCarModelId() + "　" +
//                infoEntity.getOutsideColorNameCn() + "　" + infoEntity.getInsideColorNameCn());
        if(TextUtils.isEmpty(infoEntity.getSeriesName())){
            yellowHolder.tvCarType.setText(context.getResources().getString(R.string.yellow_car_series_empty));
        }else {
            yellowHolder.tvCarType.setText(infoEntity.getSeriesName());
        }
        yellowHolder.tvSource.setText(infoEntity.getSrcTypeName());
//        yellowHolder.tvResponsible.setText(infoEntity.getOppOwnerName());
        if(TextUtils.isEmpty(infoEntity.getAppmTypeName())){
            yellowHolder.tvAction.setText(context.getResources().getString(R.string.task_tab_schedule_item_empty));
        }else {
            yellowHolder.tvAction.setText(infoEntity.getAppmTypeName());
        }
        yellowHolder.tvDateTime.setText(DateUtils.dateFormat("MM.dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", infoEntity.getUpdateTime()));

        if (position == 0) {
            yellowHolder.vUpLine.setVisibility(View.VISIBLE);
        } else {
            yellowHolder.vUpLine.setVisibility(View.GONE);
        }

        //设置是否重点客户
        if ("0".equals(infoEntity.getIsKeyuser())) {
            yellowHolder.vVipIndicator.setVisibility(View.VISIBLE);
            yellowHolder.ivBackViewOne.setImageResource(R.mipmap.ic_yellow_card_item_vip_selected);
        } else {
            yellowHolder.vVipIndicator.setVisibility(View.INVISIBLE);
            yellowHolder.ivBackViewOne.setImageResource(R.mipmap.ic_yellow_card_item_vip_unselected);
        }

        if ("11540".equals(infoEntity.getOppStatusId())) {    // 休眠状态
            setItemStatus(SLEEP_STATUS, yellowHolder, "0".equals(infoEntity.getIsKeyuser()), infoEntity.getOppOwner());
            yellowHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_sleep);
        } else if ("11530".equals(infoEntity.getOppStatusId())) {      // 战败状态
            setItemStatus(FAIL_STATUS, yellowHolder, "0".equals(infoEntity.getIsKeyuser()), infoEntity.getOppOwner());
            yellowHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_sleep);
        } else {     // 其他状态
            setItemStatus(NORMAL_STATUS, yellowHolder, "0".equals(infoEntity.getIsKeyuser()), infoEntity.getOppOwner());

            //设置用户级别
            if (!TextUtils.isEmpty(infoEntity.getOppLevel())) {
                if ("12020".equals(infoEntity.getOppLevel())) {
                    yellowHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_a);
                } else if ("12030".equals(infoEntity.getOppLevel())) {
                    yellowHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_b);
                } else if ("12040".equals(infoEntity.getOppLevel())) {
                    yellowHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_c);
                } else if ("12050".equals(infoEntity.getOppLevel())) {
                    yellowHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
                } else if ("12010".equals(infoEntity.getOppLevel())) {
                    yellowHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_h);
                } else {
                    yellowHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
                }
            } else {
                yellowHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
            }
        }

        //设置来源
        if ("11010".equals(infoEntity.getSrcTypeId())) {          //到店
            yellowHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_shop));
        } else if ("11020".equals(infoEntity.getSrcTypeId())) {    //电商
            yellowHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_e_commerce));
        } else if ("11030".equals(infoEntity.getSrcTypeId())) {    //线索
            yellowHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_leads));
        } else if ("11040".equals(infoEntity.getSrcTypeId())) {    //电话
            yellowHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_phone));
        } else if ("11050".equals(infoEntity.getSrcTypeId())) {    //外拓
            yellowHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_activities));
        } else if ("11060".equals(infoEntity.getSrcTypeId())) {    //朋友推荐
            yellowHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_friend));
        } else if ("11099".equals(infoEntity.getSrcTypeId())) {    //其他
            yellowHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_other));
        } else {                                                 //默认
            yellowHolder.tvSource.setBackgroundColor(Color.TRANSPARENT);
        }

        if(!isFroSearch) {
            //根据权限判断是否显示销售名字
            if (isShowSalesName) {
                yellowHolder.tvSalesName.setVisibility(View.VISIBLE);
                yellowHolder.tvSalesName.setText(infoEntity.getOppOwnerName());
            } else {
                yellowHolder.tvSalesName.setVisibility(View.GONE);
            }
        }else {
            yellowHolder.tvSalesName.setVisibility(View.VISIBLE);
            yellowHolder.tvSalesName.setText(infoEntity.getOppOwnerName());
        }

        if(null != showMonthEntities && showMonthEntities.contains(infoEntity)) {
            yellowHolder.tvMonthTag.setVisibility(View.VISIBLE);
            String yearMonth = DateUtils.dateFormat("MM月", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                    infoEntity.getUpdateTime());
            yellowHolder.tvMonthTag.setText(yearMonth);
        }else {
            yellowHolder.tvMonthTag.setVisibility(View.GONE);
        }

        setListener(yellowHolder, infoEntity, position);
    }

    /**
     * 根据不同的状态设置左滑出现的按钮数量、宽度、文字、图标
     *
     * @param status
     * @param holder
     * @param isKeyUser
     */
    private void setItemStatus(int status, YellowRecyclerViewHolder holder, boolean isKeyUser, String oppOwner) {

        LinearLayout.LayoutParams backViewParams = new LinearLayout.LayoutParams(holder.llBackView.getLayoutParams());

        //如果当前用户不是该黄卡的owner，不能左滑 || 从客源跟进查询，不能左划
        if ((noLeftDrag || TextUtils.isEmpty(oppOwner) || !oppOwner.equals(currentUserId))) {
            holder.dragLayout.setRange(0);
            backViewParams.width = 0;
            holder.llBackView.setLayoutParams(backViewParams);
            return;
        }

        LinearLayout.LayoutParams backViewItemOneParams = new LinearLayout.LayoutParams(holder.llBackViewOne.getLayoutParams());
        switch (status) {
            case SLEEP_STATUS:
            case FAIL_STATUS:
                backViewParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_one_item_back_layout_width);
                backViewItemOneParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_back_view_one_item_width);
                holder.llBackViewOne.setBackgroundColor(context.getResources().getColor(R.color.yellow_item_active_bg));
                holder.ivBackViewOne.setImageResource(R.mipmap.ic_yellow_card_active);
                holder.tvBackViewOne.setText(context.getResources().getText(R.string.yellow_back_view_active));
                break;
            case NORMAL_STATUS:
                backViewParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_three_item_back_layout_width);
                backViewItemOneParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_back_view_three_item_width);
                holder.llBackViewOne.setBackgroundColor(context.getResources().getColor(R.color.traffic_item_back_view_one_bg));
                holder.tvBackViewOne.setText(context.getResources().getText(R.string.yellow_back_view_vip));
                if (isKeyUser) {
                    holder.ivBackViewOne.setImageResource(R.mipmap.ic_yellow_card_item_vip_selected);
                } else {
                    holder.ivBackViewOne.setImageResource(R.mipmap.ic_yellow_card_item_vip_unselected);
                }
                break;
        }
        holder.dragLayout.setRange(backViewParams.width);
        holder.llBackView.setLayoutParams(backViewParams);
        holder.llBackViewOne.setLayoutParams(backViewItemOneParams);
    }

    private void setListener(final YellowRecyclerViewHolder yellowHolder, final YellowCardEntity.YellowCardInfoEntity infoEntity, final int position) {

        //如果当前用户不是该黄卡的owner不能点击打电话按钮
        if ((TextUtils.isEmpty(infoEntity.getOppOwner()) || !infoEntity.getOppOwner().equals(currentUserId))) {
            yellowHolder.ivCallPhone.setOnClickListener(null);
            yellowHolder.ivCallPhone.setImageResource(R.mipmap.ic_can_not_call);
        } else {
            yellowHolder.ivCallPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != iconClickListener) {
                        iconClickListener.onPhoneClick(v, infoEntity);
                    }
                }
            });
            yellowHolder.ivCallPhone.setImageResource(R.mipmap.ic_yellow_card_item_call);
        }
        yellowHolder.llBackViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iconClickListener) {
                    iconClickListener.onVipClick(v, infoEntity, position);
                }
            }
        });
        yellowHolder.llBackViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iconClickListener) {
                    iconClickListener.onFollowUpClick(v, infoEntity, position);
                }
                yellowHolder.dragLayout.closeDelay(600);
            }
        });
        yellowHolder.llBackViewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iconClickListener) {
                    iconClickListener.onTransferClick(v, infoEntity, position);
                }
            }
        });
        yellowHolder.llFrontView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iconClickListener) {
                    TalkingDataUtils.onEvent(context, "点击进入潜客详情", "资源潜客");
                    iconClickListener.onFollowUpClick(v, infoEntity, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class YellowRecyclerViewHolder extends LinearDragRecyclerViewAdapter.DragLinearRecyclerViewHolder {

        private View vVipIndicator;
        private TextView tvCustomerName;
        private ImageView ivCustomerRank;
        private TextView tvSource;
        private ImageView ivCallPhone;
        private TextView tvCarType;
        private TextView tvAction;
        private TextView tvResponsible;
        private TextView tvDateTime;
        private TextView tvSalesName;
        private LinearLayout llBackViewOne;
        private ImageView ivBackViewOne;
        private LinearLayout llBackViewTwo;
        private LinearLayout llBackViewThree;
        private View vUpLine;
        private LinearLayout llFrontView;
        private LinearLayout llBackView;
        private TextView tvBackViewOne;
        private TextView tvMonthTag;

        public YellowRecyclerViewHolder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View view) {
            dragLayout = (DragLayout) view.findViewById(R.id.drag_layout);
            vVipIndicator = view.findViewById(R.id.v_vip_indicator);
            frontLayout = (DragFrontLinearLayout) view.findViewById(R.id.ll_front_view);
            tvCustomerName = (TextView) view.findViewById(R.id.tv_customer_name);
            ivCustomerRank = (ImageView) view.findViewById(R.id.iv_customer_rank);
            tvSource = (TextView) view.findViewById(R.id.tv_source);
            ivCallPhone = (ImageView) view.findViewById(R.id.iv_call_phone);
            tvCarType = (TextView) view.findViewById(R.id.tv_car_type);
            tvAction = (TextView) view.findViewById(R.id.tv_action);
            tvResponsible = (TextView) view.findViewById(R.id.tv_delay_day);
            tvDateTime = (TextView) view.findViewById(R.id.tv_date_time);
            tvSalesName = (TextView) view.findViewById(R.id.tv_sales_name);
            llBackViewOne = (LinearLayout) view.findViewById(R.id.ll_back_view_one);
            ivBackViewOne = (ImageView) view.findViewById(R.id.iv_back_view_one);
            llBackViewTwo = (LinearLayout) view.findViewById(R.id.ll_back_view_two);
            llBackViewThree = (LinearLayout) view.findViewById(R.id.ll_back_view_three);
            vUpLine = view.findViewById(R.id.v_divider_up);
            llFrontView = (LinearLayout) view.findViewById(R.id.ll_front_view);
            llBackView = (LinearLayout) itemView.findViewById(R.id.ll_back_view);
            tvBackViewOne = (TextView) itemView.findViewById(R.id.tv_back_view_one);
            tvMonthTag = (TextView) itemView.findViewById(R.id.tv_month_tag);
        }
    }

    public interface OnIconClickListener {

        void onPhoneClick(View view, YellowCardEntity.YellowCardInfoEntity entity);

        void onVipClick(View view, YellowCardEntity.YellowCardInfoEntity entity, int position);

        void onFollowUpClick(View view, YellowCardEntity.YellowCardInfoEntity entity, int position);

        void onTransferClick(View view, YellowCardEntity.YellowCardInfoEntity entity, int position);
    }

    public void setOnIconClickListener(OnIconClickListener onIconClickListener) {
        this.iconClickListener = onIconClickListener;
    }
}
