package com.xuechuan.xcedu.baidu;

import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.xuechuan.xcedu.utils.Utils;

import java.util.HashMap;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: education
 * @Package com.xuechuan.education
 * @Description: 获取定位地址监听
 * @author: L-BackPacker
 * @date: 2018/4/3 9:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyLocationListener extends BDAbstractLocationListener {

    /**
     * 获取详细地址信息
     */
    public final String ADDR="ADDR";
    /**
     *  获取国家
     */
    public final String COUNTRY ="COUNTRY";
    /**
     *  获取省份
     */
    public final String PROVINCE ="province";
    /**
     * 获取城市
     */
    public final String CITY ="city ";
    /**
     *  获取区县
     */
    public final String DISTRICT ="district";
    /**
     *  获取街道信息
     */
    public static final String STREET ="street";
    private String addr;
    private String country;
    private String province;
    private String city;
    private String district;
    private String street;

    @Override
    public void onReceiveLocation(BDLocation location) {
        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取地址相关的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
        //获取详细地址信息
        addr = location.getAddrStr();
        //获取国家
        country = location.getCountry();
        //获取省份
        province = location.getProvince();
        //获取城市
        city = location.getCity();
        //获取区县
        district = location.getDistrict();
        //获取街道信息
        street = location.getStreet();
        Log.e("yfl", "onReceiveLocation: "+addr+country
        +province+city+district+street);
        String describe = location.getLocationDescribe();
        Log.e("yfl", ""+describe );

    }

    public HashMap<String,String> getLocationInfom() {
        HashMap<String, String> hashMap = new HashMap<>();
        if (Utils.isEmpty(addr)){
            hashMap.put(ADDR,addr);
        }
        if (Utils.isEmpty(country)){
            hashMap.put(COUNTRY,country);
        }
        if (Utils.isEmpty(province)){
            hashMap.put(PROVINCE,province);
        }
        if (Utils.isEmpty(city)){
            hashMap.put(CITY,city);
        }
        if (Utils.isEmpty(district)){
            hashMap.put(DISTRICT,district);
        }
        if (Utils.isEmpty(street)){
            hashMap.put(STREET,street);
        }
        return hashMap;

    }
}
