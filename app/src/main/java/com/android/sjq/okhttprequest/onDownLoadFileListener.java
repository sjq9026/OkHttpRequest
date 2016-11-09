package com.android.sjq.okhttprequest;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * 1、定义相关回调函数
 * 2、切换线程
 * Created by Administrator on 2016/11/4.
 */
public abstract class onDownLoadFileListener {
    private static final int PROGRESS_UPDATE_WHAT = 0;
    //开始下载，根据需求决定是否重写
    public void onStart(){}
    //下载进度更新，根据需求决定是否重写
    public void onUIProgressUpdate(long already_down, long length, boolean done){}
    //下载完成，根据需求决定是否重写
    public void onDownLoadFinish(){}
    //下载失败的回调
    abstract void onFailure(RequestException e);
    //下载成功的回调
    abstract void onSuccess(Object data);
    private Handler mHandler;
    //虽然handler都是关联的主线程的Looper，但是handler机制内部应该还维护着一个队列，
    // 每个handler处理自己对应的消息handleMessage
    public class UIHandler extends Handler {
        public UIHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == PROGRESS_UPDATE_WHAT) {
                Log.i("update", "onProgressUpdate------>" + msg.obj.toString());
                ProgressModel model = (ProgressModel) msg.obj;
                //UI线程更新界面信息
                onUIProgressUpdate(model.already_down, model.length, model.done);
            }
        }
    }
    public onDownLoadFileListener() {
        mHandler = new UIHandler(Looper.getMainLooper());
    }
    /**
     * 切换UI线程，更新界面
     * @param already
     * @param length
     * @param done
     */
    public void onProgressUpdate(long already, long length, boolean done) {
        Log.i("update", "onProgressUpdate------>" + already);
        Message msg = Message.obtain();
        ProgressModel model = new ProgressModel();
        model.setAlready_down(already);
        model.setLength(length);
        model.setDone(done);
        msg.what = PROGRESS_UPDATE_WHAT;
        msg.obj = model;
        mHandler.sendMessage(msg);
    }
}
