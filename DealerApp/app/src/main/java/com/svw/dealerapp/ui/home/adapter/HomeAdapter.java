package com.svw.dealerapp.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.home.HomeEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.TalkingDataUtils;

import java.util.List;

/**
 * Created by qinshi on 6/2/2017.
 */
@Deprecated
public class HomeAdapter extends BaseRecyclerViewAdapter<HomeEntity.HomeInfoEntity>{

    private OnBackViewClickListener onBackViewClickListener;
    private Holder lastEditHolder;

    public HomeAdapter(Context context, List<HomeEntity.HomeInfoEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_home_schedule_wait, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder viewHolder = (Holder) holder;
        HomeEntity.HomeInfoEntity entity = dataList.get(position);

        if("0".equals(entity.getIsKeyuser())){
            viewHolder.vVipIndicator.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vVipIndicator.setVisibility(View.INVISIBLE);
        }

        if("17010".equals(entity.getAppmTypeId())){  //邀约到店
            viewHolder.ivAction.setImageResource(R.mipmap.appointment_invitation);
        }else if("17020".equals(entity.getAppmTypeId())) {  //试乘试驾
            viewHolder.ivAction.setImageResource(R.mipmap.appointment_trial);
        }else if("17030".equals(entity.getAppmTypeId())) {  //订单
            viewHolder.ivAction.setImageResource(R.mipmap.appintment_order);
        }else if("17040".equals(entity.getAppmTypeId())) {  //交车
            viewHolder.ivAction.setImageResource(R.mipmap.appointment_car);
        }else {
            viewHolder.ivAction.setImageResource(0);
        }

        // 设置状态icon
        if("17510".equals(entity.getAppmStatusId())){   // 未完成
            viewHolder.ivScheduleStatus.setVisibility(View.INVISIBLE);
            viewHolder.vMaskView.setVisibility(View.INVISIBLE);
            viewHolder.ivScheduleStatus.setImageResource(0);
            viewHolder.vMaskView.setVisibility(View.INVISIBLE);
            viewHolder.llCancel.setVisibility(View.VISIBLE);
            viewHolder.llUpdate.setVisibility(View.VISIBLE);
        }else if("17520".equals(entity.getAppmStatusId())){ // 已完成
            viewHolder.ivScheduleStatus.setVisibility(View.VISIBLE);
            viewHolder.vMaskView.setVisibility(View.VISIBLE);
            viewHolder.ivScheduleStatus.setImageResource(R.mipmap.ic_home_schedule_finish);
            viewHolder.vMaskView.setVisibility(View.VISIBLE);
            viewHolder.llCancel.setVisibility(View.GONE);
            viewHolder.llUpdate.setVisibility(View.GONE);
        }else if("17530".equals(entity.getAppmStatusId())){ // 已逾期
            viewHolder.ivScheduleStatus.setVisibility(View.VISIBLE);
            viewHolder.vMaskView.setVisibility(View.VISIBLE);
            viewHolder.ivScheduleStatus.setImageResource(R.mipmap.ic_home_schedule_delay);
            viewHolder.vMaskView.setVisibility(View.VISIBLE);
            viewHolder.llCancel.setVisibility(View.VISIBLE);
            viewHolder.llUpdate.setVisibility(View.VISIBLE);
        }else if("17540".equals(entity.getAppmStatusId())){ // 取消
            viewHolder.ivScheduleStatus.setVisibility(View.VISIBLE);
            viewHolder.vMaskView.setVisibility(View.VISIBLE);
            viewHolder.ivScheduleStatus.setImageResource(R.mipmap.ic_home_schedule_cancel);
            viewHolder.vMaskView.setVisibility(View.VISIBLE);
            viewHolder.llCancel.setVisibility(View.GONE);
            viewHolder.llUpdate.setVisibility(View.GONE);
        }else {
            viewHolder.ivScheduleStatus.setVisibility(View.INVISIBLE);
            viewHolder.vMaskView.setVisibility(View.INVISIBLE);
            viewHolder.ivScheduleStatus.setImageResource(0);
            viewHolder.vMaskView.setVisibility(View.INVISIBLE);
            viewHolder.llCancel.setVisibility(View.VISIBLE);
            viewHolder.llUpdate.setVisibility(View.VISIBLE);
        }

        // 隐藏/显示 更新&删除 按钮
        if("17520".equals(entity.getAppmStatusId()) || "17540".equals(entity.getAppmStatusId())){
            viewHolder.llUpdate.setVisibility(View.GONE);
            viewHolder.llCancel.setVisibility(View.GONE);
            viewHolder.vDividerOne.setVisibility(View.GONE);
            viewHolder.vDividerTwo.setVisibility(View.GONE);
        }else {
            viewHolder.llUpdate.setVisibility(View.VISIBLE);
            viewHolder.llCancel.setVisibility(View.VISIBLE);
            viewHolder.vDividerOne.setVisibility(View.VISIBLE);
            viewHolder.vDividerTwo.setVisibility(View.VISIBLE);
        }

        if(entity.isShowEdit()){
            viewHolder.llBackView.setVisibility(View.VISIBLE);
//            viewHolder.tvFinish.setVisibility(View.INVISIBLE);
//            viewHolder.vMaskView.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.llBackView.setVisibility(View.GONE);
//            viewHolder.tvFinish.setVisibility(View.VISIBLE);
//            viewHolder.vMaskView.setVisibility(View.VISIBLE);
        }

        viewHolder.tvDate.setText(DateUtils.dateFormat("MM月dd号", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getAppmDateStr()));
        viewHolder.tvTime.setText(DateUtils.dateFormat("HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getAppmDateStr()));
        viewHolder.tvAction.setText(NewCustomerConstants.appointmentTypeMap.get(entity.getAppmTypeId()));
        viewHolder.tvName.setText(entity.getAppmCust());
        if(TextUtils.isEmpty(entity.getSeriesName())){
            viewHolder.tvCarType.setText(context.getResources().getString(R.string.yellow_car_series_empty));
        }else {
            viewHolder.tvCarType.setText(context.getResources().getString(R.string.mine_schedule_car_type) +
                    entity.getSeriesName());
        }

//        if(DateUtils.compareDateWithNow("yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getAppmDateStr()) <= 0){
//            viewHolder.vMaskView.setVisibility(View.VISIBLE);
//            viewHolder.tvFinish.setVisibility(View.VISIBLE);
//        }else {
//            viewHolder.vMaskView.setVisibility(View.INVISIBLE);
//            viewHolder.tvFinish.setVisibility(View.INVISIBLE);
//        }

        setClickListener(viewHolder, entity, position);
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private void setClickListener(final Holder holder, final HomeEntity.HomeInfoEntity entity, final int position){
        holder.ivDealMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.isDoingAnim) {
                    if (holder.llBackView.getVisibility() == View.VISIBLE) {
                        holder.hideBackView();
                        entity.setShowEdit(false);
                        if(lastEditHolder == holder){
                            lastEditHolder = null;
                        }
                    } else {
                        holder.showBackView();
                        entity.setShowEdit(true);
                        if(lastEditHolder != holder){
                            if(null != lastEditHolder) {
                                lastEditHolder.hideBackView();
                            }
                        }
                        lastEditHolder = holder;
                        TalkingDataUtils.onEvent(context, "预约事项操作", "首页");
                    }
                }
            }
        });

        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + entity.getAppmMobile());
                intent.setData(data);
                context.startActivity(intent);
            }
        });

        holder.ivSendMsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                Uri data = Uri.parse("smsto:" + entity.getAppmMobile());
                intent.setData(data);
                context.startActivity(intent);
            }
        });

        holder.llUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onBackViewClickListener){
                    onBackViewClickListener.onUpdateClick(entity, position);
                }
            }
        });

        holder.llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onBackViewClickListener){
                    onBackViewClickListener.onCancelClick(entity, position);
                }
            }
        });

        holder.llCheckYC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onBackViewClickListener){
                    onBackViewClickListener.onCheckYCClick(entity, position);
                }
            }
        });
    }

    private class Holder extends RecyclerView.ViewHolder{

        private View vUpDivider;
        private ImageView ivVerticalLine;
        private ImageView ivAction;
        private ImageView ivGenderIndicator;
        private TextView tvDate;
        private TextView tvAction;
        private TextView tvName;
        private TextView tvTime;
        private ImageView ivDealMore;
        private TextView tvCarType;
        private ImageView ivSendMsm;
        private ImageView ivCall;
        private LinearLayout llUpdate;
        private LinearLayout llCancel;
        private LinearLayout llCheckYC;
        private LinearLayout llBackView;
        private View vVipIndicator;
        private View vMaskView;
//        private TextView tvFinish;
        private ImageView ivScheduleStatus;
        private View vDividerOne;
        private View vDividerTwo;

        private AlphaAnimation showBackViewAnim;
        private AlphaAnimation hideBackViewAnim;
        private boolean isDoingAnim = false;

        public Holder(View itemView) {
            super(itemView);
            assignViews(itemView);
            initAnim();
        }

        private void assignViews(View view) {
            vUpDivider = view.findViewById(R.id.v_up_divider);
            ivVerticalLine = (ImageView) view.findViewById(R.id.iv_vertical_line);
            ivAction = (ImageView) view.findViewById(R.id.iv_action);
            ivGenderIndicator = (ImageView) view.findViewById(R.id.iv_gender_indicator);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            tvAction = (TextView) view.findViewById(R.id.tv_action);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            ivDealMore = (ImageView) view.findViewById(R.id.iv_deal_more);
            tvCarType = (TextView) view.findViewById(R.id.tv_car_type);
            ivSendMsm = (ImageView) view.findViewById(R.id.iv_send_msm);
            ivCall = (ImageView) view.findViewById(R.id.iv_call);
            llUpdate = (LinearLayout) view.findViewById(R.id.ll_schedule_update);
            llCancel = (LinearLayout) view.findViewById(R.id.ll_schedule_cancel);
            llCheckYC = (LinearLayout) view.findViewById(R.id.ll_schedule_check_yc);
            llBackView = (LinearLayout) view.findViewById(R.id.ll_back_view);
            vVipIndicator = view.findViewById(R.id.v_vip_indicator);
            vMaskView = view.findViewById(R.id.v_home_schedule_mask);
//            tvFinish = (TextView) view.findViewById(R.id.tv_home_schedule_finish);
            ivScheduleStatus = (ImageView) view.findViewById(R.id.iv_schedule_status);
            vDividerOne = view.findViewById(R.id.v_divider_one);
            vDividerTwo = view.findViewById(R.id.v_divider_two);
        }

        private void initAnim(){
            showBackViewAnim = new AlphaAnimation(0f, 1f);
            showBackViewAnim.setDuration(300);
            showBackViewAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isDoingAnim = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            hideBackViewAnim = new AlphaAnimation(1f, 0f);
            hideBackViewAnim.setDuration(300);
            hideBackViewAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    llBackView.setVisibility(View.INVISIBLE);
                    isDoingAnim = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        public void showBackView(){
            isDoingAnim = true;
            llBackView.setVisibility(View.VISIBLE);
            llBackView.clearAnimation();
            llBackView.startAnimation(showBackViewAnim);
        }

        public void hideBackView(){
            isDoingAnim = true;
            llBackView.clearAnimation();
            llBackView.startAnimation(hideBackViewAnim);
        }
    }

    public interface OnBackViewClickListener{
        void onUpdateClick(HomeEntity.HomeInfoEntity entity, int position);
        void onCancelClick(HomeEntity.HomeInfoEntity entity, int position);
        void onCheckYCClick(HomeEntity.HomeInfoEntity entity, int position);
    }

    public void setOnBackViewClickListener(OnBackViewClickListener listener){
        this.onBackViewClickListener = listener;
    }
}
