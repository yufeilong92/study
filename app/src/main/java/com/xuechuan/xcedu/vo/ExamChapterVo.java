package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/4 14:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ExamChapterVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * 历年真题
         */
        private List<ExamBeanVo> exam;
        /**
         * 独家密卷
         */
        private List<ExamBeanVo> secretexam;

        public List<ExamBeanVo> getExam() {
            return exam;
        }

        public void setExam(List<ExamBeanVo> exam) {
            this.exam = exam;
        }

        public List<ExamBeanVo> getSecretexam() {
            return secretexam;
        }

        public void setSecretexam(List<ExamBeanVo> secretexam) {
            this.secretexam = secretexam;
        }
/*
        public static class ExamBean {
            *//**
             * id : 200
             * title : 2017技术实务
             *//*

            private int id;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class SecretexamBean {
            *//**
             * id : 203
             * title : 技术实务独家密卷1
             *//*

            private int id;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }*/
    }
}
