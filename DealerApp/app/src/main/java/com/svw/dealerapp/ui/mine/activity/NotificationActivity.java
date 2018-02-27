package com.svw.dealerapp.ui.mine.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.ui.mine.contract.NotificationContract;
import com.svw.dealerapp.ui.mine.fragment.NotificationListFragment;
import com.svw.dealerapp.ui.mine.model.NotificationModel;
import com.svw.dealerapp.ui.mine.presenter.NotificationPresenter;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

/**
 * Created by qinshi on 6/1/2017.
 */

public class NotificationActivity extends BaseActivity {

    private ImageView ivBack;
    private FrameLayout flList;
    private NotificationListFragment fragment;
    private TextView tvEdit;
    private LinearLayout llEditBar;
    private LinearLayout llSelectAll;
    private ImageView ivSelectAll;
    private LinearLayout llRead;
    private TextView tvRead;
    private LinearLayout llDelete;
    private TextView tvDelete;
    private ImageView ivRead;
    private ImageView ivDelete;

    private int llEditBarMarginBottom;
    private int llEditBarHeight;
    private RelativeLayout.LayoutParams editBarParams;
    private ObjectAnimator showEditBarAnimator;
    private ObjectAnimator hideEditBarAnimator;

    private boolean isSelectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        assignViews();


        ivBack.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
        llSelectAll.setOnClickListener(this);
        llRead.setOnClickListener(this);
        llDelete.setOnClickListener(this);

