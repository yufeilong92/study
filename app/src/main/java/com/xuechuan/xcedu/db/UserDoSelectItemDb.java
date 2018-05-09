package com.xuechuan.xcedu.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db
 * @Description: 临时记录用户选择
 * @author: L-BackPacker
 * @date: 2018/5/9 11:56
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
@Entity
public class UserDoSelectItemDb {
    @Id(autoincrement = true)
    private Long id;

    @Generated(hash = 736637212)
    public UserDoSelectItemDb(Long id) {
        this.id = id;
    }

    @Generated(hash = 1582847392)
    public UserDoSelectItemDb() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
