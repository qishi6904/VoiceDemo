package com.svw.dealerapp.ui.resource.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.resource.SMYCTransferSalesEntity;
import com.svw.dealerapp.entity.resource.TransferDataEntity;
import com.svw.dealerapp.entity.resource.YCTransferSalesEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.SMListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.resource.contract.YellowCardTransferContract;
import com.svw.dealerapp.util.UserInfoUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 5/22/2017.
 */

public class YellowCardTransferPresenter extends SMListFragmentPresenter<TransferDataEntity, SMYCTransferSalesEntity>
        implements YellowCardTransferContract.Presenter {

    private Subscription postTransferSubscription;

    //返回的数据集合
    private List<SMYCTransferSalesEntity> originalList = new ArrayList<>();
    //根据输入的搜索字符串过滤出来的数据集合
    private List<SMYCTransferSalesEntity> searchResultList = new ArrayList<>();

    private YellowCardTransferContract.TransferView fragmentView;

    public YellowCardTransferPresenter(ListFragmentContract.View<TransferDataEntity, SMYCTransferSalesEntity> view, ListFragmentContract.Model<SMResEntity<TransferDataEntity>> model) {
        super(view, model);
        if(view instanceof YellowCardTransferContract.TransferView){
            fragmentView = (YellowCardTransferContract.TransferView) view;
        }
    }

    @Override
    public void postTransferYellowCard(Context context, String oppId, String oppOwner) {
        if(mModel instanceof YellowCardTransferContract.Model){
            YellowCardTransferContract.Model transferModel = (YellowCardTransferContract.Model) mModel;

            Observable observable = transferModel.postTransferYellowCard(oppId, oppOwner)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1){

                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                        fragmentView.dealTransferSuccess();
                    }else {
                        fragmentView.showTransferFailed();
                    }
                }
            };

            postTransferSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
        }
    }

    @Override
    public void loadMore(TransferDataEntity ycTransferSalesEntities) {
        //没有分页，不需要实现
    }

    @Override
    public void refresh(TransferDataEntity entity) {
        if(null != entity && null != entity.getData() && entity.getData().size() > 0) {
            this.getDataList().clear();
            this.getDataList().addAll(entity.getData());
            originalList.clear();
            originalList.addAll(entity.getData());
            //过滤掉自已
            String userId = UserInfoUtils.getUserId();
            for(int i = 0; i < originalList.size(); i++){
                if(!TextUtils.isEmpty(originalList.get(i).getUserId()) && originalList.get(i).getUserId().equals(userId)){
                    originalList.remove(i);
                    dataList.remove(i);
                    break;
                }
            }
            mView.refresh();
        }else {
            dealNoDataByPullDown();
        }
    }

    @Override
    public void searchTransferSales(String searchText) {
        if(originalList.size()  == 0){
            return;
        }
        searchResultList.clear();
        if(!TextUtils.isEmpty(searchText)) {
            for (SMYCTransferSalesEntity entity : originalList) {
                if (entity.getDisplayName().toUpperCase().contains(searchText.toUpperCase())) {
                    searchResultList.add(entity);
                }
            }
        }else {
            searchResultList.addAll(originalList);
        }
        if(searchResultList.size() == 0){
            mView.showNoDataLayout();
        }else {
            this.getDataList().clear();
            this.getDataList().addAll(searchResultList);
            mView.refresh();
        }
    }

    @Override
    protected void doHandlerAction1() { //转移黄卡请求超时后执行
        if(null != postTransferSubscription){
            postTransferSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }
}
