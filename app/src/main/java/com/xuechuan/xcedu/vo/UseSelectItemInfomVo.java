package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 用户选中的信息
 * @author: L-BackPacker
 * @date: 2018/4/28 11:05
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UseSelectItemInfomVo extends BaseVo {
    private static final long serialVersionUID = 4625736711545272654L;
    /**
     * 用户查看过的id
     */
    private int id;
    /**
     * 单选项
     */
    private String item;
    /**
     * 问题类型
      */
    private String type;
    private String selectItemA;
    private String selectItemB;
    private String selectItemC;
    private String selectItemD;
    private String selectItemE;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSelectItemA() {
        return selectItemA;
    }

    public void setSelectItemA(String selectItemA) {
        this.selectItemA = selectItemA;
    }

    public String getSelectItemB() {
        return selectItemB;
    }

    public void setSelectItemB(String selectItemB) {
        this.selectItemB = selectItemB;
    }

    public String getSelectItemC() {
        return selectItemC;
    }

    public void setSelectItemC(String selectItemC) {
        this.selectItemC = selectItemC;
    }

    public String getSelectItemD() {
        return selectItemD;
    }

    public void setSelectItemD(String selectItemD) {
        this.selectItemD = selectItemD;
    }

    public String getSelectItemE() {
        return selectItemE;
    }

    public void setSelectItemE(String selectItemE) {
        this.selectItemE = selectItemE;
    }
}
