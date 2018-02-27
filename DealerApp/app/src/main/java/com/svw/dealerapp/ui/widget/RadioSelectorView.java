package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;

/**
 * Created by qinshi on 11/30/2017.
 */

public class RadioSelectorView extends LinearLayout {

    private boolean isSelect;
    private OnSelectListener onSelectListener;

    private Context context;
    private ImageView ivSelectorIndicator;
    private TextView tvSelectorTip;

    public RadioSelectorView(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public RadioSelectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    private void initView(Context context){
        View view = View.inflate(context, R.layout.ui_radio_selector_view, this);
        ivSelectorIndicator = (ImageView) view.findViewById(R.id.iv_selector_indicator);
        tvSelectorTip = (TextView) view.findViewById(R.id.tv_selector_tip);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelect = !isSelect;
                if(null != onSelectListener) {
                    onSelectListener.OnSelect(isSelect);
                }
                setSelect(isSelect);
            }
        });
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        if(select){
            ivSelectorIndicator.setBackgroundResource(R.mipmap.ic_circle_radio_select);
        }else {
            ivSelectorIndicator.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.shape_yellow_transfer_unselect_icon_bg));
        }
    }

    public void setTipText(String tipText) {
        tvSelectorTip.setText(tipText);
    }

    public interface OnSelectListener {
        void OnSelect(boolean isSelect);
    }

    public void setOnSelectListener(OnSelectListener listener){
        this.onSelectListener = listener;
    }
}
