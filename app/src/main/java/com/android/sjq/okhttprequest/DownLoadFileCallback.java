package com.android.sjq.okhttprequest;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 处理请求成功和失败的回调
 * Created by Administrator on 2016/11/4.
 */
public class DownLoadFileCallback extends RequestCallBack {
    private onDownLoadFileListener mListener;
    private String mFilePath;
    private Handler mHandler;
    FileOutputStream outputStream;

    public DownLoadFileCallback(onDownLoadFileListener listener, String filepath) {
        Log.i("create","------------->DownLoadFileCallback()");
        this.mListener = listener;
        this.mFilePath = filepath;
        mListener.onStart();
        mHandler = new Handler(Looper.getMainLooper());

    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new RequestException(e.getMessage()));
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        InputStream inputStream = response.body().byteStream();
        outputStream = new FileOutputStream(new File(mFilePath));
        byte[] bytes = new byte[10];
        int len = 0;
        int count = 0;
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
            count += len;
            Log.i("TAG", "已写入本地SD卡------>"+(count) + "");
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onSuccess("下载成功");
            }
        });
    }
}
