package com.umeng.soexample.mvc.presenter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.umeng.soexample.bean.NewsBean;
import com.umeng.soexample.mvc.NewsCallBack;
import com.umeng.soexample.mvc.model.NewsModel;
import com.umeng.soexample.mvc.view.NewsView;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public class NewsPresenter {
    private NewsView mNewsView;
    private NewsModel mNewsModel;

    public NewsPresenter(NewsView newsView) {
        mNewsView = newsView;
        mNewsModel = new NewsModel();

    }

    //解绑
    public void datechView() {
        mNewsView = null;
    }

    public void newsDatas(int page) {
        mNewsModel.newsData(page, new NewsCallBack() {
            @Override
            public void onNewsSuccess(List<NewsBean.ResultBean.DataList> dataBeans) {
                mNewsView.onNewsItemSuccess(dataBeans);
            }

            @Override
            public void onNewsFailed(String error) {
                mNewsView.onNewsItemFailed(error);
            }
        });
    }
}
