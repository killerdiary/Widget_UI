package com.hy.frame.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.hy.frame.widget.ui.R;

/**
 * title 可控制图标大小EditText，另外只支持drawableLeft居中
 * author heyan
 * time 19-7-10 下午4:24
 * desc 无
 */
public class QEditText extends androidx.appcompat.widget.AppCompatEditText {

    private int drawLeftWidth;
    private int drawLeftHeight;
    private int drawTopWidth;
    private int drawTopHeight;
    private int drawRightWidth;
    private int drawRightHeight;
    private int drawBottomWidth;
    private int drawBottomHeight;
    private int drawPadding;
    private boolean drawCenter = false;

    public QEditText(Context context) {
        this(context, null);
    }

    public QEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public QEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    @SuppressLint("CustomViewStyleable")
    public void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QEditText, defStyleAttr, 0);
        int padding = a.getDimensionPixelSize(R.styleable.QEditText_designPadding, 0);
        int paddingLeft = a.getDimensionPixelSize(R.styleable.QEditText_designPaddingLeft, padding);
        int paddingTop = a.getDimensionPixelSize(R.styleable.QEditText_designPaddingTop, padding);
        int paddingRight = a.getDimensionPixelSize(R.styleable.QEditText_designPaddingRight, padding);
        int paddingBottom = a.getDimensionPixelSize(R.styleable.QEditText_designPaddingBottom, padding);
        int textSize = a.getDimensionPixelSize(R.styleable.QEditText_designTextSize, 0);
        this.drawLeftWidth = calDesignSize(a.getDimensionPixelSize(R.styleable.QEditText_designDrawLeftWidth, 0));
        this.drawLeftHeight = calDesignSize(a.getDimensionPixelSize(R.styleable.QEditText_designDrawLeftHeight, 0), this.drawLeftWidth);
        this.drawTopWidth = calDesignSize(a.getDimensionPixelSize(R.styleable.QEditText_designDrawTopWidth, 0));
        this.drawTopHeight = calDesignSize(a.getDimensionPixelSize(R.styleable.QEditText_designDrawTopHeight, 0), this.drawTopWidth);
        this.drawRightWidth = calDesignSize(a.getDimensionPixelSize(R.styleable.QEditText_designDrawRightWidth, 0));
        this.drawRightHeight = calDesignSize(a.getDimensionPixelSize(R.styleable.QEditText_designDrawRightHeight, 0), this.drawRightWidth);
        this.drawBottomWidth = calDesignSize(a.getDimensionPixelSize(R.styleable.QEditText_designDrawBottomWidth, 0));
        this.drawBottomHeight = calDesignSize(a.getDimensionPixelSize(R.styleable.QEditText_designDrawBottomHeight, 0), this.drawBottomWidth);
        this.drawPadding = calDesignSize(a.getDimensionPixelSize(R.styleable.QEditText_designDrawPadding, 0));
        this.drawCenter = a.getBoolean(R.styleable.QEditText_designDrawCenter, false);
        a.recycle();
        if (paddingLeft > 0) paddingLeft = calDesignSize(paddingLeft);
        else paddingLeft = getPaddingLeft();
        if (paddingTop > 0) paddingTop = calDesignSize(paddingTop);
        else paddingTop = getPaddingTop();
        if (paddingRight > 0) paddingRight = calDesignSize(paddingRight);
        else paddingRight = getPaddingRight();
        if (paddingBottom > 0) paddingBottom = calDesignSize(paddingBottom);
        else paddingBottom = getPaddingBottom();
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        if (textSize > 0)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, calDesignSize(textSize));
        if (drawPadding > 0)
            setCompoundDrawablePadding(drawPadding);
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (drawLeftWidth > 0 && left != null)
            left.setBounds(0, 0, drawLeftWidth, drawLeftHeight);
        if (drawTopWidth > 0 && top != null)
            top.setBounds(0, 0, drawTopWidth, drawTopHeight);
        if (drawRightWidth > 0 && right != null)
            right.setBounds(0, 0, drawRightWidth, drawRightHeight);
        if (drawBottomWidth > 0 && bottom != null)
            bottom.setBounds(0, 0, drawBottomWidth, drawBottomHeight);
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableLeft = drawables[0];
        if (drawCenter && drawableLeft != null) {
            float textWidth = getPaint().measureText(getText().toString());
            int drawablePadding = getCompoundDrawablePadding();
            if (this.drawPadding > 0) drawablePadding = drawPadding;
            int drawableWidth = drawLeftWidth;
            float bodyWidth = textWidth + drawableWidth + drawablePadding;
            canvas.translate((getMeasuredWidth() - bodyWidth) / 2 - getPaddingLeft(), 0f);
        }
        super.onDraw(canvas);
    }

    /**
     * 尺寸转换默认不转换
     *
     * @param size 原尺寸
     * @param def  默认值
     * @return 转换后的尺寸
     */
    public int calDesignSize(int size, int def) {
        int value = calDesignSize(size);
        if (value == 0)
            return def;
        return value;
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
