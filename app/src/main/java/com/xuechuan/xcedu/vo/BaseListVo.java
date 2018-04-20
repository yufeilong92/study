package com.xuechuan.xcedu.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/20 9:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BaseListVo implements Serializable {
    private List<Object> datas;
    private StatusVo status;
    private TotalVo total;

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    public StatusVo getStatus() {
        return status;
    }

    public void setStatus(StatusVo status) {
        this.status = status;
    }

    public TotalVo getTotal() {
        return total;
    }

    public void setTotal(TotalVo total) {
        this.total = total;
    }
}
