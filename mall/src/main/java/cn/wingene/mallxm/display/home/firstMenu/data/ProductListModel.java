package cn.wingene.mallxm.display.home.firstMenu.data;

import java.util.List;

/**
 * Created by wangcq on 2017/8/27.
 * 商品分页数据model
 */

public class ProductListModel {

    /**
     * err : 0
     * msg : 获取商品列表成功
     * data : {"RecordCount":1,"PageSize":20,"PageCount":1,"List":[{"Id":1,"Name":"战地吉普 短袖T恤男 2017夏季 休闲装韩版 立领男装",
     * "DefaultImage":"http://img.usoft100.com/upload/image/20170815/s_1502766155031239_300X300.jpg","Price":1,
     * "OldPrice":1,"Stock":199,"SaleCount":1,"IsRecommend":0,"IsNewly":1,"IsReducePrice":1,"Tag":"爆品,满赠",
     * "SellingPoint":"好吃"}]}
     */

    private int err;
    private String msg;
    /**
     * RecordCount : 1
     * PageSize : 20
     * PageCount : 1
     * List : [{"Id":1,"Name":"战地吉普 短袖T恤男 2017夏季 休闲装韩版 立领男装","DefaultImage":"http://img
     * .usoft100.com/upload/image/20170815/s_1502766155031239_300X300.jpg","Price":1,"OldPrice":1,"Stock":199,
     * "SaleCount":1,"IsRecommend":0,"IsNewly":1,"IsReducePrice":1,"Tag":"爆品,满赠","SellingPoint":"好吃"}]
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
         * Name : 战地吉普 短袖T恤男 2017夏季 休闲装韩版 立领男装
         * DefaultImage : http://img.usoft100.com/upload/image/20170815/s_1502766155031239_300X300.jpg
         * Price : 1
         * OldPrice : 1
         * Stock : 199
         * SaleCount : 1
         * IsRecommend : 0
         * IsNewly : 1
         * IsReducePrice : 1
         * Tag : 爆品,满赠
         * SellingPoint : 好吃
         */
        private List<RecommendModel.DataBean.BannerListBean> BannerList;

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

        public List<RecommendModel.DataBean.BannerListBean> getBannerList() {
            return BannerList;
        }

        public void setBannerList(List<RecommendModel.DataBean.BannerListBean> BannerList) {
            this.BannerList = BannerList;
        }

        public static class ListBean {
            private int Id;
            private String Name;
            private String DefaultImage;
            private double Price;
            private double OldPrice;
            private int Stock;
            private int SaleCount;
            private int IsRecommend;
            private int IsNewly;
            private int IsReducePrice;
            private String Tag;
            private String SellingPoint;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getDefaultImage() {
                return DefaultImage;
            }

            public void setDefaultImage(String DefaultImage) {
                this.DefaultImage = DefaultImage;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public double getOldPrice() {
                return OldPrice;
            }

            public void setOldPrice(double OldPrice) {
                this.OldPrice = OldPrice;
            }

            public int getStock() {
                return Stock;
            }

            public void setStock(int Stock) {
                this.Stock = Stock;
            }

            public int getSaleCount() {
                return SaleCount;
            }

            public void setSaleCount(int SaleCount) {
                this.SaleCount = SaleCount;
            }

            public int getIsRecommend() {
                return IsRecommend;
            }

            public void setIsRecommend(int IsRecommend) {
                this.IsRecommend = IsRecommend;
            }

            public int getIsNewly() {
                return IsNewly;
            }

            public void setIsNewly(int IsNewly) {
                this.IsNewly = IsNewly;
            }

            public int getIsReducePrice() {
                return IsReducePrice;
            }

            public void setIsReducePrice(int IsReducePrice) {
                this.IsReducePrice = IsReducePrice;
            }

            public String getTag() {
                return Tag != null ? Tag : "";
            }

            public void setTag(String Tag) {
                this.Tag = Tag;
            }

            public String getSellingPoint() {
                return SellingPoint;
            }

            public void setSellingPoint(String SellingPoint) {
                this.SellingPoint = SellingPoint;
            }
        }
    }
}
