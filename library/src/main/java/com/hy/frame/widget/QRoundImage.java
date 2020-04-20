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

import com.hy.frame.widget.ui.R;


public class QRoundImage extends androidx.appcompat.widget.AppCompatImageView {
    
    private final RectF roundRect = new RectF();
    private final RectF borderRect = new RectF();
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();
    private final Paint borderPaint = new Paint();
    private final Paint borderMaskPaint = new Paint();
    /**
     * 圆角的大小
     */
    private float radius = 15;
    private boolean circle;
    private int border;
    private int borderColor;

    public QRoundImage(Context context) {
        this(context, null);
    }

    public QRoundImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QRoundImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    @SuppressLint("CustomViewStyleable")
    public void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QRoundImage, defStyleAttr, 0);
        this.radius = calDesignSize(a.getDimensionPixelSize(R.styleable.QRoundImage_designRadius, 0));
        this.circle = a.getBoolean(R.styleable.QRoundImage_designCircle, false);
        this.border = calDesignSize(a.getDimensionPixelSize(R.styleable.QRoundImage_designBorder, 0));
        this.borderColor = a.getColor(R.styleable.QRoundImage_designBorderColor, Color.BLACK);
        a.recycle();
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(Color.WHITE);
        borderMaskPaint.setAntiAlias(true);
        borderMaskPaint.setColor(borderColor);
        borderMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        if (circle) {
            canvas.drawCircle(roundRect.width() / 2, roundRect.height() / 2, radius - border, zonePaint);
        } else {
            canvas.drawRoundRect(borderRect, radius, radius, zonePaint);
        }
        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
        //画边框
        if (border > 0) {
            canvas.saveLayer(roundRect, borderPaint, Canvas.ALL_SAVE_FLAG);
            if (circle) {
                canvas.drawCircle(roundRect.width() / 2, roundRect.height() / 2, radius - border, borderPaint);
            } else {
                canvas.drawRoundRect(borderRect, radius, radius, borderPaint);
            }
            canvas.saveLayer(roundRect, borderMaskPaint, Canvas.ALL_SAVE_FLAG);
            if (circle) {
                canvas.drawCircle(roundRect.width() / 2, roundRect.height() / 2, radius, borderMaskPaint);
            } else {
                canvas.drawRoundRect(roundRect, radius, radius, borderMaskPaint);
            }
            canvas.restore();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRect.set(0, 0, w, h);
        borderRect.set(border, border, w - border, h - border);
        if (circle) {
            if (w > h) {
                radius = h * 1f / 2;
            } else {
                radius = w * 1f / 2;
            }
        }
    }

    /**
     * 设置圆角大小
     *
     * @param radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }


    /**
     * 尺寸转换默认不转换
     *
     * @param size 原尺寸
     * @return 转换后的尺寸
     */
    public int calDesignSize(int size) {
        return size;
    }
}
