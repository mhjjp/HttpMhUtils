package com.example.mahui.httputils;

import java.io.UnsupportedEncodingException;

/**
 * Created by mahui on 2018/2/8.
 *
 * 请求的结果
 */

public class HttpResponse {

    // code
    private int code;
    //message for return
    private String content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setByteContent(byte[] content) {
        try {
            this.content = new String(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
