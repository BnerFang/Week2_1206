package com.umeng.soexample.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.umeng.soexample.R;

public class NewsItemActivity extends AppCompatActivity {

    private WebView mWeb;
    private WebSettings settings;
    private RelativeLayout mLayoutPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item);
        mWeb=(WebView) findViewById(R.id.webview);
        mLayoutPro=(RelativeLayout)findViewById(R.id.layout_progress);
        setTitle("资讯详情");

        String webUrl=getIntent().getStringExtra("weburl");
        try {
            setData(webUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setData(String url) throws Exception {
        settings = mWeb.getSettings();
        settings.setUseWideViewPort(true);//设置加载进来的页面自适应屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setUseWideViewPort(false);//禁止webview做自动缩放
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setSupportMultipleWindows(false);
        settings.setAppCachePath(getDir("cache", Context.MODE_PRIVATE).getPath());
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWeb.setFocusable(true);
        mWeb.requestFocus();
        mWeb.setWebChromeClient(new WebChromeClient());  //解决android与H5协议交互,弹不出对话框问题
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //页面加载完成之后
                mLayoutPro.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWeb.loadUrl(url);
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return true;

            }
        });
        mWeb.loadUrl(url);
    }

    //跳转
    public static void startWebActivity(Context context, String webUrl){
        Intent intent=new Intent(context,NewsItemActivity.class);
        intent.putExtra("weburl",webUrl);
        context.startActivity(intent);

    }
}
