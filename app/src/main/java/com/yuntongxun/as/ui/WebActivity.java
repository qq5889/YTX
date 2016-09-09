package com.yuntongxun.as.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yuntongxun.as.R;
import com.yuntongxun.as.common.utils.BitmapUtil;
import com.yuntongxun.as.common.utils.DemoUtils;
import com.yuntongxun.as.common.utils.FileAccessor;

import java.io.File;
import java.io.IOException;

public class WebActivity extends ECSuperActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url=getIntent().getStringExtra("url");
        initResViews();

        saveDefaultImgToSDCard();

        getTopBarView().setTopBarToStatus(1, R.drawable.topbar_back_bt,
                -1, null,
                null,
                getString(R.string.app_name), null, this);
    }


    public void saveDefaultImgToSDCard(){
        String imagePath = FileAccessor.IMESSAGE_RICH_TEXT + "/" + DemoUtils.md5(BitmapUtil.ATTACT_ICON)+".jpg";
        File file = new File(imagePath);
        if(!file.exists()) {
            try {
                file.createNewFile();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.attachment_icon);
                BitmapUtil.saveBitmapToLocalSDCard(bitmap, BitmapUtil.ATTACT_ICON);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private  String url;
    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.btn_left) {
            hideSoftKeyboard();
            finish();

        } else if (i == R.id.text_right) {
        } else {
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_about_url;
    }

    private WebView mWebView;

    private void initResViews() {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(url);
    }


}
