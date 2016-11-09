package com.android.sjq.okhttprequest;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/3.
 */

public class RequestCallBack implements Callback {

    onHttpRequestFinishListener mListener;
    Class<?> aClass = null;
    Handler mHanlder;

    public RequestCallBack() {

    }

    public RequestCallBack(onHttpRequestFinishListener listener) {
        this.mListener = listener;
        this.aClass = null;
        mHanlder = new Handler(Looper.getMainLooper());
    }

    public RequestCallBack(onHttpRequestFinishListener listener, Class<?> clazz) {
        this.mListener = listener;
        this.aClass = clazz;
        mHanlder = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mHanlder.post(new Runnable() {
            @Override
            public void run() {
                String msg = e.getMessage();
                mListener.onFailure(new RequestException("请求失败"));
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        //工作线t程
        final String str = response.body().string();
        Object obj = null;
        if (aClass != null) {
            obj = new Gson().fromJson(str, aClass);
        }
        final Object obj1 = obj;
        //UI线程
        mHanlder.post(new Runnable() {
            @Override
            public void run() {
                if (aClass == null) {
                    mListener.onSuccess(str);
                } else {
                    mListener.onSuccess(obj1);
                }

            }
        });

    }
}
