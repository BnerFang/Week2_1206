package com.umeng.soexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.soexample.R;
import com.umeng.soexample.fragment.QRCodeFragment;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends AppCompatActivity implements QRCodeView.Delegate, View.OnClickListener {

    private ZXingView mZx;
    private ImageView mBackImgicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        initView();
        mZx.setDelegate(this);

    }

    private void initView() {
        mZx = (ZXingView) findViewById(R.id.zx);
        mBackImgicon = (ImageView) findViewById(R.id.back_imgicon);
        mBackImgicon.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mZx.startCamera();//打开相机
        mZx.startSpotAndShowRect();//延迟一秒打开相机
    }

    @Override
    protected void onStop() {
        super.onStop();
        mZx.startSpot();//关闭相机
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mZx.onDestroy();//销毁
    }

    /**
     * 扫码成功  跳转到显示结果界面
     *
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back_imgicon:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }
}
