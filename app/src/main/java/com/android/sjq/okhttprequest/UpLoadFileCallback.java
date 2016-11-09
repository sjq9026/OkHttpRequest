package com.android.sjq.okhttprequest;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/9.
 */

public class UpLoadFileCallback extends RequestCallBack {
    private OnUpLoadFileListener mListener;
    private Handler mHandler;

    public UpLoadFileCallback(OnUpLoadFileListener listener) {
        this.mListener = listener;
        this.mHandler = new Handler(Looper.getMainLooper());
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
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onSuccess("上传完成！");
                }
            });
        }
    }
}
