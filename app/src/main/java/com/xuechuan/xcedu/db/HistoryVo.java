package com.xuechuan.xcedu.db;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: HistoryVo.java
 * @Package com.xuechuan.xcedu.db
 * @Description: todo
 * @author: YFL
 * @date: 2018/4/15 12:13
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/15 星期日  www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于伍峰教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@DatabaseTable(tableName = "tb_history")
public class HistoryVo {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "history" ,dataType = DataType.STRING)
    private String history;

    public HistoryVo() {
    }

    public HistoryVo(String history) {
        this.history = history;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
