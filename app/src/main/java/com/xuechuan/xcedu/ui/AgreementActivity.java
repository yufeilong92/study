package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AgreementActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 协议网址
 * @author: L-BackPacker
 * @date: 2018/5/30 20:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/30
 */
public class AgreementActivity extends BaseActivity {

    private WebView mWebAgreenement;
    private ImageView mIvAgreenmentImg;

    /*    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_agreement);
            initView();
        }*/
    private static String URLDATA = "urldata";
    private String mUrl;
    private Context mContext;

    public static Intent newInstance(Context context, String urldata) {
        Intent intent = new Intent(context, AgreementActivity.class);
        intent.putExtra(URLDATA, urldata);
        return intent;
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_agreement);
        if (getIntent() != null) {
            mUrl = getIntent().getStringExtra(URLDATA);
        }
        initView();
        initData();
    }

    private void initData() {
        mIvAgreenmentImg.setImageResource(R.drawable.animation_loading);
        final AnimationDrawable drawable = (AnimationDrawable) mIvAgreenmentImg.getDrawable();
        WebSettings settings = mWebAgreenement.getSettings();
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebAgreenement.loadUrl(mUrl);
        mWebAgreenement.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;

                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                        return true;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        mContext.startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mWebAgreenement.setVisibility(View.GONE);
                mIvAgreenmentImg.setVisibility(View.VISIBLE);
                drawable.start();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebAgreenement.setVisibility(View.VISIBLE);
                mIvAgreenmentImg.setVisibility(View.GONE);
                drawable.stop();
            }
        });
    }


    private void initView() {
        mContext = this;
        mWebAgreenement = (WebView) findViewById(R.id.web_agreenement);
        mIvAgreenmentImg = (ImageView) findViewById(R.id.iv_agreenment_img);
    }
}
