package com.umeng.soexample.mvc.presenter;

import com.umeng.soexample.mvc.LoginAndRegCallBack;
import com.umeng.soexample.mvc.model.LoginModel;
import com.umeng.soexample.mvc.view.LoginView;

/**
 * 作者:  方诗康
 * 描述:
 */
public class LoginPresenter {
    private LoginView mLoginView;
    private LoginModel mLoginModel;

    public LoginPresenter(LoginView loginView) {
        mLoginView = loginView;
        mLoginModel =  new LoginModel();
    }

    //解绑
    public void datechView(){
        mLoginView = null;
    }

    public void login(String mobile,String password){
        mLoginModel.login(mobile, password, new LoginAndRegCallBack() {
            @Override
            public void onSuccess(String result) {
                mLoginView.onLoginSuccess(result);
            }

            @Override
            public void onFailed(String error) {
                mLoginView.onLoginFailed(error);
            }
        });
    }
}
