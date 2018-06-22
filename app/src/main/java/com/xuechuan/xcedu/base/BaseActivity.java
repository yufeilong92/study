package com.xuechuan.xcedu.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.ui.AgreementActivity;
import com.xuechuan.xcedu.ui.InfomDetailActivity;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.UserInfomVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.base
 * @Description: 基类
 * @author: L-BackPacker
 * @date: 2018/4/10 10:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 传入参数-标题
     */
    public static final String CSTR_EXTRA_TITLE_STR = "title";
    private String mBaseTitle;
    private String title;
    private String isarticle;
    private String url;
    private String shareurl;
    private String articleid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getIntent() != null) {
            Uri data = this.getIntent().getData();
            if (data != null) {
                title = data.getQueryParameter("title");
                isarticle = data.getQueryParameter("isarticle");
                url = data.getQueryParameter("url");
                shareurl = data.getQueryParameter("shareurl");
                articleid = data.getQueryParameter("articleid");

            }
        }

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        Intent intent = getIntent();
        mBaseTitle = intent.getStringExtra(CSTR_EXTRA_TITLE_STR);
        MyAppliction.getInstance().addActivity(this);
        initContentView(savedInstanceState);
        if (!StringUtil.isEmpty(mBaseTitle)) {
            setTitle(mBaseTitle);
        }
        doShareActivity();
    }

    public void delectShare() {
        isarticle = null;
    }

    public ShareParems getShareParems() {
        ShareParems parems = new ShareParems();
        parems.url = url;
        parems.articleid = articleid;
        parems.title = title;
        parems.shareurl = shareurl;
        return parems;
    }

    public String getIsarticle() {
        return isarticle;
    }
    private class ShareParems {
        private String title;
        private String url;
        private String shareurl;
        private String articleid;
    }
    private void doShareActivity() {
        if (isarticle == null) {
            return;
        }
        if (isarticle.equals("0")) {
            doIntentAct(new Infom(), getShareParems());
        } else if (isarticle.equals("1")) {
            doIntentAct(new WenZhang(), getShareParems());
        }
    }

    protected abstract void initContentView(Bundle savedInstanceState);


    private void setTitle(String str) {
        TextView title = (TextView) findViewById(R.id.activity_title_text);
        title.setText(str);
    }

    public void onHomeClick(View view) {
        finish();
    }

    protected String getTextStr(View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            return tv.getText().toString().trim();
        }
        if (view instanceof Button) {
            Button btn = (Button) view;
            return btn.getText().toString().trim();
        }
        if (view instanceof EditText) {
            EditText et = (EditText) view;
            return et.getText().toString().trim();
        }
        return null;
    }

    protected String getStringWithId(int id) {
        return getResources().getString(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        MyAppliction.getInstance().finishActivity(this);
    }

    protected void finishAll() {
        MyAppliction.getInstance().exit();
    }


    public interface ShareActivity {
        public void startAct(ShareParems shareParems);
    }
    public class Infom implements ShareActivity {
        @Override
        public void startAct(ShareParems shareParems) {
            UserInfomVo infom = MyAppliction.getInstance().getUserInfom();
            if (infom == null) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                delectShare();
                Intent intent = AgreementActivity.newInstance(BaseActivity.this,   shareParems. url, AgreementActivity.SHAREMARK,
                        shareParems.title,   shareParems. shareurl);
                BaseActivity.this.startActivity(intent);
            }
        }
    }
    public class WenZhang implements ShareActivity {
        @Override
        public void startAct(ShareParems shareParems) {
            UserInfomVo infom = MyAppliction.getInstance().getUserInfom();
            if (infom == null || infom.getData().getUser() == null) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                delectShare();
                Intent intent = InfomDetailActivity.startInstance(BaseActivity.this, shareParems.url,
                        String.valueOf(shareParems.articleid),shareParems.title);
                BaseActivity.this.startActivity(intent);
            }
        }
    }

    public void doIntentAct(ShareActivity activity, ShareParems shareParems) {
        activity.startAct(shareParems);
    }
}
