package com.umeng.soexample.mvc.view;

import com.umeng.soexample.bean.NewsBean;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public interface NewsView  {
    void onNewsItemSuccess(List<NewsBean.ResultBean.DataList> dataBeans);
    void onNewsItemFailed(String error);

}
