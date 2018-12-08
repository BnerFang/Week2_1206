package com.umeng.soexample.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;
import com.umeng.soexample.R;
import com.umeng.soexample.activity.NewsItemActivity;
import com.umeng.soexample.adapter.MyNewsAdapter;
import com.umeng.soexample.bean.NewsBean;
import com.umeng.soexample.mvc.presenter.NewsPresenter;
import com.umeng.soexample.mvc.view.NewsView;
import com.umeng.soexample.util.HttpUtil;
import com.umeng.soexample.util.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 作者:  方诗康
 * 描述:
 */
public class ShowFragment extends Fragment {

    private View mView;
    private BGABanner mBgaBanner;
    private PullToRefreshListView mPullList;
    private String[] titleUrl = {
            "http://www.zhaoapi.cn/images/quarter/ad1.png",
            "http://www.zhaoapi.cn/images/quarter/ad2.png",
            "http://www.zhaoapi.cn/images/quarter/ad3.png",
            "http://www.zhaoapi.cn/images/quarter/ad4.png"
    };
    private String[] descUrl = {
            "第十三界瑞丽模特大赛", "文化艺术节", "直播封面标准", "人气谁最高，金主谁最豪气"
    };
    private List<String> mTitle;
    private List<String> mDesc;
    private NewsPresenter mNewsPresenter;
    private MyNewsAdapter mMyNewsAdapter;
    private List<NewsBean.ResultBean.DataList> mDataBeans = new ArrayList<>();
    private List<NewsBean.ResultBean.DataList> mLists = new ArrayList<>();
    private int pager = 1;
    private int total;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_show, container, false);
        //初始化控件
        initView(mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化BGABanner数据
        initBGABannerData();
        initNewsData(pager);
    }

    private void initBGABannerData() {
        mTitle = new ArrayList<>();
        mDesc = new ArrayList<>();
        //循环添加到集合
        for (int i = 0; i < titleUrl.length; i++) {
            mTitle.add(titleUrl[i]);//把数组里的值驾到集合里去
            mDesc.add(descUrl[i]);
        }
        //设置值  里面要添加集合
        mBgaBanner.setData(mTitle, mDesc);
        //设置一个BGbanner的适配器 要给他加泛型  一个图片空间一个字符类型
        mBgaBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                //使用Picasso加载图片
                Picasso.with(getActivity()).load(mTitle.get(position)).fit().into(itemView);
            }
        });
    }

    private void initView(View mView) {
        mBgaBanner = (BGABanner) mView.findViewById(R.id.bga_banner);
        mPullList = (PullToRefreshListView) mView.findViewById(R.id.pull_list);
        mPullList.setMode(PullToRefreshBase.Mode.BOTH);
        mMyNewsAdapter = new MyNewsAdapter(getActivity());
        mPullList.setAdapter(mMyNewsAdapter);
        mMyNewsAdapter.setList(mLists);
        mPullList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mLists.clear();
                initNewsData(1);
                mMyNewsAdapter.notifyDataSetChanged();
                mPullList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullList.onRefreshComplete();
                    }
                },2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (mLists.size() > total){
                    Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                }else {
                    pager++;
                    initNewsData(pager);
                    mMyNewsAdapter.notifyDataSetChanged();
                    mPullList.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullList.onRefreshComplete();
                        }
                    },2000);
                }
            }
        });
        mPullList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItemActivity.startWebActivity(getActivity(),mLists.get(i).getUrl());
            }
        });
    }

    private void initNewsData(int page) {

        String newsPath = "http://feed.mix.sina.com.cn/api/roll/get?pageid=153&lid=2509&k=&num=20&page="+page;
        new HttpUtil().get(newsPath).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                NewsBean newsBean = gson.fromJson(data, NewsBean.class);
                total = newsBean.getResult().getTotal();
                List<NewsBean.ResultBean.DataList> lists = newsBean.getResult().getData();
                mLists.addAll(lists);
                mMyNewsAdapter.setList(mLists);
                mMyNewsAdapter.notifyDataSetChanged();
                mPullList.onRefreshComplete();//关闭
            }

            @Override
            public void fail() {

            }
        });
    }

}
