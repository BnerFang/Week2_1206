package com.umeng.soexample.mvc.presenter;

import com.umeng.soexample.mvc.LoginAndRegCallBack;
import com.umeng.soexample.mvc.model.LoginModel;
import com.umeng.soexample.mvc.model.RegModel;
import com.umeng.soexample.mvc.view.LoginView;
import com.umeng.soexample.mvc.view.RegView;

/**
 * 作者:  方诗康
 * 描述:
 */
public class RegPresenter {
    private RegView mRegView;
    private RegModel mRegModel;

    public RegPresenter(RegView regView) {
        mRegView = regView;
        mRegModel =  new RegModel();
    }

    //解绑
    public void datechView(){
        mRegView = null;
    }

    public void reg(String mobile,String password){
        mRegModel.reg(mobile, password, new LoginAndRegCallBack() {
            @Override
            public void onSuccess(String result) {
                mRegView.onRegSuccess(result);
            }

            @Override
            public void onFailed(String error) {
                mRegView.onRegFailed(error);
            }
        });
    }
}
