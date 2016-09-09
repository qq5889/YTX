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
 */
package com.yuntongxun.as.ui.contact;

import android.graphics.Bitmap;

import com.yuntongxun.as.common.CCPAppManager;
import com.yuntongxun.as.common.utils.DemoUtils;
import com.yuntongxun.as.common.utils.ResourceHelper;

import java.io.IOException;
import java.util.HashMap;

/**
 * 联系人逻辑处理
 * Created by Jorstin on 2015/3/18.
 */
public class ContactLogic {

    private static HashMap<String, Bitmap> photoCache = new HashMap<>(20);


    public static Bitmap mDefaultBitmap = null;


    static {
        try {
            if (mDefaultBitmap == null) {
                mDefaultBitmap = DemoUtils.decodeStream(CCPAppManager.getContext().getAssets().open("drawable-xhdpi/personal_center_default_avatar.png"), ResourceHelper.getDensity(null));
            }
        } catch (IOException e) {
        }
    }

    private static ContactLogic sInstance;

    public static ContactLogic getInstance() {
        if (sInstance == null) {
            sInstance = new ContactLogic();
        }
        return sInstance;
    }

}
