package com.xuechuan.xcedu.base;

import android.Manifest;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.base
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/16 16:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DataMessageVo {
    //微信
    public static final String APP_ID = "wx0c71e64b9e151c84";

    //接受微信登录广播
    public static final String WEI_LOGIN_ACTION = "com.weixinlogin.com";
    //用户换取access_token的code，仅在ErrCode为0时有效
    public static final String WEICODE = "code";
    //第三方程序发送时用来标识其请求的唯一性的标志，由第三方程序调用sendReq时传入，由微信终端回传，state字符串长度不能超过1K
    public static final String WEISTATE = "state";
    //用户表示
    public static final String STAFFID = "staffid";
    //时间撮
    public static final String TIMESTAMP = "timeStamp";
    //随机数
    public static final String NONCE = "nonce";
    //加密串
    public static final String SIGNATURE = "signature";
    //请求token广播
    public static final String ACITON = "com.xuechaun.action";
    //请求体
    public static final String HTTPAPPLICAITON = "application/json";
    //网络
    public static final String HTTP_AC = "ac";
    //版本（1.2.3）
    public static final String HTTP_VERSION_NAME = "version_name";
    //版本（123）
    public static final String HTTP_VERSION_CODE = "version_code";
    //手机平台（android/ios）
    public static final String HTTP_DEVICE_PLATFORM = "device_platform";
    //手机型号
    public static final String HTTP_DEVICE_TYPE = "device_type";
    //手机品牌
    public static final String HTTP_DEVICE_BRAND = "device_brand";
    //操作系统版本（8.0.0）
    public static final String HTTP_OS_VERSION = "os_version";
    //分辨率
    public static final String HTTP_RESOLUTION = "resolution";
    //dpi
    public static final String HTTP_DPI = "dpi";
    //定位信息
    public static final String LOCATIONACTION = "com.xuechaun.loaction";
    //默认请求记录数
    public static final int CINT_PANGE_SIZE = 10;
    //ac文章评论
    public static String USERTYPEAC = "ac";
    //a文章
    public static String USERTYPEA = "a";
    //qc问题评论
    public static String USERTYPEQC = "qc";
    //vc视频评论
    public static String USERTYPEVC = "vc";
    //文章
    public static String USERTYOEARTICLE = "article";
    //视频
    public static String USERTYOEVIDEO = "question video";
    //问题
    public static String QUESTION = "question";
    //文章
    public static String ARTICLE = "article";
    //视频
    public static String VIDEO = "video";
    //案例
    public static String MARKTYPECASE = "typecase";
    //技术
    public static String MARKTYPESKILL = "typeskill";
    //综合
    public static String MARKTYPECOLLORT = "typecoloct";
    //文章标识
    public static String MARKTYPEORDER= "typeorder";
    //需要的权限
    public static  String[] Persmission ={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE
    };
   //微信支付
    public static int PAYTYPE_WEIXIN= 2;
    //支付包
    public static int PAYTYPE_ZFB= 3;
    //余额
    public static int PAYTYPE_YUER= 1;
    //兑换码
    public static int PAYTYPE_DUHAUN= 5;
    //微信id
    public static String WEIXINVID="wx0c71e64b9e151c84";



}
