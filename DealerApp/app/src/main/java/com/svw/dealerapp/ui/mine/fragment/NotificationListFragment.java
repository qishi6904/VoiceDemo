package com.svw.dealerapp.ui.mine.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.mine.NotificationEntity;
import com.svw.dealerapp.entity.mine.NotificationEntity.NotificationInfoEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.mine.activity.ApproveActivity;
import com.svw.dealerapp.ui.mine.activity.NotificationActivity;
import com.svw.dealerapp.ui.mine.adapter.NotificationListAdapter;
import com.svw.dealerapp.ui.mine.contract.NotificationContract;
import com.svw.dealerapp.ui.mine.model.NotificationModel;
import com.svw.dealerapp.ui.mine.presenter.NotificationPresenter;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailItemActivity;
import com.svw.dealerapp.ui.task.activity.TaskActivity;
import com.svw.dealerapp.util.ScreenUtils;
import com.svw.dealerapp.util.ToastUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinshi on 6/1/2017.
 */

public class NotificationListFragment extends BaseListFragment<NotificationEntity, NotificationInfoEntity>
        implements NotificationContract.View {

    private NotificationListAdapter adapter;
    private List<NotificationInfoEntity> selectDataList = new ArrayList<>();

    private boolean isShowEdit = false;
    private int rvParentScrollX;
    private int showEditScrollX;
    private ViewGroup rvParentView;
    private ObjectAnimator showEditAnimator;
    private ObjectAnimator hideEditAnimator;
    private LinearLayout.LayoutParams rvLayoutParams;

    private NotificationActivity notificationActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationActivity = (NotificationActivity) getActivity();
        presenter = new NotificationPresenter(this, new NotificationModel());
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new NotificationListAdapter(getActivity(), presenter.getDataList(), this);

        rvParentView = (ViewGroup) rvRecyclerView.getParent();
        showEditScrollX = getResources().getDimensionPixelOffset(R.dimen.mine_notification_radio_width);

        adapter.setOnItemClickListener(new NotificationListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, NotificationInfoEntity entity) {
                if (isShowEdit) {
                    if (entity.isSelect()) {
                        selectDataList.add(entity);
                    } else {
                        selectDataList.remove(entity);
                    }
                    if (selectDataList.size() > 0) {
                        ((NotificationActivity) getActivity()).changeReadDeleteBtnColor(true);
                    } else {
                        ((NotificationActivity) getActivity()).changeReadDeleteBtnColor(false);
                    }
                } else {
                    if ("0".equals(entity.getNotifStatus())) {
                        try {
                            NotificationPresenter notificationPresenter = (NotificationPresenter) presenter;
                            Map<String, Object> options = new HashMap<>();
                            List<String> idList = new ArrayList<>();
                            idList.add(entity.getNotifID());
//                            String idListStr = URLEncoder.encode(GsonUtils.changeEntityToJsonStr(idList), "utf-8");
                            options.put("noticeIDList", idList);
                            notificationPresenter.setNotificationRead(getActivity(), options, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    entity.setNotifStatus("1");
                    goToActivityByMap(getContext(), position);
                }

                if (selectDataList.size() == presenter.getDataList().size()) {
                    notificationActivity.setSelectAll(true);
                } else {
                    notificationActivity.setSelectAll(false);
                }
            }
        });

        rvLayoutParams = new LinearLayout.LayoutParams(rvRecyclerView.getLayoutParams());
        rvLayoutParams.width = ScreenUtils.getScreenWidth(getActivity()) + getResources().getDimensionPixelOffset(R.dimen.mine_notification_radio_width);
        rvLayoutParams.height = 0;
        rvLayoutParams.weight = 1;
        rvRecyclerView.setLayoutParams(rvLayoutParams);
        setFooterViewPadding(getResources().getDimensionPixelOffset(R.dimen.mine_notification_radio_width), 0, 0, 0);
        setLlErrorLayout(0, 0, getResources().getDimensionPixelOffset(R.dimen.mine_notification_radio_width), 0);
        setHeaderViewMargin(-1, getResources().getDimensionPixelOffset(R.dimen.mine_notification_radio_width));

        return adapter;
    }

    /**
     * 处理编辑的显示、隐藏
     */
    public void dealItemEdit() {
        if (null != rvParentView) {
            if (rvParentView.getScrollX() != 0 && rvParentView.getScrollX() != showEditScrollX) {
                return;
            }
            if (isShowEdit) {
                if (null == showEditAnimator) {
                    showEditAnimator = ObjectAnimator.ofInt(this, "rvParentScrollX", showEditScrollX);
                    showEditAnimator.setDuration(300);
                }
                showEditAnimator.start();
            } else {
                if (null == hideEditAnimator) {
                    hideEditAnimator = ObjectAnimator.ofInt(this, "rvParentScrollX", 0);
                    hideEditAnimator.setDuration(300);
                }
                hideEditAnimator.start();
            }
            isShowEdit = !isShowEdit;
        }
    }

    public boolean isShowEdit() {
        return isShowEdit;
    }

    /**
     * 获取选中集合
     *
     * @return
     */
    public List<NotificationInfoEntity> getSelectDataList() {
        return selectDataList;
    }

    /**
     * 清除选中的item
     */
    public void deleteItem() {
        for (NotificationInfoEntity entity : selectDataList) {
            presenter.getDataList().remove(entity);
        }
        selectDataList.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置已选的item为已读状态
     */
    public void setSelectListToHasReadStatus() {
        for (NotificationInfoEntity entity : selectDataList) {
            entity.setNotifStatus("1");
            entity.setSelect(false);
        }
        selectDataList.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * 清除所有选中的item
     */
    public void clearAllSelect() {
        for (NotificationInfoEntity entity : selectDataList) {
            entity.setSelect(false);
        }
        selectDataList.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * 所有选中的item
     */
    public void selectAll() {
        selectDataList.clear();
        selectDataList.addAll(presenter.getDataList());
        for (NotificationInfoEntity entity : selectDataList) {
            entity.setSelect(true);
        }
        adapter.notifyDataSetChanged();
    }

    public int getRvParentScrollX() {
        rvParentScrollX = rvParentView.getScrollX();
        return rvParentScrollX;
    }

    public void setRvParentScrollX(int rvParentScrollX) {
        this.rvParentScrollX = rvParentScrollX;
        rvParentView.setScrollX(rvParentScrollX);
    }

    /**
     * 设置RecycleView的下外边距
     *
     * @param marginBottom
     */
    public void setRecycleViewMarginBottom(int marginBottom) {
        rvLayoutParams.bottomMargin = marginBottom;
        rvRecyclerView.setLayoutParams(rvLayoutParams);
    }

    @Override
    public void refresh() {

        super.refresh();

        if (null != rvParentView) {
            rvParentView.setScrollX(showEditScrollX);
            setLlErrorLayout(showEditScrollX, 0, 0, 0);
        }
        setHeaderViewMargin(1, getResources().getDimensionPixelOffset(R.dimen.mine_notification_radio_width));

        notificationActivity.setEditVisibility(View.VISIBLE);
    }

    @Override
    public void showNetWorkErrorLayout() {
        super.showNetWorkErrorLayout();
        notificationActivity.setEditVisibility(View.GONE);
    }

    @Override
    public void showServerErrorLayout() {
        super.showServerErrorLayout();
        notificationActivity.setEditVisibility(View.GONE);
    }

    @Override
    public void showNoDataLayout() {
        super.showNoDataLayout();
        notificationActivity.setEditVisibility(View.GONE);
    }

    @Override
    public void showTimeoutLayout() {
        super.showTimeoutLayout();
        notificationActivity.setEditVisibility(View.GONE);
    }

    /**
     * 是否可以显示编辑状态
     *
     * @return
     */
    public boolean canShowEdit() {
        return null != rvLayoutParams;
    }

    @Override
    public void setNotificationReadSuccess() {
        setSelectListToHasReadStatus();
        ToastUtils.showToast(getResources().getString(R.string.mine_notification_set_read_success));
        notificationActivity.dealEditBarAnim();
        dealItemEdit();
    }

    @Override
    public void setNotificationReadFail() {
        ToastUtils.showToast(getResources().getString(R.string.mine_notification_set_read_fail));
    }

    @Override
    public void deleteNotificationSuccess() {
        deleteItem();
        ToastUtils.showToast(getResources().getString(R.string.mine_notification_delete_success));
        notificationActivity.dealEditBarAnim();
        dealItemEdit();
        if (presenter.getDataList().size() == 0) {
//            rvRecyclerView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    showNoDataLayout();
//                }
//            }, 200);
            requestDataFromServer();
        }
    }

    @Override
    public void deleteNotificationFail() {
        ToastUtils.showToast(getResources().getString(R.string.mine_notification_delete_fail));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != presenter) {
            presenter.onDestroy();
        }
    }

    /**
     * 调用服务设置为已读
     */
    public void setBeRead() {
        if (selectDataList.size() > 0) {
            NotificationPresenter notificationPresenter = (NotificationPresenter) presenter;
            List<String> readStr = new ArrayList<>();
            for (int i = 0; i < selectDataList.size(); i++) {
                readStr.add(selectDataList.get(i).getNotifID());
            }
            Map<String, Object> options = new HashMap<>();
            options.put("noticeIDList", readStr);
            notificationPresenter.setNotificationRead(getActivity(), options, true);
        }
    }

    /**
     * 删除通知
     */
    public void deleteNotification() {
        if (selectDataList.size() > 0) {
            NotificationPresenter notificationPresenter = (NotificationPresenter) presenter;
            List<String> readStr = new ArrayList<>();
            for (int i = 0; i < selectDataList.size(); i++) {
                readStr.add(selectDataList.get(i).getNotifID());
            }
            Map<String, Object> options = new HashMap<>();
            options.put("noticeIDList", readStr);
            notificationPresenter.postDeleteNotification(getActivity(), options);
        }
    }

    public void setCanPullDownFresh(boolean canPullDownFresh) {
        rvRecyclerView.setRefreshEnabled(canPullDownFresh);
    }

    private void goToActivityByMap(Context context, int position) {
//        Gson gson = new Gson();
//        Map<String, Object> option = gson.fromJson(extraMap, Map.class);
//        String activity = option.get("activity").toString();
//        Object oppIdObject = option.get("oppId");
//        String oppId = "";
//        if (null != oppIdObject) {
//            oppId = oppIdObject.toString();
//        }
        if (presenter.getDataList() == null || presenter.getDataList().size() == 0) {
            return;
        }

        String activity = presenter.getDataList().get(position).getActivity();
        String oppId = presenter.getDataList().get(position).getOppId();

        Intent intent = null;
        if ("1001".equals(activity)) {    // 接收线索/客流－线索跟进任务页面
            intent = new Intent(context.getApplicationContext(), TaskActivity.class);
            intent.putExtra("position", 0); //要跳到的一级导航的位置
        } else if ("1002".equals(activity)) {  // 接收客户－该客户的黄卡详情页
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        } else if ("1003".equals(activity)) {  // 客户信息更新－黄卡详情页_备注页面
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailItemActivity.class);
            intent.putExtra("fragment", NewCustomerConstants.FRAGMENT_TAG_REMARK);
            intent.putExtra("oppId", oppId);
        } else if ("1004".equals(activity)) {  // 预约提醒－黄卡详情页
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        } else if ("1005".equals(activity)) {  // 审批请求－审批列表_待审批
            intent = new Intent(context.getApplicationContext(), ApproveActivity.class);
            intent.putExtra("firstNavPosition", 0); //要跳到的一级导航的位置
        } else if ("1006".equals(activity)) {  // 审批通过－审批列表_已审批
            intent = new Intent(context.getApplicationContext(), ApproveActivity.class);
            intent.putExtra("firstNavPosition", 1); //要跳到的一级导航的位置
        } else if ("1007".equals(activity)) {  // 审批驳回－审批列表_已审批
            intent = new Intent(context.getApplicationContext(), ApproveActivity.class);
            intent.putExtra("firstNavPosition", 1); //要跳到的一级导航的位置
        }
//        else if ("1008".equals(activity)) {    //未处理信息－通知列表页
//            intent = new Intent(context.getApplicationContext(), NotificationActivity.class);
//        }
        else if ("1009".equals(activity)) {    //查重合并－黄页详情页
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        } else if ("1010".equals(activity)) {    //重复黄卡－黄页详情页
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        } else if ("1011".equals(activity)) {    //订单状态变更为开票中－黄页详情页
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        } else if ("1012".equals(activity)) {     //订单状态变更为已开票－黄页详情页
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        }

        if (null != intent) {
            intent.putExtra("isFromNotice", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);
        }

    }

}
