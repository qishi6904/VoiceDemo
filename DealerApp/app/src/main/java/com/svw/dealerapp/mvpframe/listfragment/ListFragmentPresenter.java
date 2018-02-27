package com.svw.dealerapp.mvpframe.listfragment;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.util.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 4/28/2017.
 */

public abstract class ListFragmentPresenter<T, M> extends BaseHandlerPresenter<ListFragmentContract.Model, ListFragmentContract.View>
        implements ListFragmentContract.Presenter<T, ResEntity<T>> {
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

    private List<M> showMonthEntities = new ArrayList<>();
    private List<String> showMonthList = new ArrayList<>();
    private List<Integer> showMonthPositionList = new ArrayList<>();
    private Map<Integer, String> showMonthMap = new HashMap<>();
    private int currentPageIndex = 1;

    public ListFragmentPresenter(final ListFragmentContract.View<T, M> view, ListFragmentContract.Model<ResEntity<T>> model){
        super(view, model);
    }

    public BaseObserver<ResEntity<T>> createObserver(final String pageIndex){
        return new BaseObserver<ResEntity<T>>(){

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
            public void onNext(ResEntity<T> t) {
                handler.removeMessages(handlerAction9);
                if(null != t && "200".equals(t.getRetCode())) {

                    try {
                        currentPageIndex = Integer.parseInt(pageIndex);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    if ("1".equals(pageIndex)) {
                        mView.hidePullDownLoading();
                        refresh(t.getRetData());
                    } else {
                        loadMore(t.getRetData());
                    }
                }else {
                    if(null != t && "401".equals(t.getRetCode())){
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

        this.requestType = requestType;

        if(null == context){
            return;
        }

        if (!NetworkUtils.isNetworkAvailable(context)) {
            handler.sendEmptyMessageDelayed(handlerAction8, 500);
        } else{
            isRequesting = true;
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
     * 处理刷新时item上显示的月份相关数据
     */
    protected void dealMonthDataByRefresh(){
        showMonthEntities.clear();
        showMonthPositionList.clear();
        showMonthMap.clear();
        for(int i = 0; i < this.getDataList().size(); i++) {
            String yearMonth = getShowMonth(this.getDataList().get(i));
            if(!TextUtils.isEmpty(yearMonth)) {
                if (i == 0) {
                    showMonthEntities.add(this.getDataList().get(i));
                    showMonthList.add(yearMonth);
                    showMonthMap.put(i, yearMonth);
                    showMonthPositionList.add(i);
                } else {
                    if (i != 0 && null != yearMonth && !yearMonth.equals(showMonthList.get(showMonthList.size() - 1))) {
                        showMonthEntities.add(this.getDataList().get(i));
                        showMonthList.add(yearMonth);
                        showMonthMap.put(i, yearMonth);
                        showMonthPositionList.add(i);
                    }
                }
            }
        }
    }

    /**
     * 处理加载更多时item上显示的月份相关数据
     */
    protected void dealMonthDataByLoadMore(){
        for(int i = (currentPageIndex - 1) * 10; i < this.getDataList().size(); i++) {
            String yearMonth = getShowMonth(this.getDataList().get(i));
            if(!TextUtils.isEmpty(yearMonth)) {
                if (null != yearMonth && !yearMonth.equals(showMonthList.get(showMonthList.size() - 1))) {
                    showMonthEntities.add(this.getDataList().get(i));
                    showMonthList.add(yearMonth);
                    showMonthPositionList.add(i);
                    showMonthMap.put(i, yearMonth);
                }
            }
        }
    }

    public List<M> getShowMonthEntities() {
        return showMonthEntities;
    }

    public Map<Integer, String> getShowMonthMap() {
        return showMonthMap;
    }

    public List<Integer> getShowMonthPositionList() {
        return showMonthPositionList;
    }

    /**
     * item上月份的字符串，每个子类显示的字段不一样，由子类实现
     * @param m
     * @return
     */
    protected String getShowMonth(M m){
        return null;
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
