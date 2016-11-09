package com.android.sjq.okhttprequest;

/**
 * Created by Administrator on 2016/11/8.
 */

public class ProgressModel {
    long already_down;
    long length;
    boolean done;

    public long getAlready_down() {
        return already_down;
    }

    public void setAlready_down(long already_down) {
        this.already_down = already_down;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "ProgressModel{" +
                "already_down=" + already_down +
                ", length=" + length +
                ", done=" + done +
                '}';
    }
}
