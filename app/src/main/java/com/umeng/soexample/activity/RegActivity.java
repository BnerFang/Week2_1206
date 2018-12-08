package com.umeng.soexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.soexample.R;
import com.umeng.soexample.mvc.presenter.RegPresenter;
import com.umeng.soexample.mvc.view.RegView;

public class RegActivity extends AppCompatActivity implements View.OnClickListener,RegView {

    /**
     * 请输入手机号......
     */
    private EditText mEdRegMobile;
    /**
     * 请输入密码......
     */
    private EditText mEdRegPassword;
    /**
     * 注册
     */
    private Button mRegGo;
    private RegPresenter mRegPresenter;
    private String mMobile;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();
        mRegPresenter = new RegPresenter(this);

    }

    private void initView() {
        mEdRegMobile = (EditText) findViewById(R.id.ed_reg_mobile);
        mEdRegPassword = (EditText) findViewById(R.id.ed_reg_password);
        mRegGo = (Button) findViewById(R.id.reg_go);
        mRegGo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.reg_go:
                mMobile = mEdRegMobile.getText().toString().trim();
                mPassword = mEdRegPassword.getText().toString().trim();
                mRegPresenter.reg(mMobile,mPassword);
                break;
        }
    }

    @Override
    public void onRegSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    public void onRegFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }
}
