package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.util.DensityUtil;

/**
 * Created by qinshi on 11/30/2017.
 */

public class OptionalPackageListItem extends LinearLayout {

    private boolean isSelect = false;
    private Context context;
    private OnOptionalSelectListener onOptionalSelectListener;
    private OnItemSelectListener onItemSelectListener;
    private boolean canFold = false; //是否可以折叠
    private boolean isShowDetail = false; //是否显示被折叠的详情
    private int selectIndex = -1;
    private RadioSelectorView selectorView;

    private LinearLayout rootView;
    private TextView tvOptionalTip;
    private ImageView ivDetailIndicator;
    private LinearLayout llOptionalContainer;

    public OptionalPackageListItem(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public OptionalPackageListItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.ui_optional_package_list_item, this);
        rootView = (LinearLayout) view.findViewById(R.id.ll_root_view);
        llOptionalContainer = (LinearLayout) rootView.findViewById(R.id.ll_optional_container);
        tvOptionalTip = (TextView) rootView.findViewById(R.id.tv_optional_tip);
        ivDetailIndicator = (ImageView) rootView.findViewById(R.id.iv_detail_indicator);

        if(!canFold){
            ivDetailIndicator.setVisibility(View.GONE);
        }

        //条目点击事件
        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelect = !isSelect;
                setSelect(isSelect);
                if(!isSelect){
                    if(null != selectorView) {
                        selectorView.setSelect(false);
                        selectorView = null;
                    }
                }
                if(null != onItemSelectListener) {
                    onItemSelectListener.onOptionalItemSelect(isSelect);
                }
            }
        });

        if(canFold) {
            ivDetailIndicator.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    isShowDetail = !isShowDetail;
                    if (isShowDetail) {
                        showOptionalItem();
                    } else {
                        hideOptionalItem();
                    }
                }
            });
        }
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        if(select){
            rootView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_optional_package_item_select_bg));
        }else {
            rootView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_optional_package_item_unselect_bg));
        }
    }

    /**
     * 设置条目的文字
     * @param itemText
     */
    public void setItemText(String itemText) {
        tvOptionalTip.setText(itemText);
    }

    /**
     * 显示选项
     */
    public void showOptionalItem(){
        llOptionalContainer.setVisibility(View.VISIBLE);
        ivDetailIndicator.setImageResource(R.mipmap.ic_arrow_up_gray);
    }

    /**
     * 隐藏选项
     */
    public void hideOptionalItem(){
        llOptionalContainer.setVisibility(GONE);
        ivDetailIndicator.setImageResource(R.mipmap.ic_arrow_down_gray);
    }

    public void selectOptionByIndex(int selectIndex) {
        if(selectIndex < llOptionalContainer.getChildCount()) {
            RadioSelectorView radioSelectorView = (RadioSelectorView) llOptionalContainer.getChildAt(selectIndex);
            radioSelectorView.setSelect(true);
        }
    }

    /**
     * 添加选项
     * @param index
     * @param optionalText
     * @param isSelect
     */
    public void addOptionalItem(final int index, String optionalText, boolean isSelect) {
        RadioSelectorView radioSelectorView = null;
        if(llOptionalContainer.getChildCount() > index){
            radioSelectorView = (RadioSelectorView) llOptionalContainer.getChildAt(index);
            radioSelectorView.setVisibility(View.VISIBLE);
        }else {
            radioSelectorView = new RadioSelectorView(context);
            radioSelectorView.setPadding(0, DensityUtil.dp2px(context, 10), 0, 0);
            llOptionalContainer.addView(radioSelectorView);
            LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            radioSelectorView.setLayoutParams(layoutParams);
        }
        if(null != radioSelectorView) {
            radioSelectorView.setTipText(optionalText);
            final RadioSelectorView finalRadioSelectorView = radioSelectorView;
            radioSelectorView.setSelect(isSelect);
            if(isSelect){
                selectorView = radioSelectorView;
            }
            //条目下选项的点击事件
            radioSelectorView.setOnSelectListener(new RadioSelectorView.OnSelectListener() {
                @Override
                public void OnSelect(boolean isSelect) {
                    if(null != onOptionalSelectListener) {
                        onOptionalSelectListener.onOptionalItemSelect(index, isSelect);
                    }
                    if(isSelect) {
                        OptionalPackageListItem.this.isSelect = true;
                        setSelect(OptionalPackageListItem.this.isSelect);
                        if(null != selectorView && selectorView != finalRadioSelectorView) {
                            selectorView.setSelect(false);
                        }
                        selectorView = finalRadioSelectorView;
                    }
                }
            });
        }
    }

    public interface OnOptionalSelectListener {
        void onOptionalItemSelect(int index, boolean isSelect);
    }

    public void setOnOptionalSelectListener(OnOptionalSelectListener listener){
        this.onOptionalSelectListener = listener;
    }

    public interface OnItemSelectListener {
        void onOptionalItemSelect(boolean isSelect);
    }

    public void setOnItemSelectListener(OnItemSelectListener listener){
        this.onItemSelectListener = listener;
    }

    public boolean isCanFold() {
        return canFold;
    }

    public void setCanFold(boolean canFold) {
        this.canFold = canFold;
        if(!canFold){
            ivDetailIndicator.setVisibility(View.GONE);
        }
    }

    public int getOptionChildCount(){
        return llOptionalContainer.getChildCount();
    }

    public void hideOptionChildView(int index){
        if(index < llOptionalContainer.getChildCount()){
            llOptionalContainer.getChildAt(index).setVisibility(View.GONE);
        }
    }
}