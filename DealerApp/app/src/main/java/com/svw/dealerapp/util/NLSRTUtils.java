package com.svw.dealerapp.util;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.idst.nls.realtime.NlsClient;
import com.alibaba.idst.nls.realtime.NlsListener;
import com.alibaba.idst.nls.realtime.StageListener;
import com.alibaba.idst.nls.realtime.internal.protocol.NlsRequest;
import com.alibaba.idst.nls.realtime.internal.protocol.NlsResponse;
import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.widget.RecordingDialog;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lijinkui on 2017/6/27.
 */

public class NLSRTUtils {
    private boolean isRecognizing = false;
    private EditText mResultEdit;
    private String mFullResult = "";
    private ImageView mButton;
    private NlsClient mNlsClient;
    private NlsRequest mNlsRequest;
    private Activity mContext;
    private int sentenceId = 0;
//    private HashMap<Integer,String> resultMap = new HashMap<Integer, String>();
    private Map<Integer,String> resultMap = new LinkedHashMap<>();
    private Fragment currentFragment = null;

    private RecordingDialog recordingDialog;

    private Boolean enable = true;

    public NLSRTUtils(Activity context, EditText resultEdit, ImageView button, Fragment fragment){
        mContext = context;
        mResultEdit = resultEdit;
        mButton = button;
        currentFragment = fragment;
    }

    public void setConfig(){
        String appkey = "nls-service-shurufa16khz";//请设置申请到的Appkey

        mNlsRequest = new NlsRequest();
        mNlsRequest.setAppkey(appkey);    //appkey请从 "快速开始" 帮助页面的appkey列表中获取
        mNlsRequest.setResponseMode("streaming");//流式为streaming,非流式为normal
        /*设置热词相关属性*/
        //mNlsRequest.setUserId("user_id");//详情参考热词相关接口
        //mNlsRequest.setVocabularyId("vocab_id");//详情参考热词相关接口
        /*设置热词相关属性*/
        mNlsRequest.authorize("LTAIoTqx7Cg2GFq4", "LQrGENR9WshOmee0KodXOj85NuigBz"); //请替换为用户申请到的数加认证key和密钥

        NlsClient.openLog(true);
        NlsClient.configure(mContext); //全局配置
        mNlsClient = NlsClient.newInstance(mContext, mRecognizeListener, mStageListener,mNlsRequest);//实例化NlsClient

        initRecordingDialog();

        initRecognizing();
    }

