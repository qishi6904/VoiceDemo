package com.svw.dealerapp.ui.newcustomer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityRelationsBean;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntityV2;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;
import com.svw.dealerapp.entity.task.TaskLeadsEntity;
import com.svw.dealerapp.entity.task.TaskTrafficEntity;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.ui.newcustomer.fragment.CarTypeFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerAddFollowupRecordFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerCurrentCarFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerOtherInfoFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerRequirementsDetailFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerUnitedBasicInfoFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerUnitedNextFollowupPlanFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerUnitedOtherInfoFragment;
import com.svw.dealerapp.ui.newcustomer.model.NewCustomerModel;
import com.svw.dealerapp.ui.newcustomer.presenter.NewCustomerPresenter;
import com.svw.dealerapp.ui.optionalpackage.activity.OptionalPackageListActivity;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.CustomSectionViewGroup;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.svw.dealerapp.global.NewCustomerConstants.REQUEST_CODE_PACKAGE;

/**
 * 新潜客
 * Created by xupan on 25/09/2017.
 */

public class NewCustomerUnitedActivity extends BaseActivity implements NewCustomerContract.View {

    private static final String TAG = "NewCustomerUnitedActivity";
    private NewCustomerUnitedBasicInfoFragment mBasicInfoFragment;
    private CarTypeFragment mCarTypeFragment;
    private NewCustomerAddFollowupRecordFragment mAddFollowupFragment;
    private NewCustomerUnitedNextFollowupPlanFragment mNextFollowupPlanUnitedFragment;
    private NewCustomerCurrentCarFragment mCurrentCarFragment;
    private NewCustomerRequirementsDetailFragment mRequirementsDetailFragment;
    private NewCustomerUnitedOtherInfoFragment mUnitedOtherInfoFragment;
    private NewCustomerOtherInfoFragment mOtherInfoFragment;

