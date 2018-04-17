package com.xuechuan.xcedu.base;

import java.util.ArrayList;

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
    public static final String  APP_ID="wx0c71e64b9e151c84";
    //接受微信登录广播
    public static final String WEI_LOGIN_ACTION="com.weixinlogin.com";
    //用户换取access_token的code，仅在ErrCode为0时有效
    public static final String WEICODE="code";
    //第三方程序发送时用来标识其请求的唯一性的标志，由第三方程序调用sendReq时传入，由微信终端回传，state字符串长度不能超过1K
    public static final String WEISTATE="state";
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
    public static final String HTTPAPPLICAITON="application/json";
    //网络
    public static final String HTTP_AC ="ac";
    //版本（1.2.3）
    public static final String HTTP_VERSION_NAME="version_name";
    //版本（123）
    public static final String HTTP_VERSION_CODE ="version_code";
    //手机平台（android/ios）
    public static final String HTTP_DEVICE_PLATFORM ="device_platform";
    //手机型号
    public static final String HTTP_DEVICE_TYPE ="device_type";
    //手机品牌
    public static final String HTTP_DEVICE_BRAND="device_brand";
    //操作系统版本（8.0.0）
    public static final String HTTP_OS_VERSION="os_version";
    //分辨率
    public static final String HTTP_RESOLUTION="resolution";
    //dpi
    public static final String HTTP_DPI="dpi";
    //定位信息
    public static final  String LOCATIONACTION="com.xuechaun.loaction";

    public static ArrayList<String> getImageList(){
        ArrayList<String> list = new ArrayList<>();
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        return list;
    }
    public static ArrayList<String> getImageList1(){
        ArrayList<String> list = new ArrayList<>();
        list.add("http://img.zcool.cn/community/01fca557a7f5f90000012e7e9feea8.jpg");
        list.add("http://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png");
        list.add("http://img.zcool.cn/community/01996b57a7f6020000018c1bedef97.jpg");
        list.add("http://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg");
        return list;
    }
}
