package com.umeng.soexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.umeng.soexample.adapter.MyFragmentPagerAdapter;
import com.umeng.soexample.fragment.QRCodeFragment;
import com.umeng.soexample.fragment.ShowFragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ViewPager mVp;
    /**
     * 首页
     */
    private RadioButton mBtnShow;
    /**
     * 我的
     */
    private RadioButton mBtnQr;
    private RadioGroup mRadioGroup;
    private ArrayList<Fragment> mFragments;
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //初始化数据
        initData();
        //取值
        //getIntent()方法得到intent对象
        Intent intent = getIntent();
        //获取Intent中的数据：getXXXExtra()方法
        String name = intent.getStringExtra("name");
        String pic = intent.getStringExtra("pic");
    }


    private void initData() {
        //创建fragment集合
        mFragments = new ArrayList<>();
        mFragments.add(new ShowFragment());
        mFragments.add(new QRCodeFragment());
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this, mFragments);
        mVp.setAdapter(mMyFragmentPagerAdapter);
        //滑动切换按钮
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                    mRadioGroup.check(mRadioGroup.getChildAt(i % mFragments.size()).getId());

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.btn_show:
                        mVp.setCurrentItem(0);
                        break;
                    case R.id.btn_qr:
                        mVp.setCurrentItem(1);
                        break;
                }
            }
        });
    }


    private void initView() {
        mVp = (ViewPager) findViewById(R.id.vp);
        mBtnShow = (RadioButton) findViewById(R.id.btn_show);
        mBtnQr = (RadioButton) findViewById(R.id.btn_qr);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mVp.setOnClickListener(this);
        mBtnShow.setOnClickListener(this);
        mBtnQr.setOnClickListener(this);
        mRadioGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.vp:
                break;
            case R.id.btn_show:
                break;
            case R.id.btn_qr:
                break;
            case R.id.radio_group:
                break;
        }
    }
}
