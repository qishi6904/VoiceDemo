package com.svw.dealerapp.ui.newcustomer.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.adapter.RdRecyclerViewAdapter;
import com.svw.dealerapp.ui.home.activity.CreateTrafficActivity;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailItemActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneUnitedActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerUnitedActivity;
import com.svw.dealerapp.ui.order.activity.OrderActivity;
import com.svw.dealerapp.ui.order.activity.RdOrderActivity;
import com.svw.dealerapp.ui.order.contract.CarTypeContract;
import com.svw.dealerapp.ui.order.model.CarTypeModel;
import com.svw.dealerapp.ui.order.presenter.CarTypePresenter;
import com.svw.dealerapp.ui.widget.CustomItemViewBase;
import com.svw.dealerapp.ui.widget.CustomItemViewForEditText;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.RdCustomItemViewBase;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForBottomDialog;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForEditText;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.JLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xupan on 25/01/2018.
 */

public class RdCarTypeFragment extends RdBaseCustomerFragment implements CarTypeContract.View {

    private static final String TAG = "RdCarTypeFragment";
    private boolean mSeriesMandatory, mModelMandatory, mCodeMandatory;
    //    private CustomItemViewForOptionsPicker mSeriesView, mModelView;
//    private CustomItemViewForEditText mCodeView;
    private RdCustomItemViewForBottomDialog mSeriesView, mModelView;
    private RdCustomItemViewForEditText mCodeView;
    private RdCustomItemViewBase.OnDataChangedListener mSeriesChangedListener,
            mModelChangedListener, mCodeChangedListener;

    private CarTypePresenter mCarTypePresenter;
    private boolean mCarTypeValid = false;
    private boolean isReset;
    private int num;//选择第几套设置

