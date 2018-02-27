package com.svw.dealerapp.ui.resource.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.SMYCTransferSalesEntity;
import com.svw.dealerapp.entity.resource.TransferDataEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.resource.adapter.YellowCardTransferRecyclerAdapter;
import com.svw.dealerapp.entity.resource.YCTransferSalesEntity;
import com.svw.dealerapp.ui.resource.contract.YellowCardTransferContract;
import com.svw.dealerapp.ui.resource.model.YellowCardTransferModel;
import com.svw.dealerapp.ui.resource.presenter.YellowCardTransferPresenter;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.util.List;

/**
 * Created by qinshi on 5/18/2017.
 */

public class YellowCardTransferListFragment extends BaseListFragment<TransferDataEntity, SMYCTransferSalesEntity>
        implements YellowCardTransferContract.TransferView{

    private YellowCardTransferRecyclerAdapter adapter;
    private SMYCTransferSalesEntity infoEntity;
    private String orgId;
    private String oppId;
    private int dealPosition;    //要转移的黄卡条目在list中的位置，转移成功后删除该条目需要用

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smPresenter = new YellowCardTransferPresenter(this, new YellowCardTransferModel());

        orgId = UserInfoUtils.getOrgId();
        Intent intent = getActivity().getIntent();
        if(null != intent){
//            orgId = intent.getStringExtra("orgId");
            oppId = intent.getStringExtra("oppId");
            dealPosition = intent.getIntExtra("dealPosition", -1);
        }
    }

    @Override
    public void initView() {
        super.initView();
        rvRecyclerView.setLoadMoreEnabled(false);
//        showLoadMoreNoData();
    }

    @Override
    public void refresh() {
        super.refresh();
        this.hideLoadMoreFooterView();
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new YellowCardTransferRecyclerAdapter(getActivity(), smPresenter.getDataList());
        adapter.setOnItemClickListener(new YellowCardTransferRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, SMYCTransferSalesEntity entity, boolean isSelect) {
                if(isSelect) {
                    infoEntity = entity;
                }else {
                    infoEntity = null;
                }
            }
        });
        return adapter;
    }

    /**
     * 获取选中的销售
     * @return
     */
    public SMYCTransferSalesEntity getSelectEntity(){
        return infoEntity;
    }

    @Override
    public void dealTransferSuccess() {
        ToastUtils.showToast(getResources().getString(R.string.resource_yellow_transfer_success));
        Intent intent = new Intent();
        intent.putExtra("dealPosition", dealPosition);
        getActivity().setResult(0, intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
    }

    @Override
    public void showTransferFailed() {
        ToastUtils.showToast(getResources().getString(R.string.resource_yellow_transfer_fail));
    }

    /**
     * 搜索要转移到的销售
     * @param string
     */
    public void searchTransferSales(String string){
        if(smPresenter instanceof YellowCardTransferContract.Presenter){
            ((YellowCardTransferContract.Presenter) smPresenter).searchTransferSales(string);
        }
    }

    /**
     * 黄卡转移提交
     */
    public void postYellowCardTransfer(){
        if(smPresenter instanceof YellowCardTransferContract.Presenter){
            ((YellowCardTransferContract.Presenter) smPresenter).postTransferYellowCard(getActivity(), oppId, infoEntity.getPhone());
        }
    }

    @Override
    public String[] getMoreParams() {
        return new String[]{orgId};
    }

    @Override
    public boolean isUseSMToGetListData() {
        return true;
    }
}
