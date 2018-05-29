package com.xuechuan.xcedu.XceuAppliciton;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.easefun.polyvsdk.PolyvDevMountInfo;
import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.easefun.polyvsdk.PolyvSDKClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.umeng.commonsdk.UMConfigure;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.db.DbHelp.DBHelper;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.SharedSeletIdListUtil;
import com.xuechuan.xcedu.utils.SharedSeletResultListUtil;
import com.xuechuan.xcedu.utils.SharedTextListUtil;
import com.xuechuan.xcedu.utils.SharedUserUtils;
import com.xuechuan.xcedu.vo.HttpInfomVo;
import com.xuechuan.xcedu.vo.UserInfomVo;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.XceuAppliciton
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/8 10:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyAppliction extends MultiDexApplication {
    public final String TAG = "MyAppliction.class";
    Context mContext;
    private HttpInfomVo httpInfom;
    private PolyvSDKClient mPolyclient;
    private UserInfomVo infomVo;
    private static MyAppliction application;

    public static MyAppliction getInstance() {
        if (application == null)
            application = new MyAppliction();
        return application;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 保存用户信息
     *
     * @param infomVo
     */
    public void setUserInfom(UserInfomVo infomVo) {
        this.infomVo = infomVo;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserInfomVo getUserInfom() {
        return this.infomVo;
    }

    //初始化请求数据
    public HttpInfomVo getHttpInfomInstance() {
        return httpInfom;
    }


    public MyAppliction() {
        if (httpInfom == null) {
            httpInfom = new HttpInfomVo();

        }
        if (infomVo == null) {
            new UserInfomVo();
        }
    }

    private static ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //初始化播放器
        initPolyCilent();
        initDownFile();
        initOkGo();
        initJPush();
        initUM();
        initImagerLoader();
        DBHelper.initDb(this);
        SaveUUidUtil.initSharedPreference(this);
        SharedSeletResultListUtil.initSharedPreference(this);

    }

    private void initUM() {
        UMConfigure.init(this,"5a12384aa40fa3551f0001d1"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
    }

    private void initImagerLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
                .diskCacheExtraOptions(480, 800, null)  // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
//                .diskCache(new UnlimitedDiscCache(cacheDir)) // default 可以自定义缓存路径
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)  // 可以缓存的文件数量
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs() // 打印debug log
                .build(); //开始构建
        imageLoader.init(config);
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }


    public void displayImagesWithOutHear(ImageView iv, String uri, boolean isRound) {
        String string = "http://192.168.1.111/8080";
        StringBuilder builder = new StringBuilder();
        builder.append(string);
        builder.append(uri);
        displayImages(iv, builder.toString(), isRound);
    }

    /**
     * 调用该方法下载图片
     * 配置imageLoader图片选项
     *
     * @param iv      显示图片控件
     * @param url     网络或本地图片地址
     * @param isRound true为圆形，false不处理
     */
    public void displayImages(ImageView iv, String url, boolean isRound) {
//配置一些图片选项
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.s_n)// 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.s_n)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.s_n)// 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
                .displayer(isRound ? new CircleBitmapDisplayer() : new SimpleBitmapDisplayer())//FadeInBitmapDisplayer(200)listview加载闪烁问题
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)//图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
                .bitmapConfig(Bitmap.Config.RGB_565)//图片色彩565
                .resetViewBeforeLoading(true)
                .build();
//        String string ="http://192.168.1.110/8080";
//        url = string.concat(url);

        imageLoader.displayImage(url, iv, options);
    }

    private void initPolyCilent() {
        mPolyclient = PolyvSDKClient.getInstance();
        mPolyclient.initCrashReport(mContext);
        MessageData data = MessageData.getInstance();
        //配置加密
        mPolyclient.setConfig(data.getSdkBunc(), data.getSdkSecretkey(), data.getSdkVector(), mContext);
        //初始化SDK
        mPolyclient.initSetting(mContext);
        mPolyclient.initCrashReport(mContext);
        //使用SDK加密串来配置
    }

    public PolyvSDKClient getPolyColent() {
        if (mPolyclient == null) {
            initPolyCilent();
        }
        return mPolyclient;
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
//全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
//全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())//设置链接
                .setCacheMode(CacheMode.NO_CACHE) //设置缓存
                .setRetryCount(3); //断网链接

    }

    /***
     * 播放初始化下载地址
     */
    private void initDownFile() {
//获取SD卡信息
        PolyvDevMountInfo.getInstance().init(this, new PolyvDevMountInfo.OnLoadCallback() {

            @Override
            public void callback() {
                //是否有可移除的存储介质（例如 SD 卡）或内部（不可移除）存储可供使用。
                if (!PolyvDevMountInfo.getInstance().isSDCardAvaiable()) {
                    // TODO 没有可用的存储设备,后续不能使用视频缓存功能
                    Log.e(TAG, "没有可用的存储设备,后续不能使用视频缓存功能");
                    return;
                }
                //可移除的存储介质（例如 SD 卡），需要写入特定目录/storage/sdcard1/Android/data/包名/。
                String externalSDCardPath = PolyvDevMountInfo.getInstance().getExternalSDCardPath();
                if (!TextUtils.isEmpty(externalSDCardPath)) {
                    StringBuilder dirPath = new StringBuilder();
                    dirPath.append(externalSDCardPath).append(File.separator).append("Android").append(File.separator).append("data")
                            .append(File.separator).append(getPackageName()).append(File.separator).append("polyvdownload");
                    File saveDir = new File(dirPath.toString());
                    if (!saveDir.exists()) {
                        getExternalFilesDir(null); // 生成包名目录
                        saveDir.mkdirs();//创建下载目录
                    }

                    //设置下载存储目录
                    PolyvSDKClient.getInstance().setDownloadDir(saveDir);
                    return;
                }

                //如果没有可移除的存储介质（例如 SD 卡），那么一定有内部（不可移除）存储介质可用，都不可用的情况在前面判断过了。
                File saveDir = new File(PolyvDevMountInfo.getInstance().getInternalSDCardPath() + File.separator + "polyvdownload");
                if (!saveDir.exists()) {
                    saveDir.mkdirs();//创建下载目录
                }

                //设置下载存储目录
                PolyvSDKClient.getInstance().setDownloadDir(saveDir);
            }
        });
        PolyvDownloaderManager.setDownloadQueueCount(1);
    }

}
