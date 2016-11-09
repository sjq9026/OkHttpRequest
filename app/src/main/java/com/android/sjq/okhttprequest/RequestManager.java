package com.android.sjq.okhttprequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/4.
 */

public class RequestManager {
    static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();


    /**
     * http get请求
     *
     * @param request
     * @param callBack
     */
    public static void httpGet(Request request, RequestCallBack callBack) {
        client.newCall(request).enqueue(callBack);
    }

    /**
     * http post请求
     *
     * @param request
     * @param callBack
     */
    public static void httpPost(Request request, RequestCallBack callBack) {
        client.newCall(request).enqueue(callBack);
    }


    /***
     * 文件上传post 请求
     *
     * @param request
     * @param callBack
     */
    public static void httpUpLoadFile(Request request, RequestCallBack callBack) {
        client.newCall(request).enqueue(callBack);
    }

    /**
     * 文件下载请求
     *
     * @param request
     * @param callback
     */
    public static void httpDownloadFile(Request request, RequestCallBack callback) {
        client.newCall(request).enqueue(callback);
    }

    /**
     * @param request
     * @param callBack
     * @param listener
     * @param isShowProgress
     */
    public static void httpDownLoadFileProgress(Request request, final RequestCallBack callBack, final onDownLoadFileListener listener, boolean isShowProgress) {
        if (!isShowProgress) {
            httpDownloadFile(request, callBack);
        } else {
            OkHttpClient client1 = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Response response = chain.proceed(chain.request());
                            return response.newBuilder().body(new ProgressResponseBody(response.body(), listener)).build();
                        }
                    })
                    .build();
            client1.newCall(request).enqueue(callBack);
        }
    }

    /**
     * @param request            request实体类
     * @param filePath          下载文件的路径
     * @param listener          下载过程中的回调
     * @param isShowProgress    是否显示下载进度
     */
    public static void httpDownLoadFileProgress(Request request, String filePath,
                                                final onDownLoadFileListener listener,
                                                boolean isShowProgress) {
        DownLoadFileCallback callBack = new DownLoadFileCallback(listener, filePath);
        if (!isShowProgress) {
            httpDownloadFile(request, callBack);
        } else {
            OkHttpClient client1 = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Response response = chain.proceed(chain.request());
                            return response.newBuilder().body(new ProgressResponseBody(response.body(), listener)).build();
                        }
                    })
                    .build();
            client1.newCall(request).enqueue(callBack);
        }
    }

    /**
     * @param listener
     * @param isShowPro
     */
    public static void httpUpLoadFileProress(String url, final RequestBody requestBody, final OnUpLoadFileListener listener, boolean isShowPro) {
        UpLoadFileCallback callback = new UpLoadFileCallback(listener);
        if (!isShowPro) {
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            httpUpLoadFile(request, callback);
        } else {
            ProgressRequestBody body = new ProgressRequestBody(requestBody,listener);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callback);
        }
    }
}