    public static RdCarTypeFragment newInstance(Serializable entity) {
        RdCarTypeFragment fragment = new RdCarTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 设置三行输入项分别是否为必填项
     *
     * @param series 车系是否必填
     * @param model  车型是否必填
     * @param code   6位车型码是否必填
     */
    public void setViewMandatory(boolean series, boolean model, boolean code) {
        mSeriesMandatory = series;
        mModelMandatory = model;
        mCodeMandatory = code;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_fragment_car_type;
    }

    @Override
    protected void initViews(View view) {
        initMVPData();
        mSeriesView = (RdCustomItemViewForBottomDialog) view.findViewById(R.id.fragment_car_type_series);
        mSeriesView.setMandatory(mSeriesMandatory);
        mSeriesView.setTitleText("意向车系");
        mSeriesView.setHintTextForContentView("请选择车系");
        mSeriesView.initAdapter(NewCustomerConstants.carSeriesMap, RdRecyclerViewAdapter.MODE_SINGLE);
        mModelView = (RdCustomItemViewForBottomDialog) view.findViewById(R.id.fragment_car_type_model);
        mModelView.setMandatory(mModelMandatory);
        mModelView.setTitleText("意向车型");
        mModelView.setHintTextForContentView("请选择车型");
        mCodeView = (RdCustomItemViewForEditText) view.findViewById(R.id.fragment_car_type_code);
        mCodeView.setMandatory(mCodeMandatory);
        mCodeView.setTitleText("车型代码");
        mCodeView.setMaxLengthForContentView(6);
        mCodeView.setHintTextForContentView("请输入6位车型代码");
        if (isReset) {
            if (num == 1) {
                resetSubViewAttri1();
            }
        }
    }

    private void initMVPData() {
        mCarTypePresenter = new CarTypePresenter(new CarTypeModel(), this);
    }

    @Override
    protected void initBaseViews() {
        super.initBaseViews();
        mAllBaseView = new RdCustomItemViewBase[]{mSeriesView, mModelView, mCodeView};
    }

    @Override
    protected void initOptions() {
        super.initOptions();
//        CommonUtils.initOptionsView(NewCustomerConstants.carSeriesMap, mSeriesView);
    }

    @Override
    protected void setListeners() {
        mSeriesChangedListener = new RdCustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                List<String> list = (List<String>) object;
                if(null != list && list.size() > 0) {
                    String series = list.get(0);
                    queryCarModelsBySeries(series);
                    resetCarTypeColor();
                    askActivityToChangeCarSeries(series);
                    mCodeView.setOnDataChangedListener(null);
                    mCodeView.clearData();
                    mCodeView.setOnDataChangedListener(mCodeChangedListener);
                    if (mSeriesMandatory) {
                        askActivityToCheckData();
                    }
                    mModelView.setTextForContentView("");
                }
            }
        };
        mSeriesView.setOnDataChangedListener(mSeriesChangedListener);
        mModelChangedListener = new RdCustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                JLog.d(TAG, "mModelChangedListener onDateChanged: " + object);
                resetCarTypeColor();
                List<String> list = (List<String>) object;
                String carType = list.get(0);
                if(null != list && list.size() > 0) {
                    mCarTypePresenter.queryCarType(carType);
                    mCodeView.setOnDataChangedListener(null);
                    mCodeView.setData(carType);
                    mCodeView.setOnDataChangedListener(mCodeChangedListener);
                    if (mModelMandatory) {
                        askActivityToCheckData();
                    }
                }
            }
        };
        mModelView.setOnDataChangedListener(mModelChangedListener);
        mCodeChangedListener = new RdCustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                String code = (String) object;
                if (TextUtils.isEmpty(code) || code.length() != 6) {
                    mCarTypeValid = false;
                    mSeriesView.clearSelection();
                    mModelView.clearData();
                    resetCarTypeColor();
                    askActivityToChangeCarSeries(null);
                    askActivityToCheckData();
                    return;
                }
                JLog.d(TAG, "mCodeChangedListener onDateChanged: " + object);
                String series = queryCarSeriesModelById(code.toUpperCase());
                if (TextUtils.isEmpty(series)) {
                    askActivityToCheckData();
                    return;
                }
                askActivityToChangeCarSeries(series);
                mCarTypePresenter.queryCarType(code);
                mSeriesView.setOnDataChangedListener(null);
                List<String> seriesList = new ArrayList<>();
                seriesList.add(series);
                mSeriesView.setData(seriesList);
                mSeriesView.setOnDataChangedListener(mSeriesChangedListener);
                List<String> modeList = new ArrayList<>();
                modeList.add(code.toUpperCase());
                mModelView.setOnDataChangedListener(null);
                mModelView.setData(modeList);
                mModelView.setOnDataChangedListener(mModelChangedListener);
                if (mCodeMandatory) {
                    askActivityToCheckData();
                }
            }
        };
        mCodeView.setOnDataChangedListener(mCodeChangedListener);
    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        if (mEntity instanceof OpportunityDetailEntity) {
            OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
            //非本人或战败或休眠的黄卡
            if (!entity.isYellowCardOwner() || entity.isSleepStatus() || entity.isFailedStatus()) {
                mSeriesView.setClickable(false);
                mModelView.setClickable(false);
                mCodeView.setEnabled(false);
            }
            String seriesId = entity.getSeriesId();
            if (!TextUtils.isEmpty(seriesId)) {
                List<String> list = new ArrayList<>();
                list.add(seriesId);
                mSeriesView.setData(list);
                queryCarModelsBySeries(seriesId);
            }
            String modelId = entity.getCarModelId();
            if (!TextUtils.isEmpty(modelId)) {
                modelId = modelId.toUpperCase();
                List<String> list = new ArrayList<>();
                list.add(modelId);
                mModelView.setData(list);
                mCodeView.setData(modelId);
            }
        } else if (mEntity instanceof QueryOrderDetailResponseEntity) {
            QueryOrderDetailResponseEntity entity = (QueryOrderDetailResponseEntity) mEntity;
            enableBaseViews(entity.isBuyerInfoEditable());
            String seriesId = entity.getSeriesId();
            List<String> serialsList = new ArrayList<>();
            serialsList.add(seriesId);
            mSeriesView.setData(serialsList);
            queryCarModelsBySeries(seriesId);
            String modelId = entity.getModelId().toUpperCase();
            List<String> modelIdList = new ArrayList<>();
            modelIdList.add(modelId);
            mModelView.setData(modelIdList);
            mCodeView.setData(modelId);
            if (!entity.isBuyerInfoEditable()) {
                mCarTypePresenter.queryCarType(modelId);
            }
        }
    }

    /**
     * 根据车系获取对应的车型码
     *
     * @param series 车系
     */
    private void queryCarModelsBySeries(String series) {
        Cursor cursor = DealerApp.dbHelper.rawQuery("select modelDescCn, modelId from CarSeriesModelDict where seriesId=?", new String[]{series});
        if (cursor != null) {
            NewCustomerConstants.carModelMap.clear();
            while (cursor.moveToNext()) {
                NewCustomerConstants.carModelMap.put(cursor.getString(cursor.getColumnIndex("modelId")), cursor.getString(cursor.getColumnIndex("modelDescCn")));
            }
            cursor.close();
//            CommonUtils.initOptionsView(NewCustomerConstants.carModelMap, mModelView);
            mModelView.initAdapter(NewCustomerConstants.carModelMap, RdRecyclerViewAdapter.MODE_SINGLE);
        }
    }

    /**
     * 根据车型码获取对应的车型车系
     *
     * @param modelId 车型码
     */
    private String queryCarSeriesModelById(String modelId) {
        String seriesId = "";
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from CarSeriesModelDict where modelId=?", new String[]{modelId});
        if (cursor != null && cursor.moveToNext()) {
            seriesId = cursor.getString(cursor.getColumnIndex("seriesId"));
            cursor.close();
        }
        if (!TextUtils.isEmpty(seriesId)) {
            queryCarModelsBySeries(seriesId);
        }
        return seriesId;
    }

    //获取车系码
    public String getSeriesId() {
        return mSeriesView.getInputData().get(0);
    }

    //获取车型码
    public String getModelId() {
        return mCodeView.getInputData();
    }

    @Override
    public OpportunitySubmitReqEntity getParameters() {
        OpportunitySubmitReqEntity entity = new OpportunitySubmitReqEntity();
        if(mSeriesView.getInputData().size() > 0) {
            entity.setSeriesId(mSeriesView.getInputData().get(0));//意向车系
        }
        entity.setCarModelId(mCodeView.getInputData());//意向车型
        return entity;
    }

    @Override
    public void onQueryCarTypeSuccess(CarTypesEntity entity) {
        mCarTypeValid = true;
        FragmentActivity activity = getActivity();
        if (activity instanceof NewCustomerStepOneUnitedActivity) {
            ((NewCustomerStepOneUnitedActivity) activity).onQueryCarTypeSuccess(entity);
        } else if (activity instanceof CustomerDetailItemActivity) {
            ((CustomerDetailItemActivity) activity).onQueryCarTypeSuccess(entity);
        } else if (activity instanceof RdOrderActivity) {
            ((RdOrderActivity) activity).onQueryCarTypeSuccess(entity);
        } else if (activity instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) activity).onQueryCarTypeSuccess(entity);
        }
    }

    @Override
    public void onQueryCarTypeFailure(String msg) {
        FragmentActivity activity = getActivity();
        if (activity instanceof NewCustomerStepOneUnitedActivity) {

        } else if (activity instanceof CustomerDetailItemActivity) {
            ((CustomerDetailItemActivity) activity).onQueryCarTypeError(msg);
        } else if (activity instanceof OrderActivity) {

        }
    }

    private void askActivityToCheckData() {
        if (getActivity() instanceof NewCustomerStepOneUnitedActivity) {
            ((NewCustomerStepOneUnitedActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof CreateTrafficActivity) {
            ((CreateTrafficActivity) getActivity()).setSubmitBtnColor();
        } else if (getActivity() instanceof OrderActivity) {
            ((OrderActivity) getActivity()).checkFragmentsValidation();
        }
    }

    private void resetCarTypeColor() {
        FragmentActivity activity = getActivity();
        if (activity instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) activity).resetCarTypeColor();
            ((NewCustomerUnitedActivity) activity).resetOptionalPackage();
        } else if (activity instanceof CustomerDetailItemActivity) {
            ((CustomerDetailItemActivity) activity).resetCarTypeColor();
            ((CustomerDetailItemActivity) activity).resetOptionalPackage();
        } else if (activity instanceof OrderActivity) {
            ((OrderActivity) activity).resetCarTypeColor();
            ((OrderActivity) activity).resetOptionalPackage();
        }
    }

    private void askActivityToChangeCarSeries(String series) {
        if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).changeCurrentSeries(series);
        }
    }

    public void setResetSubViewAttri(boolean isReset, int num) {
        this.isReset = isReset;
        this.num = num;
    }

    private void resetSubViewAttri1() {
        mSeriesView.setTextSize(14);
//        mSeriesView.setStarTextSize(18);
        mSeriesView.setHintColor(Color.parseColor("#999999"));
        mSeriesView.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
        mSeriesView.setLeftRightMargin(DensityUtil.dp2px(getActivity(), 20), DensityUtil.dp2px(getActivity(), 20));
        mSeriesView.setBottomLineMargin(0, 0);

        mModelView.setTextSize(14);
//        mModelView.setStarTextSize(18);
        mModelView.setHintColor(Color.parseColor("#999999"));
        mModelView.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
        mModelView.setLeftRightMargin(DensityUtil.dp2px(getActivity(), 20), DensityUtil.dp2px(getActivity(), 20));
        mModelView.setBottomLineMargin(0, 0);

        mCodeView.setTextSize(14);
//        mCodeView.setStarTextSize(18);
        mCodeView.setHintColor(Color.parseColor("#999999"));
        mCodeView.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
        mCodeView.setLeftRightMargin(DensityUtil.dp2px(getActivity(), 20), DensityUtil.dp2px(getActivity(), 20));
        mCodeView.setBottomLineMargin(0, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != mSeriesView) {
            mSeriesView.dismiss();
        }
        if(null != mModelView) {
            mModelView.dismiss();
        }
    }
}