    private int dealPosition;//从上一页带入，建卡成功时发广播传走
    private int fromFlag;//从上一页带入，建卡成功时发广播传走
    private String mLeadsId;//创建黄卡的入参
    private OpportunityDetailEntity mEntity;//手动放入姓名，性别，手机
    private Button mNextBt;
    private NewCustomerPresenter mPresenter;
    private LoadingDialog mLoadingDialog;
    private ScrollView mScrollView;
    private CustomSectionViewGroup.OnExpandOrCollapseListener mListener;
    private CustomSectionViewGroup mFollowupPlanViewGroup;
    private ArrayList<OptionalPackageEntity.OptionListBean> mCurrentOptionalPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        initMVPData();
        initFragments();
        addFragmentsToViewGroup();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        TalkingDataUtils.onPageStart(this, "建卡第一页");
    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this, "建卡第一页");
    }

    @Override
    public void onBackPressed() {
        SimpleMessageDialogAdapter adapter = new SimpleMessageDialogAdapter();
        final CustomDialog dialog = new CustomDialog(this, adapter);
        adapter.setDialogMessageText(getString(R.string.new_customer_cancel_tip));
        dialog.setBtnCancelText(getString(R.string.new_customer_cancel_confirm));
        dialog.setBtnConfirmText(getString(R.string.new_customer_cancel_cancel));
        dialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                dialog.dismiss();
                finish();
                TalkingDataUtils.onEvent(NewCustomerUnitedActivity.this, "建卡第一页-取消提示-丢弃");
            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
                TalkingDataUtils.onEvent(NewCustomerUnitedActivity.this, "建卡第一页-取消提示-取消");
            }
        });
        dialog.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_customer_united;
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        dealPosition = intent.getIntExtra("dealPosition", -1);
        fromFlag = intent.getIntExtra("fromFlag", -1);
        Parcelable parcelable = intent.getParcelableExtra("trafficInfoEntity");
        if (parcelable instanceof TaskLeadsEntity.TaskLeadsInfoEntity) {
            TaskLeadsEntity.TaskLeadsInfoEntity entity = (TaskLeadsEntity.TaskLeadsInfoEntity) parcelable;
            mEntity = new OpportunityDetailEntity();
            mEntity.setCustName(entity.getCustName());
            mEntity.setCustGender(entity.getCustGender());
            mEntity.setCustMobile(entity.getCustMobile());
            mEntity.setSrcTypeId(entity.getSrcTypeId());
            mEntity.setChannelId(entity.getChannelId());
            mEntity.setCarModelId(entity.getCarModelId());
            mEntity.setRecomName(entity.getRecomName());
            mEntity.setRecomMobile(entity.getRecomMobile());
            mEntity.setActivityId(entity.getActivityId());
            mEntity.setActivitySubject(entity.getActivitySubject());
            mEntity.setSeriesId(entity.getSeriesId());
            mEntity.setLeadsId(entity.getLeadsId());
            mLeadsId = entity.getLeadsId();
        } else if (parcelable instanceof TaskTrafficEntity.TaskTrafficInfoEntity) {
            TaskTrafficEntity.TaskTrafficInfoEntity entity = (TaskTrafficEntity.TaskTrafficInfoEntity) parcelable;
            mEntity = new OpportunityDetailEntity();
            mEntity.setCustName(entity.getCustName());
            mEntity.setCustGender(entity.getCustGender());
            mEntity.setCustMobile(entity.getCustMobile());
            mEntity.setSrcTypeId(entity.getSrcTypeId());
            mEntity.setChannelId(entity.getChannelId());
            mEntity.setCarModelId(entity.getCarModelId());
            mEntity.setLeadsId(entity.getLeadsId());
            mLeadsId = entity.getLeadsId();
        }
        JLog.d(TAG, "leadsId: " + mLeadsId);
    }

    private void initFragments() {
        mBasicInfoFragment = NewCustomerUnitedBasicInfoFragment.newInstance(mEntity);
        mCarTypeFragment = CarTypeFragment.newInstance(mEntity);
        mBasicInfoFragment.setCarTypeFragment(mCarTypeFragment);
//        HashMap<String, String> map = new HashMap<>();
//        map.put("seriesId", mEntity.getSeriesId());
//        mAddFollowupFragment = NewCustomerAddFollowupRecordFragment.newInstance(map);
//        mNextFollowupPlanUnitedFragment = NewCustomerUnitedNextFollowupPlanFragment.newInstance(null);
//        mCurrentCarFragment = NewCustomerCurrentCarFragment.newInstance(null);
//        mRequirementsDetailFragment = NewCustomerRequirementsDetailFragment.newInstance(null);
//        mRequirementsDetailFragment.setChangeStyleTag();
//        mUnitedOtherInfoFragment = NewCustomerUnitedOtherInfoFragment.newInstance(mEntity);
//        mOtherInfoFragment = NewCustomerOtherInfoFragment.newInstance(null);
    }

    private void addFragmentsToViewGroup() {
        initOnExpandListener();
        CustomSectionViewGroup basicInfoViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_basic_info_container);
        basicInfoViewGroup.setTitleText(R.string.new_customer_basic_info_header);
        basicInfoViewGroup.addFragment(mBasicInfoFragment);
//        CustomSectionViewGroup firstFollowupViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_first_followup_container);
//        firstFollowupViewGroup.setTitleText(R.string.new_customer_followup_record_header);
//        firstFollowupViewGroup.addFragment(mAddFollowupFragment);
//        mFollowupPlanViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_followup_plan_container);
//        mFollowupPlanViewGroup.setTitleText(R.string.new_customer_next_followup_plan_title);
//        mFollowupPlanViewGroup.addFragment(mNextFollowupPlanUnitedFragment);
//        CustomSectionViewGroup requirementsDetailViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_requirements_detail_container);
//        requirementsDetailViewGroup.setCollapsed(true);
//        requirementsDetailViewGroup.setTitleText(R.string.new_customer_requirements_header);
//        requirementsDetailViewGroup.addFragment(mCurrentCarFragment);
//        requirementsDetailViewGroup.addFragment(mRequirementsDetailFragment);
//        CustomSectionViewGroup otherInfoViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_other_info_container);
//        otherInfoViewGroup.setCollapsed(true);
//        otherInfoViewGroup.setTitleText(R.string.new_customer_intention_other_header);
//        otherInfoViewGroup.addFragment(mUnitedOtherInfoFragment);
//        otherInfoViewGroup.addFragment(mOtherInfoFragment);

        basicInfoViewGroup.setOnExpandOrCollapseListener(mListener);
