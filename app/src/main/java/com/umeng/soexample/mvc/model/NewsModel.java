package com.umeng.soexample.mvc.model;

import android.content.Context;

import com.google.gson.Gson;
import com.umeng.soexample.bean.NewsBean;
import com.umeng.soexample.mvc.NewsCallBack;
import com.umeng.soexample.util.HttpUtil;
import com.umeng.soexample.util.HttpUtils;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public class NewsModel {

    private String newsPath = "http://feed.mix.sina.com.cn/api/roll/get?pageid=153&lid=2509&k=&num=20&page=";

    public void newsData(int paer, NewsCallBack callBack) {
        paer = 1;
        doHttpData(callBack, paer);

    }

    public void doHttpData(final NewsCallBack callBack, int paer) {

        new HttpUtils(new HttpUtils.HttpLinear() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                NewsBean newsBean = gson.fromJson(data, NewsBean.class);
                List<NewsBean.ResultBean.DataList> beans = newsBean.getResult().getData();
                if (beans != null) {
                    callBack.onNewsSuccess(beans);
                } else {
                    callBack.onNewsFailed("失败");
                }
            }

            @Override
            public void fail() {

            }
        }).get(newsPath + paer);
    }

}
