package com.example.mahui.httpmhutils.network;

import java.util.HashMap;

/**
 * Created by mahui on 2018/2/8.
 */

public class HttpMhUtils {

    public static String DownLoadContent(String url){

      return  DownLoadContent(url, null, null);
    }

    public static String DownLoadContent(String url, HashMap<String, String> params){
        return DownLoadContent(url, params, null);
    }

    public static String DownLoadContent(String url, HashMap<String, String> params, HashMap<String,String>headers){
        return HttpDown.get(url, params, headers);
    }

    public static HttpResponse Get(String url, HashMap<String, String> params){
        return HttpUp.get(url, params);
    }

    public static HttpResponse Post(String url, HashMap<String, String> params){
        return HttpUp.post(url, params);
    }
}
