package com.xuechuan.xcedu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: HoistoryHlper.java
 * @Package com.xuechuan.xcedu.db
 * @Description: todo
 * @author: YFL
 * @date: 2018/4/15 12:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/15 星期日  www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于伍峰教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class HoistoryHlper extends OrmLiteSqliteOpenHelper {
    public static String databaseName = "history";
    public static int databaseVersion = 1;
    private Dao<HistoryVo, Integer> mDao;

    public HoistoryHlper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, HistoryVo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
    }

    public Dao<HistoryVo, Integer> getHistoryDao() {
        try {
            if (mDao == null)
                mDao = getDao(HistoryVo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mDao;
    }

}
