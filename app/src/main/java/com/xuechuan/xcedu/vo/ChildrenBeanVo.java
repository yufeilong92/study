package com.xuechuan.xcedu.vo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description:  教程子类
 * @author: L-BackPacker
 * @date: 2018/4/23 10:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ChildrenBeanVo extends BookHomePageVo {
    /**
     * children : []
     * gourl : http://192.168.1.110:8081/article/jc/202
     * id : 202
     * isend : true
     * parentid : 19
     * title : 案例1 木器厂房防火案例分析
     */

    private String gourl;
    private int id;
    private boolean isend;
    private int parentid;
    private String title;
    private List<?> children;

    public String getGourl() {
        return gourl;
    }

    public void setGourl(String gourl) {
        this.gourl = gourl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsend() {
        return isend;
    }

    public void setIsend(boolean isend) {
        this.isend = isend;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }
}