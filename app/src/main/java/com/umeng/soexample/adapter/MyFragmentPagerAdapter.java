package com.umeng.soexample.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.umeng.soexample.MainActivity;

import java.util.ArrayList;

/**
 * 作者:  方诗康
 * 描述:
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private ArrayList<Fragment> mFragments;
    public MyFragmentPagerAdapter(FragmentManager fm, Context context, ArrayList<Fragment> fragments) {
        super(fm);
        mContext = context;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
