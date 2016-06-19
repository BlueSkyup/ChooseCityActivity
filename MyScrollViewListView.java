package com.yangsheng.ydzd_lb.androidpnpro;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by yangsheng on 2016/6/17.
 */
public class MyScrollViewListView extends ListView{

    public MyScrollViewListView(Context context) {
        super(context);
    }

    public MyScrollViewListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollViewListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO 自动生成的方法存根
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
