/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */package com.yuntongxun.as.common.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.yuntongxun.as.common.CCPAppManager;

/**
 * 资源文件帮助类
 * Created by Jorstin on 2015/3/18.
 */
public class ResourceHelper {

    static {
        density = -1.0F;
    }

    private static final String TAG = LogUtil.getLogUtilsTag(ResourceHelper.class);

    private static float density;

    /**
     *
     * @param context
     * @param ratio
     * @return
     */
    public static int fromDPToPix(Context context, int ratio) {
        return Math.round(getDensity(context) * ratio);
    }

    /**
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        if (context == null) {
            context = CCPAppManager.getContext();
        }
        if (density < 0.0F){
            density = context.getResources().getDisplayMetrics().density;
        }
        return density;
    }
    /**
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawableById(Context context , int resId) {

        if(context == null) {
            LogUtil.e(TAG, "get drawable, resId " + resId + ", but context is null");
            return null;
        }

        return context.getResources().getDrawable(resId);
    }

    /**
     *
     * @param context
     * @param resId
     * @return
     */
    public static ColorStateList getColorStateList(Context context , int resId) {
        if(context == null) {
            LogUtil.e(TAG, "get drawable, resId " + resId + ", but context is null");
            return null;
        }
        return context.getResources().getColorStateList(resId);
    }
}
