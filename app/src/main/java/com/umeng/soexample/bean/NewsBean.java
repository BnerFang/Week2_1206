package com.umeng.soexample.bean;

import java.util.List;



/**
 * 新闻数据bean
 * */
public class NewsBean {
    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public class ResultBean{

        private int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        private List<DataList> data;

        public List<DataList> getData() {
            return data;
        }

        public void setData(List<DataList> data) {
            this.data = data;
        }

        public class DataList{
            private String title;
            private String intor;

            private String summary;

            private String ctime;

            private String media_name;

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getMedia_name() {
                return media_name;
            }

            public void setMedia_name(String media_name) {
                this.media_name = media_name;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            private List<ImageBean> images;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIntor() {
                return intor;
            }

            public void setIntor(String intor) {
                this.intor = intor;
            }

            public List<ImageBean> getImages() {
                return images;
            }

            public void setImages(List<ImageBean> images) {
                this.images = images;
            }

            public class ImageBean{
                private String u;

                public String getU() {
                    return u;
                }

                public void setU(String u) {
                    this.u = u;
                }
            }
        }

    }
}

