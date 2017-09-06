package com.hurry.autoscrollview;

/**
 * Created by hurry on 2017/9/5.
 *
 */

public interface AutoScrollDao {

    /**
     * 设置是否重复滚动
     * @param repeatAble
     */
    void setRepeatAble(boolean repeatAble);

    void setFinishState(int scrollending);

    /**
     *
     */
    void onResume();

    void onPause();

    void onDestory();

}
