package com.umeng.soexample.mvc;

import com.umeng.soexample.bean.NewsBean;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public interface NewsCallBack {

    void onNewsSuccess(List<NewsBean.ResultBean.DataList> dataBeans);
    void onNewsFailed(String error);


}
