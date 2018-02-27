package com.svw.dealerapp.api;

import android.os.Handler;
import android.os.Message;

import com.svw.dealerapp.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by qinshi on 7/4/2017.
 */

public abstract class DownloadCallback implements Callback<ResponseBody> {

    private final int refreshDownloadUI = 10001;
    private final int downloadFinish = 10002;

    private String filePath;
    private long currentSize;
    private int rangeStart;
    private DownloadHandler downloadHandler;

    public DownloadCallback(String dirPath, String fileName, int rangeStart){
        this.rangeStart = rangeStart;
        currentSize = rangeStart;
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        filePath = dirPath + "/" + fileName;

        downloadHandler = new DownloadHandler(this);
    }

    @Override
    public void onResponse(final Call<ResponseBody> call, final Response<ResponseBody> response) {

        if(null == response || null == response.body()){
            onFailure(call, new Throwable());
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                if(rangeStart <= 0){
                    FileUtils.deleteFile(filePath);
                }

                InputStream is = response.body().byteStream();
                RandomAccessFile accessFile = null;
                try {
                    accessFile = new RandomAccessFile(new File(filePath),
                            "rwd");
                    accessFile.seek(rangeStart);

                    byte [] buff = new byte[1024 * 10];
                    for(int i = is.read(buff); i != -1; i = is.read(buff)){
                        accessFile.write(buff ,0 ,i);
                        currentSize = currentSize + i;
                        Message msg = Message.obtain();
                        msg.arg1 = (int)currentSize;
                        msg.what = refreshDownloadUI;
                        downloadHandler.sendMessage(msg);
                    }
                    downloadHandler.sendEmptyMessage(downloadFinish);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(null != is){
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(null != accessFile){
                        try {
                            accessFile.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public abstract void refreshDownloadUI(long currentSize);

    public abstract void downloadFinish();

    private class DownloadHandler extends Handler {

        WeakReference<DownloadCallback> weakReference;

        DownloadHandler(DownloadCallback weakReference) {
            this.weakReference = new WeakReference<>(weakReference);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case refreshDownloadUI:
                    refreshDownloadUI(msg.arg1);
                    break;
                case downloadFinish:
                    downloadFinish();
                    break;
            }

        }
    }
}
