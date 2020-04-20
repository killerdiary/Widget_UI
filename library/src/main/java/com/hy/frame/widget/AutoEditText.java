package com.hy.frame.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.hy.frame.util.AutoUtil;
import com.hy.frame.widget.ui.R;

/**
 * title 可控制图标大小EditText，另外只支持drawableLeft居中
 * author heyan
 * time 19-7-10 下午4:24
 * desc 无
 */
public class AutoEditText extends QEditText implements IAutoDesign {
    /**
     * 设计尺寸
     */
    private int designScreenWidth;
    /**
     * 设计比例缩放比例
     */
    private float designScale = 1F;


    public AutoEditText(Context context) {
        this(context, null);
    }

    public AutoEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public AutoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("CustomViewStyleable")
    @Override
    public void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QTextView, defStyleAttr, 0);
        this.designScreenWidth = a.getDimensionPixelSize(R.styleable.QTextView_designScreenWidth, AutoUtil.getDesignScreenWidth(context));
        this.designScale = calDesignScale();
        a.recycle();
        super.initAttrs(context, attrs, defStyleAttr);
    }


    @Override
    public int getDesignScreenWidth() {
        return this.designScreenWidth;
    }

    @Override
    public float getDesignScale() {
        return this.designScale;
    }

    @Override
    public float calDesignScale() {
        return AutoUtil.calDesignScale(getContext(), getDesignScreenWidth());
    }

    @Override
    public int calDesignWidth(int width) {
        return AutoUtil.calDesignWidth(width, getDesignScale());
    }

    @Override
    public int calDesignSize(int size) {
        return calDesignWidth(size);
    }
}
