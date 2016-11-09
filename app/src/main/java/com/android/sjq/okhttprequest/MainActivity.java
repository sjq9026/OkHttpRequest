package com.android.sjq.okhttprequest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button http_get;
    TextView result_tv;
    Button post_btn;
    Button upload;
    Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        http_get = (Button) findViewById(R.id.http_get);
        result_tv = (TextView) findViewById(R.id.result_tv);
        post_btn = (Button) findViewById(R.id.post_btn);
        upload = (Button) findViewById(R.id.upload);
        download = (Button) findViewById(R.id.download);
        upload.setOnClickListener(this);
        download.setOnClickListener(this);
        http_get.setOnClickListener(this);
        post_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.http_get:
                requestGetHttp();
                break;
            case R.id.post_btn:
                requestPostHttp();
                break;
            case R.id.upload:
                requestUpLoad();
                break;
            case R.id.download:
                requestDownLoad();
                break;
        }


    }


    private void requestPostHttp() {
        String url = "http://58.kxpo2o.cn/mjsb02/AccessPoint.do?";
        Map<String, String> map = new HashMap<>();
        map.put("action", "LOGIN");
        map.put("method", "USER_LOGIN");
        map.put("content", "{\"MALastUpdateDate\":\"2015/11/30 15:58:37\",\"PDACode\":\"A000004F47755E\",\"PDACompany\":\"\",\"Password\":\"000000\",\"UserId\":\"444\"}");
        Request request = RequestUtil.createPostRequest(url, map);
        RequestManager.httpPost(request, new RequestCallBack(new onHttpRequestFinishListener() {
            @Override
            public void onSuccess(Object data) {
                Log.i("response", data.toString());
                result_tv.setText(data.toString());

            }

            @Override
            public void onFailure(RequestException e) {
                result_tv.setText(e.getMsg());
            }
        }));
    }

    private void requestGetHttp() {
        RequestManager.httpGet(RequestUtil.creatGetRequest("https://www.baidu.com"),
                new RequestCallBack(new onHttpRequestFinishListener() {
                    @Override
                    public void onSuccess(Object data) {
                        result_tv.setText(data.toString());
                    }

                    @Override
                    public void onFailure(RequestException e) {
                        result_tv.setText(e.getMsg());
                    }
                }));
    }

    //上传文件
    private void requestUpLoad() {
        String url = "https://api.github.com/markdown/raw";
        //不带进度
//        RequestManager.httpUpLoadFile(RequestUtil.createUpLoadFileRequest(url, "/sdcard/xiaomi.txt"), new RequestCallBack(new onHttpRequestFinishListener() {
//            @Override
//            public void onSuccess(Object data) {
//                result_tv.setText(data.toString());
//            }
//
//            @Override
//            public void onFailure(RequestException e) {
//                Log.i("TAG", e.getMsg());
//                result_tv.setText(e.getMsg());
//            }
//        }));
        //带进度
        RequestBody body = RequestBody.create(RequestUtil.MEDIA_TYPE_MARKDOWN, new File("/sdcard/xiaomi.txt"));
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("application/octet-stream","xiaomi.txt",body)
//                .build();
        RequestManager.httpUpLoadFileProress(url, body, new OnUpLoadFileListener() {

            @Override
            public void onStart() {
                Log.i("upload", "onStart()");
            }

            @Override
            public void onUIProgressUpdate(long already_up_load, long length, boolean done) {
                Log.i("upload", "onUIProgressUpdate-------------->" + (already_up_load * 100 / length) + "%");
                result_tv.setText((already_up_load * 100 / length) + "%");
            }

            @Override
            void onFailure(RequestException e) {
                Log.i("upload", "onFailure");
            }

            @Override
            void onSuccess(Object data) {
                Log.i("upload", "onSuccess");
                result_tv.setText(data.toString());
            }
        }, true);

    }

    //下载文件
    private void requestDownLoad() {
        String url = "http://img1qn.moko.cc/2016-02-28/c3caa31e-99d8-46cf-9dab-de9e599ba30e.jpg?imageView2/2/w/915/h/915";
        //不带下载进度
        RequestManager.httpDownloadFile(RequestUtil.createDownLoadFileRequest(url), new DownLoadFileCallback(new onDownLoadFileListener() {
            @Override
            void onFailure(RequestException e) {

            }

            @Override
            void onSuccess(Object data) {

            }
        }, "/sdcard/csdn.jpg"));

        //带下载进度
        RequestManager.httpDownLoadFileProgress(RequestUtil.createDownLoadFileRequest(url),
                "/sdcard/csdn.jpg", downLoadFileListener, true);

    }


    onDownLoadFileListener downLoadFileListener = new onDownLoadFileListener() {
        @Override
        public void onStart() {
            Log.i("http", "-------->" + "onStart()");}
        @Override
        public void onUIProgressUpdate(long already_down, long length, boolean done) {
            Log.i("UIUPDATE", "------*******-->" + "already_down" + already_down + "" + length);}
        @Override
        public void onDownLoadFinish() {
            Log.i("http", "-------->onDownLoadFinish()");
        }
        @Override
        public void onFailure(RequestException e) {
            Log.i("http", "-------->onFailure()");}
        @Override
        public void onSuccess(Object data) {
            Log.i("http", "-------->onSuccess()");}
    };


}
