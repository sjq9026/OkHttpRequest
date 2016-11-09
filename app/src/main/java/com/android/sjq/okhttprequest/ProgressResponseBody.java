package com.android.sjq.okhttprequest;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Administrator on 2016/11/8.
 */

public class ProgressResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private onDownLoadFileListener mListener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody body, onDownLoadFileListener listener) {
        this.responseBody = body;
        this.mListener = listener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(getBufSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source getBufSource(BufferedSource source) {
        return new ForwardingSource(source) {
            long totalReadBytes = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                Long readBytes = super.read(sink, byteCount);
                totalReadBytes += readBytes != -1 ? readBytes : 0;
                Long length = responseBody.contentLength();
                if (readBytes != -1) {
                    //调用此函数切换UI线程
                    mListener.onProgressUpdate(totalReadBytes, length, readBytes == -1);
                } else {
                    mListener.onDownLoadFinish();
                }
                return readBytes;
            }
        };
    }
}
