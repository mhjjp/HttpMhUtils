package com.example.mahui.httpmhutils;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.mahui.httpmhutils.network.HttpMhUtils;
import com.example.mahui.httpmhutils.network.HttpUp;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private TextView contentTextView;
    private String url = "http://www.weather.com.cn/data/sk/101010100.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentTextView = (TextView)findViewById(R.id.contentTextView);

        ExAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);

    }

    AsyncTask<String, Void, String> ExAsyncTask = new AsyncTask<String, Void, String>() {
        @Override
        protected String doInBackground(String... strings) {


            String content = HttpMhUtils.DownLoadContent(strings[0]);

            Log.i(TAG, "doInBackground: content:"+content);
            return content;
        }

        @Override
        protected void onPostExecute(String content) {

            contentTextView.setText(content);
        }
    };
}
