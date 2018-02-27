//package com.svw.dealerapp.util;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.alibaba.idst.nls.NlsClient;
//import com.alibaba.idst.nls.NlsListener;
//import com.alibaba.idst.nls.StageListener;
//import com.alibaba.idst.nls.internal.protocol.NlsRequest;
//import com.alibaba.idst.nls.internal.protocol.NlsRequestProto;
//import com.google.gson.Gson;
//import com.svw.dealerapp.R;
//
//import java.util.Map;
//
///**
// * Created by lijinkui on 2017/6/27.
// */
//
//public class NLSOneUtils {
//    private boolean isRecognizing = false;
//    private EditText mResultEdit;
//    private ImageView mButton;
//    private NlsClient mNlsClient;
//    private NlsRequest mNlsRequest;
//    private Context mContext;
//
//    public NLSOneUtils(Context context, EditText resultEdit, ImageView button){
//        mContext = context;
//        mResultEdit = resultEdit;
//        mButton = button;
//    }
//
//    public void setConfig(){
//        String appkey = "nls-service";//请设置申请到的Appkey
//
//        mNlsRequest = initNlsRequest();
//        mNlsRequest.setApp_key(appkey);//appkey请从 "快速开始" 帮助页面的appkey列表中获取
//        mNlsRequest.setAsr_sc("opu");//设置语音格式
//        /*设置热词相关属性*/
//        mNlsRequest.setAsrUserId("userid");
//        mNlsRequest.setAsrVocabularyId("vocabid");
//        mNlsRequest.authorize("LTAIoTqx7Cg2GFq4", "LQrGENR9WshOmee0KodXOj85NuigBz"); //请替换为用户申请到的数加认证key和密钥
//
//        NlsClient.openLog(true);
//        NlsClient.configure(mContext); //全局配置
//        mNlsClient = NlsClient.newInstance(mContext, mRecognizeListener, mStageListener,mNlsRequest);//实例化NlsClient
//        mNlsClient.setMaxRecordTime(60000);  //设置最长语音
//        mNlsClient.setMaxStallTime(1000);    //设置最短语音
//        mNlsClient.setMinRecordTime(500);    //设置最大录音中断时间
//        mNlsClient.setRecordAutoStop(false);  //设置VAD
//        mNlsClient.setMinVoiceValueInterval(200); //设置音量回调时长
//
//        initRecognizing();
//    }
//
//    private NlsRequest initNlsRequest(){
//        NlsRequestProto proto = new NlsRequestProto(mContext);
//        proto.setApp_user_id("svwandibm"); //设置在应用中的用户名，可选
//        return new NlsRequest(proto);
//    }
//
//    private void initRecognizing(){
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!isRecognizing){
//                    isRecognizing = true;
//                    mNlsClient.start();
//                    mButton.setImageResource(R.mipmap.nls_select);
//                }else{
//                    isRecognizing = false;
//                    mNlsClient.stop();
//                    mButton.setImageResource(R.mipmap.nls_unselect);
//                }
//            }
//        });
//    }
//
//    private NlsListener mRecognizeListener = new NlsListener() {
//
//        @Override
//        public void onRecognizingResult(int status, RecognizedResult result) {
//            switch (status) {
//                case NlsClient.ErrorCode.SUCCESS:
//                    Log.i("asr", "[demo]  callback onRecognizResult " + result.asr_out);
//                    Gson gson = new Gson();
//                    Map<String,Object> option = gson.fromJson(result.asr_out,Map.class);
//                    String content = option.get("result").toString();
//                    mResultEdit.setText(content);
//                    break;
//                case NlsClient.ErrorCode.RECOGNIZE_ERROR:
//                    Toast.makeText(mContext, "recognizer error", Toast.LENGTH_LONG).show();
//                    break;
//                case NlsClient.ErrorCode.RECORDING_ERROR:
//                    Toast.makeText(mContext,"recording error",Toast.LENGTH_LONG).show();
//                    break;
//                case NlsClient.ErrorCode.NOTHING:
//                    Toast.makeText(mContext,"nothing",Toast.LENGTH_LONG).show();
//                    break;
//            }
//            isRecognizing = false;
//        }
//
//    } ;
//
//    private StageListener mStageListener = new StageListener() {
//        @Override
//        public void onStartRecognizing(NlsClient recognizer) {
//            super.onStartRecognizing(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void onStopRecognizing(NlsClient recognizer) {
//            super.onStopRecognizing(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void onStartRecording(NlsClient recognizer) {
//            super.onStartRecording(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void onStopRecording(NlsClient recognizer) {
//            super.onStopRecording(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void onVoiceVolume(int volume) {
//            super.onVoiceVolume(volume);
//        }
//
//    };
//
//}
