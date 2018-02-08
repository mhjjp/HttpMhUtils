package com.example.mahui.httpmhutils.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mahui on 2018/2/8.
 */

public class HttpDown {


    public static String get(String url) {
        return get(url, null);
    }

    public static String get(String url, HashMap<String, String> params) {
        return get(url, params, null);
    }

    public static String get(String url, HashMap<String, String> params, HashMap<String, String> headers) {

        String content;

        HttpClient httpClient = new HttpClient(url);

        if (params != null) {
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();

                httpClient.addParam(key, val);
            }
        }

        if (headers != null) {
            Iterator iter = headers.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();

                httpClient.addHeader(key, val);
            }
        }

        httpClient.execute(RequestStyle.GET);

        byte[] cons = httpClient.getContent();

        if(cons == null){
            return null;
        }

        content = new String(cons);

        return content;
    }


}
