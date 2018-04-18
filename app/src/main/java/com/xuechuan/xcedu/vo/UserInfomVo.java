package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 用户信息
 * @author: L-BackPacker
 * @date: 2018/4/17 14:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UserInfomVo extends BaseVo {
    /**
     * 用户数据
     */
    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
    public static class DataBean {

        /**
         *是否绑定手机号注册
         */

        private String info;
        /**
         *
         */
        private int status;
        private UserBean user;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * 生日
             */
            private String birthday;
            /**
             * 城市
             */
            private String city;
            /**
             * 性别（1男,2女）
             */
            private int gender;
            /**
             * 头像
             */
            private String headicon;
            /**
             * id
             */
            private int id;
            /**
             * 昵称
             */
            private String nickname;
            /**
             * 手机号
             */
            private String phone;
            /**
             * 省份
             */
            private String province;
            /**
             * token
             */
            private String token;
            /**
             * token 时效
             */
            private String tokenexpire;
            /**
             * 用户标识
             */
            private String uuid;

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getHeadicon() {
                return headicon;
            }

            public void setHeadicon(String headicon) {
                this.headicon = headicon;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
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

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }
    }
}
