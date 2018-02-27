package com.svw.dealerapp.mvpframe.listfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.adapter.LinearDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.adapter.RelativeDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.home.fragment.HomeFragment;
import com.svw.dealerapp.ui.home.fragment.RdHomeFragment;
import com.svw.dealerapp.ui.mine.fragment.ApproveCompleteFragment;
import com.svw.dealerapp.ui.mine.fragment.ApproveWaitFragment;
import com.svw.dealerapp.ui.mine.fragment.NotificationListFragment;
import com.svw.dealerapp.ui.mine.fragment.ScheduleCompleteFragment;
import com.svw.dealerapp.ui.mine.fragment.ScheduleWaitFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.CustomerDetailRemarkListFragment;
import com.svw.dealerapp.ui.optionalpackage.fragment.OptionalPackageListFragment;
import com.svw.dealerapp.ui.order.fragment.AppraiserListFragment;
import com.svw.dealerapp.ui.report.fragment.ReportFragment;
import com.svw.dealerapp.ui.resource.fragment.DealCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.FailedCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.OrderCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.SalesFilterListFragment;
import com.svw.dealerapp.ui.resource.fragment.SleepCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.TrafficFragment;
import com.svw.dealerapp.ui.resource.fragment.YellowCardFragment;
import com.svw.dealerapp.ui.resource.fragment.YellowCardSearchFragment;
import com.svw.dealerapp.ui.resource.fragment.YellowCardTransferListFragment;
import com.svw.dealerapp.ui.task.fragment.TaskECommerceFragment;
import com.svw.dealerapp.ui.task.fragment.TaskFollowUpFragment;
import com.svw.dealerapp.ui.task.fragment.TaskLeadsFragment;
import com.svw.dealerapp.ui.widget.LoadMoreFooterView;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.CollectionUtils;
import com.svw.dealerapp.util.ToastUtils;

/**
 * Created by qinshi on 4/28/2017.
 */

public abstract class BaseListFragment<T, M> extends BaseFragment implements ListFragmentContract.View<T, M>, OnLoadMoreListener, OnRefreshListener {

    protected boolean isLoading = true;
    protected boolean isFirstShowFragment = false;
    protected ListFragmentPresenter<T, M> presenter;
    protected SMListFragmentPresenter<T, M> smPresenter;
    protected String pageSize = "10";
    protected IRecyclerView rvRecyclerView;
    protected String filterString;
    protected FreshReceiver freshReceiver;
    protected LinearLayoutManager layoutManager;
    protected TextView tvHorizontalTag;

    private ViewGroup rootView;
    private LinearLayout llErrorLayout;
    private TextView tvErrorDesc;
    private LinearLayout llLoadingLayout;
    private LoadMoreFooterView loadMoreFooterView;
    private View refreshHeaderView;
    private ProgressBar pbLoading;
    private LoadingDialog loadingDialog;

    private BaseRecyclerViewAdapter adapter;
    private int pageIndex = 1;

    private boolean hasFilter = false; //当前列表数据是否带过过滤条件
    private boolean isShowHorizontalTag = false; //是否显示月份横条

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        setContentView(R.layout.fragment_list_base);
        rootView = (ViewGroup) getContentView();
        assignViews(rootView);

        layoutManager = new LinearLayoutManager(getActivity());
        rvRecyclerView.setLayoutManager(layoutManager);
        adapter = getAdapter();
        if(null != adapter){
            rvRecyclerView.setIAdapter(adapter);
        }

        if (isFirstShowFragment) {
            showLoadingView();
        }

        rvRecyclerView.setOnLoadMoreListener(this);
        rvRecyclerView.setOnRefreshListener(this);

