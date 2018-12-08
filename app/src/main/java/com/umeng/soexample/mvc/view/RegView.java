package com.umeng.soexample.mvc.view;

/**
 * 作者:  方诗康
 * 描述:
 */
public interface RegView {
    void onRegSuccess(String result);
    void onRegFailed(String error);
}
