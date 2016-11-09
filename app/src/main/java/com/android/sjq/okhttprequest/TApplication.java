package com.android.sjq.okhttprequest;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/11/4.
 */

public class TApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = TApplication.this;
    }
}