    private void initRecognizing(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(enable){
                    //如果运行在6.0及以上的手机需先请求权限
                    if(Build.VERSION.SDK_INT >= 23){
                        PermissionUtils.requestPermission(mContext,
                                new String[]{Manifest.permission.RECORD_AUDIO,
                                        Manifest.permission.READ_PHONE_STATE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                PermissionUtils.REQUEST_AUDIO_RECORD_PERMISSION, new PermissionUtils.OnGrantedListener() {
                                    @Override
                                    public void onGranted() {
                                        clickRecordButton();
                                    }
                                }, currentFragment);
                    }else {
                        clickRecordButton();
                    }
                }
                
            }
        });
    }

    public void clickRecordButton(){
        if(!isRecognizing && enable){
//            mNlsClient = NlsClient.newInstance(mContext, mRecognizeListener, mStageListener,mNlsRequest);//实例化NlsClient
            isRecognizing = true;
            mNlsClient.start();
            mButton.setImageResource(R.mipmap.nls_select);
//            ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.nls_start_record));
            recordingDialog.show();
        }else{
//            isRecognizing = false;
//            mNlsClient.stop();
//            mButton.setImageResource(R.mipmap.nls_unselect);
//            mFullResult = "";
//            sentenceId = 0;
//            resultMap.clear();
//            ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.nls_stop_record));
//            recordingDialog.hide();
        }
    }

    private void initRecordingDialog (){
        recordingDialog = new RecordingDialog(mContext,R.style.recording_dialog);//创建Dialog并设置样式主题
//        Window win = recordingDialog.getWindow();
//        LayoutParams params = new LayoutParams();
//        params.x = -80;//设置x坐标
//        params.y = -60;//设置y坐标
//        win.setAttributes(params);
        recordingDialog.setOnBtnClickListener(new RecordingDialog.OnBtnClickListener() {
            @Override
            public void onCompleteBtnClick() {
//                isRecognizing = false;
                mNlsClient.stop();
//                mButton.setImageResource(R.mipmap.nls_unselect);
//                mFullResult = "";
//                sentenceId = 0;
//                resultMap.clear();
//                recordingDialog.dismiss();
            }
        });
        recordingDialog.setCanceledOnTouchOutside(false);
    }

    private NlsListener mRecognizeListener = new NlsListener() {
        @Override
        public void onRecognizingResult(int status, NlsResponse result) {
            switch (status) {
                case NlsClient.ErrorCode.SUCCESS:
                    if (result!=null){
                        if(result.getResult()!=null) {
                            //获取句子id对应结果。
                            if (sentenceId != result.getSentenceId()) {
                                Log.i("asr", "[demo] callback result.getSentenceId() :" + result.getSentenceId());
                                sentenceId = result.getSentenceId();
                                mFullResult = "";
                                mFullResult = mResultEdit.getText().toString();
                            }
                            resultMap.put(sentenceId,result.getText());
//                            for (Map.Entry<Integer, String> entry : resultMap.entrySet()){
//                                mFullResult = mFullResult+entry.getValue();
//                            }
                            Log.i("asr", "[demo] callback onRecognizResult :" + result.getResult().getText());
                            mResultEdit.setText(mFullResult+resultMap.get(sentenceId));
                            int length = (mFullResult+resultMap.get(sentenceId)).length();
                            if(length < getMaxLengthForTextView(mResultEdit)){
                                mResultEdit.setSelection(length);
                            }else {
                                mResultEdit.setSelection(getMaxLengthForTextView(mResultEdit));
                            }
                        }
                    }else {
                        Log.i("asr", "[demo] callback onRecognizResult finish!" );
                    }
                    break;
                case NlsClient.ErrorCode.RECOGNIZE_ERROR:
                    Toast.makeText(mContext, "recognizer error", Toast.LENGTH_LONG).show();
                    break;
                case NlsClient.ErrorCode.RECORDING_ERROR:
                    Toast.makeText(mContext,"recording error", Toast.LENGTH_LONG).show();
                    break;
                case NlsClient.ErrorCode.NOTHING:
                    Toast.makeText(mContext,"nothing", Toast.LENGTH_LONG).show();
                    break;
            }
//            isRecognizing = false;
        }
    } ;
    private StageListener mStageListener = new StageListener() {
        @Override
        public void onStartRecognizing(NlsClient recognizer) {
            super.onStartRecognizing(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }
        @Override
        public void onStopRecognizing(NlsClient recognizer) {
            super.onStopRecognizing(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
//            if(isRecognizing){
            isRecognizing = false;
            mNlsClient.stop();
            mButton.setImageResource(R.mipmap.nls_unselect);
            mFullResult = "";
            sentenceId = 0;
//            mNlsClient = null;
            resultMap.clear();
            recordingDialog.dismiss();
//            }

        }
        @Override
        public void onStartRecording(NlsClient recognizer) {
            super.onStartRecording(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }
        @Override
        public void onStopRecording(NlsClient recognizer) {
            super.onStopRecording(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }
        @Override
        public void onVoiceVolume(int volume) {
            super.onVoiceVolume(volume);
        }
    };

    public void stopNLS(){
        isRecognizing = false;
        mNlsClient.stop();
        mButton.setImageResource(R.mipmap.nls_unselect);
        mFullResult = "";
        sentenceId = 0;
//        mNlsClient = null;
        resultMap.clear();
    }

    public int getMaxLengthForTextView(TextView textView)
    {
        int maxLength = -1;

        for (InputFilter filter : textView.getFilters()) {
            if (filter instanceof InputFilter.LengthFilter) {
                try {
                    Field maxLengthField = filter.getClass().getDeclaredField("mMax");
                    maxLengthField.setAccessible(true);

                    if (maxLengthField.isAccessible()) {
                        maxLength = maxLengthField.getInt(filter);
                    }
                } catch (IllegalAccessException e) {
                    Log.w(filter.getClass().getName(), e);
                } catch (IllegalArgumentException e) {
                    Log.w(filter.getClass().getName(), e);
                } catch (NoSuchFieldException e) {
                    Log.w(filter.getClass().getName(), e);
                } // if an Exception is thrown, Log it and return -1
            }
        }

        return maxLength;
    }

    public void setEnabled(boolean status){
        enable = status;
    }

}
