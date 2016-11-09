package com.android.sjq.okhttprequest;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016/11/3.
 */

public class RequestUtil {
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");


    //构造http get请求request
    public static Request creatGetRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        return request;
    }

    //构造http post请求request
    public static Request createPostRequest(String url, Map<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry entry : map.entrySet()) {
            builder.add(entry.getKey() + "", entry.getValue() + "");
        }
        FormBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        return request;
    }

    //文件上传requests
    public static Request createUpLoadFileRequest(String url, String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                throw new RequestException("file not exists");
            } catch (RequestException e) {
                e.printStackTrace();
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
        return request;
    }


    //文件下载
    public static Request createDownLoadFileRequest(String url) {
        return new Request.Builder().url(url).build();
    }


}
