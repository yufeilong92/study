package com.xuechuan.xcedu.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: NewLineViewGroup.java
 * @Description: 按钮点击
 * @author: YFL
 * @date: 2018/4/15 14:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 */
public class NewLineViewGroup extends ViewGroup {
    private final static String TAG = "123";

    private final static int VIEW_MARGIN = 2;
    public interface OnchildClickListener{
        public void setClickListener(View view);
    }
    private OnchildClickListener clickListener;

    public void setClickListener(OnchildClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public NewLineViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewLineViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "widthMeasureSpec = " + widthMeasureSpec + " heightMeasureSpec = " + heightMeasureSpec);

        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            // measure
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int jiange = 10;//按钮之间的间隔

    @Override
    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
        Log.d(TAG, "changed = " + arg0 + " left = " + arg1 + " top = " + arg2 + " right = " + arg3 + " botom = " + arg4);
        final int count = getChildCount();
        int row = 0;
        int lengthX = arg1;
        int lengthY = arg2;
        for (int i = 0; i < count; i++) {

            final View child = this.getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            if (i == 0) {
                lengthX += width + VIEW_MARGIN;//第一个的时候不需要加
            } else {
                lengthX += width + VIEW_MARGIN + jiange;//按钮之间的间隔
            }
            lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height + arg2;
            //if it can't drawing on a same line , skip to next line
            if (lengthX > arg3) {
                lengthX = width + VIEW_MARGIN + arg1;
                row++;
                lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height + arg2;
            }
            child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener!=null){
                        clickListener.setClickListener(v);
                    }
                }
            });
        }
    }



}
