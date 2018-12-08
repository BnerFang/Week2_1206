package com.umeng.soexample.mvc.model;

import com.google.gson.Gson;
import com.umeng.soexample.bean.LoginAndRegBean;
import com.umeng.soexample.mvc.LoginAndRegCallBack;
import com.umeng.soexample.util.HttpUtil;

/**
 * 作者:  方诗康
 * 描述:
 */
public class LoginModel {

    public void login(String mobile, String password, final LoginAndRegCallBack callBack) {
        String loginPath = "http://www.zhaoapi.cn/user/login?mobile="+mobile+"&password=" + password;

        new HttpUtil().get(loginPath).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                LoginAndRegBean bean = gson.fromJson(data, LoginAndRegBean.class);
                if (bean.getCode().equals("0")) {
                    callBack.onSuccess(bean.getMsg());
                }else {
                    callBack.onFailed(bean.getMsg());
                }
            }

            @Override
            public void fail() {

            }
        });
    }

}
