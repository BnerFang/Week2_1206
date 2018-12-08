package com.umeng.soexample.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.soexample.MainActivity;
import com.umeng.soexample.R;
import com.umeng.soexample.mvc.presenter.LoginPresenter;
import com.umeng.soexample.mvc.view.LoginView;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    /**
     * 登录
     */
    private Button mBtnLogin;
    /**
     * 注册
     */
    private Button mBtnReg;
    /**
     * 第三方QQ登录
     */
    private Button mBtnQq;
    /**
     * 请输入手机号......
     */
    private EditText mEdLoginMobile;
    /**
     * 请输入密码......
     */
    private EditText mEdLoginPassword;
    private LoginPresenter mLoginPresenter;
    private String mMobile;
    private String mPassword;
    private Button mBtnWx;
    UMAuthListener authListener;
    /**
     * 记住密码
     */
    private CheckBox mCheckJz;
    /**
     * 自动登录
     */
    private CheckBox mCheckLogin;
    private SharedPreferences mSP;
    private SharedPreferences.Editor mEdit;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        initView();
        mLoginPresenter = new LoginPresenter(this);
        //6.0版本以上的动态权限
        initPermission();
        //QQ授权
        initListenr();
        //创建SharedPreferences储存数据
        mSP = getSharedPreferences("config", MODE_PRIVATE);
        boolean isCheck = mSP.getBoolean("isCheck", false);//是否记住密码
        boolean isLogin = mSP.getBoolean("isLogin", false);//是否自动登录
        mEdit = mSP.edit();
        //判断是否记住密码
        if (isCheck) {
            mEdLoginMobile.setText(mSP.getString("mobile", mMobile));
            mEdLoginPassword.setText(mSP.getString("password", mPassword));
            mCheckJz.setChecked(true);
            //判断自动登录多选状态
            if (isLogin) {
                mCheckLogin.setChecked(true);
                //跳转
                mIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mIntent);
            }
        }
        //复选框内容改变方法
        initChecked();

    }

    //复选框内容改变方法
    private void initChecked() {
        mCheckLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //判断是否自动登录
                if (mCheckLogin.isChecked()) {
                    mCheckJz.setChecked(true);
                    mEdit.putBoolean("isLogin",true).commit();
                }else {
                    mEdit.putBoolean("isLogin",false).commit();
                }
            }
        });
    }

    //QQ授权
    private void initListenr() {
        authListener = new UMAuthListener() {
            /**
             * @desc 授权开始的回调
             * @param platform 平台名称
             */
            @Override
            public void onStart(SHARE_MEDIA platform) {

            }

            /**
             * @desc 授权成功的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param data 用户资料返回
             */
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                mIntent = new Intent(LoginActivity.this, MainActivity.class);
                mIntent.putExtra("name", data.get("name"));
                mIntent.putExtra("pri", data.get("profile_image_url"));
                startActivity(mIntent);
                finish();
                Toast.makeText(LoginActivity.this, platform + "登录成功了！！！", Toast.LENGTH_LONG).show();
            }

            /**
             * @desc 授权失败的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param t 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
            }

            /**
             * @desc 授权取消的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             */
            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
            }
        };
    }

    //初始化控件
    private void initView() {
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        mBtnReg = (Button) findViewById(R.id.btn_reg);
        mBtnReg.setOnClickListener(this);
        mBtnQq = (Button) findViewById(R.id.btn_qq);
        mBtnQq.setOnClickListener(this);
        mEdLoginMobile = (EditText) findViewById(R.id.ed_login_mobile);
        mEdLoginPassword = (EditText) findViewById(R.id.ed_login_password);
        mBtnWx = (Button) findViewById(R.id.btn_wx);
        mBtnWx.setOnClickListener(this);
        mCheckJz = (CheckBox) findViewById(R.id.check_jz);
        mCheckJz.setOnClickListener(this);
        mCheckLogin = (CheckBox) findViewById(R.id.check_login);
        mCheckLogin.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_login://登录
                mMobile = mEdLoginMobile.getText().toString().trim();
                mPassword = mEdLoginPassword.getText().toString().trim();
                mLoginPresenter.login(mMobile, mPassword);
                break;
            case R.id.btn_reg://注册
                startActivity(new Intent(this, RegActivity.class));
                finish();
                break;
            case R.id.btn_qq://QQ登录
                initPermission();
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.btn_wx://微信登录
                initPermission();
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }

    /**
     * mvp接口回调  访问数据成功
     *
     * @param result
     */
    @Override
    public void onLoginSuccess(String result) {
        if (mCheckJz.isChecked()){//记住密码
            mEdit.putBoolean("isCheck",true);
            mEdit.putString("mobile",mMobile);
            mEdit.putString("password",mPassword);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            mIntent = new Intent(this, MainActivity.class);
            mIntent.putExtra("mobile",mMobile);
            startActivity(mIntent);
            finish();
            mEdit.commit();
        }else {//清除记住密码
            mEdit.clear();
            mEdit.commit();
        }

    }

    /**
     * mvp接口回调  访问数据失败
     *
     * @param error
     */
    @Override
    public void onLoginFailed(String error) {
        if (error.equals("天呢！用户不存在")) {
            Toast.makeText(this, "用户不存在,请去注册页面注册！！！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * QQ授权
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 防止内存泄露
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.datechView();
    }

    //动态权限
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

    }
}
