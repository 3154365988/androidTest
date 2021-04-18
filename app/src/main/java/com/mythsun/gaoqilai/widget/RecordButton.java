package com.mythsun.gaoqilai.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

public class RecordButton extends AppCompatButton {
    private OnVoiceButtonCallBack mOnVoiceButtonCallBack;

    public RecordButton(Context context) {
        super(context);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 设置监听回调
     *
     * @param onVoiceButtonCallBack
     */
    public void setOnVoiceButtonCallBack(OnVoiceButtonCallBack onVoiceButtonCallBack) {
        this.mOnVoiceButtonCallBack = onVoiceButtonCallBack;
    }

    /**
     * 内部接口，当使用该控件的人？？？
     */
    public interface OnVoiceButtonCallBack {
        /**
         * 开始录音
         */
        void onStartRecord();

        /**
         * 停止录音
         */
        void onStopRecord();

        /**
         * 准备取消录音
         */
        void onWillCancelRecord();

        /**
         * 继续录音
         */
        void onContinueRecord();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mOnVoiceButtonCallBack != null) {
                    mOnVoiceButtonCallBack.onStartRecord();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (isCancelled(this, event)) {
                    if (mOnVoiceButtonCallBack != null) {
                        mOnVoiceButtonCallBack.onWillCancelRecord();
                    }
                } else {
                    if (mOnVoiceButtonCallBack != null) {
                        mOnVoiceButtonCallBack.onContinueRecord();
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mOnVoiceButtonCallBack != null) {
                    mOnVoiceButtonCallBack.onStopRecord();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 判断是否滑回去
     *
     * @param view
     * @param event
     * @return
     */
    private boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth() || event.getRawY() < location[1] - 40) {
            return true;
        }
        return false;
    }
}
