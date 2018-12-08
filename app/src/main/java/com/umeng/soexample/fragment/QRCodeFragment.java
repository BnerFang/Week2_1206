package com.umeng.soexample.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.soexample.R;
import com.umeng.soexample.activity.LoginActivity;
import com.umeng.soexample.activity.ScanActivity;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者:  方诗康
 * 描述:
 */
public class QRCodeFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private View view;
    private ImageView mQrImgicon;
    private ImageView mQrImgicons;
    /**
     * 退出登录
     */
    private Button mBtnQuit;
    private String mName;
    private String mPri;
    private CircleImageView mCQrImgicon;
    private TextView mTxtQrTexts;
    private SharedPreferences mSP;
    private SharedPreferences.Editor mEdit;
    private String mMobile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_qr, container, false);
        initView(mView);
        return mView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //取出QQ昵称的值
        mName = getActivity().getIntent().getStringExtra("name");
        mPri = getActivity().getIntent().getStringExtra("pri");
        mMobile = getActivity().getIntent().getStringExtra("mobile");
        ImageLoader.getInstance().displayImage(mPri, mCQrImgicon);
        //判断是QQ昵称还是手机号
        if (mName == null) {
            mTxtQrTexts.setText(mMobile);
        }else {
            mTxtQrTexts.setText(mName);
        }

        //初始化  自动生成二维码
        initZxingData();

        mSP = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        mEdit = mSP.edit();
    }

    private void initZxingData() {
        //判断是QQ昵称还是手机号,然后生成二维码
        if (mName == null) {
            QRTask qrTask = new QRTask(getActivity(), mQrImgicons, mMobile);
            qrTask.execute(mMobile);
        }else {
            QRTask qrTask = new QRTask(getActivity(), mQrImgicons, mName);
            qrTask.execute(mName);
        }

    }

    //创建  QRTask 类,实现自动生成二维码
    class QRTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<Context> mContextWeakReference;
        private final WeakReference<ImageView> mImageViewWeakReference;
        String mString;

        //第一步,先创建构造方法
        public QRTask(Context context, ImageView imageView, String string) {
            //new 出实例
            mContextWeakReference = new WeakReference<>(context);
            mImageViewWeakReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String string = strings[0];
            if (TextUtils.isEmpty(string)) {
                return null;
            }
            int size = 200;
            return QRCodeEncoder.syncEncodeQRCode(string, size);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                mImageViewWeakReference.get().setImageBitmap(bitmap);
            } else {
                Toast.makeText(mContextWeakReference.get(), "二维码生成失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView(View mView) {
        mQrImgicon = (ImageView) mView.findViewById(R.id.qr_imgicon);
        mQrImgicons = (ImageView) mView.findViewById(R.id.qr_imgicons);
        mBtnQuit = (Button) mView.findViewById(R.id.btn_quit);
        mBtnQuit.setOnClickListener(this);
        mQrImgicon.setOnClickListener(this);
        mCQrImgicon = (CircleImageView) mView.findViewById(R.id.c_qr_imgicon);
        mTxtQrTexts = (TextView) mView.findViewById(R.id.txt_qr_texts);
        mCQrImgicon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_quit:
                mEdit.clear();//清空所记住的内容
                mEdit.commit();//关闭
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.qr_imgicon:
                startActivity(new Intent(getActivity(), ScanActivity.class));
                getActivity().finish();
                break;
            case R.id.c_qr_imgicon:
                Toast.makeText(getActivity(), mPri, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
