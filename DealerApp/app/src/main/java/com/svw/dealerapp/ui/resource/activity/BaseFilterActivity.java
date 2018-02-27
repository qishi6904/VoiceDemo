package com.svw.dealerapp.ui.resource.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.ui.resource.adapter.ResourceFilterAdapter;
import com.svw.dealerapp.ui.resource.contract.FilterGetCarNameContractor;
import com.svw.dealerapp.ui.resource.entity.BaseFilterEntity;
import com.svw.dealerapp.ui.resource.entity.BaseFilterIntentEntity;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;
import com.svw.dealerapp.ui.resource.model.FilterGetCarNameModel;
import com.svw.dealerapp.ui.resource.presenter.FilterGetCarNamePresenter;
import com.svw.dealerapp.ui.widget.DatePeriodPickerView;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.AllCapTransformationMethod;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.ScreenUtils;
import com.svw.dealerapp.util.StringUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;
import com.svw.dealerapp.util.dbtools.PrivilegeDBUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.svw.dealerapp.global.Constants.E_RESOURCE_FILTER_SALES_CONSULTANT;
import static com.svw.dealerapp.global.Constants.E_RESOURCE_FILTER_SHOW_SELF;
import static com.svw.dealerapp.global.Constants.P_SA_APP_RESOURCE_FILTER;

/**
 * Created by qinshi on 12/19/2017.
 */

public abstract class BaseFilterActivity extends BaseActivity implements FilterGetCarNameContractor.View {

    protected final static int salesFilterCode = 9001;
    protected final static int seriesFilterCode = 9002;

    protected TextView tvTitle;
    protected TextView tvCancel;
    protected TextView tvReset;

    protected View vShowSelfUnderLine;
    protected RelativeLayout rlShowSelfSwitch;
    protected View vShowSelfSwitchBg;
    protected ImageView ivShowSelfSwitchLeftIcon;
    protected ImageView ivShowSelfSwitchRightIcon;
    protected boolean isOpenShowSelf;

    protected RecyclerView rvYellowCardSource;

    protected ResourceFilterAdapter sourceAdapter;
    protected List<ResourceFilterItemEntity> sourceList = new ArrayList<>();
    protected List<ResourceFilterItemEntity> sourceSelectList = new ArrayList<>();

    protected View vSalesFilterDivider;
    protected LinearLayout llSalesFilter;
    protected TextView tvSalesName;
    protected LinearLayout llSeriesFilter;
    protected TextView tvSeriesName;
    protected String userId;      //选中的销售顾问id字符串，逗号分割
    protected String userName;    //选中的销售顾问name字符串，逗号分割
    protected String seriesCode;  //选中的车系code字符串，逗号分割
    protected String seriesName;  //选中的车系name字符串，逗号分割

    protected Rect visibleRect;
    protected EditText etCarTypeCode;
    protected String inputCarCode;
    protected TextView tvCarName;
    protected boolean isShowingSoftInput = false;

    protected DatePeriodPickerView createCardPeriodPicker;

    protected boolean canReset = false;

    protected BaseFilterIntentEntity filterIntentEntity;

    private FilterGetCarNamePresenter filterGetCarNamePresenter;
    private LoadingDialog loadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        filterGetCarNamePresenter = new FilterGetCarNamePresenter(this, new FilterGetCarNameModel());

        assignViews();

        Intent intent = getIntent();
        if(null != intent){
            filterIntentEntity = intent.getParcelableExtra("filterIntentEntity");
            if(null != filterIntentEntity) {
                setCanReset(true);
            }else {
                setCanReset(false);
            }
        }

//        //如果是经理角色，显示按销售顾问筛选
//        if(UserInfoUtils.isManagerPermission()){
//            llSalesFilter.setVisibility(View.VISIBLE);
//            vSalesFilterDivider.setVisibility(View.VISIBLE);
//
//            vShowSelfUnderLine.setVisibility(View.VISIBLE);
//            rlShowSelfSwitch.setVisibility(View.VISIBLE);
//        }

