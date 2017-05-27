package com.gaoyuan.csdnandroid.net;


import java.io.Serializable;

public class BaseResp<T> implements Serializable{
    public T data;
    public int code;
    public String message;
    public int size;

    @Override
    public String toString() {
        return "BaseResp{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
