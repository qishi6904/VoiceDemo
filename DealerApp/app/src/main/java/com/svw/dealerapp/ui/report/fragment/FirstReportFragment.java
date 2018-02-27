package com.svw.dealerapp.ui.report.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.common.BaseWebViewFragment;
import com.svw.dealerapp.ui.report.activity.SubReportActivity;
import com.svw.dealerapp.ui.report.contract.ReportContract;
import com.svw.dealerapp.ui.report.model.ReportModel;
import com.svw.dealerapp.ui.report.presenter.ReportPresenter;
import com.svw.dealerapp.util.CommonUtils;

/**
 * Created by lijinkui on 2017/8/25.
 */

public class FirstReportFragment extends BaseWebViewFragment implements ReportContract.View{

    private ReportPresenter mReportPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReportPresenter = new ReportPresenter(new ReportModel(),this);
    }

    @Override
    public void setJSInterface(WebView webView) {
        webView.addJavascriptInterface(new JSInterfaceForFR(),"Android");
    }

    private class JSInterfaceForFR extends JSInterface{
        @JavascriptInterface
        public void updateWebToken(){
            String accessToken = getAccessTokenFromDB();
            mReportPresenter.updateWebToken(accessToken);
        }

        @JavascriptInterface
        public void goToSubReport(String url){
            Intent intent = new Intent(getActivity(), SubReportActivity.class);
            intent.putExtra("URL", url);
            startActivity(intent);
        }
    }

    @Override
    public void getWebTokenSuccess(String result) {
        saveWebTokenFromDB(result);
        CommonUtils.syncCookie(getUri(),"webToken="+result);
        getWebView().loadUrl(getUri());
    }

    @Override
    public void getWebTokenFail(String result) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != mReportPresenter){
            mReportPresenter.onDestroy();
        }
    }

    private void saveWebTokenFromDB(String webToken){
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from User", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ContentValues values = new ContentValues();
                values.put("webToken", webToken);
                DealerApp.dbHelper.update("User",values,null,null);
            }
            cursor.close();
        }
    }

    private String getAccessTokenFromDB(){
        String result = "";
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from User", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                result = cursor.getString(cursor.getColumnIndex("accessToken"));
            }
            cursor.close();
        }
        return result;
    }
}
