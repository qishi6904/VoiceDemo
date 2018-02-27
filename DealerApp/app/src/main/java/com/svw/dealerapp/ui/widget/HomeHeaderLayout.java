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
 * Created by qinshi on 6/9/2017.
 */

public class HomeHeaderLayout extends LinearLayout {

    private ImageView ivHeaderIcon;
    private TextView tvHeaderNumber;
    private TextView tvHeaderText;
    private View vDivider;
    private TextView tvCircleTip;

    public HomeHeaderLayout(Context context) {
        super(context);
        initView(context);
    }

    public HomeHeaderLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        View view = View.inflate(context, R.layout.item_home_header, this);
        assignViews(view);
    }

    private void assignViews(View view) {
        ivHeaderIcon = (ImageView) view.findViewById(R.id.iv_header_icon);
        tvHeaderNumber = (TextView) view.findViewById(R.id.tv_header_number);
        tvHeaderText = (TextView) view.findViewById(R.id.tv_header_text);
        vDivider = view.findViewById(R.id.v_divider);
        tvCircleTip = (TextView) view.findViewById(R.id.tv_delay_num);

    }

    public void setIvHeaderIcon(int resId){
        ivHeaderIcon.setImageResource(resId);
    }

    public void setTvHeaderNumber(String number){
        tvHeaderNumber.setText(number);
    }

    public String getTVHeaderNumber(){
        return tvHeaderNumber.getText().toString();
    }

    public void setTvHeaderText(String text){
        tvHeaderText.setText(text);
    }

    public void hideDivider(){
        vDivider.setVisibility(View.GONE);
    }

    public void hideCircleTip(){
        tvCircleTip.setVisibility(View.INVISIBLE);
    }

    public void setCircleTip(String circleTip){
        tvCircleTip.setVisibility(View.VISIBLE);
        tvCircleTip.setText(circleTip);
    }
}
