package com.android.sjq.okhttprequest;

/**
 * Created by Administrator on 2016/11/3.
 */

public interface  onHttpRequestFinishListener {
      void onSuccess(Object data);

     void onFailure(RequestException e);
}