        // TODO: 5/15/2017
        if(this instanceof YellowCardTransferListFragment ||
                this instanceof YellowCardFragment ||
                this instanceof TaskFollowUpFragment ||
                this instanceof TaskECommerceFragment ||
                this instanceof HomeFragment ||
                this instanceof RdHomeFragment ||
                this instanceof ScheduleWaitFragment ||
                this instanceof ScheduleCompleteFragment ||
                this instanceof ApproveWaitFragment ||
                this instanceof ApproveCompleteFragment ||
                this instanceof NotificationListFragment ||
                this instanceof CustomerDetailRemarkListFragment ||
                this instanceof OrderCustomerFragment ||
                this instanceof DealCustomerFragment ||
                this instanceof TaskLeadsFragment ||
                this instanceof ReportFragment ||
                this instanceof FailedCustomerFragment ||
                this instanceof SleepCustomerFragment ||
                this instanceof SalesFilterListFragment ||
                this instanceof AppraiserListFragment ||
                this instanceof OptionalPackageListFragment){
            if(!(this instanceof YellowCardSearchFragment)) {
                if(this instanceof FailedCustomerFragment ||
                        this instanceof SleepCustomerFragment){
                    rvRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            requestDataFromServer();
                        }
                    }, 5000);
                }else {
                    requestDataFromServer();
                }
            }
        }else {
            llLoadingLayout.setVisibility(View.GONE);
            rvRecyclerView.setRefreshEnabled(false);
            rvRecyclerView.setLoadMoreEnabled(false);
        }

        if(isShowHorizontalTag){
            setRecycleViewScrollListener();
        }
    }

    /**
     * 请求数据
     */
    public void requestDataFromServer(){
        showLoadingLayout();
        showLoadingView();
        filterString = getFilter();
        if(isUseSMToGetListData()) {
            smPresenter.getDataFromServer(getActivity(), "1", pageSize, "1", filterString, SMListFragmentPresenter.REQUEST_BY_INIT, getMoreParams());
        }else {
            presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filterString, ListFragmentPresenter.REQUEST_BY_INIT, getMoreParams());
        }
    }

    private void assignViews(View view) {
        rvRecyclerView = (IRecyclerView) view.findViewById(R.id.rv_leads_recycler_view);
//        llErrorLayout = (LinearLayout) view.findViewById(R.id.ll_error_layout);
        llLoadingLayout = (LinearLayout) view.findViewById(R.id.ll_loading_layout);
        pbLoading = (ProgressBar) view.findViewById(R.id.pb_loading);
//        tvErrorDesc = (TextView) view.findViewById(R.id.tv_error_desc);
        tvHorizontalTag = (TextView) view.findViewById(R.id.tv_horizontal_tag);

        llErrorLayout = (LinearLayout) View.inflate(getActivity(), R.layout.ui_header_error_layout, null);
        tvErrorDesc = (TextView) llErrorLayout.findViewById(R.id.tv_error_desc);
        rvRecyclerView.addHeaderView(llErrorLayout);

        loadMoreFooterView = (LoadMoreFooterView) rvRecyclerView.getLoadMoreFooterView();
        refreshHeaderView = rvRecyclerView.getRefreshHeaderView();
    }

    /**
     * 设置recycleview的滚动监听，切换月份
     */
    private void setRecycleViewScrollListener(){
        rvRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy >= 0) {
                    if (presenter.getShowMonthPositionList().contains(layoutManager.findFirstVisibleItemPosition() - 2)) {
                        tvHorizontalTag.setText(presenter.getShowMonthMap().get(layoutManager.findFirstVisibleItemPosition() - 2));
                    }
                }else {
                    if (presenter.getShowMonthPositionList().contains(layoutManager.findFirstVisibleItemPosition() - 1)) {
                        int index = CollectionUtils.getIndex(presenter.getShowMonthPositionList(),
                                layoutManager.findFirstVisibleItemPosition() - 1);
                        if(index > 0) {
                            index = index - 1;
                        }
                        tvHorizontalTag.setText(presenter.getShowMonthMap().get(index));
                    }
                }
            }
        });
    }

    @Override
    public void showLoadingLayout() {
        isLoading = true;
        pbLoading.setVisibility(View.VISIBLE);
        llLoadingLayout.setVisibility(View.VISIBLE);
        llErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingView() {
        if (isLoading && pbLoading.getVisibility() != View.VISIBLE) {
            pbLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoadingView() {
        if (View.INVISIBLE != pbLoading.getVisibility()) {
            pbLoading.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showTimeoutLayout() {
        isLoading = false;
        setShowErrorLayout();
        tvErrorDesc.setText(getResources().getString(R.string.timeout_error));
    }

    @Override
    public void showNoDataLayout() {
        isLoading = false;
        setShowErrorLayout();
        if(this instanceof TrafficFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_data_traffic));
        }else if(this instanceof YellowCardFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_data_yellow_card));
        }else if(this instanceof TaskLeadsFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_data_task_traffic));
        }else if(this instanceof TaskECommerceFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_data_task_e_commerce));
        }else if(this instanceof TaskFollowUpFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_data_task_follow));
        }else if(this instanceof ScheduleWaitFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_data_schedule_wait));
        }else if(this instanceof ScheduleCompleteFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_data_schedule_complete));
        }else if(this instanceof ApproveWaitFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_data_approve_wait));
        }else if(this instanceof ApproveCompleteFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_data_approve_complete));
        }else if(this instanceof NotificationListFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_data_notification));
        }else if(this instanceof OrderCustomerFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_order_customer));
        }else if(this instanceof DealCustomerFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_deal_customer));
        }else if(this instanceof SleepCustomerFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_sleep_customer));
        }else if(this instanceof FailedCustomerFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_failed_customer));
        }else if(this instanceof OptionalPackageListFragment){
            tvErrorDesc.setText(getResources().getString(R.string.empty_optional_package));
        }else {
            tvErrorDesc.setText(getResources().getString(R.string.empty_data));
        }
    }

    @Override
    public void showServerErrorLayout() {
        isLoading = false;
        setShowErrorLayout();
        tvErrorDesc.setText(getResources().getString(R.string.server_error));
    }

    @Override
    public void showNetWorkErrorLayout() {
        isLoading = false;
        setShowErrorLayout();
        tvErrorDesc.setText(getResources().getString(R.string.network_error));
    }

    @Override
    public void showTokenInvalidLayout() {
        isLoading = false;
        setShowErrorLayout();
        tvErrorDesc.setText(getResources().getString(R.string.token_error));
    }

    protected void setShowErrorLayout(){
        if(isUseSMToGetListData()) {
            smPresenter.dataList.clear();
        }else {
            presenter.dataList.clear();
        }
        adapter.notifyDataSetChanged();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(llErrorLayout.getLayoutParams());
        params.height = rvRecyclerView.getHeight();
        llErrorLayout.setLayoutParams(params);
        llErrorLayout.setVisibility(View.VISIBLE);
        llLoadingLayout.setVisibility(View.GONE);
        rvRecyclerView.setLoadMoreEnabled(false);
        loadMoreFooterView.setVisibility(View.GONE);
    }


    @Override
    public void showNoDataToast() {
        rvRecyclerView.setRefreshing(false);
        ToastUtils.showToast(getResources().getString(R.string.empty_data));
    }

    @Override
    public void showNetWorkErrorToast() {
        rvRecyclerView.setRefreshing(false);
        ToastUtils.showToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void showServerErrorToast() {
        rvRecyclerView.setRefreshing(false);
        ToastUtils.showToast(getResources().getString(R.string.server_error));
    }

    @Override
    public void showTimeOutToast() {
        rvRecyclerView.setRefreshing(false);
        ToastUtils.showToast(getResources().getString(R.string.timeout_error));
    }

    @Override
    public void showLoadMoreNetWorkError() {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.ERROR,
                getResources().getString(R.string.network_error));
    }

    @Override
    public void showLoadMoreNoData() {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.ERROR,
                getResources().getString(R.string.no_more_data));
    }

    @Override
    public void showLoadMoreTimeout() {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.ERROR,
                getResources().getString(R.string.timeout_error));
    }

    @Override
    public void showLoadMoreServerError() {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.ERROR,
                getResources().getString(R.string.server_error));
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
    public void hidePullDownLoading() {
        rvRecyclerView.setRefreshing(false);
    }

    @Override
    public void refresh() {
        isLoading = false;
        if(adapter instanceof LinearDragRecyclerViewAdapter){
            ((LinearDragRecyclerViewAdapter) adapter).clearIntercept();
        }else if(adapter instanceof RelativeDragRecyclerViewAdapter){
            ((RelativeDragRecyclerViewAdapter) adapter).clearIntercept();
        }
        adapter.notifyDataSetChanged();
        llErrorLayout.setVisibility(View.GONE);
        llLoadingLayout.setVisibility(View.GONE);

        rvRecyclerView.setLoadMoreEnabled(true);
        loadMoreFooterView.setVisibility(View.VISIBLE);

        rvRecyclerView.smoothScrollToPosition(0);
        pageIndex = 1;

        int dataListSize;
        if(isUseSMToGetListData()) {
            dataListSize = smPresenter.getDataList().size();
        }else {
            dataListSize = presenter.getDataList().size();
        }
        if(dataListSize < Integer.parseInt(pageSize)){
            rvRecyclerView.setLoadMoreEnabled(false);
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.ERROR,
                    getResources().getString(R.string.all_is_load));
//            loadMoreFooterView.setVisibility(View.GONE);
        }else {
            rvRecyclerView.setLoadMoreEnabled(true);
//            loadMoreFooterView.setVisibility(View.VISIBLE);
        }

        if(isShowHorizontalTag){
            tvHorizontalTag.setVisibility(View.VISIBLE);
        }else {
            tvHorizontalTag.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadMore() {
        isLoading = false;
        if(adapter instanceof LinearDragRecyclerViewAdapter){
            ((LinearDragRecyclerViewAdapter) adapter).clearIntercept();
        }else if(adapter instanceof RelativeDragRecyclerViewAdapter){
            ((RelativeDragRecyclerViewAdapter) adapter).clearIntercept();
        }
        adapter.notifyDataSetChanged();
        pageIndex = pageIndex + 1;
    }

    /**
     * 设置该Fragment是否是ViewPager中第一个显示的Fragment
     *
     * @param isFirstShowFragment
     */
    public void setIsFirstShowFragment(boolean isFirstShowFragment) {
        this.isFirstShowFragment = isFirstShowFragment;
    }

    @Override
    public void onLoadMore() {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING, null);
        if(isUseSMToGetListData()) {
            smPresenter.getDataFromServer(getActivity(), String.valueOf(pageIndex + 1), pageSize, "1", filterString, SMListFragmentPresenter.REQUEST_BY_LOAD_MORE, getMoreParams());
        }else {
            presenter.getDataFromServer(getActivity(), String.valueOf(pageIndex + 1), pageSize, "1", filterString, ListFragmentPresenter.REQUEST_BY_LOAD_MORE, getMoreParams());
        }
    }

    @Override
    public void onRefresh() {
        rvRecyclerView.setRefreshing(true);
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE, null);
        if(isUseSMToGetListData()) {
            smPresenter.getDataFromServer(getActivity(), "1", pageSize, "1", filterString, SMListFragmentPresenter.REQUEST_BY_PULL_DOWN, getMoreParams());
        }else {
            presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filterString, ListFragmentPresenter.REQUEST_BY_PULL_DOWN, getMoreParams());
        }
    }

    /**
     * 隐藏加载更多的视图
     */
    protected void hideLoadMoreFooterView(){
        rvRecyclerView.setLoadMoreEnabled(false);
        loadMoreFooterView.setVisibility(View.GONE);
    }

    /**
     * 设置页脚的颜色
     * @param color
     */
    protected void setFooterBgColor(int color){
        loadMoreFooterView.setBackgroundColor(color);
    }

    /**
     * 隐藏加载更多
     */
    protected void hideFooter(){
        rvRecyclerView.setLoadMoreEnabled(false);
        loadMoreFooterView.setVisibility(View.GONE);
    }

    /**
     * 显示加载更多
     */
    protected void showFooter(){
        rvRecyclerView.setLoadMoreEnabled(true);
        loadMoreFooterView.setVisibility(View.VISIBLE);
    }

    /**
     * 设置是否显示横条
     * @param isShowHorizontalTag
     */
    protected void setShowHorizontalTag(boolean isShowHorizontalTag) {
        this.isShowHorizontalTag = isShowHorizontalTag;
    }

    /**
     * 设置加载状态
     * @param refreshing
     */
    @Override
    public void setRefreshing(boolean refreshing){
        rvRecyclerView.setRefreshing(refreshing);
    }

    public abstract BaseRecyclerViewAdapter getAdapter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != presenter){
            presenter.onDestroy();
        }
        if(null != freshReceiver) {
            getActivity().unregisterReceiver(freshReceiver);
        }
    }

    /**
     * 获取请求的额外参数，由子类实现返回
     * @return
     */
    public String[] getMoreParams(){
        return null;
    }

    /**
     * 获取进入应用首次请求时的过滤条件，由子类实现返回
     * @return
     */
    public String getFilter(){
        return null;
    }

    /**
     * 设置脚的内边距
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    protected void setFooterViewPadding(int left, int top, int right, int bottom){
        loadMoreFooterView.setPadding(left, top, right, bottom);
    }

    /**
     * 设置头部的margin
     * @param flag  大于0设置marginleft，小于0设置marginright
     * @param marginValue
     */
    protected void setHeaderViewMargin(int flag, int marginValue){
        if(null != refreshHeaderView) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(refreshHeaderView.getLayoutParams());
            if(flag > 0) {
                layoutParams.leftMargin = marginValue;
            }else {
                layoutParams.rightMargin = marginValue;
            }
            refreshHeaderView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置错页的padding
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    protected void setLlErrorLayout(int left, int top, int right, int bottom){
        llErrorLayout.setPadding(left, top, right, bottom);
    }


    protected void addView(View view, int position){
        rootView.addView(view, position);
    }

    /**
     * 注册刷新页面的广播
     * @param ReceiverFilterString
     */
    protected void registerFreshReceiver(String ReceiverFilterString){
        IntentFilter filter = new IntentFilter(ReceiverFilterString);
        freshReceiver = new FreshReceiver();
        getActivity().registerReceiver(freshReceiver, filter);
    }

    /**
     * 刷新列表的广播接收者
     */
    private class FreshReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filterString, ListFragmentPresenter.REQUEST_BY_INIT, getMoreParams());
        }
    }

    public boolean isHasFilter() {
        return hasFilter;
    }

    public void setHasFilter(boolean hasFilter) {
        this.hasFilter = hasFilter;
    }

    public boolean isUseSMToGetListData() {
        return false;
    }
}
