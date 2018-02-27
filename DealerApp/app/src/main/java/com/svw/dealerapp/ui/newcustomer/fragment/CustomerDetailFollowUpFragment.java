package com.svw.dealerapp.ui.newcustomer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.AddFollowupRecordActivity;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.TalkingDataUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 黄卡详情页-跟进记录Fragment.
 * Created by xupan on 07/06/2017.
 */

public class CustomerDetailFollowUpFragment extends BaseCustomerFragment {

    private RecyclerView mRecyclerView;
    //    private List<String> mList;
    private OpportunityDetailEntity entity;
    private String mIsOtdOrder;//0：是，1：否
    private String mOrderId;
    private String mOrderStatus;//-1:首次创建，0：有效，1：无效，1.1：无效要创建，2：OTD，3：OTD取消中，4：开票中，5：已开票，6：已交车

    public static CustomerDetailFollowUpFragment newInstance(OpportunityDetailEntity entity) {
        CustomerDetailFollowUpFragment fragment = new CustomerDetailFollowUpFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_customer_detail_followup;
    }

    @Override
    protected void initViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_customer_detail_followup_recyclerview);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        checkOrderStatus();
        entity = (OpportunityDetailEntity) mEntity;
        List<OpportunityDetailEntity.FollowupsBean> list = entity.getFollowups();
        if (list == null || list.isEmpty()) {
            return;
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new RecyclerAdapter(list));
    }

    /**
     * 检查跟进结果是否选择过订单，以及orderId和orderStatus
     */
    private void checkOrderStatus() {

        if (mEntity != null) {
            mOrderId = ((OpportunityDetailEntity) mEntity).getOrderId();
            mOrderStatus = ((OpportunityDetailEntity) mEntity).getOrderStatus();
            mIsOtdOrder = ((OpportunityDetailEntity) mEntity).getIsOtdOrder();
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private List<OpportunityDetailEntity.FollowupsBean> mList;
        private CustomDialog notFollowupDialog;

        RecyclerAdapter(List<OpportunityDetailEntity.FollowupsBean> list) {
            mList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_customer_detail_followup, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final OpportunityDetailEntity.FollowupsBean bean = mList.get(position);
            String modeId = bean.getModeId();
            if (!TextUtils.isEmpty(modeId)) {
                switch (modeId) {
                    case "15010":
                    case "20010":
                        //展厅
                        holder.typeIv.setVisibility(View.VISIBLE);
                        holder.typeIv.setImageResource(R.mipmap.item_customer_detail_followup_hall);
                        break;
                    case "15020":
                    case "20020":
                        //电话
                        holder.typeIv.setVisibility(View.VISIBLE);
                        holder.typeIv.setImageResource(R.mipmap.item_customer_detail_followup_phone);
                        break;
                    case "15030":
                    case "20030":
                        //微信
                        holder.typeIv.setVisibility(View.VISIBLE);
                        holder.typeIv.setImageResource(R.mipmap.item_customer_detail_followup_wechat);
                        break;
                    case "15040":
                    case "20040":
                        //短信
                        holder.typeIv.setVisibility(View.VISIBLE);
                        holder.typeIv.setImageResource(R.mipmap.item_customer_detail_followup_message);
                        break;
                }
            } else {
                holder.typeIv.setVisibility(View.INVISIBLE);
            }
            showLevelImage(bean, holder.levelIv);
            if (bean.getFollowupStatus().equals("0")) {//完成
                //跟进日期
                if (!TextUtils.isEmpty(bean.getUpdateTimeStr())) {
                    showUpdateDateAndTime(bean.getUpdateTimeStr(), holder.dateTv, holder.timeTv);
                } else {
                    holder.dateTv.setText("");
                    holder.timeTv.setText("                 ");
                }
                if (TextUtils.isEmpty(bean.getApplicationDesc())) {
                    holder.line1Tv.setText(bean.getResultDesc());
                } else {
                    //休眠或失败具体原因
                    String result = bean.getResultDesc() + "——" + bean.getApplicationDesc();
                    holder.line1Tv.setText(result);
                }
                holder.contentBg.setBackgroundResource(R.drawable.customer_detail_followup_not_editable_bg);
                holder.editIv.setVisibility(View.GONE);
                holder.line1Tv.setTextColor(getResources().getColor(R.color.customer_detail_followup_item_line1_black));
                holder.line2Tv.setTextColor(getResources().getColor(R.color.customer_detail_followup_item_line2_grey));
                if (TextUtils.isEmpty(bean.getRemark())) {
                    holder.line2Tv.setVisibility(View.GONE);
                    holder.dashLine.setVisibility(View.GONE);
                } else {
                    holder.line2Tv.setText(bean.getRemark());//已完成时第二行显示备注
                    holder.line2Tv.setVisibility(View.VISIBLE);
                    holder.dashLine.setVisibility(View.VISIBLE);
                }
            } else if (bean.getFollowupStatus().equals("1")) {//未完成
                //计划日期
                if (!TextUtils.isEmpty(bean.getScheduleDateStr())) {
                    holder.dateTv.setText(bean.getScheduleDateStr());
                    holder.timeTv.setVisibility(View.GONE);//未完成时只显示日期不显示时间
                } else {
                    holder.dateTv.setText(getString(R.string.pending_status));
                    holder.timeTv.setText("                 ");
                }

                // 当前用户不是该黄卡的owner或黄卡为休眠/战败状态
                if (!entity.isYellowCardOwner() || entity.isSleepStatus() || entity.isFailedStatus()) {
                    holder.contentBg.setBackgroundResource(R.drawable.customer_detail_followup_editable_no_owner_bg);
                    holder.line1Tv.setTextColor(getResources().getColor(R.color.customer_detail_followup_item_line1_black));
                    holder.line2Tv.setTextColor(getResources().getColor(R.color.customer_detail_followup_item_line2_grey));
                    holder.editIv.setVisibility(View.GONE);
                } else {
                    holder.line1Tv.setText(TextUtils.isEmpty(bean.getResultDesc()) ? "待跟进" : bean.getResultDesc());
                    holder.contentBg.setBackgroundResource(R.drawable.customer_detail_followup_editable_bg);
                    holder.line1Tv.setTextColor(getResources().getColor(R.color.customer_detail_followup_item_text_blue));
                    holder.line2Tv.setTextColor(getResources().getColor(R.color.customer_detail_followup_item_text_blue));
                }

                if (TextUtils.isEmpty(bean.getScheduleDesc())) {
                    holder.line2Tv.setVisibility(View.GONE);
                    holder.dashLine.setVisibility(View.GONE);
                } else {
                    holder.line2Tv.setText(bean.getScheduleDesc());//待跟进时第二行显示跟进计划
                    holder.line2Tv.setVisibility(View.VISIBLE);
                    holder.dashLine.setVisibility(View.VISIBLE);
                }
            }
            String approvalStatusId = bean.getApprovalStatusId();
            if (!TextUtils.isEmpty(approvalStatusId)) {
                switch (approvalStatusId) {
                    case "18510"://待审核
                        holder.approvalStatusTv.setTextColor(getResources().getColor(R.color.customer_detail_followup_pending));
                        holder.approvalStatusTv.setText(getString(R.string.customer_detail_followup_pending));
                        break;
                    case "18520"://通过
                        holder.approvalStatusTv.setTextColor(getResources().getColor(R.color.customer_detail_followup_approved));
                        holder.approvalStatusTv.setText(getString(R.string.customer_detail_followup_approved));
                        break;
                    case "18530"://驳回
                        holder.approvalStatusTv.setTextColor(getResources().getColor(R.color.customer_detail_followup_rejected));
                        holder.approvalStatusTv.setText(getString(R.string.customer_detail_followup_rejected));
                        break;
                }
            }

            holder.editIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TalkingDataUtils.onEvent(getActivity(), "添加跟进记录", "潜客详情");
                    if ("1.1".equals(mOrderStatus) || "-1".equals(mOrderStatus)) {
                        showNotFollowupDialog(getString(R.string.double_followup_order));
                    } else if ("3".equals(mOrderStatus)) {
                        showNotFollowupDialog(getString(R.string.otd_canceling_followup));
                    } else if ("4".equals(mOrderStatus)) {
                        showNotFollowupDialog(getString(R.string.order_pending_followup));
                    } else {
                        Intent i = new Intent(getActivity(), AddFollowupRecordActivity.class);
                        i.putExtra("oppId", bean.getOppId());
                        i.putExtra("followupId", bean.getFollowupId());
                        i.putExtra("leadId", entity.getLeadId());
                        if (mEntity != null) {
                            OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
                            i.putExtra("oppLevelId", entity.getOppLevel());
                            i.putExtra("oppStatus", entity.getOppStatusId());
                            i.putExtra("seriesId", entity.getSeriesId());
                        }
//                        if (!TextUtils.isEmpty(mOrderStatus)) {
//                            i.putExtra("orderStatus", mOrderStatus);
//                        }
                        startActivity(i);
                    }
                }
            });
        }

        private void showLevelImage(OpportunityDetailEntity.FollowupsBean followupsBean, ImageView levelIv) {
            List<OpportunityDetailEntity.ResultsBean> resultsBeen = followupsBean.getResults();
            if (resultsBeen != null && !resultsBeen.isEmpty()) {
                for (OpportunityDetailEntity.ResultsBean item : resultsBeen) {
                    if (item == null) {
                        continue;
                    }
                    //战败或休眠时，潜客级别显示感叹号图标
                    if ("15570".equals(item.getDictId()) || "15590".equals(item.getDictId())) {
                        levelIv.setImageResource(R.mipmap.customer_detail_followup_error);
                        return;
                    }
                }
            }
            String levelId = followupsBean.getOppLevel();
            //若当前记录里的levelId为空，则取外层的levelId
            if (TextUtils.isEmpty(levelId)) {
                OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
                showLevelImageIfNecessary(entity.getOppLevel(), levelIv);
            } else {
                showLevelImageIfNecessary(levelId, levelIv);
            }
        }

        private void showLevelImageIfNecessary(String levelId, ImageView levelIv) {
            if (TextUtils.isEmpty(levelId)) {
                levelIv.setVisibility(View.INVISIBLE);
                return;
            }
            if (levelIv == null) {
                return;
            }
            switch (levelId) {
                case "12010":
                    levelIv.setImageResource(R.mipmap.customer_detail_followup_h);
                    break;
                case "12020":
                    levelIv.setImageResource(R.mipmap.customer_detail_followup_a);
                    break;
                case "12030":
                    levelIv.setImageResource(R.mipmap.customer_detail_followup_b);
                    break;
                case "12040":
                    levelIv.setImageResource(R.mipmap.customer_detail_followup_c);
                    break;
                case "12050":
                    levelIv.setImageResource(R.mipmap.customer_detail_followup_n);
                    break;
                default:
                    levelIv.setVisibility(View.INVISIBLE);
                    break;
            }
        }

        private void showUpdateDateAndTime(String sourceDateString, TextView dateTv, TextView timeTv) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date = null;
            try {
                date = simpleDateFormat.parse(sourceDateString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (date != null) {
                dateTv.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
                timeTv.setText(new SimpleDateFormat("HH:mm").format(date));
            }
        }

        private void showNotFollowupDialog(String msg) {
            SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
            notFollowupDialog = new CustomDialog(getContext(), dialogAdapter);
            dialogAdapter.setDialogMessageText(msg);
            dialogAdapter.setTextViewPadding(DensityUtil.dp2px(getContext(), 30), 0, DensityUtil.dp2px(getContext(), 30), 0);
            notFollowupDialog.hideShowCancelBtn();
            notFollowupDialog.setBtnConfirmText(getResources().getString(R.string.dialog_confirm));
            notFollowupDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
                @Override
                public void onCancelBtnClick() {
                    notFollowupDialog.dismiss();
                }

                @Override
                public void onConfirmBtnClick() {
                    notFollowupDialog.dismiss();
                }
            });
            notFollowupDialog.show();
        }

        @Override
        public int getItemCount() {
            if (mList == null || mList.size() == 0) {
                return 0;
            } else {
                return mList.size();
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout contentBg;
            LinearLayout paddingBg;
            TextView line1Tv, line2Tv, dateTv, timeTv;
            View dashLine;
            ImageView editIv, typeIv, levelIv;
            TextView approvalStatusTv;

            public ViewHolder(View itemView) {
                super(itemView);
                contentBg = (RelativeLayout) itemView.findViewById(R.id.item_customer_detail_followup_content_bg);
                paddingBg = (LinearLayout) itemView.findViewById(R.id.item_customer_detail_followup_right_ll);
                line2Tv = (TextView) itemView.findViewById(R.id.item_customer_detail_followup_line2_tv);
                line1Tv = (TextView) itemView.findViewById(R.id.item_customer_detail_followup_line1_tv);
                dashLine = itemView.findViewById(R.id.item_customer_detail_followup_dash_line);
                editIv = (ImageView) itemView.findViewById(R.id.item_customer_detail_followup_edit_iv);
                typeIv = (ImageView) itemView.findViewById(R.id.item_customer_detail_followup_type_iv);
                levelIv = (ImageView) itemView.findViewById(R.id.item_customer_detail_followup_level_iv);
                dateTv = (TextView) itemView.findViewById(R.id.item_customer_detail_followup_date_tv);
                timeTv = (TextView) itemView.findViewById(R.id.item_customer_detail_followup_time_tv);
                approvalStatusTv = (TextView) itemView.findViewById(R.id.item_customer_detail_followup_approval_status_tv);
            }

        }
    }

}
