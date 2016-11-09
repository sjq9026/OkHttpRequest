package com.android.sjq.okhttprequest;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ProgressRequestBody extends RequestBody {
    //请求实体
    private RequestBody mRequestBody;
    //封装的接口回调
    private OnUpLoadFileListener mListener;
    //包装完成的BufferedSink
    private BufferedSink bufferedSink;

    public ProgressRequestBody(RequestBody body, OnUpLoadFileListener listener) {
        this.mRequestBody = body;
        this.mListener = listener;
        listener.onStart();
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(getSink(sink));
        }

        mRequestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private Sink getSink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入的字数
            long bytesWritten = 0L;
            //总字节数
            long contentLenth = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLenth == 0) {
                    contentLenth = contentLength();
                    Log.i("upload","文件总长度------------>"+contentLenth+"");
                }
                bytesWritten += byteCount;
                //Log.i("upload","已上传文件长度------------>"+bytesWritten+"");
                //回调
                mListener.onProgressUpdate(bytesWritten, contentLenth, bytesWritten == contentLenth);
            }
        };
    }

}


