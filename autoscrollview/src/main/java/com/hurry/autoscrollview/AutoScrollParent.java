package com.hurry.autoscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by aaaaa on 2017/9/5.
 * 滑动控件自身功能封装
 */

public abstract class AutoScrollParent extends HorizontalScrollView {

    public AutoScrollParent(Context context) {
        super(context);
    }

    public AutoScrollParent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 滑动
     */
    protected abstract void scrolling();

    /**
     * 初始化滚动数据 开始滚动
     */
    protected abstract void startScroll();

}
