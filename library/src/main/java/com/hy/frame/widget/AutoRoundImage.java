package com.hy.frame.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;


import com.hy.frame.util.AutoUtil;
import com.hy.frame.widget.ui.R;


public class AutoRoundImage extends QRoundImage implements IAutoDesign {
    /**
     * 设计尺寸
     */
    private int designScreenWidth;
    /**
     * 设计比例缩放比例
     */
    private float designScale = 1F;


    public AutoRoundImage(Context context) {
        this(context, null);
    }

    public AutoRoundImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoRoundImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @SuppressLint("CustomViewStyleable")
    @Override
    public void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QRoundImage, defStyleAttr, 0);
        this.designScreenWidth = a.getDimensionPixelSize(R.styleable.QRoundImage_designScreenWidth, AutoUtil.getDesignScreenWidth(context));
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
