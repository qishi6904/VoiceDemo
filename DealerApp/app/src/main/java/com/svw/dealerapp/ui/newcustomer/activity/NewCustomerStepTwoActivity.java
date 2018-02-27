package com.svw.dealerapp.ui.newcustomer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityRelationsBean;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.mvpframe.base.BaseFrameActivity;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerCurrentCarFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerOtherInfoFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerRequirementsDetailFragment;
import com.svw.dealerapp.ui.newcustomer.model.NewCustomerModel;
import com.svw.dealerapp.ui.newcustomer.presenter.NewCustomerStepTwoPresenter;
import com.svw.dealerapp.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * (由于合卡已废弃)创建新潜客第二步
 * Created by xupan on 17/05/2017.
 */
@Deprecated
public class NewCustomerStepTwoActivity extends BaseFrameActivity<NewCustomerStepTwoPresenter, NewCustomerModel> implements NewCustomerContract.StepTwoView {

    private static final String TAG = "NewCustomerStepTwoActivity";
    private String mOppId, mFollowUpId;

    private Button mNextBt;
    private CarTypesEntity mCarTypeEntity;

    private NewCustomerCurrentCarFragment mCurrentCarFragment;
    private NewCustomerRequirementsDetailFragment mRequirementsDetailFragment;
    private NewCustomerOtherInfoFragment mOtherInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_yellow_card_step_two);
        getDataFromIntent();
        initFragments();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        mCarTypeEntity = (CarTypesEntity) intent.getSerializableExtra(NewCustomerConstants.CAR_TYPE_ENTITY);
        mOppId = intent.getStringExtra(NewCustomerConstants.KEY_OPP_ID);
        mFollowUpId = intent.getStringExtra(NewCustomerConstants.KEY_FOLLOWUP_ID);
    }

    private void initFragments() {
        mCurrentCarFragment = new NewCustomerCurrentCarFragment();
        mRequirementsDetailFragment = NewCustomerRequirementsDetailFragment.newInstance(mCarTypeEntity);
        mOtherInfoFragment = new NewCustomerOtherInfoFragment();
        getSupportFragmentManager().beginTransaction().
                add(R.id.new_customer_step_2_current_car_container, mCurrentCarFragment)
                .add(R.id.new_customer_step_2_intention_container, mRequirementsDetailFragment)
                .add(R.id.new_customer_step_2_others_container, mOtherInfoFragment).commit();
    }

    public void initView() {
        super.initView();
        mNextBt = (Button) findViewById(R.id.activity_new_customer_step_2_next_bt);
    }

    public void initListener() {
        super.initListener();
        mNextBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_new_customer_step_2_next_bt:
                if (checkDataValidation()) {
                    prepareToSubmit();
                } else {
                    ToastUtils.showToast(getString(R.string.new_customer_validation_hint));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onQueryCarTypeSuccess(CarTypesEntity entity) {
        mRequirementsDetailFragment.onQueryCarTypeSuccess(entity);
    }

    private void prepareToSubmit() {
        OpportunityUpdateReqEntity entity = new OpportunityUpdateReqEntity();
        entity.setOppId(mOppId);
        entity.setFollowupId(mFollowUpId);

        OpportunityUpdateReqEntity entityFromCurrentCar = mCurrentCarFragment.getParameters();
        entity.setCurrentModel(entityFromCurrentCar.getCurrentModel());//现用车型
        entity.setModelYear(entityFromCurrentCar.getModelYear());//购车年份
        entity.setCurrentMileage(entityFromCurrentCar.getCurrentMileage());//当前里程

        OpportunityUpdateReqEntity entityFromRequirementsDetail = mRequirementsDetailFragment.getParameters();
        entity.setPaymentMode(entityFromRequirementsDetail.getPaymentMode());//分期或全款
        entity.setBudgetMin(entityFromRequirementsDetail.getBudgetMin());
        entity.setBudgetMax(entityFromRequirementsDetail.getBudgetMax());
        entity.setCarModelId(entityFromRequirementsDetail.getCarModelId());//意向车型
        entity.setOutsideColorId(entityFromRequirementsDetail.getOutsideColorId());
        entity.setInsideColorId(entityFromRequirementsDetail.getInsideColorId());

        OpportunityUpdateReqEntity entityFromOtherInfo = mOtherInfoFragment.getParameters();
        entity.setIsWechat(entityFromOtherInfo.getIsWechat());

        List<OpportunityRelationsBean> resultList = new ArrayList<>();
        resultList.addAll(entityFromRequirementsDetail.getOpportunityRelations());
        resultList.addAll(entityFromOtherInfo.getOpportunityRelations());
        entity.setOpportunityRelations(resultList);
        mPresenter.updateOpportunities(entity);
    }

    private boolean checkDataValidation() {
        return mRequirementsDetailFragment.checkDataValidation();
    }

    @Override
    public void onUpdateOpportunitySuccess(OpportunityEntity entity) {
        Intent i = new Intent(this, NewCustomerStepThreeActivity.class);
        i.putExtra(NewCustomerConstants.KEY_OPP_ID, mOppId);
        i.putExtra(NewCustomerConstants.KEY_FOLLOWUP_ID, mFollowUpId);
        startActivity(i);
    }

    @Override
    public void onUpdateOpportunityFailure() {
        ToastUtils.showToast("update failure");
    }

    public void searchCarType(String carTypeId) {
        mPresenter.searchByCarType(carTypeId);
    }
}
