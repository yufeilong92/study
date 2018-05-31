package com.xuechuan.xcedu.ui.net;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easefun.polyvsdk.PolyvBitRate;
import com.easefun.polyvsdk.PolyvSDKUtil;
import com.easefun.polyvsdk.marquee.PolyvMarqueeItem;
import com.easefun.polyvsdk.marquee.PolyvMarqueeView;
import com.easefun.polyvsdk.video.PolyvPlayErrorReason;
import com.easefun.polyvsdk.video.PolyvVideoView;
import com.easefun.polyvsdk.video.listener.IPolyvOnAdvertisementEventListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnErrorListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureClickListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureLeftDownListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureLeftUpListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureRightDownListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureRightUpListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureSwipeLeftListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureSwipeRightListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnPreparedListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnQuestionAnswerTipsListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnVideoPlayErrorListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnVideoStatusListener;
import com.easefun.polyvsdk.vo.PolyvADMatterVO;
import com.xuechuan.xcedu.Event.NetPlayEvent;
import com.xuechuan.xcedu.Event.NetPlayTrySeeEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.MyNetBookIndicatorAdapter;
import com.xuechuan.xcedu.adapter.MyTagPagerAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.fragment.NetBookinfomFragment;
import com.xuechuan.xcedu.fragment.NetTableFragment;
import com.xuechuan.xcedu.player.player.PolyvPlayerLightView;
import com.xuechuan.xcedu.player.player.PolyvPlayerMediaController;
import com.xuechuan.xcedu.player.player.PolyvPlayerProgressView;
import com.xuechuan.xcedu.player.player.PolyvPlayerVolumeView;
import com.xuechuan.xcedu.player.util.PolyvErrorMessageUtils;
import com.xuechuan.xcedu.player.util.PolyvScreenUtils;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.CoursesBeanVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.NoScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookInfomActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 网课详情页
 * @author: L-BackPacker
 * @date: 2018/5/14 14:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/14
 */
public class NetBookInfomActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = NetBookInfomActivity.class.getSimpleName();
    /**
     * 播放器的parentView
     */
    private RelativeLayout viewLayout = null;
    /**
     * 播放主视频播放器
     */
    private PolyvVideoView videoView = null;
    /**
     * 跑马灯控件
     */
    private PolyvMarqueeView marqueeView = null;
    private PolyvMarqueeItem marqueeItem = null;
    /**
     * 视频控制栏
     */
    private PolyvPlayerMediaController mediaController = null;
    /**
     * 手势出现的亮度界面
     */
    private PolyvPlayerLightView lightView = null;
    /**
     * 手势出现的音量界面
     */
    private PolyvPlayerVolumeView volumeView = null;
    /**
     * 手势出现的进度界面
     */
    private PolyvPlayerProgressView progressView = null;
    /**
     * 视频加载缓冲视图
     */
    private ProgressBar loadingProgress = null;

    private int fastForwardPos = 0;
    private boolean isPlay = false;

    private LinearLayout ll_title_bar;

    /***
     * 数据类型
     */
    public static final String SERIALIZABLELIST = "person_data";
    private CoursesBeanVo dataVo;
    private TextView mTvNetBookTitle;
    private MagicIndicator mNetMagicIndicator;
    private NoScrollViewPager mVpNetBar;
    private Context mContext;
    private TextView mTvNetBookAllprice;
    private TextView mTvNetContactService;
    private Button mBtnNetGoBuy;
    private ImageView mIvNetPlay;
    private ImageView mIvNetBookPlay;
    private RelativeLayout mRlPlaylayout;
    private String vid;
    private CommonPopupWindow popDown;
    private LinearLayout mLlNetPlayRoot;
    private LinearLayout mLlNetBuyLayou;

    public static Intent newInstance(Context context, CoursesBeanVo o) {
        Intent intent = new Intent(context, NetBookInfomActivity.class);
        intent.putExtra(SERIALIZABLELIST, o);
        return intent;
    }

    /*   @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_net_book_infom);
           initView();
       }
   */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        setContentView(R.layout.activity_net_book_infom);
        if (getIntent() != null) {
            dataVo = (CoursesBeanVo) getIntent().getSerializableExtra(SERIALIZABLELIST);
        }
        initView();
        initViewData();
        PolyvScreenUtils.generateHeight16_9(this);
        PolyvScreenUtils.initTitleBar(ll_title_bar, mRlPlaylayout, mLlNetBuyLayou);
        int playModeCode = getIntent().getIntExtra("playMode", PlayMode.portrait.getCode());
        PlayMode playMode = PlayMode.getPlayMode(playModeCode);
        if (playMode == null)
            playMode = PlayMode.portrait;
        //视频id
        String vid = getIntent().getStringExtra("value");
        int bitrate = getIntent().getIntExtra("bitrate", PolyvBitRate.ziDong.getNum());
        boolean startNow = getIntent().getBooleanExtra("startNow", false);
        boolean isMustFromLocal = getIntent().getBooleanExtra("isMustFromLocal", false);

        switch (playMode) {
            case landScape: //横屏
                ll_title_bar.setVisibility(View.GONE);
                mediaController.changeToLandscape();
                break;
            case portrait://竖屏
                ll_title_bar.setVisibility(View.VISIBLE);
                mediaController.changeToPortrait();
                break;
        }
        initData();
        EventBus.getDefault().register(this);
        initVideo();
