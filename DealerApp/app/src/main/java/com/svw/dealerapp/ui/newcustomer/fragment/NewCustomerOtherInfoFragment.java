package com.svw.dealerapp.ui.newcustomer.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TableRow;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityRelationsBean;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.newcustomer.GridSpacingItemDecoration;
import com.svw.dealerapp.ui.widget.EditTextWithMicLayout;
import com.svw.dealerapp.ui.widget.InterceptTouchCoverView;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.PermissionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 其他信息(客户了解信息途径、是否添加微信）
 * Created by xupan on 09/06/2017.
 */

public class NewCustomerOtherInfoFragment extends BaseCustomerFragment {
    private static final String TAG = "NewCustomerOtherInfoFragment";
    private boolean mChangeStyleFlag;

    private RecyclerView mMethodRv;
    private CheckBox mWechatAddedCb;
    private TableRow mWechatTr;
    private View mWechatLine;

    private RelativeLayout rlRootView;
    private InterceptTouchCoverView itvCoverView;

    private List<String> mMethodCodeList;
    private List<String> mMethodValueList;

    private GridAdapter mMethodAdapter;

    private EditTextWithMicLayout editTextWithMicLayout;

    private boolean hasCheckCoverView = false;

    public static NewCustomerOtherInfoFragment newInstance(Serializable entity) {
        NewCustomerOtherInfoFragment fragment = new NewCustomerOtherInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_new_customer_step_2_others;
    }

    public void setChangeStyleFlag() {
        mChangeStyleFlag = true;
    }

    protected void initViews(View view) {
        mWechatTr = (TableRow) view.findViewById(R.id.new_customer_step_2_others_wechat_tr);
        mMethodRv = (RecyclerView) view.findViewById(R.id.new_customer_step_2_others_way_rv);
        mWechatAddedCb = (CheckBox) view.findViewById(R.id.new_customer_step_2_others_wechat_cb);
        mWechatLine = view.findViewById(R.id.new_customer_step_2_others_wechat_line);
        rlRootView = (RelativeLayout) view.findViewById(R.id.rl_root_view);
        itvCoverView = (InterceptTouchCoverView) view.findViewById(R.id.itv_cover_view);

        editTextWithMicLayout = (EditTextWithMicLayout) view.findViewById(R.id.et_with_mic);
        editTextWithMicLayout.initEditWithMic(getActivity(), NewCustomerOtherInfoFragment.this);
        editTextWithMicLayout.setMaxTextNum(50);
        editTextWithMicLayout.setEnabled(false);
        editTextWithMicLayout.setHint(getResources().getString(R.string.new_customer_intention_other_info_method_hint));
        changeStyleForDetail();
        initMethodRecyclerView();

        // 设置根视图的布局监听，如果当前用户是不该黄卡的owner，获取根视图的高设置给遮盖层，显示遮盖层，并拦截消费掉事件
        rlRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (null == mEntity) {
                    return;
                }
                if (!hasCheckCoverView) {
                    hasCheckCoverView = true;
                    OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
                    if (!entity.isYellowCardOwner() || entity.isSleepStatus() || entity.isFailedStatus()) {   // 当前用户不是该黄卡的owner或该黄卡处在休眠/战败状态
                        if (View.VISIBLE != itvCoverView.getVisibility()) {
                            itvCoverView.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itvCoverView.getLayoutParams());
                            params.height = rlRootView.getHeight();
                            itvCoverView.setLayoutParams(params);
                        }
                    }
                }
            }
        });
    }

    private void changeStyleForDetail() {
        if (mChangeStyleFlag) {
            mWechatTr.setVisibility(View.GONE);
            mWechatLine.setVisibility(View.GONE);
        }
    }

    private void initMethodRecyclerView() {
        Iterator<Map.Entry<String, String>> methodIterator = NewCustomerConstants.methodsMap.entrySet().iterator();
        mMethodCodeList = new ArrayList<>();
        mMethodValueList = new ArrayList<>();
        while (methodIterator.hasNext()) {
            Map.Entry<String, String> entry = methodIterator.next();
            mMethodCodeList.add(entry.getKey());
            mMethodValueList.add(entry.getValue());
        }
        mMethodRv.setLayoutManager(new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false));
        mMethodRv.addItemDecoration(new GridSpacingItemDecoration(4, getResources().getDimensionPixelSize(R.dimen.new_customer_grid_recycler_spacing), true));
        mMethodAdapter = new GridAdapter(mMethodCodeList, mMethodValueList, 0);
        mMethodRv.setAdapter(mMethodAdapter);
    }

    public OpportunityUpdateReqEntity getParameters() {
        OpportunityUpdateReqEntity entity = new OpportunityUpdateReqEntity();
        entity.setIsWechat(mWechatAddedCb.isChecked() ? "0" : "1");
        entity.setOpportunityRelations(getOpportunityRelationsBeansList());
        return entity;
    }

    /**
     * 生成多选项结果List
     *
     * @return
     */
    public List<OpportunityRelationsBean> getOpportunityRelationsBeansList() {
        List<OpportunityRelationsBean> list = new ArrayList<>();
        //客户了解信息途径
        Set<Integer> methodSet = mMethodAdapter.getCheckedIndexSet();
        String flag = "1";
        for (Integer index : methodSet) {
            OpportunityRelationsBean methodRelationsBean = new OpportunityRelationsBean();
            methodRelationsBean.setRelaId(mMethodCodeList.get(index));
            methodRelationsBean.setRelaDesc(mMethodValueList.get(index));
            methodRelationsBean.setRelaFlag(flag);
            if (mMethodValueList.get(index).contains("其它")) {
//                methodRelationsBean.setRemark(mMethodOtherEt.getText().toString());
                methodRelationsBean.setRemark(editTextWithMicLayout.getTextContent());
            }
            JLog.d(TAG, "bean: " + methodRelationsBean);
            list.add(methodRelationsBean);
        }
        return list;
    }

    private class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

        private List<String> mValueList, mCodeList;
        private Set<Integer> mCheckedIndexSet = new HashSet<>();
        private Set<String> mCheckedCodeSet;

        private GridAdapter(List<String> codeList, List<String> valueList, int flag) {
            mCodeList = codeList;
            mValueList = valueList;
        }

        Set<Integer> getCheckedIndexSet() {
            return mCheckedIndexSet;
        }

        void setCheckedCodeSet(Set<String> set) {
            mCheckedCodeSet = set;
        }

        @Override
        public GridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_customer_grid, parent, false);
            return new GridAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final GridAdapter.ViewHolder holder, final int position) {
            final int adapterPosition = holder.getAdapterPosition();
            holder.checkBox.setText(mValueList.get(adapterPosition));
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mCheckedIndexSet.add(adapterPosition);
                        if (mValueList.get(adapterPosition).contains("其它")) {
                            editTextWithMicLayout.setEnabled(true);
                        }
                    } else {
                        mCheckedIndexSet.remove(adapterPosition);
                        if (mValueList.get(adapterPosition).contains("其它")) {
                            editTextWithMicLayout.setEnabled(false);
                            editTextWithMicLayout.setText("");
                        }
                    }
                }
            });
            if (mCheckedCodeSet != null && mCheckedCodeSet.contains(mCodeList.get(adapterPosition))) {
                holder.checkBox.setChecked(true);
                // 如果当前用户不是该黄卡的owner
                if (mEntity instanceof OpportunityDetailEntity) {
                    OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
                    if (!entity.isYellowCardOwner() || entity.isSleepStatus() || entity.isFailedStatus()) {    // 当前用户不是该黄卡的owner或该黄卡处在休眠/战败状态
                        holder.checkBox.setBackground(getResources().getDrawable(R.drawable.new_customer_checkbox_no_owner_checked));
                        holder.checkBox.setTextColor(getResources().getColor(R.color.white));
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            return mValueList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private CheckBox checkBox;

            private ViewHolder(View itemView) {
                super(itemView);
                checkBox = (CheckBox) itemView.findViewById(R.id.item_new_customer_grid_checkbox);
            }
        }
    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
        List<OpportunityDetailEntity.RelationsBean> list = entity.getOpportunityRelations();
        if (list == null || list.isEmpty()) {
            return;
        }
        Set<String> set = new HashSet<>();
        for (OpportunityDetailEntity.RelationsBean bean : list) {
            //relaFlag为0表示其他需求，为1表示了解信息途径
            if ("1".equals(bean.getRelaFlag())) {
                set.add(bean.getRelaId());
                if (!TextUtils.isEmpty(bean.getRemark())) {
                    editTextWithMicLayout.setEnabled(true);
                    editTextWithMicLayout.setText(bean.getRemark());
                }
            }
        }
        mMethodAdapter.setCheckedCodeSet(set);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editTextWithMicLayout.stopNLS();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_AUDIO_RECORD_PERMISSION:
                if (Manifest.permission.RECORD_AUDIO.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    editTextWithMicLayout.clickRecordButton();
                }
                break;
        }
    }
}
