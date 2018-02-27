package com.svw.dealerapp.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.drawer.RdLeftListDrawerActivity;
import com.svw.dealerapp.entity.update.AppUpdateEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.main.fragment.RdMainContentFragment;
import com.svw.dealerapp.ui.resource.fragment.ResourceFragment;
import com.svw.dealerapp.ui.update.UpdateActivity;
import com.svw.dealerapp.ui.update.contract.AppUpdateContract;
import com.svw.dealerapp.ui.update.model.AppUpdateModel;
import com.svw.dealerapp.ui.update.presenter.AppUpdatePresenter;
import com.svw.dealerapp.util.dbtools.DBUtils;
import com.svw.dealerapp.util.ToastUtils;

/**
 * Created by qinshi on 1/11/2018.
 */

public class RdMainActivity extends RdLeftListDrawerActivity implements AppUpdateContract.View {

    private AppUpdateContract.Presenter updatePresenter;
    private RdMainContentFragment contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //dev环境不提示自动升级
        String currentEnv = DBUtils.getCurrentEnvLabel();
        if (TextUtils.isEmpty(currentEnv) && currentEnv != "Env0") {
            //检查版本方法
            checkVersion();
        }

        setExit(true);

        dealIntentData(getIntent());

    }

    /**
     * 检查应用版本
     */
    public void checkVersion() {
        if (null == updatePresenter) {
            updatePresenter = new AppUpdatePresenter(this, new AppUpdateModel());
        }
        updatePresenter.checkAppUpdate(this, false);
    }

    /**
     * 处理intent，根据传过来的位置切换页面
     * @param intent
     */
    private void dealIntentData(Intent intent) {
        if (null != intent) {
            int firstNavPosition = intent.getIntExtra("firstNavPosition", -1);
            int secondNavPosition = intent.getIntExtra("secondNavPosition", -1);
            setViewPagerPosition(firstNavPosition, secondNavPosition);
        }
    }

    /**
     * 设置一\二级导航的位置
     *
     * @param firstNavPosition
     * @param secondNavPosition
     */
    public void setViewPagerPosition(int firstNavPosition, int secondNavPosition) {
        if (firstNavPosition >= 0) {
            contentFragment.getNvpMainViewPager().setCurrentItem(firstNavPosition);
        }
        if (firstNavPosition == 1 && secondNavPosition >= 0) {
            contentFragment.getNvpMainViewPager().setCurrentItem(secondNavPosition);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 切换页面
        dealIntentData(intent);
        // 刷新切换到的页面
        if (null != intent) {
            int firstNavPosition = intent.getIntExtra("firstNavPosition", -1);
            int secondNavPosition = intent.getIntExtra("secondNavPosition", -1);
            if (firstNavPosition == 1 && secondNavPosition >= 0 && secondNavPosition <= 1) {
                BaseListFragment fragment = getResourceFragment().getFragment(secondNavPosition);
                if (null != fragment) {
                    fragment.requestDataFromServer();
                }
            }
        }
    }

    //首页检查升级为后台执行，无需实现下列方法----start
    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void hideLoadingDialog() {

    }

    @Override
    public void showServerErrorToast() {

    }

    @Override
    public void showTimeOutToast() {

    }

    @Override
    public void showNetWorkErrorToast() {

    }

    @Override
    public void checkUpdateFail() {

    }
    //首页检查升级为后台执行，无需实现上面方法----end

    @Override
    public void hasNewVersion(AppUpdateEntity updateEntity) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("downloadUrl", updateEntity.getInstall_url());
        intent.putExtra("apkFileSize", updateEntity.getBinary().getFsize());
        intent.putExtra("isShowLoading", false);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
    }

    @Override
    public void isLatestVersion() {
        ToastUtils.showToast(getResources().getString(R.string.update_find_is_new));
    }

    @Override
    protected Fragment getMainContentFragment() {
        if(null == contentFragment) {
            contentFragment = new RdMainContentFragment();
        }
        return contentFragment;
    }

    @Override
    public boolean isSetStatusBarColor() {
        return true;
    }

    /**
     * 是否显示 我的 右上角的提示点
     *
     * @param isShow
     */
    public void isShowMeRedTip(boolean isShow) {
        contentFragment.isShowMeRedTip(isShow);
    }

    /**
     * 根据当前fragment是否带过滤条件显示过滤按钮的颜色
     *
     * @param hasFilter
     */
    public void setIvFilterStatus(boolean hasFilter) {
        contentFragment.setIvFilterStatus(hasFilter);
    }

    /**
     * 获取资源Fragment
     * @return
     */
    public ResourceFragment getResourceFragment() {
        return contentFragment.getResourceFragment();
    }

    /**
     * 通过位置切换Fragment
     * @param position
     */
    public void changeFragmentByPosition(int position) {
        contentFragment.changeFragmentByPosition(position);
    }
}
