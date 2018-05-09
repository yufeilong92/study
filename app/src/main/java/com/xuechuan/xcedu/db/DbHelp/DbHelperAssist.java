package com.xuechuan.xcedu.db.DbHelp;

import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.db.UserInfomDbDao;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;
import com.xuechuan.xcedu.vo.UserLookVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db.DbHelp
 * @Description: db辅助类
 * @author: L-BackPacker
 * @date: 2018/5/9 11:17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DbHelperAssist {

    private final UserInfomDbDao dao;
    private static DbHelperAssist assist = null;

    public DbHelperAssist() {
        dao = DBHelper.getDaoSession().getUserInfomDbDao();

    }

    public static DbHelperAssist getInstance() {
        if (assist == null) {
            assist = new DbHelperAssist();
        }
        return assist;
    }

    /**
     * 删除所有
     */
    public void delectAll() {
        dao.deleteAll();
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<UserInfomDb> queryAll() {
        List<UserInfomDb> userInfomDbs = dao.loadAll();
        if (userInfomDbs == null) {
            return null;
        }
        return userInfomDbs;
    }

    /**
     * 更新技术
     *
     * @param mUuid 用户uuid
     * @param mdata 跟新记录
     */
    public void upDataSkillRecord(String mUuid, List<UserLookVo> mdata) {
        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(mUuid)).unique();
        if (infomDb == null) {
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            upDataSkillRecord(mUuid, mdata);
        }
        infomDb.setSkillData(mdata);
        dao.update(infomDb);
    }

    /**
     * 跟新综合记录
     *
     * @param mUuid 用户uuid
     * @param mdata 跟新记录
     */
    public void upDataColoctRecord(String mUuid, List<UserLookVo> mdata) {
        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(mUuid)).unique();
        if (infomDb == null) {
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            upDataColoctRecord(mUuid, mdata);
        }
        infomDb.setColoctData(mdata);
        dao.update(infomDb);
    }

    /**
     * 跟新案例
     *
     * @param mUuid 用户uuid
     * @param mdata 跟新记录
     */
    public void upDataCaseRecord(String mUuid, List<UserLookVo> mdata) {
        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(mUuid)).unique();
        if (infomDb == null) {
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            upDataCaseRecord(mUuid, mdata);
        }
        infomDb.setCaseData(mdata);
        dao.update(infomDb);
    }

    /**
     * 保存数据
     *
     * @param vo
     */
    public void saveUserInfom(UserInfomVo vo) {
        if (vo == null) {
            return;
        }
        //用户信息
        UserBean user = vo.getData().getUser();
        String id = String.valueOf(user.getId());
        SaveUUidUtil.getInstance().delectUUid();
        SaveUUidUtil.getInstance().putUUID(id);
        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(id)).unique();
        //数据库表
        UserInfomDb userInfomDb = new UserInfomDb();
        if (infomDb != null) {
            userInfomDb = infomDb;
            userInfomDb.setVo(vo);
            userInfomDb.setMoid(String.valueOf(user.getId()));
            userInfomDb.setToken(user.getToken());
            userInfomDb.setTokenTime(user.getTokenexpire());
            MyAppliction.getInstance().setUserInfom(vo);
            dao.update(userInfomDb);
        } else {
            //更新信息
            userInfomDb.setVo(vo);
            userInfomDb.setMoid(String.valueOf(user.getId()));
            userInfomDb.setToken(user.getToken());
            userInfomDb.setTokenTime(user.getTokenexpire());
            MyAppliction.getInstance().setUserInfom(vo);
            dao.insert(userInfomDb);

        }


    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    public UserInfomDb queryWithuuId(String id) {
        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(id)).unique();
        if (infomDb == null) {
            return null;
        }
        return infomDb;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserInfomDb queryWithuuUserInfom() {
        String userId = SaveUUidUtil.getInstance().getUserId();
        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(userId)).unique();
        if (infomDb == null) {
            return null;
        }
        return infomDb;
    }

    /***
     * 查询技术章节
     * @param id
     * @return
     */
    public List<UserLookVo> querySkill(String id) {
        UserInfomDb userInfomDb = queryWithuuId(id);
        return userInfomDb.getSkillData();
    }

    /***
     * 查询综合章节
     * @param id
     * @return
     */
    public List<UserLookVo> queryColoct(String id) {
        UserInfomDb userInfomDb = queryWithuuId(id);
        return userInfomDb.getColoctData();
    }

    /***
     * 查询案例章节
     * @param id
     * @return
     */
    public List<UserLookVo> queryCase(String id) {
        UserInfomDb userInfomDb = queryWithuuId(id);
        return userInfomDb.getCaseData();
    }

    /**
     * 更新模式选择
     *
     * @param day
     */
    public void upDataDayOrNight( String day) {
        String userId = SaveUUidUtil.getInstance().getUserId();
        UserInfomDb userInfomDb = queryWithuuId(userId);
        userInfomDb.setShowDayOrNight(day);
        dao.update(userInfomDb);
    }

    /**
     * 更新自动跳下一题
     *
     * @param isGo
     */
    public void upDataNextGo( boolean isGo) {
        String userId = SaveUUidUtil.getInstance().getUserId();
        UserInfomDb userInfomDb = queryWithuuId(userId);
        userInfomDb.setUserNextGo(isGo);
        dao.update(userInfomDb);
    }

    /**
     * 更新自动跳下一题
     *
     * @param number
     */
    public void upDataDelect( String number) {
        String userId = SaveUUidUtil.getInstance().getUserId();
        UserInfomDb userInfomDb = queryWithuuId(userId);
        userInfomDb.setDelectQuestion(number);
        dao.update(userInfomDb);
    }

    /**
     * 更新自动跳下一题
     *
     * @param number
     */
    public void upDataBuyInfom( String number, boolean isbuy) {
        String userId = SaveUUidUtil.getInstance().getUserId();
        UserInfomDb userInfomDb = queryWithuuId(userId);
        if (number.equals("1")) {
            userInfomDb.setSkillBook(isbuy);
        } else if (number.equals("2")) {
            userInfomDb.setColligateBook(isbuy);
        } else if (number.equals("3")) {
            userInfomDb.setCaseBook(isbuy);
        }

        dao.update(userInfomDb);
    }

}
