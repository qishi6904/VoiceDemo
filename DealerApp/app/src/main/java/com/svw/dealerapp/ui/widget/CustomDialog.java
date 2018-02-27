package com.svw.dealerapp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.adapter.CustomDialogAdapter;

/**
 * Created by qinshi on 5/10/2017.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {

    private TextView tvDialogTitle;
    private ImageView ivClose;
    private LinearLayout llContentContainer;
    private Button btnCancel;
    private Button btnConfirm;
    private View vBtnDivider;
    private ImageView ivTitleIcon;
    private View vTitleUnderLine;

    private OnBtnClickListener onBtnClickListener;

    public CustomDialog(@NonNull Context context, CustomDialogAdapter adapter) {
        super(context, R.style.custom_dialog);
        setContentView(R.layout.dialog_custom);
        assignViews();

        this.setCanceledOnTouchOutside(false);

        //设置对话框的具体内容视图
        if(null != adapter) {
            View contentView = adapter.getDialogView(context);
            if (null != contentView) {
                llContentContainer.addView(contentView, 0);
            }
        }

        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }

    private void assignViews() {
        tvDialogTitle = (TextView) findViewById(R.id.tv_dialog_title);
        ivClose = (ImageView) findViewById(R.id.iv_close);
        llContentContainer = (LinearLayout) findViewById(R.id.ll_content_container);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        vBtnDivider = findViewById(R.id.v_btn_divider);
        ivTitleIcon = (ImageView) findViewById(R.id.iv_title_icon);
        vTitleUnderLine = findViewById(R.id.v_title_under_line);
    }

    /**
     * 设置对话框的标题
     * @param title
     */
    public void setDialogTitle(String title){
        tvDialogTitle.setText(title);
    }

    /**
     * 隐藏消取按钮
     */
    public void hideShowCancelBtn(){
        btnCancel.setVisibility(View.GONE);
        vBtnDivider.setVisibility(View.GONE);
    }

    /**
     * 隐藏确认按钮
     */
    public void hideShowConfirmBtn(){
        btnConfirm.setVisibility(View.GONE);
        vBtnDivider.setVisibility(View.GONE);
    }

    /**
     * 显示确认按钮
     */
    public void showConfirmBtn(){
        btnConfirm.setVisibility(View.VISIBLE);
        vBtnDivider.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                if(null != onBtnClickListener){
                    onBtnClickListener.onCancelBtnClick();
                }
                break;
            case R.id.btn_confirm:
                if(null != onBtnClickListener){
                    onBtnClickListener.onConfirmBtnClick();
                }
                break;
            case R.id.iv_close:
                this.dismiss();
                break;
        }
    }

    /**
     * 设置标题的Icon
     * @param resId
     */
    public void setTitleIcon(int resId){
        ivTitleIcon.setImageResource(resId);
    }

    /**
     * 按钮回调接口
     */
    public interface OnBtnClickListener{
        void onCancelBtnClick();
        void onConfirmBtnClick();
    }

    /**
     * 设置按钮的回调接口
     * @param onBtnClickListener
     */
    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener){
        this.onBtnClickListener = onBtnClickListener;
    }

    /**
     * 设置取消按钮的文字
     * @param text
     */
    public void setBtnCancelText(String text){
        btnCancel.setText(text);
    }

    /**
     * 设置确认按钮的文字
     * @param text
     */
    public void setBtnConfirmText(String text){
        btnConfirm.setText(text);
    }

    /**
     * 隐藏标题上的图标
     */
    public void hideTitleIcon(){
        ivTitleIcon.setVisibility(View.GONE);
    }

    /**
     * 显示标题上的图标
     */
    public void showTitleIcon(){
        ivTitleIcon.setVisibility(View.VISIBLE);
    }

    /**
     * 设置标题字体大小
     * @param size
     */
    public void setTitleTextSize(int size){
        tvDialogTitle.setTextSize(size);
    }

    /**
     * 显示题标下划线
     */
    public void showTitleUnderLine(){
        vTitleUnderLine.setVisibility(View.VISIBLE);
    }

}
