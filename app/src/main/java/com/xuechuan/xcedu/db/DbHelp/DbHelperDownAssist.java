package com.xuechuan.xcedu.db.DbHelp;

import com.andview.refreshview.callback.IFooterCallBack;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.db.DownVideoDbDao;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db.DbHelp
 * @Description: 下载数据库
 * @author: L-BackPacker
 * @date: 2018/5/18 13:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DbHelperDownAssist {

    private final DownVideoDbDao dao;
    private static DbHelperDownAssist assist;

    public DbHelperDownAssist() {
        dao = DBHelper.getDaoSession().getDownVideoDbDao();
    }

    public static DbHelperDownAssist getInstance() {
        if (assist == null) {
            assist = new DbHelperDownAssist();
        }
        return assist;
    }

    /**
     * 添加数据
     *
     * @param db
     */
    public void addDownItem(DownVideoDb db) {
        if (db == null) {
            return;
        }
        List<DownVideoDb> dbs = queryUserDownInfom();
        if (dbs == null || dbs.isEmpty()) {
            savaUserId(db.getKid());
            addDownItem(db);
            return;
        }
        //判断当前课目是否有缓存过
        List<DownVideoDb> list = queryUserDownInfom(db.getKid());
        if (list == null || list.isEmpty()) {
            savaUserId(db.getKid());
            addDownItem(db);
            return;
        }

        for (DownVideoDb videoDb : dbs) {
            if (videoDb.getKid().equals(db.getKid())) {
                List<DownVideoVo> downlist = videoDb.getDownlist();
                if (downlist != null && !downlist.isEmpty()) {//已有情况
                    for (DownVideoVo vo : db.getDownlist()) {
                        if (!downlist.contains(vo)) {
                            downlist.add(vo);
                        }
                    }
                } else {//没有
                    downlist = db.getDownlist();
                }
                videoDb.setDownlist(downlist);
                dao.update(videoDb);
            } else {

            }
        }

    }

    /**
     * 删除章节
     *
     * @param kid
     * @param zid
     */
    public void delectZItem(String kid, String pid, String zid) {
        if (StringUtil.isEmpty(zid) || StringUtil.isEmpty(kid) || StringUtil.isEmpty(pid)) {
            return;
        }
        List<DownVideoDb> dbs = queryUserDownInfom();
        if (dbs == null || dbs.isEmpty()) {
            return;
        }
        for (int i = 0; i < dbs.size(); i++) {
            DownVideoDb db = dbs.get(i);
            if (db.getKid().equals(kid)) {//找到下载的科目
                List<DownVideoVo> downlist = db.getDownlist();
                for (int j = 0; j < downlist.size(); j++) {
                    DownVideoVo vo = downlist.get(j);
                    if (vo.getZid().equals(zid) && vo.getPid().equals(pid)) {//找到该篇
                        downlist.remove(j);
                    }
                }
            }
        }
    }


    /**
     * 删除科目
     *
     * @param kid
     */
    public void delectKItem(String kid) {
        if (StringUtil.isEmpty(kid)) {
            return;
        }
        List<DownVideoDb> dbs = queryUserDownInfom();
        if (dbs == null || dbs.isEmpty()) {
            return;
        }
        for (int i = 0; i < dbs.size(); i++) {
            DownVideoDb db = dbs.get(i);
            if (db.getKid().equals(kid)) {//找到下载的科目
                dao.deleteByKey(db.getId());
            }
        }
    }

    private void addDownItemVo(DownVideoDb db) {
        if (db == null) {
            return;
        }
        dao.insert(db);

    }

    /**
     * 删除全部
     */
    public void delectAll() {
        List<DownVideoDb> dbs = queryUserDownInfom();
        if (dbs == null || dbs.isEmpty()) {
            return;
        }
        dao.detachAll();

    }

    /**
     * 更新用户选中数据
     *
     * @param db
     */
    public void addUpDataItem(DownVideoDb db) {
        if (db == null) {
            return;
        }
        List<DownVideoDb> dbs = queryUserDownInfom();
        if (dbs == null || dbs.isEmpty()) {
            savaUserId(db.getKid());
            addUpDataItem(db);
            return;
        }
        for (DownVideoDb videoDb : dbs) {
            if (videoDb.getKid().equals(db.getKid())) {
                videoDb.setKid(db.getKid());
                videoDb.setDownlist(db.getDownlist());
                dao.update(videoDb);
            }
        }
    }


    public void savaUserId(String kid) {
        String userId = SaveUUidUtil.getInstance().getUserId();
        DownVideoDb videoDb = dao.queryBuilder().where(DownVideoDbDao.Properties.Staffid.eq(userId)).unique();
        if (videoDb == null) {
            DownVideoDb downVideoDb = new DownVideoDb();
            downVideoDb.setStaffid(userId);
            downVideoDb.setKid(kid);
            dao.insert(downVideoDb);
        } else {
            videoDb.setStaffid(userId);
            videoDb.setKid(kid);
            dao.update(videoDb);
        }

    }

    /**
     * 查询该用户的所有下载数据
     *
     * @return
     */
    public List<DownVideoDb> queryUserDownInfom() {
        String userId = SaveUUidUtil.getInstance().getUserId();
        return dao.queryBuilder().where(DownVideoDbDao.Properties.Staffid.eq(userId)).list();
    }

    /**
     * 查询该用户的所有下载数据
     *
     * @return
     */
    public List<DownVideoDb> queryUserDownInfom(int kid) {
        String userId = SaveUUidUtil.getInstance().getUserId();
        return dao.queryBuilder().where(DownVideoDbDao.Properties.Staffid.eq(userId),
                DownVideoDbDao.Properties.Kid.eq(String.valueOf(kid))).list();
    }

    /**
     * 查询该用户的所有下载数据
     *
     * @return
     */
    public List<DownVideoDb> queryUserDownInfom(String kid) {
        String userId = SaveUUidUtil.getInstance().getUserId();
        return dao.queryBuilder().where(DownVideoDbDao.Properties.Staffid.eq(userId),
                DownVideoDbDao.Properties.Kid.eq(kid)).list();
    }


}
