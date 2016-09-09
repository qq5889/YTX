/*
 *  Copyright (c) 2015 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */package com.yuntongxun.as.photopicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.yuntongxun.as.R;
import com.yuntongxun.as.photopicker.model.PhotoDirectory;
import com.yuntongxun.as.photopicker.utils.PhotoUtils;

import java.util.List;

/**
 * 图片目录适配器
 * @author 容联•云通讯
 * @since 2016-4-6
 * @version 5.0
 */
public class FolderAdapter extends BaseAdapter {

    private final DisplayImageOptions options;
    List<PhotoDirectory> mData;
    Context mContext;
    int mWidth;

    public FolderAdapter(Context context, List<PhotoDirectory> mData) {
        this.mData = mData;
        this.mContext = context;
        mWidth = PhotoUtils.dip2px(context, 90);
        options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.picker_ic_photo_loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.picker_item_floder_layout, null);
            holder.photoIV = (ImageView) convertView.findViewById(R.id.imageview_floder_img);
            holder.folderNameTV = (TextView) convertView.findViewById(R.id.textview_floder_name);
            holder.photoNumTV = (TextView) convertView.findViewById(R.id.textview_photo_num);
            holder.selectIV = (ImageView) convertView.findViewById(R.id.imageview_floder_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.selectIV.setVisibility(View.GONE);
       // holder.photoIV.setImageResource(R.drawable.picker_ic_photo_loading);
        PhotoDirectory folder = mData.get(position);
        if(folder.isSelected()) {
            holder.selectIV.setVisibility(View.VISIBLE);
        }
        holder.folderNameTV.setText(folder.getName());
        holder.photoNumTV.setText(folder.getPhotos().size() + "张");
        ImageLoader.getInstance().displayImage("file://"+folder.getPhotos().get(0).getPath(),holder.photoIV,options);
        //Glide.with(mContext).load(folder.getPhotos().get(0).getPath()).dontAnimate().thumbnail(0.1f).into(holder.photoIV);
        return convertView;
    }

    private class ViewHolder {
        private ImageView photoIV;
        private TextView folderNameTV;
        private TextView photoNumTV;
        private ImageView selectIV;
    }

}
