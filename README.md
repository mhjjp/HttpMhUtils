#####  gradle 添加

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

compile 'com.github.mhjjp:HttpMhUtils:v1.0'

##### 使用方法

```
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
```
