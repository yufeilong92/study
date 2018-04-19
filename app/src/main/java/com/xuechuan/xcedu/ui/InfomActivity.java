package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.xuechuan.xcedu.R;

import static android.view.KeyEvent.KEYCODE_BACK;
/**
 * @Title:  InfomActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 详情信息页
 * @author: L-BackPacker
 * @date:   2018/4/19 16:35
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class InfomActivity extends AppCompatActivity {

    private WebView mWebInfom;
    private RelativeLayout mRlvInfomShow;


    private static String URLPARAM = "urlparam";
    private String mUrl;

    public static void newInstance(Context context, String url) {
        Intent intent = new Intent(context, InfomActivity.class);
        intent.putExtra(URLPARAM, url);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infom);
        if (getIntent() != null) {
//            mUrl = getIntent().getStringExtra(URLPARAM);
            mUrl = "https://www.baidu.com/";
        }
        initView();
        initVebView();
    }

    private void initVebView() {
        WebSettings settings = mWebInfom.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebInfom.loadUrl(mUrl);
        mWebInfom.setWebChromeClient(new WebChromeClient());
        mWebInfom.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url == null) return false;

                try {
                    if (url.startsWith("http:") || url.startsWith("https:"))
                    {
                        view.loadUrl(url);
                        return true;
                    }
                    else
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mRlvInfomShow.setVisibility(View.VISIBLE);
                mWebInfom.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mRlvInfomShow.setVisibility(View.GONE);
                mWebInfom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }


    private void initView() {
        mWebInfom = (WebView) findViewById(R.id.wb_infom);
        mRlvInfomShow = (RelativeLayout) findViewById(R.id.rlv_infom_show);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebInfom.canGoBack()) {
            mWebInfom.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
