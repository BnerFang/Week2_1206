package com.umeng.soexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.umeng.soexample.R;

public class WebActivity extends AppCompatActivity {

    private TextView mQrTxt;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        //支持js语言
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 缩放至屏幕的大小
        mWebView.getSettings().setLoadWithOverviewMode(true);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        //取值
        Intent intent = getIntent();
        //获取Intent中的数据
        String result = intent.getStringExtra("result");
        //把字符串赋值给输入框
        mQrTxt.setText(result);
        mWebView.loadUrl(result);
        //设置用自己的浏览器打开
        mWebView.setWebViewClient(new MyWebViewClient());

    }

    private void initView() {
        mQrTxt = (TextView) findViewById(R.id.qr_txt);
        mWebView = (WebView) findViewById(R.id.web_view);
    }


    //自定义浏览器
    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    //预防内存泄露
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
