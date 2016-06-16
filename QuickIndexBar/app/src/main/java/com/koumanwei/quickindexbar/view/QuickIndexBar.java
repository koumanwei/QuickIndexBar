package com.koumanwei.quickindexbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.koumanwei.quickindexbar.util.ToastUtils;

/**
 * Created by koumanwei on 2016-06-15.
 */

public class QuickIndexBar extends View {
    private static String[] LETTERS = new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    private Paint mPaint;
    /**
     * 单元格的宽度
     */
    private float mCellWidth;
    /**
     * 单元格的高度
     */
    private float mCellHeight;

    /**
     * 暴露一个字母的监听
     */
    public interface OnLetterUpdateListener {
        void onLetterUpdate(String letter);
    }

    private OnLetterUpdateListener listener;

    public void setListener(OnLetterUpdateListener listener) {
        this.listener = listener;
    }

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //抗锯齿的画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#9d9d9d"));
        mPaint.setTextSize(40f);
        //设置文字粗体
//        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //计算x，y的坐标才是核心内容
        for (int i = 0; i < LETTERS.length; i++) {
            String text = LETTERS[i];
            //计算坐标
            int x = (int) (mCellWidth / 2.0f - mPaint.measureText(text) / 2.0f);
            //矩形
            Rect bounds = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), bounds);
            //文字的高度
            int textHeight = bounds.height();
            int y = (int) (mCellHeight / 2.0f + textHeight / 2.0f + i * mCellHeight);
            //根据按下的字母，设置画笔的颜色
            mPaint.setColor(touchIndex == i ? Color.parseColor("#fb005b") : Color.parseColor("#9d9d9d"));
            canvas.drawText(text, x, y, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //用float，不然会丢失精度
        mCellWidth = getMeasuredWidth();
        float mHeight = getMeasuredHeight();
        mCellHeight = mHeight * 1.0f / LETTERS.length;
    }

    /**
     * 记录按下的索引值
     */
    private int touchIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = -1;
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                //获取到当前触摸到的字母所在的索引
                index = (int) (event.getY() / mCellHeight);
                if (index >= 0 && index < LETTERS.length) {
                    //判断是否跟上一次触摸的一样
                    if (index != touchIndex) {
                        if (listener != null) {
                            listener.onLetterUpdate(LETTERS[index]);
                        }
                        touchIndex = index;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                index = (int) (event.getY() / mCellHeight);
                if (index >= 0 && index < LETTERS.length) {
                    if (index != touchIndex) {
                        if (listener != null) {
                            listener.onLetterUpdate(LETTERS[index]);
                        }
                        touchIndex = index;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                break;
        }
        invalidate();
        return true;
//        return super.onTouchEvent(event);
    }
}
