package com.android.sjq.okhttprequest;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Administrator on 2016/11/9.
 */

public abstract class OnUpLoadFileListener {
    static final int UP_LOAD_PROGRESS_UPDATE = 0;
    UpLoadFileHandler mHandler = new UpLoadFileHandler(Looper.getMainLooper());

    public void onStart(){}

    public void onUIProgressUpdate(long already_up_load, long length, boolean done){}

    abstract void onFailure(RequestException e);

    abstract void onSuccess(Object data);


    public void onProgressUpdate(long already_up_load, long length, boolean done) {
        ProgressModel model = new ProgressModel();
        model.setAlready_down(already_up_load);
        model.setLength(length);
        model.setDone(done);
        Message msg = Message.obtain();
        msg.what = UP_LOAD_PROGRESS_UPDATE;
        msg.obj = model;
        mHandler.sendMessage(msg);
    }


    class UpLoadFileHandler extends Handler {
        public UpLoadFileHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UP_LOAD_PROGRESS_UPDATE:
                    ProgressModel model = (ProgressModel) msg.obj;
                    onUIProgressUpdate(model.already_down, model.length, model.done);
                    break;
            }
        }
    }
}
