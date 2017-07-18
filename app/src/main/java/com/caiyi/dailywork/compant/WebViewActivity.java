package com.caiyi.dailywork.compant;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.inter.HttpCallbackListener;
import com.caiyi.dailywork.net.HttpUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * WebView
 * HttpUrlConnection
 *
 * @author Ren ZeQiang
 * @since 2017/7/4
 */
public class WebViewActivity extends BaseActivity {
    /**
     * webView
     */
    private WebView webView;

    private TextView mText;

    private static final int UPDATE_TEXT = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    mText.setText("nice to meet you");
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        mText = (TextView) findViewById(R.id.tv_http);

        setViewClickListeners(R.id.btn_web, R.id.btn_http, R.id.btn_okHttp, R.id.btn_handler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_web:
                webView.loadUrl("http://www.baidu.com");
                break;
            case R.id.btn_http:
//                openNetWork();
//                okhttpGetMethod();
//                okhttpPostMethod();
                netRequest();
                break;
            case R.id.btn_okHttp:
                okHttpRequest();
                break;
            case R.id.btn_handler:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        Message message = new Message();
//                        message.what = UPDATE_TEXT;
//                        handler.sendMessage(message);
                        handler.sendEmptyMessage(UPDATE_TEXT);
                    }
                }).start();
                break;
            default:
                break;

        }
    }

    private void openNetWork() {
        //开启线程发送网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    showResponse(builder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mText.setText(response);
            }
        });
    }

    /**
     * GET
     */
    private void okhttpGetMethod() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://www.baidu.com")
                        .build();

                /**
                 * 调用OKHttpClient的newCall来创建一个call对象
                 * 调用execute()方法发送请求并获取服务器返回的数据。
                 */
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String data = null;
                try {
                    data = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showResponse(data);
            }
        }).start();

    }

    /**
     * PODT
     */
    private void okhttpPostMethod() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                FormBody formBody = new FormBody.Builder()
                        .add("username", "admin")
                        .add("password", "123456")
                        .build();

                Request request = new Request.Builder()
                        .post(formBody)
                        .url("http://www.baidu.com")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    showResponse(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * PULL解析xml
     * @param xmlData
     */
    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = parserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:
                        break;
                    //完成解析某个节点
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void netRequest() {
        HttpUtils.sendHttpRequest("http://www.baidu.com", new HttpCallbackListener() {
            @Override
            public void onSuccess(String response) {
                mText.setText(response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void okHttpRequest() {
        HttpUtils.sendOkHttpRequest("http://www.baidu.com", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String data = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mText.setText(data);
                    }
                });
            }
        });
    }


}
