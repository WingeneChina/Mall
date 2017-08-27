package cn.wingene.mallxm.display.home.secondMenu.data;

import java.util.List;

/**
 * Created by wangcq on 2017/8/27.
 * 菜单页面内容模型
 */

public class MenuItemContentModel {
    /**
     * err : 0
     * msg : 获取专题列表成功
     * data : {"RecordCount":1,"PageSize":20,"PageCount":1,"List":[{"Id":1,"Title":"上海迪士尼乐园","From":"迪士尼乐园",
     * "Click":0,"CreateTime":"2017-08-18","ImageList":[{"Src":"http://img
     * .usoft100.com/upload/image/20170818/b_1503048953021755_300X300.jpg","ThumbSrc":"http://img
     * .usoft100.com/upload/image/20170818/s_1503048953021755_300X300.jpg"}]}]}
     */

    private int err;
    private String msg;
    /**
     * RecordCount : 1
     * PageSize : 20
     * PageCount : 1
     * List : [{"Id":1,"Title":"上海迪士尼乐园","From":"迪士尼乐园","Click":0,"CreateTime":"2017-08-18",
     * "ImageList":[{"Src":"http://img.usoft100.com/upload/image/20170818/b_1503048953021755_300X300.jpg",
     * "ThumbSrc":"http://img.usoft100.com/upload/image/20170818/s_1503048953021755_300X300.jpg"}]}]
     */

    private DataBean data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int RecordCount;
        private int PageSize;
        private int PageCount;
        /**
         * Id : 1
         * Title : 上海迪士尼乐园
         * From : 迪士尼乐园
         * Click : 0
         * CreateTime : 2017-08-18
         * ImageList : [{"Src":"http://img.usoft100.com/upload/image/20170818/b_1503048953021755_300X300.jpg",
         * "ThumbSrc":"http://img.usoft100.com/upload/image/20170818/s_1503048953021755_300X300.jpg"}]
         */

        private java.util.List<ListBean> List;

        public int getRecordCount() {
            return RecordCount;
        }

        public void setRecordCount(int RecordCount) {
            this.RecordCount = RecordCount;
        }

        public int getPageSize() {
            return PageSize;
        }

        public void setPageSize(int PageSize) {
            this.PageSize = PageSize;
        }

        public int getPageCount() {
            return PageCount;
        }

        public void setPageCount(int PageCount) {
            this.PageCount = PageCount;
        }

        public List<ListBean> getList() {
            return List;
        }

        public void setList(List<ListBean> List) {
            this.List = List;
        }

        public static class ListBean {
            private int Id;
            private String Title;
            private String From;
            private int Click;
            private String CreateTime;
            /**
             * Src : http://img.usoft100.com/upload/image/20170818/b_1503048953021755_300X300.jpg
             * ThumbSrc : http://img.usoft100.com/upload/image/20170818/s_1503048953021755_300X300.jpg
             */

            private java.util.List<ImageListBean> ImageList;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getFrom() {
                return From;
            }

            public void setFrom(String From) {
                this.From = From;
            }

            public int getClick() {
                return Click;
            }

            public void setClick(int Click) {
                this.Click = Click;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public List<ImageListBean> getImageList() {
                return ImageList;
            }

            public void setImageList(List<ImageListBean> ImageList) {
                this.ImageList = ImageList;
            }

            public static class ImageListBean {
                private String Src;
                private String ThumbSrc;

                public String getSrc() {
                    return Src;
                }

                public void setSrc(String Src) {
                    this.Src = Src;
                }

                public String getThumbSrc() {
                    return ThumbSrc;
                }

                public void setThumbSrc(String ThumbSrc) {
                    this.ThumbSrc = ThumbSrc;
                }
            }
        }
    }
}
