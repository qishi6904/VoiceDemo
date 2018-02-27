package com.svw.dealerapp.mvpframe.listfragment;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 4/28/2017.
 */

public abstract class SMListFragmentPresenter<T, M> extends BaseHandlerPresenter<ListFragmentContract.Model, ListFragmentContract.View>
        implements ListFragmentContract.Presenter<T, SMResEntity<T>> {
    /**
     * 通过下拉的请求
     */
    public final static int REQUEST_BY_PULL_DOWN = 2001;
    /**
     * 通过上拉加载的请求
     */
    public final static int REQUEST_BY_LOAD_MORE = 2002;
    /**
     * 初始化时的请求
     */
    public final static int REQUEST_BY_INIT = 2003;

    protected List<M> dataList = new ArrayList<>();
    protected int requestType = REQUEST_BY_INIT;  //请求方式(下拉/上拉/初始化)，根据不同的请求方式处理异常

    private Subscription subscription;
    private boolean isRequesting = false;

    public SMListFragmentPresenter(final ListFragmentContract.View<T, M> view, ListFragmentContract.Model<SMResEntity<T>> model){
        super(view, model);
    }

    public BaseObserver<SMResEntity<T>> createObserver(final String pageIndex){
        return new BaseObserver<SMResEntity<T>>(){

            @Override
            public void onCompleted() {
                handler.removeMessages(handlerAction9);

                isRequesting = false;
            }

            @Override
            public void onServerError(Throwable e) {
                handler.removeMessages(handlerAction9);
                dealServerError();

                isRequesting = false;
            }

            @Override
            public void onNext(SMResEntity<T> t) {
                handler.removeMessages(handlerAction9);
                if(null != t && "200".equals(t.getStatus())) {
                    if ("1".equals(pageIndex)) {
                        mView.hidePullDownLoading();
                        refresh(t.getData());
                    } else {
                        loadMore(t.getData());
                    }
                }else {
                    if(null != t && "401".equals(t.getStatus())){
                        mView.showTokenInvalidLayout();
                        if(requestType == REQUEST_BY_PULL_DOWN) {
                            mView.setRefreshing(false);
                        }
                    }else {
                        dealServerError();
                    }
                }

                isRequesting = false;
            }
        };
    }

    @Override
    public void getDataFromServer(Context context, final String pageIndex, final String pageSize, final String orderType, final String filter, int requestType, String... moreParams) {

        if(isRequesting){
            return;
        }

        isRequesting = true;

        this.requestType = requestType;

        if(null == context){
            return;
        }

        if (!NetworkUtils.isNetworkAvailable(context)) {
            handler.sendEmptyMessageDelayed(handlerAction8, 500);
        } else{
            subscription = mModel.getDataFromServer(pageIndex, pageSize, orderType, filter, moreParams)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(createObserver(pageIndex));
            handler.sendEmptyMessageDelayed(handlerAction9, timeoutMill);
            mRxManager.add(subscription);
        }
    }

    public List<M> getDataList(){
        return dataList;
    }

    /**
     * 处理请求超时
     */
    private void dealTimeoutError(){
        switch (requestType){
            case REQUEST_BY_PULL_DOWN:
                if(dataList.size() == 0){
                    mView.showTimeoutLayout();
                    mView.setRefreshing(false);
                }
                mView.showTimeOutToast();
                break;
            case REQUEST_BY_LOAD_MORE:
                mView.showLoadMoreTimeout();
                break;
            case REQUEST_BY_INIT:
                mView.showTimeoutLayout();
                break;
        }

        isRequesting = false;
    }

    /**
     * 处理服务异常
     */
    private void dealServerError(){
        switch (requestType){
            case REQUEST_BY_PULL_DOWN:
                if(dataList.size() == 0){
                    mView.showServerErrorLayout();
                    mView.setRefreshing(false);
                }
                mView.showServerErrorToast();
                break;
            case REQUEST_BY_LOAD_MORE:
                mView.showLoadMoreServerError();
                break;
            case REQUEST_BY_INIT:
                mView.showServerErrorLayout();
                break;
        }
    }

    /**
     * 处理网络异常
     */
    private void dealNetworkError(){
        switch (requestType){
            case REQUEST_BY_PULL_DOWN:
                if(dataList.size() == 0){
                    mView.showNetWorkErrorLayout();
                    mView.setRefreshing(false);
                }
                mView.showNetWorkErrorToast();
                break;
            case REQUEST_BY_LOAD_MORE:
                mView.showLoadMoreNetWorkError();
                break;
            case REQUEST_BY_INIT:
                mView.showNetWorkErrorLayout();
                break;
        }
    }

    /**
     * 下拉无数据处理
     */
    protected void dealNoDataByPullDown(){
        this.getDataList().clear();
        mView.refresh();
        mView.showNoDataLayout();
        mView.showNoDataToast();
    }

    @Override
    protected void doHandlerAction9() { //请求超时后执行
        if(null != subscription){
            subscription.unsubscribe();
        }
        dealTimeoutError();
    }

    @Override
    protected void doHandlerAction8() { //无网络
        dealNetworkError();
    }

    public abstract void loadMore(T t);

    public abstract void refresh(T t);

    @Override
    public void onDestroy() {
        super.onDestroy();
        mView.hideLoadingDialog();
    }
}
