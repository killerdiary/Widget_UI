package com.hy.frame.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;

import com.hy.frame.widget.ui.R;


/**
 * title AutoUtil
 * author heyan
 * time 19-7-10 下午4:34
 * desc  0.5F用于四舍五入 效率高
 */
public final class AutoUtil {
    /**
     * 获取默认 designDesignScreenWidth
     *
     * @param cxt 上下文
     * @return int
     */
    public static int getDesignScreenWidth(Context cxt) {
        TypedArray a = cxt.getTheme().obtainStyledAttributes(new int[]{R.attr.designScreenWidth});
        int width = a.getDimensionPixelSize(0, 0);
        a.recycle();
        return width;
    }

    /**
     * 计算设计比例
     *
     * @param cxt               上下文
     * @param designScreenWidth 设计尺寸
     * @return 比例 float
     */
    public static float calDesignScale(Context cxt, int designScreenWidth) {
        int screenWidth = cxt.getResources().getDisplayMetrics().widthPixels;
        if (designScreenWidth == 0 || designScreenWidth == screenWidth) return 1F;
        return ((float) screenWidth) / designScreenWidth;
    }

    /**
     * 计算设计比例
     *
     * @param width 设计尺寸
     * @param scale 需要缩放比例
     * @return 真实尺寸
     */
    public static int calDesignWidth(int width, float scale) {
        if (scale == 0 || scale == 1) return width;
        return (int) (width * scale + 0.5F);
    }

    /**
     * 获取默认 designWidthDP
     *
     * @param cxt 上下文
     * @return int
     */
    public static int getDesignWidthDP(Context cxt) {
        TypedArray a = cxt.getTheme().obtainStyledAttributes(new int[]{R.attr.designWidthDP});
        int width = a.getInt(0, 0);
        a.recycle();
        return width;
    }

    /**
     * 开启缩放尺寸
     *
     * @param activity Activity
     * @param enable   是否开启
     */
    public static void autoScale(Activity activity, boolean enable) {
        float designWidth = getDesignWidthDP(activity);
        autoScale(activity, designWidth, enable);
    }

    /**
     * 开启缩放尺寸
     *
     * @param activity    Activity
     * @param designWidth 设计尺寸(单位DP)
     * @param enable      是否开启
     */
    public static void autoScale(Activity activity, float designWidth, boolean enable) {
        Resources r = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            r = activity.getTheme().getResources();
        } else {
            r = activity.getResources();
        }
        DisplayMetrics original = activity.getApplicationContext().getResources().getDisplayMetrics();
        int widthPixels = original.widthPixels;
        int heightPixels = original.heightPixels;
        DisplayMetrics d = r.getDisplayMetrics();
        if (enable) {
            float density = widthPixels * 1f / designWidth;
            d.density = density; //作用于DP单位
            d.densityDpi = (int) designWidth;//作用于DP单位
            d.scaledDensity = density; //作用于文本缩放，SP单位
            d.xdpi = designWidth * 1f;
            d.ydpi = heightPixels / density;
        } else {
            //这里做一个还原
            d.density = original.density; //作用于DP单位
            d.densityDpi = original.densityDpi;//作用于DP单位
            d.scaledDensity = original.scaledDensity; //作用于文本缩放，SP单位
            d.xdpi = original.xdpi;
            d.ydpi = original.ydpi;
        }
    }
}
