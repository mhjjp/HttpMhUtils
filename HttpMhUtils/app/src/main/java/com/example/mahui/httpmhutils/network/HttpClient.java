package com.example.mahui.httpmhutils.network;

import android.util.Log;

import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mahui on 2018/2/8.
 */

public class HttpClient {

    private static String TAG = HttpClient.class.getSimpleName();

    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();

    private String jsonBody;
    private String message;
    private int responseCode;
    private byte[] content;
    private String url;

    OkHttpClient okHttpClient = new OkHttpClient();
    Request.Builder builder = new Request.Builder();


    public HttpClient(String url) {
        this.url = url;

        okhttp3.OkHttpClient.Builder ClientBuilder = new okhttp3.OkHttpClient.Builder();
        ClientBuilder.readTimeout(30, TimeUnit.SECONDS);//读取超时
        ClientBuilder.connectTimeout(5, TimeUnit.SECONDS);//连接超时
        ClientBuilder.writeTimeout(60, TimeUnit.SECONDS);//写入超时
        okHttpClient = ClientBuilder.build();
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void addParam(String name, String value) {
        params.put(name, value);
    }

    public void setJSONString(String jsonString) {
        jsonBody = jsonString;
    }

    public String getErrorMessage() {
        return message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public byte[] getContent() {
        return content == null ? new byte[0] : content;
    }

    public void execute(RequestStyle method) {

        appendParams();
        appendHeaders();

        Request request = null;
        if (method == RequestStyle.GET) {
            request = builder.url(url).build();
        } else if (method == RequestStyle.POST) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);
            request = builder.url(url).post(requestBody).build();
        }
        Response execute = null;
        try {
            Call call = okHttpClient.newCall(request);
            execute = call.execute();
            this.responseCode = execute.code();
            this.message = execute.message();
            if (this.responseCode == 200) {
                this.content = execute.body().bytes();
            }

        } catch (SocketTimeoutException ex1) {
            this.responseCode = 1025;
            this.message = "Timeout";
        } catch (Exception ex) {
            Log.e(TAG, "call over failed: " + ex.getMessage(), ex);
            Log.e(TAG, ex.getMessage(), ex);
        } finally {
            try {
                if (execute != null) {
                    execute.close();
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    private void appendParams() {

        if (this.url.indexOf("?") <= 0 && params.keySet().size() > 0) {
            this.url += "?";
        }

        StringBuffer combinedParams = new StringBuffer();
        for (String key : params.keySet()) {
            combinedParams.append((combinedParams.length() > 1 ? "&" : "")
                    + key + "="
                    + URLEncoder(params.get(key).toString()));
        }

        // 添加过就清除，避免多次调用的时候出问题
        params.clear();

        if (combinedParams.length() > 0) {
            this.url += combinedParams.toString();
        }
    }

    private void appendHeaders() {

        for (String key : headers.keySet()) {
            builder.addHeader(key, headers.get(key));
        }

        headers.clear();
    }

    /**
     * 对中文URL进行编码
     *
     * @param str
     * @return
     */
    public String URLEncoder(String str) {
        try {
            if (isNullOrEmpty(str)) {
                return "";
            } else {
                str = URLEncoder.encode(str.trim(), "UTF-8");
            }
        } catch (Exception ex) {
        }
        return str;
    }

    public  boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
