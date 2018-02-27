package com.svw.dealerapp.common;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.JLog;

/**
 * Created by lijinkui on 2017/7/28.
 */

public abstract class BaseWebViewFragment extends Fragment {

    private String TAG = "BaseWebViewFragment";

    private WebView mWebView;
    private String mUrl;
    private LoadingDialog loadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mWebView = new WebView(getActivity());
        return mWebView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewGroup.LayoutParams params = mWebView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        mWebView.setLayoutParams(params);


        mWebView.getSettings().setJavaScriptEnabled(true);//设置支持JavaScript脚本
        //缩放操作
        mWebView.getSettings().setSupportZoom(true);//设置是否支持缩放
        mWebView.getSettings().setBuiltInZoomControls(true);//设置内置的缩放控件
        mWebView.getSettings().setDisplayZoomControls(false);//设置隐藏原生的缩放控件
        //设置自适应屏幕，两者合用
        mWebView.getSettings().setUseWideViewPort(true);//设置将图片调整到适合webview的大小
        mWebView.getSettings().setLoadWithOverviewMode(true);//设置缩放至屏幕的大小

        mWebView.getSettings().setSupportMultipleWindows(true);//设置多窗口
//        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//设置支持内容重新布局
        mWebView.getSettings().setCacheMode(mWebView.getSettings().LOAD_DEFAULT); //设置webview缓存模式
//        mWebView.getSettings().setAppCachePath(getActivity().getApplication().getDir("cache", getContext().MODE_PRIVATE).getPath());//设置应用缓存目录
        mWebView.getSettings().setAppCacheEnabled(true);//开启应用缓存功能
        mWebView.getSettings().setDatabaseEnabled(true);//开启数据缓存功能
        mWebView.getSettings().setAllowFileAccess(true);//设置可以访问文件
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置支持通过JS打开新窗口
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.getSettings().setLoadsImagesAutomatically(true);//设置支持自动加载图片
        } else {
            mWebView.getSettings().setLoadsImagesAutomatically(false);
        }
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");//设置设置编码格式

        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());

        mUrl = getArguments().getString("URL");

        String webToken = getWebTokenFromDB();
        if (!TextUtils.isEmpty(webToken)) {
            CommonUtils.syncCookie(mUrl, "webToken=" + webToken);
        }

//        Map<String, String> extraHeaders;
//        extraHeaders = new HashMap<>();
//        extraHeaders.put("Cache-Control", "max-age=3600");
//        mWebView.loadUrl(mUrl, extraHeaders);
        mWebView.loadUrl(mUrl);

        mWebView.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }
                return false;
            }

        });

        setJSInterface(mWebView);
    }

    public WebView getWebView() {
        return mWebView;
    }

    public String getUri() {
        return mUrl;
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            JLog.e(TAG, "url = " + url);

            //第一次附加在Activity 上时只加载一次
//            if (mWebViewLoadImpl != null && !mWebViewLoadImpl.isActFirstCreate()) {
//                mWebViewLoadImpl.shouldOverrideUrlLoading(view, url);
//                JLog.e(TAG, "拦截了 ");
//                return true;
//            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoadingDialog();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                mWebView.getSettings().setLoadsImagesAutomatically(true);
            }
            hideLoadingDialog();
        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    webViewGoBack();
                }
                break;
            }
        }
    };

    private void webViewGoBack() {
        mWebView.goBack();
    }

    public class JSInterface {
        //JS需要调用的方法
//        @JavascriptInterface
    }

    public abstract void setJSInterface(WebView webView);

    private String getWebTokenFromDB() {
        String result = "";
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from SMToken", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                result = cursor.getString(cursor.getColumnIndex("accessSubToken"));
            }
            cursor.close();
        }
        return result;
    }

    private void showLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    private void hideLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        handler = null;
    }
}
