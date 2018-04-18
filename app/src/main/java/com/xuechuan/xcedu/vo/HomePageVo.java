package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/18 16:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomePageVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
    public static class DataBean {
        private List<AdvisoryBean> advisory;
        private List<ArticleBean> article;
        private List<BannerBean> banner;

        public List<AdvisoryBean> getAdvisory() {
            return advisory;
        }

        public void setAdvisory(List<AdvisoryBean> advisory) {
            this.advisory = advisory;
        }

        public List<ArticleBean> getArticle() {
            return article;
        }

        public void setArticle(List<ArticleBean> article) {
            this.article = article;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public static class AdvisoryBean {
            private String gourl;
            private int id;
            private String provincecode;
            private String publishdate;
            private String source;
            private String thumbnailimg;
            private String title;
            private int type;
            private int viewcount;

            public String getGourl() {
                return gourl;
            }

            public void setGourl(String gourl) {
                this.gourl = gourl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProvincecode() {
                return provincecode;
            }

            public void setProvincecode(String provincecode) {
                this.provincecode = provincecode;
            }

            public String getPublishdate() {
                return publishdate;
            }

            public void setPublishdate(String publishdate) {
                this.publishdate = publishdate;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getThumbnailimg() {
                return thumbnailimg;
            }

            public void setThumbnailimg(String thumbnailimg) {
                this.thumbnailimg = thumbnailimg;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getViewcount() {
                return viewcount;
            }

            public void setViewcount(int viewcount) {
                this.viewcount = viewcount;
            }
        }

        public static class ArticleBean {
            private String gourl;
            private int id;
            private boolean issupport;
            private String publishdate;
            private int supportcount;
            private String thumbnailimg;
            private String title;
            private int type;
            private int viewcount;

            public String getGourl() {
                return gourl;
            }

            public void setGourl(String gourl) {
                this.gourl = gourl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isIssupport() {
                return issupport;
            }

            public void setIssupport(boolean issupport) {
                this.issupport = issupport;
            }

            public String getPublishdate() {
                return publishdate;
            }

            public void setPublishdate(String publishdate) {
                this.publishdate = publishdate;
            }

            public int getSupportcount() {
                return supportcount;
            }

            public void setSupportcount(int supportcount) {
                this.supportcount = supportcount;
            }

            public String getThumbnailimg() {
                return thumbnailimg;
            }

            public void setThumbnailimg(String thumbnailimg) {
                this.thumbnailimg = thumbnailimg;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getViewcount() {
                return viewcount;
            }

            public void setViewcount(int viewcount) {
                this.viewcount = viewcount;
            }
        }

        public static class BannerBean {
            private String gourl;
            private int id;
            private String imageurl;
            private int sort;
            private int type;

            public String getGourl() {
                return gourl;
            }

            public void setGourl(String gourl) {
                this.gourl = gourl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImageurl() {
                return imageurl;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
