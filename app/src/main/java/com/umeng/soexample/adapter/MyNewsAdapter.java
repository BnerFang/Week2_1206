package com.umeng.soexample.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.umeng.soexample.R;
import com.umeng.soexample.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者:  方诗康
 * 描述:
 */
public class MyNewsAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewsBean.ResultBean.DataList> mDataBeans = new ArrayList<>();

    public MyNewsAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<NewsBean.ResultBean.DataList> dataBeans) {
        this.mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        MyViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.news_item_view, null);
            holder = new MyViewHolder();
            holder.mCircleImageView = view.findViewById(R.id.c_imgicon);
            holder.mTextViewTitle = view.findViewById(R.id.txt_news_title);
            holder.mTextViewIntor = view.findViewById(R.id.txt_news_intro);
            holder.mTextViewName = view.findViewById(R.id.txt_news_name);
            view.setTag(holder);
        } else {
            holder = (MyViewHolder) view.getTag();
        }
        //Picasso.with(mContext).load(mDataBeans.get(i).getImages().get(i).getU()).fit().into(holder.mCircleImageView);
        //图片非空判断,必须添加,不然会报   数组下标越界异常
        if (mDataBeans.get(i).getImages() != null && mDataBeans.get(i).getImages().size() > 0) {
            ImageLoader.getInstance().displayImage(mDataBeans.get(i).getImages().get(0).getU(), holder.mCircleImageView);
        } else {
            holder.mCircleImageView.setImageResource(R.drawable.p);
        }
        holder.mTextViewTitle.setText(mDataBeans.get(i).getTitle());
        holder.mTextViewIntor.setText(mDataBeans.get(i).getIntor());
        holder.mTextViewName.setText(mDataBeans.get(i).getMedia_name());
        return view;
    }

    class MyViewHolder {
        CircleImageView mCircleImageView;
        TextView mTextViewTitle, mTextViewIntor, mTextViewName;
    }
}
