package com.umeng.soexample.mvc.view;

/**
 * 作者:  方诗康
 * 描述:
 */
public interface LoginView {
    void onLoginSuccess(String result);
    void onLoginFailed(String error);
}
