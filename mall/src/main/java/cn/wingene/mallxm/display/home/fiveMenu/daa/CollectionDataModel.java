package cn.wingene.mallxm.display.home.fiveMenu.daa;

import java.util.List;

/**
 * Created by wangcq on 2017/9/10.
 */

public class CollectionDataModel {

    /**
     * err : 0
     * msg : 我的收藏列表成功
     * data : {"pageCount":1,"recordCount":1,"list":[{"Id":2,"ProductId":1,"ProductName":"法国 兰蔻（Lancome） 精华肌底眼部凝霜
     * 15ml ","ProductImage":"http://120.25.105.30:8080/upload/image/20170712/s_1499827806679674.jpg",
     * "ProductPrice":240}]}
     */

    private int err;
    private String msg;
    /**
     * pageCount : 1
     * recordCount : 1
     * list : [{"Id":2,"ProductId":1,"ProductName":"法国 兰蔻（Lancome） 精华肌底眼部凝霜 15ml ",
     * "ProductImage":"http://120.25.105.30:8080/upload/image/20170712/s_1499827806679674.jpg","ProductPrice":240}]
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
        private int PageCount;
        private int RecordCount;
        private int PageSize;

        public int getPageSize() {
            return PageSize;
        }

        public void setPageSize(int pageSize) {
            PageSize = pageSize;
        }

        /**
         * Id : 2
         * ProductId : 1
         * ProductName : 法国 兰蔻（Lancome） 精华肌底眼部凝霜 15ml
         * ProductImage : http://120.25.105.30:8080/upload/image/20170712/s_1499827806679674.jpg
         * ProductPrice : 240
         */

        private List<ListBean> List;

        public int getPageCount() {
            return PageCount;
        }

        public void setPageCount(int pageCount) {
            this.PageCount = pageCount;
        }

        public int getRecordCount() {
            return RecordCount;
        }

        public void setRecordCount(int recordCount) {
            this.RecordCount = recordCount;
        }

        public List<ListBean> getList() {
            return List;
        }

        public void setList(List<ListBean> list) {
            this.List = list;
        }

        public static class ListBean {
            private int Id;
            private int ProductId;
            private String ProductName;
            private String ProductImage;
            private String ProductPrice;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getProductId() {
                return ProductId;
            }

            public void setProductId(int ProductId) {
                this.ProductId = ProductId;
            }

            public String getProductName() {
                return ProductName;
            }

            public void setProductName(String ProductName) {
                this.ProductName = ProductName;
            }

            public String getProductImage() {
                return ProductImage;
            }

            public void setProductImage(String ProductImage) {
                this.ProductImage = ProductImage;
            }

            public String getProductPrice() {
                return ProductPrice;
            }

            public void setProductPrice(String ProductPrice) {
                this.ProductPrice = ProductPrice;
            }
        }
    }
}
