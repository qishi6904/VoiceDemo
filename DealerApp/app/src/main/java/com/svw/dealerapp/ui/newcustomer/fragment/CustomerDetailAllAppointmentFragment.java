package com.svw.dealerapp.ui.newcustomer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.adapter.StringCustomDialogAdapter;
import com.svw.dealerapp.ui.mine.activity.ScheduleUpdateActivity;
import com.svw.dealerapp.ui.newcustomer.contract.CustomerDetailContract;
import com.svw.dealerapp.ui.newcustomer.model.CustomerDetailModel;
import com.svw.dealerapp.ui.newcustomer.presenter.CustomerDetailPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 黄卡详情页-当前预约Fragment.
 * Created by xupan on 06/06/2017.
 */

public class CustomerDetailAllAppointmentFragment extends BaseCustomerFragment implements CustomerDetailContract.View {

    private static final int updateScheduleCode = 1001;

    private static final String TAG = "CustomerDetailAllAppointmentFragment";
    private RecyclerView mRecyclerView;
    private RelativeLayout mEmptyRl;
    private List<OpportunityDetailEntity.AppointmentsBean> mList;
    private RecyclerAdapter.RecyclerViewHolder lastEditHolder;
    private CustomDialog cancelDialog;
    private LoadingDialog loadingDialog;
    private RecyclerAdapter adapter;
    private CustomerDetailPresenter presenter;

