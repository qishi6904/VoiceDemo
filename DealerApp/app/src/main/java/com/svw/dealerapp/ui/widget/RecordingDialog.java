package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.svw.dealerapp.R;
import com.svw.dealerapp.util.imageload.ImageLoaderManager;
import com.svw.dealerapp.util.imageload.ImageLoaderOptions;

import static com.alibaba.sdk.android.ams.common.global.AmsGlobalHolder.getPackageName;

/**
 * Created by lijinkui on 2017/7/18.
 */

public class RecordingDialog extends AlertDialog implements View.OnClickListener {

    private ImageView recordingGif;
    private Button completeBtn;
    private OnBtnClickListener onBtnClickListener;
    private Context mContext;

    public RecordingDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public RecordingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recording);
        assignViews();
        completeBtn.setOnClickListener(this);

//        Glide.with(mContext).load(R.drawable.recording).diskCacheStrategy(DiskCacheStrategy.ALL).into(recordingGif);

        //ImageLoaderManager中init方法中设置初次启动选择哪个图片库
        //若使用fresco，静态资源使用："android.resource://" + getPackageName() + "/" + R.drawable.recording
        ImageLoaderOptions options = new ImageLoaderOptions.Builder(recordingGif, R.drawable.recording)
                .diskCacheStrategy(ImageLoaderOptions.DiskCacheStrategy.All)
                .asGif(true)
                .build();
        ImageLoaderManager.getInstance().showImage(options);
    }

    private void assignViews() {
        recordingGif = (ImageView) findViewById(R.id.iv_gif);
        completeBtn = (Button) findViewById(R.id.btn_complete);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_complete:
                if (null != onBtnClickListener) {
                    onBtnClickListener.onCompleteBtnClick();
                }
                break;
        }
    }

    /**
     * 按钮回调接口
     */
    public interface OnBtnClickListener {
        void onCompleteBtnClick();
    }

    /**
     * 设置按钮的回调接口
     *
     * @param onBtnClickListener
     */
    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        this.onBtnClickListener = onBtnClickListener;
    }
}
