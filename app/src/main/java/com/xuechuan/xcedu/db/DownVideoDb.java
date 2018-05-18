package com.xuechuan.xcedu.db;

import com.xuechuan.xcedu.db.Converent.DownVideoConverent;
import com.xuechuan.xcedu.db.Converent.UserLookConverent;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

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
public class DownVideoDb {
    @Id(autoincrement = true)
    private Long id;
    /**
     * 用户id
     */
    private String staffid;
    /**
     * 科目id
     */
    private String kid;
    @Convert(converter = DownVideoConverent.class, columnType = String.class)
    private List<DownVideoVo> downlist;
    @Generated(hash = 1439020414)
    public DownVideoDb(Long id, String staffid, String kid,
            List<DownVideoVo> downlist) {
        this.id = id;
        this.staffid = staffid;
        this.kid = kid;
        this.downlist = downlist;
    }
    @Generated(hash = 1189966955)
    public DownVideoDb() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKid() {
        return this.kid;
    }
    public void setKid(String kid) {
        this.kid = kid;
    }
    public List<DownVideoVo> getDownlist() {
        return this.downlist;
    }
    public void setDownlist(List<DownVideoVo> downlist) {
        this.downlist = downlist;
    }
    public String getStaffid() {
        return this.staffid;
    }
    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }


}