        //是否有权限显示“只显示自己”按钮
        if(PrivilegeDBUtils.isCheck(P_SA_APP_RESOURCE_FILTER, E_RESOURCE_FILTER_SHOW_SELF)){
            vShowSelfUnderLine.setVisibility(View.VISIBLE);
            rlShowSelfSwitch.setVisibility(View.VISIBLE);
        }
        //是否有权限显示“按销售顾问筛选”按钮
        if(PrivilegeDBUtils.isCheck(P_SA_APP_RESOURCE_FILTER, E_RESOURCE_FILTER_SALES_CONSULTANT)){
            llSalesFilter.setVisibility(View.VISIBLE);
            vSalesFilterDivider.setVisibility(View.VISIBLE);
        }

        sourceAdapter = new ResourceFilterAdapter(this, sourceList, true, true);
        rvYellowCardSource.setAdapter(sourceAdapter);

        sourceAdapter.setOnItemClickListener(new ResourceFilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ResourceFilterItemEntity entity, boolean isMultiSelect, boolean isSelect) {
                dealFilterItemClick(isSelect, sourceSelectList, entity);
                if(sourceSelectList.size() > 1 ||
                        (sourceSelectList.size() == 1 && !sourceSelectList.get(0).isAll())){
                    setCanReset(true);
                }else {
                    checkAndSetCanReset();
                }
            }
        });

        llSalesFilter.setOnClickListener(this);
        llSeriesFilter.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvReset.setOnClickListener(this);

        etCarTypeCode.setTransformationMethod(new AllCapTransformationMethod());
        visibleRect = new Rect();
        etCarTypeCode.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BaseFilterActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(visibleRect);
                //键盘弹出后输入框的光标移到文字的最后
                if (visibleRect.bottom < ScreenUtils.getScreenHeight(BaseFilterActivity.this)){
                    isShowingSoftInput = true;
                    if(etCarTypeCode.hasFocus() && etCarTypeCode.getText().length() > 1) {
                        Selection.setSelection(etCarTypeCode.getText(), etCarTypeCode.getText().length());
                    }
                }else if(isShowingSoftInput) {
                    isShowingSoftInput = false;
                    String carTypeCode = etCarTypeCode.getText().toString();
                    //校验输入的车型代码是否符合规范
                    if(!TextUtils.isEmpty(carTypeCode) && !StringUtils.isMatchPattern("[0-9a-zA-Z]{6}", carTypeCode.trim())){
                        ToastUtils.showToast(getResources()
                                .getString(R.string.resource_yellow_invalid_car_code));
                        inputCarCode = null;
                        tvCarName.setText("");
                        return;
                    }else if(!TextUtils.isEmpty(carTypeCode)) {
                        carTypeCode = carTypeCode.toUpperCase().trim();
                        if (!carTypeCode.equals(inputCarCode)) {
                            //调用服务接口
                            filterGetCarNamePresenter.getCarTypeByCode(BaseFilterActivity.this, carTypeCode);
                            inputCarCode = carTypeCode;
                            tvCarName.setText("");
                        }
                    }else {
                        tvCarName.setText("");
                        inputCarCode = null;
                        checkAndSetCanReset();
                    }
                }
            }
        });

        createCardPeriodPicker.setOnAllTimeClickListener(new DatePeriodPickerView.OnAllTimeClickListener() {
            @Override
            public void onAllTimeClick(boolean isSelect) {
                if(isSelect) {
                    checkAndSetCanReset();
                }
            }
        });

        createCardPeriodPicker.setOnPickerSelectedListener(new DatePeriodPickerView.OnPickerSelectedListener() {
            @Override
            public void OnPickerSelected(Date date) {
                setCanReset(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.ll_filter_sales:      //销售顾问
                intent = new Intent(this, SalesFilterActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, salesFilterCode);
                break;
            case R.id.ll_filter_series:     //车系
                intent = new Intent(this, SeriesFilterActivity.class);
                intent.putExtra("seriesCode", seriesCode);
                startActivityForResult(intent, seriesFilterCode);
                break;
            case R.id.tv_cancel:    //取消
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case R.id.tv_reset:    //重置
                if(canReset) {
                    reset();
                    setCanReset(false);
                }
                break;
            case R.id.rl_show_self_switch:    //只显示自已开关
                if(isOpenShowSelf){
                    closeShowSelf();
                    isOpenShowSelf = false;
                    checkAndSetCanReset();
                }else {
                    openShowSelf();
                    isOpenShowSelf = true;
                    setCanReset(true);
                }
                break;
        }
    }

    /**
     * 检查设置重置按钮的状态
     */
    protected void checkAndSetCanReset(){
        // 是否有显示 只显示自已 的权限
        if(PrivilegeDBUtils.isCheck(Constants.P_SA_APP_RESOURCE_FILTER, Constants.E_RESOURCE_FILTER_SHOW_SELF)) {
            if (isOpenShowSelf) {
                setCanReset(true);
                return;
            }
        }
        if(sourceSelectList.size() > 1 ||
                (sourceSelectList.size() == 1 && !sourceSelectList.get(0).isAll())){
            setCanReset(true);
            return;
        }
        if(!TextUtils.isEmpty(createCardPeriodPicker.getStartTime()) ||
                null != createCardPeriodPicker.getEndTimeDate()) {
            setCanReset(true);
            return;
        }
        if(!TextUtils.isEmpty(seriesCode)) {
            setCanReset(true);
            return;
        }
        if(!TextUtils.isEmpty(inputCarCode)) {
            setCanReset(true);
            return;
        }
        // 是否有显示 按销售顾问 的权限
        if(PrivilegeDBUtils.isCheck(Constants.P_SA_APP_RESOURCE_FILTER, Constants.E_RESOURCE_FILTER_SALES_CONSULTANT)) {
            if (!TextUtils.isEmpty(userId)) {
                setCanReset(true);
                return;
            }
        }

        checkAndSetCanResetImpl();
    }

    protected void checkAndSetCanResetImpl(){

    }

    protected void setCanReset(boolean canReset) {
        this.canReset = canReset;
        if(canReset) {
            tvReset.setTextColor(getResources().getColor(R.color.resource_main_text));
        }else {
            tvReset.setTextColor(getResources().getColor(R.color.gray_999));
        }
    }

    /**
     * 获取控件实例
     */
    protected abstract void assignViews();

    /**
     * 重置其他过滤条件，由子类按需实现
     */
    protected void resetOtherFilter(){

    }


    /**
     * 重置筛选条件
     */
    protected void reset(){
        //只显示自己
        closeShowSelf();

        //来源
        resetGridList(sourceList, sourceSelectList, sourceAdapter);

        //销售顾问
        userId = null;
        userName = null;
        tvSalesName.setText(getResources().getString(R.string.resource_sales_filter_all));

        //车系
        seriesCode = null;
        seriesName = null;
        tvSeriesName.setText(getResources().getString(R.string.resource_sales_filter_all));

        //意向车型
        inputCarCode = null;
        etCarTypeCode.setText("");
        tvCarName.setText("");

        //建卡时间
        createCardPeriodPicker.reset();

        //其他
        resetOtherFilter();
    }

    protected void resetGridList(List<ResourceFilterItemEntity> list,
                                 List<ResourceFilterItemEntity> selectList, ResourceFilterAdapter adapter){
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).isAll()) {
                list.get(i).setInitSelect(true);
            }else {
                list.get(i).setInitSelect(false);
            }
        }
        selectList.clear();
        adapter.clearSelectHolder();
        adapter.notifyDataSetChanged();
    }

    protected void openShowSelf(){
        isOpenShowSelf = true;
        ivShowSelfSwitchRightIcon.setVisibility(View.VISIBLE);
        ivShowSelfSwitchLeftIcon.setVisibility(View.GONE);
        vShowSelfSwitchBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_yellow_filter_switch_open_bg));
    }

    protected void closeShowSelf(){
        isOpenShowSelf = false;
        ivShowSelfSwitchRightIcon.setVisibility(View.GONE);
        ivShowSelfSwitchLeftIcon.setVisibility(View.VISIBLE);
        vShowSelfSwitchBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_yellow_filter_switch_close_bg));
    }

    /**
     * 处理item的点击事件
     * @param isSelect
     * @param dataList
     * @param entity
     */
    protected void dealFilterItemClick(boolean isSelect, List<ResourceFilterItemEntity> dataList,
                                     ResourceFilterItemEntity entity){
        if(isSelect){
            boolean isContainAll = false;
            for(int i = 0; i < dataList.size(); i++){
                if(dataList.get(i).isAll()){
                    isContainAll = true;
                    break;
                }
            }
            if(isContainAll || entity.isAll()){
                dataList.clear();
            }
            dataList.add(entity);
        }else {
            dataList.remove(entity);
        }
    }

    /**
     * 初始化上次选中数据对应的UI
     * @param filterIntentEntity
     */
    protected void initSelectedCommonUI(BaseFilterIntentEntity filterIntentEntity){
        //来源
        if (null != filterIntentEntity && null != filterIntentEntity.getSourceSelectList() &&
                filterIntentEntity.getSourceSelectList().size() > 0) {
            sourceSelectList.addAll(filterIntentEntity.getSourceSelectList());
        }else  {
            sourceSelectList.add(sourceList.get(0));
        }

        //只显示自己
        if(null != filterIntentEntity && filterIntentEntity.isShowSelf()){
            isOpenShowSelf = true;
            vShowSelfSwitchBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_yellow_filter_switch_open_bg));
            ivShowSelfSwitchRightIcon.setVisibility(View.VISIBLE);
            ivShowSelfSwitchLeftIcon.setVisibility(View.GONE);
        }

        //意向车型
        if(null != filterIntentEntity && !TextUtils.isEmpty(filterIntentEntity.getCarCode())){
            etCarTypeCode.setText(filterIntentEntity.getCarCode());
            inputCarCode = filterIntentEntity.getCarCode();
            if(!TextUtils.isEmpty(filterIntentEntity.getCarName())) {
                tvCarName.setText(filterIntentEntity.getCarName());
            }
        }

        //销售顾问
        if(null != filterIntentEntity && !TextUtils.isEmpty(filterIntentEntity.getUserId())){
            userId = filterIntentEntity.getUserId();
        }
        if(null != filterIntentEntity && !TextUtils.isEmpty(filterIntentEntity.getUserName())){
            userName = filterIntentEntity.getUserName();
            tvSalesName.setText(userName);
        }

        //车系
        if(null != filterIntentEntity && !TextUtils.isEmpty(filterIntentEntity.getSeriesCode())){
            seriesCode = filterIntentEntity.getSeriesCode();
        }
        if(null != filterIntentEntity && !TextUtils.isEmpty(filterIntentEntity.getSeriesName())){
            seriesName = filterIntentEntity.getSeriesName();
            tvSeriesName.setText(seriesName);
        }

        //建卡时间
        if(null == filterIntentEntity ||
                (TextUtils.isEmpty(filterIntentEntity.getCreateStartTime()) &&
                        null == filterIntentEntity.getCreateEndTimeDate()) ||
                filterIntentEntity.isCreateSelectAllTime()) {
            createCardPeriodPicker.setSelectAllTime(true);
        }else {
            if(!TextUtils.isEmpty(filterIntentEntity.getCreateStartTime())){
                createCardPeriodPicker.setStartTimeStr(filterIntentEntity.getCreateStartTime());
            }

            if(null != filterIntentEntity.getCreateEndTimeDate()){
                createCardPeriodPicker.setEndTimeDate(filterIntentEntity.getCreateEndTimeDate());
            }
            createCardPeriodPicker.setSelectAllTime(false);
        }
    }

    /**
     * 公共的过滤件条赋值
     * @param filterEntity
     */
    protected void setCommonFilterValue(BaseFilterEntity filterEntity){
        //来源
        if(sourceSelectList.size() > 0 && !sourceSelectList.get(0).isAll()) {
            StringBuilder resource = new StringBuilder();
            for (int i = 0; i < sourceSelectList.size(); i++) {
                resource.append(sourceSelectList.get(i).getCode());
                if (i != sourceSelectList.size() - 1) {
                    resource.append(",");
                }
            }
            if(!TextUtils.isEmpty(resource.toString())) {
                filterEntity.setSrcTypeId(resource.toString());
            }
        }

        //意向车型
        if(!TextUtils.isEmpty(etCarTypeCode.getText())){
            filterEntity.setCarModelId(etCarTypeCode.getText().toString());
        }

        //是否只显示自己
        if(isOpenShowSelf){
            filterEntity.setShowSelf("0");
        }
        //销售顾问
        if(!TextUtils.isEmpty(userId)) {
            filterEntity.setUserId(userId);
        }
        //车系
        if(!TextUtils.isEmpty(seriesCode)) {
            filterEntity.setSeriesId(seriesCode);
        }
        //建卡时间
        if(!createCardPeriodPicker.isSelectAllTime()){
            if(!TextUtils.isEmpty(createCardPeriodPicker.getStartTime())){
                String startFormatTime = DateUtils.dateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy年MM月dd日",
                        createCardPeriodPicker.getStartTime());
                if(!TextUtils.isEmpty(startFormatTime)) {
                    filterEntity.setCreateTimeFrom(startFormatTime);
                }
            }
            //结束时间转为已选时间的下一天的0点0分0秒
            if(null != createCardPeriodPicker.getEndTimeDate()) {
                String endTime = DateUtils.addDay("yyyy-MM-dd'T'HH:mm:ss.SSSZ", createCardPeriodPicker.getEndTimeDate(), 0);
                if(!TextUtils.isEmpty(endTime)){
                    filterEntity.setCreateTimeTo(endTime);
                }
            }

        }
    }

    protected void setPeriodPickerListener(DatePeriodPickerView periodPickerView){
        periodPickerView.setOnAllTimeClickListener(new DatePeriodPickerView.OnAllTimeClickListener() {
            @Override
            public void onAllTimeClick(boolean isSelect) {
                if(isSelect) {
                    checkAndSetCanReset();
                }
            }
        });

        periodPickerView.setOnPickerSelectedListener(new DatePeriodPickerView.OnPickerSelectedListener() {
            @Override
            public void OnPickerSelected(Date date) {
                setCanReset(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(null != data) {
            if (requestCode == salesFilterCode) {
                userId = data.getStringExtra("userId");
                userName = data.getStringExtra("userName");
                tvSalesName.setText(userName);
                if(!TextUtils.isEmpty(userId)) {
                    setCanReset(true);
                }else {
                    checkAndSetCanReset();
                }
            } else if(requestCode == seriesFilterCode) {
                seriesCode = data.getStringExtra("seriesCode");
                seriesName = data.getStringExtra("seriesName");
                tvSeriesName.setText(seriesName);
                if(!TextUtils.isEmpty(seriesCode)) {
                    setCanReset(true);
                }else {
                    checkAndSetCanReset();
                }
            }
        }
    }

    @Override
    public void showMatchCarSuccess() {
//        ToastUtils.showToast(this, getResources().getString(R.string.resource_yellow_match_car_type_success));
        setCanReset(true);
    }

    @Override
    public void showMatchCarFailed() {
        ToastUtils.showToast(getResources().getString(R.string.resource_yellow_match_car_type_fail));
    }
    @Override
    public void showLoadingDialog() {
        if(null == loadingDialog){
            loadingDialog = new LoadingDialog(this);
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
    protected void onDestroy() {
        super.onDestroy();
        if(null != createCardPeriodPicker) {
            createCardPeriodPicker.dismissPicker();
        }
    }
}