        llEditBarHeight = getResources().getDimensionPixelOffset(R.dimen.mine_notification_edit_bar_height);
        editBarParams = new RelativeLayout.LayoutParams(llEditBar.getLayoutParams());
        editBarParams.bottomMargin = -llEditBarHeight;
        editBarParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        llEditBar.setLayoutParams(editBarParams);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TalkingDataUtils.onPageStart(this,"我的-通知");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(null != intent) {
            boolean isFromNotice = intent.getBooleanExtra("isFromNotice", false);
            if(isFromNotice) {
                fragment.requestDataFromServer();
            }
        }
    }

    private void assignViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        flList = (FrameLayout) findViewById(R.id.fl_list);
        tvEdit = (TextView) findViewById(R.id.tv_edit);
        llEditBar = (LinearLayout) findViewById(R.id.ll_edit_bar);
        llSelectAll = (LinearLayout) findViewById(R.id.ll_select_all);
        ivSelectAll = (ImageView) findViewById(R.id.iv_select_all);
        llRead = (LinearLayout) findViewById(R.id.ll_read);
        tvRead = (TextView) findViewById(R.id.tv_read);
        llDelete = (LinearLayout) findViewById(R.id.ll_delete);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        ivRead = (ImageView) findViewById(R.id.iv_read);
        ivDelete = (ImageView) findViewById(R.id.iv_delete);

        fragment = new NotificationListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_list, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                Intent intent = new Intent("com.svw.dealerapp.notice.center.back");
                sendBroadcast(intent);
                finish();
                break;
            case R.id.tv_edit:
                if(!fragment.canShowEdit()){
                    return;
                }
                dealEditBarAnim();
                fragment.dealItemEdit();
                if(fragment.isShowEdit()){
                    tvEdit.setText(getResources().getString(R.string.mine_notification_cancel));
                    TalkingDataUtils.onEvent(this,"取消编辑","我的-通知");
                }else {
                    tvEdit.setText(getResources().getString(R.string.mine_notification_edit));
                    TalkingDataUtils.onEvent(this,"编辑","我的-通知");
                }
                break;
            case R.id.ll_select_all:
                isSelectAll = !isSelectAll;
                if(isSelectAll){
                    fragment.selectAll();
                    ivSelectAll.setImageResource(R.mipmap.ic_notification_select);
                    TalkingDataUtils.onEvent(this,"全选","我的-通知");
                }else {
                    fragment.clearAllSelect();
                    ivSelectAll.setImageResource(R.mipmap.ic_notification_unselect);
                    TalkingDataUtils.onEvent(this,"取消全选","我的-通知");
                }
                changeReadDeleteBtnColor(isSelectAll);
                break;
            case R.id.ll_read:
                if(null == fragment.getSelectDataList() || fragment.getSelectDataList().size() == 0){
                    ToastUtils.showToast(getResources().getString(R.string.mine_notification_empty_tip));
                    return;
                }
                TalkingDataUtils.onEvent(this,"标记已读","我的-通知");
                boolean hasNoReadNotification = false;
                for(int i = 0; i < fragment.getSelectDataList().size(); i++){
                    if("0".equals(fragment.getSelectDataList().get(i).getNotifStatus())){
                        hasNoReadNotification = true;
                        break;
                    }
                }
                if(hasNoReadNotification) {
                    fragment.setBeRead();
                }else {
                    ToastUtils.showToast(getResources().getString(R.string.mine_notification_no_not_read_tip));
                }
                break;
            case R.id.ll_delete:
                if(null == fragment.getSelectDataList() || fragment.getSelectDataList().size() == 0){
                    ToastUtils.showToast(getResources().getString(R.string.mine_notification_empty_tip));
                    return;
                }
                TalkingDataUtils.onEvent(this,"删除","我的-通知");
                fragment.deleteNotification();
                break;
        }
    }

    /**
     * 处理下面编辑栏的动画
     */
    public void dealEditBarAnim(){
        if(editBarParams.bottomMargin != 0 && editBarParams.bottomMargin != -llEditBarHeight){
            return;
        }
        if(fragment.isShowEdit()){
            doHideEditBarAnim();
            tvEdit.setText(getResources().getString(R.string.mine_notification_edit));
        }else {
            doShowEditBarAnim();
            tvEdit.setText(getResources().getString(R.string.mine_notification_cancel));
        }
    }

    private void doShowEditBarAnim(){
        if(null == showEditBarAnimator){
            showEditBarAnimator = ObjectAnimator.ofInt(this, "llEditBarMarginBottom", 0);
            showEditBarAnimator.setDuration(300);
        }
        showEditBarAnimator.start();
        llEditBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                fragment.setRecycleViewMarginBottom(llEditBarHeight - DensityUtil.dp2px(NotificationActivity.this, 8));
            }
        }, 500);
        fragment.setCanPullDownFresh(false);
    }

    private void doHideEditBarAnim(){
        if(null == hideEditBarAnimator){
            hideEditBarAnimator = ObjectAnimator.ofInt(this, "llEditBarMarginBottom", -llEditBarHeight);
            hideEditBarAnimator.setDuration(300);
        }
        hideEditBarAnimator.start();
        fragment.setRecycleViewMarginBottom(0);
        isSelectAll = false;
        llEditBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                fragment.clearAllSelect();
                ivSelectAll.setImageResource(R.mipmap.ic_notification_unselect);
            }
        }, 500);
        fragment.setCanPullDownFresh(true);
    }

    public int getLlEditBarMarginBottom() {
        return editBarParams.bottomMargin;
    }

    public void setLlEditBarMarginBottom(int llEditBarMarginBottom) {
        editBarParams.bottomMargin = llEditBarMarginBottom;
        llEditBar.setLayoutParams(editBarParams);
    }

    /**
     * 切换标记已读、删除 按钮颜色
     * @param hasSelect
     */
    public void changeReadDeleteBtnColor(boolean hasSelect){
        if(hasSelect){
            ivRead.setImageResource(R.mipmap.ic_notification_read_select);
            ivDelete.setImageResource(R.mipmap.ic_notification_delete_select);
            tvRead.setTextColor(getResources().getColor(R.color.mine_blue));
            tvDelete.setTextColor(getResources().getColor(R.color.mine_blue));
        }else {
            ivRead.setImageResource(R.mipmap.ic_notification_read);
            ivDelete.setImageResource(R.mipmap.ic_notification_delete);
            tvRead.setTextColor(getResources().getColor(R.color.resource_assist_text));
            tvDelete.setTextColor(getResources().getColor(R.color.resource_assist_text));
        }
    }

    /**
     * 设置 编辑 按钮是否可见
     * @param visibility
     */
    public void setEditVisibility(int visibility){
        tvEdit.setVisibility(visibility);
    }

    public void setSelectAll(boolean isSelectAll){
        if(isSelectAll){
            ivSelectAll.setImageResource(R.mipmap.ic_notification_select);
        }else {
            ivSelectAll.setImageResource(R.mipmap.ic_notification_unselect);
        }
        this.isSelectAll = isSelectAll;
    }

    @Override
    public void onBackPressed() {
        if(fragment.isShowEdit()){
            doHideEditBarAnim();
            fragment.dealItemEdit();
            tvEdit.setText(getResources().getString(R.string.mine_notification_edit));
        }else {
            Intent intent = new Intent("com.svw.dealerapp.notice.center.back");
            sendBroadcast(intent);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this,"我的-通知");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
