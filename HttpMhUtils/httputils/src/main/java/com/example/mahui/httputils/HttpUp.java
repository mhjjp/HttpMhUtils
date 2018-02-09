package com.example.mahui.httputils;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mahui on 2018/2/8.
 */

public class HttpUp {

    private static final String TAG = RequestStyle.class.getSimpleName();

    public static HttpResponse get(String url, HashMap<String, String> param) {
        return execute(url, param, RequestStyle.GET);
    }

    public static HttpResponse post(String url, HashMap<String, String> param) {
        return execute(url, param, RequestStyle.POST);
    }

    /**
     * 最终实际发起网络请求的方法。
     * 所有的请求，都有这里发起。
     *
     * @param url
     * @param param
     * @param method
     * @return
     */
    private static HttpResponse execute(String url, HashMap<String, String> param, RequestStyle method) {

        HttpClient http = new HttpClient(url);

        if (param != null) {
            Iterator iter = param.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                String key = entry.getKey();
                String value = entry.getValue();
                http.addParam(key, value);
            }
        }

        HttpResponse response = new HttpResponse();

        try {
            http.execute(method);

            response.setCode(http.getResponseCode());
            response.setByteContent(http.getContent());
        } catch (Exception ex) {
            Log.e(TAG, "network error: " + ex.getMessage());
            response.setCode(1024);
            response.setContent(ex.getMessage());
        }

        return response;
    }

    private static HttpResponse postJson(String url, HashMap<String, String> param) {

        HttpClient http = new HttpClient(url);

        String json = JSON.toJSONString(param);

        Log.i(TAG, "post body: " + json);

        http.setJSONString(json);

        HttpResponse response = new HttpResponse();

        try {

            http.execute(RequestStyle.POST);

            response.setCode(http.getResponseCode());
            response.setByteContent(http.getContent());

        } catch (Exception ex) {

            Log.e(TAG, "can't post json to server: " + ex.getMessage(), ex);

            response.setCode(http.getResponseCode());
            response.setContent(ex.getMessage());
        }

        return response;
    }

    private static HttpResponse postObject(String url, Object content) {

        HttpClient http = new HttpClient(url);

        Log.i(TAG, "post object body: " + JSON.toJSONString(content));

        http.setJSONString(JSON.toJSONString(content));

        HttpResponse response = new HttpResponse();

        try {

            http.execute(RequestStyle.POST);

            response.setCode(http.getResponseCode());
            response.setByteContent(http.getContent());

        } catch (Exception ex) {

            Log.e(TAG, "can't post json to server: " + ex.getMessage(), ex);

            response.setCode(http.getResponseCode());
            response.setContent(ex.getMessage());
        }

        return response;
    }
}
