package com.xuechuan.xcedu.XceuAppliciton;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import com.easefun.polyvsdk.PolyvDevMountInfo;
import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.easefun.polyvsdk.PolyvSDKClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.xuechuan.xcedu.baidu.LocationService;

import com.xuechuan.xcedu.vo.HttpInfomVo;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

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
public class MyAppliction extends Application {
    public static final String TAG = "MyAppliction.class";
    Context mContext;
    public LocationService locationService;
    public Vibrator mVibrator;
    public static OkHttpClient client;
    private static HttpInfomVo httpInfom;
    private PolyvSDKClient mPolyclient;


    //初始化请求数据
    public static HttpInfomVo getHttpInfomInstance() {
        return httpInfom;
    }

    public MyAppliction() {
        if (httpInfom == null) {
            httpInfom = new HttpInfomVo();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext= this;
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        //初始化播放器
        initPolyCilent();
        initDownFile();
        initOkGo();
        initOkHttp();

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
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间
       OkGo.getInstance().init(this)
               .setOkHttpClient(builder.build())
               .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
               .setRetryCount(3);                              //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0

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

    //初始化数请求
    private static void initOkHttp() {
        //请求超时
        int HTTPTIMEOUT = 6000;
        //读取超时
        int HTTPTIMEREAD = 6000;
        if (client == null) {
            synchronized (OkHttpClient.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            .connectTimeout(HTTPTIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(HTTPTIMEREAD, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }
}
