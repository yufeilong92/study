package com.xuechuan.xcedu.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 *
 * @version V 1.0 xxxxxxxx
 * @Title: TextVeiw.java
 * @Package com.xuechuan.xcedu.weight
 * @Description:位置显示四位
 * @author: YFL
 * @date: 2018/4/14 22:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/14 星期六
 */
@SuppressLint("AppCompatCustomView")
public class AddressTextView extends TextView {
    public AddressTextView(Context context) {
        super(context);
    }

    public AddressTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AddressTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text.length() > 4) {
            text = text.subSequence(0, 4);
            super.setText(text + "...", type);
        } else {
            super.setText(text, type);
        }

    }
}
