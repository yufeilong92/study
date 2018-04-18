package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 微信用户信息
 * @author: L-BackPacker
 * @date: 2018/4/17 13:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class WeiXinInfomVo extends BaseVo{
    /**
     * 数据bean
     */
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean {
        private int code;
        /**
         * 平台id
         */
        private String unionid;
        /**
         * 微信标识
         */
        private String openid;
        /**
         * 是否绑定手机号（true 绑定 false 未绑定）
         */
        private boolean isbinduser;
        /**
         * 用户信息
         */
        private UserBean user;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public boolean isIsbinduser() {
            return isbinduser;
        }

        public void setIsbinduser(boolean isbinduser) {
            this.isbinduser = isbinduser;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 0
             * uuid : null
             * phone : null
             * nickname : null
             * headicon : null
             * birthday : null
             * gender : null
             * province : null
             * city : null
             * token : null
             * tokenexpire : 0001-01-01 00:00:00
             */
            /**
             *id
             */
            private int id;
            /**
             * 标识
             */
            private String uuid;
            /**
             * 手机号码
             */
            private String phone;
            /***
             * 昵称
             */
            private Object nickname;
            /**
             * 头像地址
             */
            private String headicon;
            /**
             * 生日
             */
            private Object birthday;
            /**
             * 性别 (1 位男 ，2女)
             */
            private int gender;
            /**
             * 省份
             */
            private String province;
            /**
             * 城市
             */
            private String city;
            /**
             * token
             */
            private String token;
            /**
             * 时效
             */
            private String tokenexpire;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getNickname() {
                return nickname;
            }

            public void setNickname(Object nickname) {
                this.nickname = nickname;
            }

            public String getHeadicon() {
                return headicon;
            }

            public void setHeadicon(String headicon) {
                this.headicon = headicon;
            }

            public Object getBirthday() {
                return birthday;
            }

            public void setBirthday(Object birthday) {
                this.birthday = birthday;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getTokenexpire() {
                return tokenexpire;
            }

            public void setTokenexpire(String tokenexpire) {
                this.tokenexpire = tokenexpire;
            }
        }
    }
}
