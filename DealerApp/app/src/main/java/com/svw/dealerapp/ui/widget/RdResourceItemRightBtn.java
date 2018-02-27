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
 * Created by qinshi on 1/13/2018.
 */

public class RdResourceItemRightBtn extends LinearLayout{

    private ImageView ivIcon;
    private TextView tvTip;
    private OnItemClickListener onItemClickListener;

    public RdResourceItemRightBtn(Context context) {
        super(context);
        initView(context);
    }

    public RdResourceItemRightBtn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.rd_resource_item_btn, this);
        assignViews(view);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onItemClickListener) {
                    onItemClickListener.onItemClick(v);
                }
            }
        });
    }

    private void assignViews(View view) {
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        tvTip = (TextView) view.findViewById(R.id.tv_tip);
    }

    public void setIconById(int id) {
        ivIcon.setImageResource(id);
    }

    public void setTip(String tip) {
        tvTip.setText(tip);
    }

    public interface OnItemClickListener {
        void onItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
