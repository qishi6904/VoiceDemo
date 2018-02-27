package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.svw.dealerapp.R;
import com.svw.dealerapp.util.DensityUtil;

/**
 * Created by qinshi on 4/27/2017.
 */

public class NavTab extends RelativeLayout {

    private SimpleDraweeView ivTabIcon;
    private TextView tvTabTitle;
    private TextView tvTabTip;

    private int position;
    private Context context;

    public NavTab(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public NavTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.widget_second_nav_tab, this);
        assignViews();
    }

    private void assignViews() {
        ivTabIcon = (SimpleDraweeView) findViewById(R.id.iv_tab_icon);
        tvTabTitle = (TextView) findViewById(R.id.tv_tab_title);
        tvTabTip = (TextView) findViewById(R.id.tv_tab_tip);
    }

    /**
     * 设置图标的尺寸大小
     *
     * @param width
     * @param height
     */
    public void setIconSize(int width, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ivTabIcon.getLayoutParams());
        params.width = width;
        params.height = height;
        ivTabIcon.setLayoutParams(params);
    }

    /**
     * 设置提示圆的半径
     *
     * @param radius
     */
    public void setTvTabTipRadius(int radius) {
        LayoutParams params = new LayoutParams(tvTabTip.getLayoutParams());
        params.height = radius * 2;
        params.width = radius * 2;
        tvTabTip.setLayoutParams(params);
    }

    /**
     * 设置Tab标题的字体大小
     *
     * @param size
     */
    public void setTitleTextSize(int size) {
        tvTabTitle.setTextSize(size);
    }

    /**
     * 设置提示圈的标题大小
     *
     * @param size
     */
    public void setTipTextSize(int size) {
        tvTabTip.setTextSize(size);
    }

    /**
     * 设置Tab标题的字体颜色
     *
     * @param color
     */
    public void setTitleTextColor(int color) {
        tvTabTitle.setTextColor(color);
    }

    /**
     * 设置提示圈的字体颜色
     *
     * @param color
     */
    public void setTipTextColor(int color) {
        tvTabTip.setTextColor(color);
    }

    /**
     * 设置提示圈的背景
     * @param drawableId
     */
    public void setTipBackgroundDrawable(int drawableId){
        tvTabTip.setBackgroundDrawable(getResources().getDrawable(drawableId));
    }

    /**
     * 设置Tab标题内容
     *
     * @param tabTitleText
     */
    public void setTabTitleText(String tabTitleText) {
        if (!TextUtils.isEmpty(tabTitleText)) {
            tvTabTitle.setText(tabTitleText);
        }
    }

    /**
     * 设置提示圈内容
     *
     * @param tabTipText
     */
    public void setTvTabTipText(String tabTipText) {
        if (!TextUtils.isEmpty(tabTipText)) {
            tvTabTip.setText(tabTipText);
        }
    }

    /**
     * 获取提示圈内的内容
     * @return
     */
    public String getTabTipText(){
        return tvTabTip.getText().toString();
    }

    /**
     * 加载Tab图标
     *
     * @param url
     */
    public void loadIcon(String url) {

        Uri uri = Uri.parse(url);
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(false)
                        .build();
        ivTabIcon.setController(draweeController);
    }

    /**
     * 加载默认图标
     */
    public void loadDefaultIcon(int id){
        GenericDraweeHierarchy hierarchy = ivTabIcon.getHierarchy();
        hierarchy.setPlaceholderImage(id);
    }

    /**
     * 隐藏图标
     */
    public void hideIcon() {
        ivTabIcon.setVisibility(View.GONE);
    }

    /**
     * 隐藏提示圈
     */
    public void hideTipTv() {
        tvTabTip.setVisibility(View.GONE);
    }

    /**
     * 显示提示圈
     */
    public void showTipTv(){
        tvTabTip.setVisibility(View.VISIBLE);
    }

    /**
     * 设置提示圈的位置
     */
    public void setTabLayoutParams(int tabWidth, int offset) {
        LayoutParams params = new LayoutParams(tvTabTip.getLayoutParams());
        params.leftMargin = (int)(tabWidth * 0.5f + offset);
        params.topMargin = DensityUtil.dp2px(context, 6);
        tvTabTip.setLayoutParams(params);
    }

    /**
     * 设置提示圈是否为实心
     * @param isTvTipSolid
     */
    public void setTvTabTipSolid(boolean isTvTipSolid){
        if(isTvTipSolid){
            tvTabTip.setBackground(getResources().getDrawable(R.drawable.shape_nav_tab_solid_tip_round_bg));
        }
    }

    /**
     * 设置Tab在NavBar中的位置
     *
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 获取Tab在NavBar中的位置
     *
     * @return
     */
    public int getPosition() {
        return position;
    }
}
