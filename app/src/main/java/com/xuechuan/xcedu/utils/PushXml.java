package com.xuechuan.xcedu.utils;

import android.content.Context;
import com.xuechuan.xcedu.vo.ProvincesVo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: Xml解析
 * @author: L-BackPacker
 * @date: 2018/4/10 15:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PushXml {

    /**
     * 解析后的数据集合
     */
    private ArrayList<ProvincesVo> mProvinces = null;
    private static PushXml pushXml;
    //要解析的xml名字
    private String XML = "province.xml";
    public PushXml() {
        if (mProvinces == null) {
            mProvinces = new ArrayList<>();
        }
    }
    public static PushXml getInstance() {
        if (pushXml == null) {
            pushXml = new PushXml();
        }
        return pushXml;
    }

    public ArrayList<ProvincesVo> pushXml(Context mContext) {
        SAXReader reader = new SAXReader();
        try {
            InputStream inputStream = mContext.getResources().getAssets().open(XML);
            Document read = reader.read(inputStream);
            Element rootElement = read.getRootElement();
            for (Iterator i = rootElement.elementIterator(); i.hasNext(); ) {
                Element employee = (Element) i.next();
                ProvincesVo provinces = new ProvincesVo();
                for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
                    if (node.getName().equals("name")) {
                        provinces.setName(node.getText());
                    }
                    if (node.getName().equals("code")) {
                        provinces.setCode(node.getText());
                    }
                }
                mProvinces.add(provinces);
            }


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mProvinces;
    }
}
