package com.leyou.game.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.leyou.game.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ListView右侧的字母索引View
 */
public class CustomSlideBar extends View {

    public static String[] INDEX_STRING = {"", "*", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private List<String> letterList;
    private int choose = -1;
    private Paint paint = new Paint();
    private TextView mTextDialog;
    private Context context;

    public CustomSlideBar(Context context) {
        this(context, null);
        this.context = context;
    }

    public CustomSlideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public CustomSlideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        setBackgroundColor(getResources().getColor(R.color.transparent));
        letterList = Arrays.asList(INDEX_STRING);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();// 获取对应高度
        int width = getWidth();// 获取对应宽度
        int singleHeight = height / letterList.size();// 获取每一个字母的高度
        for (int i = 0; i < letterList.size(); i++) {
            paint.setColor(getResources().getColor(R.color.black_a46));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(getResources().getDimension(R.dimen.text_size_12));
            // 选中的状态
            if (i == choose) {
                paint.setColor(getResources().getColor(R.color.transparent_half));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(letterList.get(i)) / 2;
            float yPos = singleHeight * i + singleHeight / 2;
            canvas.drawText(letterList.get(i), xPos, yPos, paint);
            paint.reset();// 重置画笔
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        // 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        final int c = (int) (y / getHeight() * letterList.size());

        switch (action) {
            case MotionEvent.ACTION_UP:
                // TODO: 2017/7/15 添加背景
                setBackgroundColor(getResources().getColor(R.color.transparent));
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.GONE);
                }
                break;
            default:
                // TODO: 2017/7/15 添加背景
                setBackgroundColor(getResources().getColor(R.color.white_e5));
                if (oldChoose != c) {
                    if (c >= 0 && c < letterList.size()) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(letterList.get(c));
                        }
                        if (mTextDialog != null && !TextUtils.isEmpty(letterList.get(c))) {
                            mTextDialog.setText(letterList.get(c));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setIndexText(ArrayList<String> indexStrings) {
        this.letterList = indexStrings;
        invalidate();
    }

    /**
     * 为SideBar设置显示当前按下的字母的TextView
     *
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }
}