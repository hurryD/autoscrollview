package com.hurry.autoscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by aaaaa on 2017/9/5.
 * 自动滚动控件
 */

public class AutoScrollView extends AutoScrollParent implements AutoScrollDao {

    /**
     * 单次滚动距离
     */
    private float singleScrollWidth = 2;

    /**
     * 单次滚动间隔  控制滚动速度 数字越小越快
     */
    private int singleDuration = 50;

    /**
     * 单次滚动开始时的准备时间
     */
    private int startDuration = 3500;

    /**
     * 单次滚动完成时停留尾部的时间
     */
    private int endDuration = 5000;

    /**
     * 滚动完成时 控制控件停在尾部 还是跳转到头部
     */
    private int finishState = ScrollEnding.STATE_END;

    /**
     * 是否重复滚动
     */
    private boolean repeatAble = true;

    //一次完整的滚动距离
    private float needScrollWidth;
    //已经滚动的距离
    private float haveScrollWidth;
    //是否滚动完成  仅在禁止控件重复滚动的前提下 完成单次滚动时为true
    private boolean scrollFinish = false;
    //是否暂停滚动
    private boolean stopScroll = false;


    public AutoScrollView(Context context) {
        this(context, null);
    }

    public AutoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AutoScrollView);
        int indexCount = array.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.AutoScrollView_singleScrollWidth) {//单次滚动距离
                singleScrollWidth = array.getFloat(attr, singleScrollWidth);

            } else if (attr == R.styleable.AutoScrollView_singleDuration) {//单次滚动间隔
                singleDuration = array.getInteger(attr, singleDuration);

            } else if (attr == R.styleable.AutoScrollView_startDuration) {//开始滚动间隔
                startDuration = array.getInteger(attr, startDuration);

            } else if (attr == R.styleable.AutoScrollView_endDuration) {//结束滚动间隔
                endDuration = array.getInteger(attr, endDuration);

            } else if (attr == R.styleable.AutoScrollView_repeatAble) {//是否重复滚动
                repeatAble = array.getBoolean(attr, repeatAble);

            } else if (attr == R.styleable.AutoScrollView_finishState) {//滚动完成状态
                finishState = array.getInt(attr, finishState);

            }
        }
        array.recycle();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        startScroll();
    }

    /**
     * 开始滚动
     */
    protected void startScroll() {
        View rootView = getChildAt(0);
        float rootViewWidth = rootView.getWidth();

        needScrollWidth = rootViewWidth - getWidth();

        if (needScrollWidth <= 0) {
            return;
        }

        scrollRunnable = new Runnable() {
            @Override
            public void run() {
                scrollHandler.removeCallbacks(this);
                if (stopScroll) {
                    return;
                }
                scrollHandler.sendEmptyMessage(1);
            }
        };
        scrollHandler.postDelayed(scrollRunnable, startDuration);
    }

    /**
     * 滚动
     */
    protected void scrolling() {
        if (needScrollWidth == haveScrollWidth) {
            //已经滚动完成一轮
            if (repeatAble) {
                haveScrollWidth = 0;
                scrollBy((int) -needScrollWidth, 0);
                scrollHandler.postDelayed(scrollRunnable, startDuration);
            } else {
                scrollFinish = true;
                if (finishState == ScrollEnding.STATE_START) {
                    haveScrollWidth = 0;
                    scrollBy((int) -needScrollWidth, 0);
                }
            }

            return;
        }
        float leaveWidth = needScrollWidth - haveScrollWidth;
        //判断是否将滚动至最尾部
        boolean isLast = leaveWidth <= singleScrollWidth;
        scrollBy(isLast ? (int) leaveWidth : (int) singleScrollWidth, 0);
        haveScrollWidth += isLast ? leaveWidth : singleScrollWidth;
        scrollHandler.postDelayed(scrollRunnable, isLast ? endDuration : singleDuration);

    }


    @Override
    public void setRepeatAble(boolean repeatAble) {
        this.repeatAble = repeatAble;
    }

    public void setFinishState(int scrollending) {

        this.finishState = scrollending;
    }

    @Override
    public void onResume() {
        if (!stopScroll || scrollFinish) {
            return;
        }
        stopScroll = false;
        scrollHandler.postDelayed(scrollRunnable, 2000);
    }

    @Override
    public void onPause() {
        stopScroll = true;
    }

    public void onDestory() {
        scrollHandler.removeCallbacksAndMessages(null);
        scrollRunnable = null;
    }


    private Runnable scrollRunnable;
    private final ScrollHandler scrollHandler = new ScrollHandler(this);

    private static class ScrollHandler extends Handler {
        private WeakReference<AutoScrollView> mContext;

        ScrollHandler(AutoScrollView context) {
            mContext = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mContext.get() == null) {
                return;
            }

            mContext.get().scrolling();
        }
    }


}
