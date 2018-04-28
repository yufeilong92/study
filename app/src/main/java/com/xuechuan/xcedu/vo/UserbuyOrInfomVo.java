package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 用户信息
 * @author: L-BackPacker
 * @date: 2018/4/28 8:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UserbuyOrInfomVo  implements Serializable{
    /**
     * 技术实务 true 为购买，false为不购买
     */
    private boolean Skillbook;
    private boolean colligatebook;
    private boolean casebook;
    /**
     * 用户token
     */
    private String token;
    /**
     * token 过期时间
     */
    private String time;

    public boolean getSkillbook() {
        return Skillbook;
    }

    public void setSkillbook(boolean skillbook) {
        Skillbook = skillbook;
    }

    public boolean getColligatebook() {
        return colligatebook;
    }

    public void setColligatebook(boolean colligatebook) {
        this.colligatebook = colligatebook;
    }

    public boolean getCasebook() {
        return casebook;
    }

    public void setCasebook(boolean casebook) {
        this.casebook = casebook;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
