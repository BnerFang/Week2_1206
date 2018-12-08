package com.umeng.soexample.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 作者:  方诗康
 * 描述:
 */
@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {


    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean isFocused() {
        return true;
    }
}