//        firstFollowupViewGroup.setOnExpandOrCollapseListener(mListener);
//        mFollowupPlanViewGroup.setOnExpandOrCollapseListener(mListener);
//        requirementsDetailViewGroup.setOnExpandOrCollapseListener(mListener);
//        otherInfoViewGroup.setOnExpandOrCollapseListener(mListener);
    }

    private void initOnExpandListener() {
        mScrollView = (ScrollView) findViewById(R.id.new_customer_scrollview);
        mListener = new CustomSectionViewGroup.OnExpandOrCollapseListener() {
            @Override
            public void onExpandOrCollapse(CustomSectionViewGroup viewGroup, boolean expand) {
                if (expand) {
                    int height = (int) viewGroup.getY();
                    JLog.d(TAG, "height: " + height);
                    mScrollView.smoothScrollTo(0, height);
                }
            }
        };
    }

    private void initMVPData() {
        mPresenter = new NewCustomerPresenter(new NewCustomerModel(), this);
    }

    private void initViews() {
        mNextBt = (Button) findViewById(R.id.activity_new_customer_next_bt);
        mNextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TalkingDataUtils.onEvent(NewCustomerUnitedActivity.this, "建卡第一页-下一步");
                if (!mBasicInfoFragment.checkDataCorrectness()) {
                    return;
                }
                //检查下次跟进日期和预约日期是否输入合法
                if (mFollowupPlanViewGroup.getVisibility() == View.VISIBLE
                        && !mNextFollowupPlanUnitedFragment.validateDate()) {
                    return;
                }
                if (!mUnitedOtherInfoFragment.checkDataCorrectness()) {
                    return;
                }
                prepareToSubmit();
            }
        });
    }

    private void prepareToSubmit() {
        OpportunitySubmitReqEntityV2 entityV2 = new OpportunitySubmitReqEntityV2();
        entityV2.setLeadsId(mLeadsId);
        OpportunitySubmitReqEntityV2 entityFromBasicInfo = mBasicInfoFragment.getParameters();
        entityV2.setCustName(entityFromBasicInfo.getCustName());//姓名
        entityV2.setCustGender(entityFromBasicInfo.getCustGender());//性别
        entityV2.setCustMobile(entityFromBasicInfo.getCustMobile());//电话
        entityV2.setPurchaseId(entityFromBasicInfo.getPurchaseId());//购买区分
        entityV2.setPropertyId(entityFromBasicInfo.getPropertyId());//购买性质
        entityV2.setOppLevel(entityFromBasicInfo.getOppLevel());//潜客级别
        entityV2.setFollowupDesc(entityFromBasicInfo.getFollowupDesc());//继续跟进理由
        entityV2.setAppraiserId(entityFromBasicInfo.getAppraiserId());//评估师
        OpportunitySubmitReqEntity entityFromCarType = mCarTypeFragment.getParameters();
        entityV2.setSeriesId(entityFromCarType.getSeriesId());//意向车系
        entityV2.setCarModelId(entityFromCarType.getCarModelId());//意向车型

        OpportunitySubmitReqEntityV2 entityFromUnitedOtherInfo = mUnitedOtherInfoFragment.getParameters();
        entityV2.setCustAge(entityFromUnitedOtherInfo.getCustAge());//年龄
        entityV2.setIsKeyuser(entityFromUnitedOtherInfo.getIsKeyuser());//重点客户
        entityV2.setSrcTypeId(entityFromUnitedOtherInfo.getSrcTypeId());//来源
        if (!TextUtils.isEmpty(entityFromUnitedOtherInfo.getChannelId())) {
            entityV2.setChannelId(entityFromUnitedOtherInfo.getChannelId());//媒体
        }
        if (!TextUtils.isEmpty(entityFromUnitedOtherInfo.getActivityId())) {
            entityV2.setActivityId(entityFromUnitedOtherInfo.getActivityId());//外拓
        }
        if (!TextUtils.isEmpty(entityFromUnitedOtherInfo.getRecomName())) {
            entityV2.setRecomName(entityFromUnitedOtherInfo.getRecomName());//推荐人姓名
            entityV2.setRecomMobile(entityFromUnitedOtherInfo.getRecomMobile());//推荐人手机
        }

        //仅当现用车型显示时才传入相关参数
        if (mCurrentCarFragment.isVisible()) {
            OpportunityUpdateReqEntity entityFromCurrentCar = mCurrentCarFragment.getParameters();
            entityV2.setCurrentModel(entityFromCurrentCar.getCurrentModel());//现用车型
            entityV2.setModelYear(entityFromCurrentCar.getModelYear());//购车年份
            entityV2.setCurrentMileage(entityFromCurrentCar.getCurrentMileage());//当前里程
        }

        OpportunityUpdateReqEntity entityFromRequirementsDetail = mRequirementsDetailFragment.getParameters();
        entityV2.setPaymentMode(entityFromRequirementsDetail.getPaymentMode());//分期或全款
        entityV2.setBudgetMin(entityFromRequirementsDetail.getBudgetMin());
        entityV2.setBudgetMax(entityFromRequirementsDetail.getBudgetMax());
        entityV2.setOptionPackage(entityFromRequirementsDetail.getOptionPackage());//选装包
        entityV2.setOutsideColorId(entityFromRequirementsDetail.getOutsideColorId());
        entityV2.setInsideColorId(entityFromRequirementsDetail.getInsideColorId());
        List<OpportunityRelationsBean> listOfRqmDetail = entityFromRequirementsDetail.getOpportunityRelations();
        List<OpportunityRelationsBean> resultList = new ArrayList<>();
        resultList.addAll(listOfRqmDetail);//其他需求

        OpportunityUpdateReqEntity otherInfoEntity = mOtherInfoFragment.getParameters();//其它需求和添加微信
        entityV2.setIsWechat(otherInfoEntity.getIsWechat());
        List<OpportunityRelationsBean> listOfOthInf = otherInfoEntity.getOpportunityRelations();
        resultList.addAll(listOfOthInf);//客户了解信息途径
        entityV2.setOpportunityRelations(resultList);

        FollowupCreateReqEntity entityFromAddFollowup = mAddFollowupFragment.getParameters();//首次跟进记录
        entityV2.setModeId(entityFromAddFollowup.getModeId());
        entityV2.setResults(entityFromAddFollowup.getResults());
        entityV2.setFollowupRemark(entityFromAddFollowup.getRemark());
        entityV2.setCompetitor(entityFromAddFollowup.getCompetitor());
        if (!TextUtils.isEmpty(entityFromAddFollowup.getIsTestdrive())) {
            entityV2.setIsTestdrive(entityFromAddFollowup.getIsTestdrive());
        }
        if (mFollowupPlanViewGroup.getVisibility() == View.VISIBLE) {
            OpportunitySubmitReqEntityV2 entityFromUnitedFollowupPlan = mNextFollowupPlanUnitedFragment.getParameters();
            entityV2.setScheduleDateStr(entityFromUnitedFollowupPlan.getScheduleDateStr());
            entityV2.setNextScheduleDesc(entityFromUnitedFollowupPlan.getScheduleDesc());
            entityV2.setNextModeId(entityFromUnitedFollowupPlan.getNextModeId());
            //仅当勾选了添加预约时才提交预约相关入参
            if (!TextUtils.isEmpty(entityFromUnitedFollowupPlan.getAppmDateStr())) {
                //新的接口入参
                OpportunitySubmitReqEntityV2.Appointment appointment = new OpportunitySubmitReqEntityV2.Appointment();
                appointment.setAppmDateStr(entityFromUnitedFollowupPlan.getAppmDateStr());
                appointment.setAppmTypeId(entityFromUnitedFollowupPlan.getAppmTypeId());
                entityV2.setAppointment(appointment);
                //旧的入参
                entityV2.setAppmDateStr(entityFromUnitedFollowupPlan.getAppmDateStr());
                entityV2.setAppmTypeId(entityFromUnitedFollowupPlan.getAppmTypeId());
            }
        }
        if (mCurrentOptionalPackage != null) {
            entityV2.setEcCarOptions(mCurrentOptionalPackage);
        }
        JLog.d(TAG, "entity: " + entityV2);
        mPresenter.submitOpportunitiesV2(entityV2);
    }

    private boolean checkDataValidation() {
        if (mFollowupPlanViewGroup.getVisibility() == View.VISIBLE) {
            return mBasicInfoFragment.checkDataValidation() && mCarTypeFragment.checkDataValidation()
                    && mAddFollowupFragment.checkDataValidation()
                    && mNextFollowupPlanUnitedFragment.checkDataValidation()
                    && mUnitedOtherInfoFragment.checkDataValidation();
        } else {
            return mBasicInfoFragment.checkDataValidation() && mCarTypeFragment.checkDataValidation()
                    && mAddFollowupFragment.checkDataValidation()
                    && mUnitedOtherInfoFragment.checkDataValidation();
        }
    }

    public void queryOptionalPackage() {
        OpportunitySubmitReqEntity entityFromCarType = mCarTypeFragment.getParameters();
        String seriesId = entityFromCarType.getSeriesId();
        String carModelId = entityFromCarType.getCarModelId();
        OpportunityUpdateReqEntity entityFromDetail = mRequirementsDetailFragment.getParameters();
        String insideColorId = entityFromDetail.getInsideColorId();
        if (TextUtils.isEmpty(seriesId) || TextUtils.isEmpty(carModelId)) {
            ToastUtils.showToast("车系车型必填");
        } else {
            Intent i = new Intent(this, OptionalPackageListActivity.class);
            i.putExtra("serId", seriesId);
            i.putExtra("modelId", carModelId);
            i.putExtra("color", insideColorId);
            i.putExtra("selectData", mCurrentOptionalPackage);
            startActivityForResult(i, REQUEST_CODE_PACKAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PACKAGE) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<OptionalPackageEntity.OptionListBean> list =
                        mCurrentOptionalPackage = data.getParcelableArrayListExtra("selectData");
                List<String> resultList = CommonUtils.getOptionalPackage(list, true);
                mRequirementsDetailFragment.displayOptionalPackage(resultList);
            }
        }
    }

    @Override
    public void onRequestStart() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestEnd() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onInternetError() {

    }

    @Override
    public void onQuickCreateSuccess() {

    }

    @Override
    public void onQuickCreateError(String message) {

    }

    @Override
    public void onStepOneSubmitSuccess(OpportunityEntity entity) {
        mAddFollowupFragment.onSubmitSuccess();
        finish();
        //同时发送广播
        Intent broadcastIntent = new Intent("com.svw.dealerapp.create.yellow.card.success");
        broadcastIntent.putExtra("dealPosition", dealPosition);
        broadcastIntent.putExtra("fromFlag", fromFlag);
        broadcastIntent.putExtra("oppId", entity.getOppId());
        sendBroadcast(broadcastIntent);
        goToOppList();
    }

    //跳转到首页的资源/潜客列表
    private void goToOppList() {
        Intent i = new Intent(this, RdMainActivity.class);
        i.putExtra("firstNavPosition", 1);
        i.putExtra("secondNavPosition", 0);
        startActivity(i);
    }

    @Override
    public void onStepOneSubmitError(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void onQueryCarTypeSuccess(CarTypesEntity entity) {
        mRequirementsDetailFragment.onQueryCarTypeSuccess(entity);
    }

    @Override
    public void onQueryCarTypeFailure(String msg) {

    }

    public void changeScheduleDate(String result) {
//        mNextFollowupPlanUnitedFragment.changeScheduleDate(result);
    }

    public void setFollowupCalendars(int delayOfDefault, int delayOfStart, int delayOfEnd) {
        mNextFollowupPlanUnitedFragment.setFollowupCalendars(delayOfDefault, delayOfStart, delayOfEnd);
    }

    public void resetCarTypeColor() {
        mRequirementsDetailFragment.resetCarTypeColor();
    }

    public void resetOptionalPackage() {
        mRequirementsDetailFragment.resetOptionalPackage();
        mCurrentOptionalPackage = null;
    }

    public void showCurrentCarFragment(boolean show) {
        if (show) {
            getSupportFragmentManager().beginTransaction().show(mCurrentCarFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().hide(mCurrentCarFragment).commit();
        }
    }

    public void showNextFollowupAndAppointment(boolean show) {
        mFollowupPlanViewGroup.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void checkToResetFollowupCalendars() {
        String levelCode = mBasicInfoFragment.getOppLevel();
        String followupResult = mAddFollowupFragment.getFollowupResult();
        if (TextUtils.isEmpty(levelCode) || TextUtils.isEmpty(followupResult)) {
            return;
        }
        int[] delays = CommonUtils.calculateDelayedDays(true, levelCode, followupResult);
        if (delays.length != 3) {
            JLog.d(TAG, "checkToResetFollowupCalendars days array length error: " + delays.length);
            return;
        }
        mNextFollowupPlanUnitedFragment.setFollowupCalendars(delays[0], delays[1], delays[2]);
    }

    public void changeCurrentSeries(String seriesId) {
        mAddFollowupFragment.setSeriesId(seriesId);
    }

    public void checkIfNeedToEnableSubmit() {
        if (mNextBt != null) {
            mNextBt.setEnabled(checkDataValidation());
        }
    }
}