//        play("d740a56357c361f76cdd800b204e9800_d", 0, true, false);
    }

    private void initVideo() {
        submitPlayProgress();

    }

    public void submitPlayProgress() {
        PolyvVideoView view = new PolyvVideoView(mContext);
        int position = view.getCurrentPosition();
        String vid = view.getCurrentVid();
        Log.e(TAG, "视频播放进度: " + position + "\n" + vid);

    }

    private void initData() {
        if (dataVo != null) {
            List<String> list;
            mTvNetBookTitle.setText(dataVo.getName());
            if (dataVo.isIsall()) {
                mIvNetBookPlay.setVisibility(View.GONE);
                list = new ArrayList<>();
                list.add("详情");
            } else
                list = ArrayToListUtil.arraytoList(mContext, R.array.net_book_title);
            mNetMagicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
            CommonNavigator commonNavigator = new CommonNavigator(this);
            commonNavigator.setScrollPivotX(0.25f);
            commonNavigator.setAdjustMode(true);
            MyNetBookIndicatorAdapter adapter = new MyNetBookIndicatorAdapter(list, mVpNetBar);
            mNetMagicIndicator.setNavigator(commonNavigator);
            commonNavigator.setAdapter(adapter);
            List<Fragment> fragments = creartFragment(list);
            MyTagPagerAdapter tagPagerAdapter = new MyTagPagerAdapter(getSupportFragmentManager(), fragments);
            mVpNetBar.setAdapter(tagPagerAdapter);
            mVpNetBar.setOffscreenPageLimit(4);
            ViewPagerHelper.bind(mNetMagicIndicator, mVpNetBar);
            mTvNetBookAllprice.setText("￥" + dataVo.getPrice() + "");
            if (!StringUtil.isEmpty(dataVo.getCoverimg())) {
                MyAppliction.getInstance().displayImages(mIvNetPlay, dataVo.getCoverimg(), false);
            }

        }
    }

    private List<Fragment> creartFragment(List<String> list) {
        if (list.size() < 2) {
            mNetMagicIndicator.setVisibility(View.GONE);
        }
        List<Fragment> fragments = new ArrayList<>();
        NetBookinfomFragment bookinfomFragment = NetBookinfomFragment.newInstance(dataVo.getDescription(), "");
        fragments.add(bookinfomFragment);
        if (dataVo.isIsall()) {
        } else {
//        NetBooKListFragment booKListFragment = NetBooKListFragment.newInstance(String.valueOf(dataVo.getId()));
            NetTableFragment booKListFragment = NetTableFragment.newInstance(String.valueOf(dataVo.getId()));
            fragments.add(booKListFragment);
        }
        return fragments;
    }

    /**
     * 播放视频
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNetMainPlayId(NetPlayEvent event) {
        VideosBeanVo vo = event.getVo();
        L.e(vo.getVid());
        L.e("调用===========" + vo.getVid());
        play(vo.getVid(), 0, true, false);
        mRlPlaylayout.setVisibility(View.GONE);
    }

    /**
     * 播放视频
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNetMainTryPlayId(NetPlayTrySeeEvent event) {
        VideosBeanVo vo = event.getVo();
        L.e(vo.getVid());
        vid = vo.getVid();
    }

    private void initView() {
        mContext = this;
        mLlNetPlayRoot = (LinearLayout) findViewById(R.id.ll_net_play_root);
        mLlNetPlayRoot.setOnClickListener(this);
        mIvNetBookPlay = (ImageView) findViewById(R.id.iv_net_book_play);
        mIvNetBookPlay.setOnClickListener(this);
        mRlPlaylayout = (RelativeLayout) findViewById(R.id.rl_play_layout);
        mIvNetPlay = (ImageView) findViewById(R.id.iv_net_play);
        mIvNetPlay.setOnClickListener(this);
        mTvNetBookAllprice = (TextView) findViewById(R.id.tv_net_book_allprice);
        mTvNetBookAllprice.setOnClickListener(this);
        mTvNetContactService = (TextView) findViewById(R.id.tv_net_contact_service);
        mTvNetContactService.setOnClickListener(this);
        mBtnNetGoBuy = (Button) findViewById(R.id.btn_net_go_buy);
        mBtnNetGoBuy.setOnClickListener(this);
        mTvNetBookTitle = (TextView) findViewById(R.id.tv_net_book_title);
        mTvNetBookTitle.setOnClickListener(this);
        mNetMagicIndicator = (MagicIndicator) findViewById(R.id.net_magic_indicator);
        mNetMagicIndicator.setOnClickListener(this);
        mVpNetBar = (NoScrollViewPager) findViewById(R.id.vp_net_bar);
        mVpNetBar.setOnClickListener(this);
        ll_title_bar = (LinearLayout) findViewById(R.id.activity_title_container);
        viewLayout = (RelativeLayout) findViewById(R.id.view_layout);
        videoView = (PolyvVideoView) findViewById(R.id.polyv_video_view);
        marqueeView = (PolyvMarqueeView) findViewById(R.id.polyv_marquee_view);
        mediaController = (PolyvPlayerMediaController) findViewById(R.id.polyv_player_media_controller);

        lightView = (PolyvPlayerLightView) findViewById(R.id.polyv_player_light_view);
        volumeView = (PolyvPlayerVolumeView) findViewById(R.id.polyv_player_volume_view);
        progressView = (PolyvPlayerProgressView) findViewById(R.id.polyv_player_progress_view);
        loadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
        mLlNetBuyLayou = (LinearLayout) findViewById(R.id.ll_net_buy_layou);
        mLlNetBuyLayou.setOnClickListener(this);
        mediaController.initTitltBar(ll_title_bar, mRlPlaylayout, mLlNetBuyLayou);
        mediaController.initConfig(viewLayout);
        videoView.setMediaController(mediaController);
        videoView.setPlayerBufferingIndicator(loadingProgress);
        // 设置跑马灯
        videoView.setMarqueeView(marqueeView, marqueeItem = new PolyvMarqueeItem()
                .setStyle(PolyvMarqueeItem.STYLE_ROLL_FLICK) //样式
                .setDuration(10000) //时长
                .setText("POLYV Android SDK") //文本
                .setSize(16) //字体大小
                .setColor(Color.YELLOW) //字体颜色
                .setTextAlpha(70) //字体透明度
                .setInterval(1000) //隐藏时间
                .setLifeTime(1000) //显示时间
                .setTweenTime(1000) //渐隐渐现时间
                .setHasStroke(true) //是否有描边
                .setBlurStroke(true) //是否模糊描边
                .setStrokeWidth(3) //描边宽度
                .setStrokeColor(Color.MAGENTA) //描边颜色
                .setStrokeAlpha(70)); //描边透明度

    }


    private void initViewData() {
        videoView.setOpenAd(true);
        videoView.setOpenTeaser(true);
        videoView.setOpenQuestion(true);
        videoView.setOpenSRT(true);
        videoView.setOpenPreload(true, 2);
        videoView.setOpenMarquee(true);
        videoView.setAutoContinue(true);
        videoView.setNeedGestureDetector(true);

        videoView.setOnPreparedListener(new IPolyvOnPreparedListener2() {
            @Override
            public void onPrepared() {
                mediaController.preparedView();
                progressView.setViewMaxValue(videoView.getDuration());
                // 没开预加载在这里开始弹幕
                // danmuFragment.start();
            }
        });
        videoView.setOnVideoStatusListener(new IPolyvOnVideoStatusListener() {
            @Override
            public void onStatus(int status) {
                if (status < 60) {
                    Toast.makeText(NetBookInfomActivity.this, "状态错误 " + status, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, String.format("状态正常 %d", status));
                }
            }
        });

        videoView.setOnVideoPlayErrorListener(new IPolyvOnVideoPlayErrorListener2() {
            @Override
            public boolean onVideoPlayError(@PolyvPlayErrorReason.PlayErrorReason int playErrorReason) {
                String message = PolyvErrorMessageUtils.getPlayErrorMessage(playErrorReason);
                message += "(error code " + playErrorReason + ")";
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(NetBookInfomActivity.this);
                builder.setTitle("错误");
                builder.setMessage(message);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

                if (videoView.getWindowToken() != null) {
                    builder.show();
                }
                return true;
            }
        });

        videoView.setOnErrorListener(new IPolyvOnErrorListener2() {
            @Override
            public boolean onError() {
                Toast.makeText(NetBookInfomActivity.this, "当前视频无法播放，请向管理员反馈(error code " + PolyvPlayErrorReason.VIDEO_ERROR + ")", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        videoView.setOnAdvertisementEventListener(new IPolyvOnAdvertisementEventListener2() {
            @Override
            public void onShow(PolyvADMatterVO adMatter) {
                Log.i(TAG, "开始播放视频广告");
            }

            @Override
            public void onClick(PolyvADMatterVO adMatter) {
                if (!TextUtils.isEmpty(adMatter.getAddrUrl())) {
                    try {
                        new URL(adMatter.getAddrUrl());
                    } catch (MalformedURLException e1) {
                        Log.e(TAG, PolyvSDKUtil.getExceptionFullMessage(e1, -1));
                        return;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(adMatter.getAddrUrl()));
                    startActivity(intent);
                }
            }
        });


        videoView.setOnQuestionAnswerTipsListener(new IPolyvOnQuestionAnswerTipsListener() {

            @Override
            public void onTips(@NonNull String msg) {
            }
        });


        videoView.setOnGestureLeftUpListener(new IPolyvOnGestureLeftUpListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("LeftUp %b %b brightness %d", start, end, videoView.getBrightness(NetBookInfomActivity.this)));
                int brightness = videoView.getBrightness(NetBookInfomActivity.this) + 5;
                if (brightness > 100) {
                    brightness = 100;
                }

                videoView.setBrightness(NetBookInfomActivity.this, brightness);
                lightView.setViewLightValue(brightness, end);
            }
        });

        videoView.setOnGestureLeftDownListener(new IPolyvOnGestureLeftDownListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("LeftDown %b %b brightness %d", start, end, videoView.getBrightness(NetBookInfomActivity.this)));
                int brightness = videoView.getBrightness(NetBookInfomActivity.this) - 5;
                if (brightness < 0) {
                    brightness = 0;
                }

                videoView.setBrightness(NetBookInfomActivity.this, brightness);
                lightView.setViewLightValue(brightness, end);
            }
        });

        videoView.setOnGestureRightUpListener(new IPolyvOnGestureRightUpListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("RightUp %b %b volume %d", start, end, videoView.getVolume()));
                // 加减单位最小为10，否则无效果
                int volume = videoView.getVolume() + 10;
                if (volume > 100) {
                    volume = 100;
                }

                videoView.setVolume(volume);
                volumeView.setViewVolumeValue(volume, end);
            }
        });

        videoView.setOnGestureRightDownListener(new IPolyvOnGestureRightDownListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("RightDown %b %b volume %d", start, end, videoView.getVolume()));
                // 加减单位最小为10，否则无效果
                int volume = videoView.getVolume() - 10;
                if (volume < 0) {
                    volume = 0;
                }

                videoView.setVolume(volume);
                volumeView.setViewVolumeValue(volume, end);
            }
        });

        videoView.setOnGestureSwipeLeftListener(new IPolyvOnGestureSwipeLeftListener() {

            @Override
            public void callback(boolean start, boolean end) {
                // 左滑事件
                Log.d(TAG, String.format("SwipeLeft %b %b", start, end));
                if (fastForwardPos == 0) {
                    fastForwardPos = videoView.getCurrentPosition();
                }

                if (end) {
                    if (fastForwardPos < 0)
                        fastForwardPos = 0;
                    videoView.seekTo(fastForwardPos);
                    if (videoView.isCompletedState()) {
                        videoView.start();
                    }
                    fastForwardPos = 0;
                } else {
                    fastForwardPos -= 10000;
                    if (fastForwardPos <= 0)
                        fastForwardPos = -1;
                }
                progressView.setViewProgressValue(fastForwardPos, videoView.getDuration(), end, false);
            }
        });

        videoView.setOnGestureSwipeRightListener(new IPolyvOnGestureSwipeRightListener() {

            @Override
            public void callback(boolean start, boolean end) {
                // 右滑事件
                Log.d(TAG, String.format("SwipeRight %b %b", start, end));
                if (fastForwardPos == 0) {
                    fastForwardPos = videoView.getCurrentPosition();
                }

                if (end) {
                    if (fastForwardPos > videoView.getDuration())
                        fastForwardPos = videoView.getDuration();
                    if (!videoView.isCompletedState()) {
                        videoView.seekTo(fastForwardPos);
                    } else if (videoView.isCompletedState() && fastForwardPos != videoView.getDuration()) {
                        videoView.seekTo(fastForwardPos);
                        videoView.start();
                    }
                    fastForwardPos = 0;
                } else {
                    fastForwardPos += 10000;
                    if (fastForwardPos > videoView.getDuration())
                        fastForwardPos = videoView.getDuration();
                }
                progressView.setViewProgressValue(fastForwardPos, videoView.getDuration(), end, true);
            }
        });

        videoView.setOnGestureClickListener(new IPolyvOnGestureClickListener() {
            @Override
            public void callback(boolean start, boolean end) {
                if (videoView.isInPlaybackState() && mediaController != null)
                    if (mediaController.isShowing())
                        mediaController.hide();
                    else
                        mediaController.show();
            }
        });


    }

    /**
     * 播放视频
     *
     * @param vid             视频id
     * @param bitrate         码率（清晰度）
     * @param startNow        是否现在开始播放视频
     * @param isMustFromLocal 是否必须从本地（本地缓存的视频）播放
     */
    public void play(final String vid, final int bitrate, boolean startNow, final boolean isMustFromLocal) {
        if (TextUtils.isEmpty(vid)) return;
        videoView.release();
        mediaController.hide();
        loadingProgress.setVisibility(View.GONE);
        progressView.resetMaxValue();
        if (startNow) {
            //调用setVid方法视频会自动播放
            videoView.setVid(vid, bitrate, isMustFromLocal);
        }
    }

    private void clearGestureInfo() {
        videoView.clearGestureInfo();
        progressView.hide();
        volumeView.hide();
        lightView.hide();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //回来后继续播放
        if (isPlay) {
            videoView.onActivityResume();
//            danmuFragment.resume();

        }
        mediaController.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearGestureInfo();
        mediaController.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //弹出去暂停
        isPlay = videoView.onActivityStop();
//        danmuFragment.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.destroy();
        EventBus.getDefault().removeAllStickyEvents();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mediaController.disable();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (PolyvScreenUtils.isLandscape(this) && mediaController != null) {
                mediaController.changeToPortrait();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public static Intent newIntent(Context context, PlayMode playMode, String vid) {
        return newIntent(context, playMode, vid, PolyvBitRate.ziDong.getNum());
    }

    public static Intent newIntent(Context context, PlayMode playMode, String vid, int bitrate) {
        return newIntent(context, playMode, vid, bitrate, false);
    }

    public static Intent newIntent(Context context, PlayMode playMode, String vid, int bitrate, boolean startNow) {
        return newIntent(context, playMode, vid, bitrate, startNow, false);
    }

    public static Intent newIntent(Context context, PlayMode playMode, String vid, int bitrate, boolean startNow,
                                   boolean isMustFromLocal) {
        Intent intent = new Intent(context, NetBookInfomActivity.class);
        intent.putExtra("playMode", playMode.getCode());
        intent.putExtra("value", vid);
        intent.putExtra("bitrate", bitrate);
        intent.putExtra("startNow", startNow);
        intent.putExtra("isMustFromLocal", isMustFromLocal);
        return intent;
    }

    public static void intentTo(Context context, PlayMode playMode, String vid) {
        intentTo(context, playMode, vid, PolyvBitRate.ziDong.getNum());
    }

    public static void intentTo(Context context, PlayMode playMode, String vid, int bitrate) {
        intentTo(context, playMode, vid, bitrate, false);
    }

    public static void intentTo(Context context, PlayMode playMode, String vid, int bitrate, boolean startNow) {
        intentTo(context, playMode, vid, bitrate, startNow, false);
    }

    public static void intentTo(Context context, PlayMode playMode, String vid, int bitrate, boolean startNow,
                                boolean isMustFromLocal) {
        context.startActivity(newIntent(context, playMode, vid, bitrate, startNow, isMustFromLocal));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_net_contact_service://客服

                break;
            case R.id.btn_net_go_buy://购买
                Intent intent = NetBuyActivity.newInstance(mContext, dataVo.getPrice(), dataVo.getId(),
                        dataVo.getName(), dataVo.getCoverimg());
                startActivity(intent);
                break;
            case R.id.iv_net_play:
                break;
            case R.id.iv_net_book_play:
                if (dataVo.isIsall()) {
                    return;
                }
                play();
                break;
            default:

        }
    }

    /**
     * 播放视频
     */
    private void play() {
        if (!StringUtil.isEmpty(vid)) {
            mRlPlaylayout.setVisibility(View.GONE);
            play(vid, 0, true, false);
            mediaController.setIsPlay(true);
            PolyvScreenUtils.IsPlay(true);
        }else {
            T.showToast(mContext,getString(R.string.no_try_see));
        }

    }

    /**
     * 播放模式
     *
     * @author TanQu
     */
    public enum PlayMode {
        /**
         * 横屏
         */
        landScape(3),
        /**
         * 竖屏
         */
        portrait(4);

        private final int code;

        private PlayMode(int code) {
            this.code = code;
        }

        /**
         * 取得类型对应的code
         *
         * @return
         */
        public int getCode() {
            return code;
        }

        public static PlayMode getPlayMode(int code) {
            switch (code) {
                case 3:
                    return landScape;
                case 4:
                    return portrait;
            }
            return null;
        }
    }


    /**
     * 设置背景颜色
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);

    }

}