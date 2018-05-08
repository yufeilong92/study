package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.view
 * @Description: 资讯详情页
 * @author: L-BackPacker
 * @date: 2018/5/8 15:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface SpecalTabView {
    /**
     * tab标签
     * @param con
     */
    public void SpecalTagSuccess(String con);
    public void SpecalTagError(String con);

    //标签下内容
    public void SpecalTagConSuccess(String con);
    public void SpecalTagConError(String con);


}
