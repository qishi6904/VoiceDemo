package com.svw.dealerapp.ui.newcustomer.contract;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntityV2;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.mvpframe.BaseModel;
import com.svw.dealerapp.mvpframe.BasePresenter;
import com.svw.dealerapp.mvpframe.BaseView;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/5/15.
 */

public interface NewCustomerContract {

    interface Model extends BaseModel {
        Observable<ResEntity<OpportunityEntity>> submitOpportunities(Map<String, String> options);

        Observable<ResEntity<OpportunityEntity>> submitOpportunities(OpportunitySubmitReqEntity options);

        Observable<ResEntity<OpportunityEntity>> submitOpportunitiesV2(OpportunitySubmitReqEntityV2 options);

        Observable<ResEntity<OpportunityEntity>> updateOpportunities(Map<String, String> options);

        Observable<ResEntity<OpportunityEntity>> updateOpportunities(OpportunityUpdateReqEntity options);

        Observable<ResEntity<CarTypesEntity>> searchByCarType(String ecModelId);

        Observable<ResEntity<Object>> submitFollowupInfo(Map<String, String> options);

        Observable<ResEntity<Object>> submitFollowupInfo(FollowupCreateReqEntity options);

        Observable<ResEntity<OpportunityDetailEntity>> showOpportunitiesDetail(String oppId);

        /**
         * 激活黄卡
         *
         * @param options
         * @return
         */
        Observable<ResEntity<Object>> postActiveYellow(Map<String, Object> options);

        /**
         * 备注提交
         *
         * @param options
         * @return
         */
        Observable<ResEntity<Object>> submitRemark(Map<String, Object> options);
    }

    interface View extends BaseView {
        void onQuickCreateSuccess();

        void onQuickCreateError(String message);

        void onStepOneSubmitSuccess(OpportunityEntity entity);

        void onStepOneSubmitError(String message);

        void onQueryCarTypeSuccess(CarTypesEntity entity);

        void onQueryCarTypeFailure(String msg);
    }

    interface StepTwoView extends BaseView {
        void onQueryCarTypeSuccess(CarTypesEntity entity);

        void onUpdateOpportunitySuccess(OpportunityEntity entity);

        void onUpdateOpportunityFailure();
    }

    interface StepThreeView extends BaseView {
        void onSubmitFollowupInfoSuccess(String msg);

        void onSubmitFollowupInfoFailure(String msg);
    }

    interface StepFourView extends BaseView {
        void onSubmitFollowupInfoSuccess();

        void onSubmitFollowupInfoFailure();
    }

    interface CustomerDetailView extends BaseView {
        void onQueryCustomerDetailSuccess(OpportunityDetailEntity entity);

        void onQueryCustomerDetailFailure(String msg);

        void postActiveSuccess();

        void postActiveFail();
    }

    interface AddFollowupRecordView extends BaseView {
        void onSubmitFollowupInfoSuccess();

        void onSubmitFollowupInfoFailure();
    }

    interface UpdateOpportunityView extends BaseView {
        void onUpdateOpportunitySuccess(OpportunityEntity entity);

        void onUpdateOpportunityFailure(String msg);

        void onQueryCarTypeSuccess(CarTypesEntity entity);

        void onQueryCarTypeError(String msg);

        void submitRemarkSuccess();

        void submitRemarkFail();
    }

    abstract class Presenter extends BasePresenter<NewCustomerContract.Model, NewCustomerContract.View> {
        public abstract void submitOpportunities(Map<String, String> options);

        public abstract void submitOpportunities(OpportunitySubmitReqEntity options);

        public abstract void submitOpportunitiesV2(OpportunitySubmitReqEntityV2 options);

        public abstract void updateOpportunities(Map<String, String> options);

        public abstract void updateOpportunities(OpportunityUpdateReqEntity entity);

        public abstract void searchByCarType(String ecModelId);

//        abstract void queryCustomerById(String id);
    }

    abstract class StepTwoPresenter extends BasePresenter<NewCustomerContract.Model, NewCustomerContract.StepTwoView> {
        public abstract void searchByCarType(String ecModelId);

        public abstract void updateOpportunities(Map<String, String> options);

        public abstract void updateOpportunities(OpportunityUpdateReqEntity entity);
    }

    abstract class StepThreePresenter extends BasePresenter<NewCustomerContract.Model, NewCustomerContract.StepThreeView> {
        public abstract void submitFollowupInfo(FollowupCreateReqEntity options);
    }

    abstract class StepFourPresenter extends BasePresenter<NewCustomerContract.Model, NewCustomerContract.StepFourView> {
        public abstract void submitFollowupInfo(FollowupCreateReqEntity options);
    }

    abstract class CustomerDetailPresenter extends BasePresenter<NewCustomerContract.Model, NewCustomerContract.CustomerDetailView> {
        public abstract void queryCustomerDetail(String oppId);

        /**
         * 激活黄卡
         *
         * @param options
         */
        public abstract void postActiveYellow(Map<String, Object> options);
    }

    abstract class AddFollowupRecordPresenter extends BasePresenter<NewCustomerContract.Model, AddFollowupRecordView> {
        abstract void submitFollowupInfo(FollowupCreateReqEntity options);
    }

    abstract class UpdateOpportunityPresenter extends BasePresenter<Model, UpdateOpportunityView> {
        public abstract void updateOpportunities(OpportunityUpdateReqEntity entity);

        public abstract void searchByCarType(String ecModelId);

        /**
         * 备注提交
         *
         * @param options
         * @return
         */
        public abstract void submitRemark(Map<String, Object> options);
    }
}