    public static CustomerDetailAllAppointmentFragment newInstance(OpportunityDetailEntity entity) {
        CustomerDetailAllAppointmentFragment fragment = new CustomerDetailAllAppointmentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CustomerDetailAllAppointmentFragment(){
        presenter = new CustomerDetailPresenter(this, new CustomerDetailModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_customer_detail_all_appointments;
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void renderView() {

    }

    protected void initViews(View view) {
        mEmptyRl = (RelativeLayout) view.findViewById(R.id.fragment_customer_detail_all_appointments_empty_rl);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_customer_detail_all_appointments_recyclerview);
        if (validateAppointmentsList()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyRl.setVisibility(View.GONE);
            adapter = new RecyclerAdapter(((OpportunityDetailEntity) mEntity).getAppointments());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setNestedScrollingEnabled(false);

            adapter.setOnBackViewClickListener(new OnBackViewClickListener() {
                @Override
                public void onUpdateClick(OpportunityDetailEntity.AppointmentsBean entity, int position) {
                    Intent intent = new Intent(getActivity(), ScheduleUpdateActivity.class);
                    intent.putExtra("scheduleId", entity.getAppmId());
                    intent.putExtra("dateTimeStr", entity.getAppmDateStr());
                    intent.putExtra("appTypeId", entity.getAppmTypeId());
                    intent.putExtra("position", position);
                    startActivityForResult(intent, updateScheduleCode);
                }

                @Override
                public void onCancelClick(OpportunityDetailEntity.AppointmentsBean entity, int position) {
                    showCancelDialog(entity, position);
                }
            });

        } else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyRl.setVisibility(View.VISIBLE);
        }
    }

    private void showCancelDialog(final OpportunityDetailEntity.AppointmentsBean entity, final int position){
        if(null == cancelDialog) {
            StringCustomDialogAdapter dialogAdapter = new StringCustomDialogAdapter();
            cancelDialog = new CustomDialog(getActivity(), dialogAdapter);
            dialogAdapter.setContent(getResources().getString(R.string.mine_schedule_cancel_tip));
        }
        cancelDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                cancelDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                Map<String, Object> options = new HashMap<>();
                options.put("appmId", entity.getAppmId());
                presenter.cancelSchedule(getActivity(), options, position);
                cancelDialog.dismiss();
            }
        });
        cancelDialog.show();
    }

    /**
     * 验证appointment列表数据是否有效
     *
     * @return true: 预约列表条数不为0 false: 预约列表无数据
     */
    private boolean validateAppointmentsList() {
        if (mEntity == null) {
            return false;
        }
        OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
        return entity.getAppointments() != null && !entity.getAppointments().isEmpty();
    }

    @Override
    public void showLoadingDialog() {
        if(null == loadingDialog){
            loadingDialog = new LoadingDialog(getActivity());
        }
        if(!loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if(null != loadingDialog){
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showNetWorkErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void showServerErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.server_error));
    }

    @Override
    public void showTimeOutToast() {
        ToastUtils.showToast(getResources().getString(R.string.timeout_error));
    }

    @Override
    public void cancelScheduleSuccess(int position) {
        List<OpportunityDetailEntity.AppointmentsBean> dataList = ((OpportunityDetailEntity) mEntity).getAppointments();
        dataList.remove(position);

        if(dataList.size() > 0){
            adapter.notifyDataSetChanged();
        }else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyRl.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
        ToastUtils.showToast(getResources().getString(R.string.mine_schedule_cancel_success));
    }

    @Override
    public void cancelScheduleFailed() {
        ToastUtils.showToast(getResources().getString(R.string.mine_schedule_cancel_fail));
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

        private List<OpportunityDetailEntity.AppointmentsBean> mList;
        private OnBackViewClickListener onBackViewClickListener;

        RecyclerAdapter(List<OpportunityDetailEntity.AppointmentsBean> list) {
            mList = list;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_detail_all_appointment, parent, false);
            RecyclerViewHolder holder = new RecyclerViewHolder(view);

            //非owner不显示"..."按钮
            if(null != mEntity) {
                OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
                if(!entity.isYellowCardOwner()){
                    holder.moreIv.setVisibility(View.INVISIBLE);
                }
            }

            return holder;

        }

        @Override
        public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
            final OpportunityDetailEntity.AppointmentsBean bean = mList.get(position);
            holder.typeTv.setText(NewCustomerConstants.appointmentTypeMap.get(bean.getAppmTypeId()));
            String sourceDateString = bean.getAppmDateStr();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date = null;
            try {
                date = simpleDateFormat.parse(sourceDateString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (date != null) {
                holder.dateTv.setText(new SimpleDateFormat("MM月dd日").format(date));
                holder.timeTv.setText(new SimpleDateFormat("HH:mm").format(date));
            }

            if(bean.isShowEdit()){
                holder.llBackLayout.setVisibility(View.VISIBLE);
            }else {
                holder.llBackLayout.setVisibility(View.GONE);
            }

            String appmTypeId = bean.getAppmTypeId();
            if (!TextUtils.isEmpty(appmTypeId)) {
                switch (appmTypeId) {
                    case "17010":
                        //邀约到店
                        holder.iconIv.setImageResource(R.mipmap.appointment_invitation);
                        break;
                    case "17020":
                        //试乘试驾
                        holder.iconIv.setImageResource(R.mipmap.appointment_trial);
                        break;
                    case "17030":
                        //订单
                        holder.iconIv.setImageResource(R.mipmap.appintment_order);
                        break;
                    case "17040":
                        //交车
                        holder.iconIv.setImageResource(R.mipmap.appointment_car);
                        break;
                    default:
                        break;
                }
            }

            holder.moreIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!holder.isDoingAnim) {
                        if (holder.llBackLayout.getVisibility() == View.VISIBLE) {
                            holder.hideBAckView();
                            bean.setShowEdit(false);
                            if(lastEditHolder == holder){
                                lastEditHolder = null;
                            }
                        } else {
                            holder.showBackView();
                            bean.setShowEdit(true);
                            if(lastEditHolder != holder){
                                if(null != lastEditHolder) {
                                    lastEditHolder.hideBAckView();
                                }
                            }
                            lastEditHolder = holder;
                        }
                    }
                }
            });

            holder.llCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != onBackViewClickListener){
                        onBackViewClickListener.onCancelClick(bean, position);
                    }
                }
            });

            holder.llUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != onBackViewClickListener){
                        onBackViewClickListener.onUpdateClick(bean, position);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            if (mList == null || mList.size() == 0) {
                return 0;
            } else {
                return mList.size();
            }
        }

        class RecyclerViewHolder extends RecyclerView.ViewHolder {
            ImageView iconIv, moreIv;
            TextView typeTv, dateTv, timeTv;
            LinearLayout llBackLayout;
            LinearLayout llUpdate;
            LinearLayout llCancel;
            RelativeLayout rlFrontView;


            private AlphaAnimation showBackViewAnim;
            private AlphaAnimation hideBackViewAnim;
            private boolean isDoingAnim = false;

            RecyclerViewHolder(View itemView) {
                super(itemView);
                iconIv = (ImageView) itemView.findViewById(R.id.item_customer_detail_appointment_icon_iv);
                moreIv = (ImageView) itemView.findViewById(R.id.item_customer_detail_appointment_more_iv);
                typeTv = (TextView) itemView.findViewById(R.id.item_customer_detail_appointment_text_tv);
                dateTv = (TextView) itemView.findViewById(R.id.item_customer_detail_appointment_date_tv);
                timeTv = (TextView) itemView.findViewById(R.id.item_customer_detail_appointment_time_tv);
                llBackLayout = (LinearLayout) itemView.findViewById(R.id.ll_customer_back_view);
                rlFrontView = (RelativeLayout) itemView.findViewById(R.id.rl_front_view);
                llUpdate = (LinearLayout) itemView.findViewById(R.id.ll_schedule_update);
                llCancel = (LinearLayout) itemView.findViewById(R.id.ll_schedule_cancel);

                initAnim();
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
                        llBackLayout.setVisibility(View.INVISIBLE);
                        isDoingAnim = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            public void showBackView(){
                isDoingAnim = true;
                llBackLayout.setVisibility(View.VISIBLE);
                llBackLayout.clearAnimation();
                llBackLayout.startAnimation(showBackViewAnim);
            }

            public void hideBAckView(){
                isDoingAnim = true;
                llBackLayout.clearAnimation();
                llBackLayout.startAnimation(hideBackViewAnim);
            }
        }

        public void setOnBackViewClickListener(OnBackViewClickListener listener){
            this.onBackViewClickListener = listener;
        }
    }

    public interface OnBackViewClickListener{
        void onUpdateClick(OpportunityDetailEntity.AppointmentsBean entity, int position);
        void onCancelClick(OpportunityDetailEntity.AppointmentsBean entity, int position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case updateScheduleCode:
                if(null != data){
                    int dealPosition = data.getIntExtra("dealPosition", -1);
                    if(dealPosition >= 0) {
                        String appmDateStr = data.getStringExtra("appmDateStr");
                        String appmTypeId = data.getStringExtra("appmTypeId");
                        OpportunityDetailEntity.AppointmentsBean bean = ((OpportunityDetailEntity) mEntity)
                                .getAppointments().get(dealPosition);
                        bean.setAppmDateStr(appmDateStr);
                        bean.setAppmTypeId(appmTypeId);
                        bean.setShowEdit(false);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != cancelDialog){
            cancelDialog.dismiss();
        }
        if(null != loadingDialog){
            loadingDialog.dismiss();
        }
    }
}
