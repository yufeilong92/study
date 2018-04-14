package com.xuechuan.xcedu.utils;

import android.util.Log;

import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.vo.HttpInfomVo;

import org.json.JSONObject;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 字符串排序
 * @author: L-BackPacker
 * @date: 2018/4/8 10:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class StringSort {
    /**
     * 获取升序后的数据
     *
     * @param param
     * @return
     */
    public String getOrderMd5Data(String param) {
        HttpInfomVo infom = MyAppliction.getHttpInfomInstance();
        String time = infom.getTimeStamp();
        String random = infom.getNonce();
        String staffid = infom.getStaffid();
        String token = infom.getToken();
        String data = null;
        if (param != null && !param.isEmpty()) {
            data = time + random + staffid + token + param;
        }
        String sort = sort(data);
        String md5 = Md5.getMD5String(sort);
       String md5String = md5.toUpperCase();
        Log.i("yfl", "升序结果: " + sort);
        Log.i("yfl", "getOrderMd5Data: " + md5);
        Log.i("yfl", "大小写结果: " + md5String);
        return md5String;
    }

    /**
     * 获取升序后的数据
     *
     * @param param
     * @return
     */
    public String getOrderMd5Data(JSONObject param) {
        HttpInfomVo infom = MyAppliction.getHttpInfomInstance();
        String time = infom.getTimeStamp();
        String random = infom.getNonce();
        String staffid = infom.getStaffid();
        String token = infom.getToken();
        String data;
        if (param == null) {
            data = time + random + staffid + token;
        } else {
            data = time + random + staffid + token + param.toString();
        }
        String sort = sort(data);
        String md5= Md5.getMD5String(sort);
        String md5String = md5.toUpperCase();
        Log.i("yfl", "升序排序结果: " + sort);
        Log.i("yfl", "getOrderMd5Data: " + md5);
        Log.i("yfl", "大写排序: " + md5String);

        return md5String;
    }

    /**
     * 升序排序
     * @param str
     * @return
     */
    public static String sort(String str) {
        char[] s1 = str.toCharArray();
        System.out.println(s1);
        for (int i = 0; i < s1.length; i++) {
            for (int j = 0; j < i; j++) {
                if (s1[i] < s1[j]) {
                    char temp = s1[i];
                    s1[i] = s1[j];
                    s1[j] = temp;
                }
            }
        }
        return String.valueOf(s1);
    }
}
