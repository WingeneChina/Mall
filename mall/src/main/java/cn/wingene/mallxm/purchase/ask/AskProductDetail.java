package cn.wingene.mallxm.purchase.ask;

import java.util.List;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

/**
 * Created by Wingene on 2017/8/19.
 */

public class AskProductDetail {
    public static class Response extends MyBaseResponse {
        Data result;
        @Override
        protected void initData(JsonElement value) {
            result = AKit.getGson().fromJson(value,Data.class);
        }

        public Product getProduct() {
            return result.getProduct();
        }

        public List<ProductImageList> getProductImageList() {
            return result.getProductImageList();
        }

        public List<ProductSpecList> getProductSpecList() {
            return result.getProductSpecList();
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 促销活动ID	可空
         */
        @RequestArgs
        @SerializedName("PromotionId")
        private Integer promotionId;

        /**
         * 商品ID	不可
         */
        @RequestArgs
        @SerializedName("Id")
        private Integer id;

        public Request(Integer id, Integer promotionId) {
            super(HttpAddress.PRODUCT_DETAIL, new Response());
            this.id = id;
            this.promotionId = promotionId;
        }

    }

    public static class Data {
        /**
         * 商品信息	不可
         */
        private Product Product;

        /**
         * 商品图片列表	不可
         */
        private List<ProductImageList> ProductImageList;

        /**
         * 商品规格列表	不可
         */
        private List<ProductSpecList> ProductSpecList;


        /**
         * 商品信息	不可
         */
        public Product getProduct() {
            return Product;
        }

        /**
         * 商品图片列表	不可
         */
        public List<ProductImageList> getProductImageList() {
            return ProductImageList;
        }

        /**
         * 商品规格列表	不可
         */
        public List<ProductSpecList> getProductSpecList() {
            return ProductSpecList;
        }


    }

    public static class Product {
        /**
         * 商品ID	不可
         */
        @SerializedName("Id")
        private Integer id;

        /**
         * 商品名称	不可
         */
        @SerializedName("Name")
        private String name;

        /**
         * 图片路径	不可
         */
        @SerializedName("DefaultImage")
        private String defaultImage;

        /**
         * 销售价格	不可
         */
        @SerializedName("Price")
        private Double price;

        /**
         * 原价	不可
         */
        @SerializedName("OldPrice")
        private Double oldPrice;

        /**
         * 库存数量	不可
         */
        @SerializedName("Stock")
        private Integer stock;

        /**
         * 销售数量	不可
         */
        @SerializedName("SaleCount")
        private Integer saleCount;

        /**
         * 满几件包邮 	不可 0：表示不免运费
         */
        @SerializedName("IsFreePost")
        private Integer isFreePost;

        /**
         * 邮费费用	不可
         */
        @SerializedName("DeliveryFee")
        private Double deliveryFee;

        /**
         * 商品详情	不可
         */
        @SerializedName("Detail")
        private String detail;

        /**
         * 商品详情路径	不可
         */
        @SerializedName("DetailUrl")
        private String detailUrl;

        /**
         * 商品卖点	可空
         */
        @SerializedName("SellingPoint")
        private String sellingPoint;

        /**
         * 卖点展位图片路径	可空
         */
        @SerializedName("SellingImage")
        private String sellingImage;

        /**
         * 品牌	可空
         */
        @SerializedName("BrandName")
        private String brandName;

        /**
         * 商品标签	可空 如：爆品,满赠 逗号隔开
         */
        @SerializedName("Tag")
        private String tag;

        /**
         * 规格分类1 名称	可空
         */
        @SerializedName("SpecDesp1")
        private String specDesp1;

        /**
         * 规格分类2 名称	可空
         */
        @SerializedName("SpecDesp2")
        private String specDesp2;

        /**
         * 规格分类数量	不可
         */
        @SerializedName("SpecQuantity")
        private Integer specQuantity;

        /**
         * 是否促销活动	不可
         */
        @SerializedName("IsPromotion")
        private Boolean isPromotion;

        /**
         * 促销类型	不可
         */
        @SerializedName("PromotionType")
        private Integer promotionType;

        /**
         * 促销类型说明	不可
         */
        @SerializedName("PromotionTypeDesp")
        private String promotionTypeDesp;

        /**
         * 促销活动ID	不可
         */
        @SerializedName("PromotionId")
        private Integer promotionId;

        /**
         * 促销开始时间	不可
         */
        @SerializedName("PromotionBeginTime")
        private Integer promotionBeginTime;

        /**
         * 促销结束时间	不可
         */
        @SerializedName("PromotionEndTime")
        private Integer promotionEndTime;

        /**
         * 每人限购数量	不可 0、表示不限购
         */
        @SerializedName("LimitEachNum")
        private Integer limitEachNum;


        /**
         * 商品ID	不可
         */
        public Integer getId() {
            return id;
        }

        /**
         * 商品名称	不可
         */
        public String getName() {
            return name;
        }

        /**
         * 图片路径	不可
         */
        public String getDefaultImage() {
            return defaultImage;
        }

        /**
         * 销售价格	不可
         */
        public Double getPrice() {
            return price;
        }

        /**
         * 原价	不可
         */
        public Double getOldPrice() {
            return oldPrice;
        }

        /**
         * 库存数量	不可
         */
        public Integer getStock() {
            return stock;
        }

        /**
         * 销售数量	不可
         */
        public Integer getSaleCount() {
            return saleCount;
        }

        /**
         * 满几件包邮 	不可 0：表示不免运费
         */
        public Integer getIsFreePost() {
            return isFreePost;
        }

        /**
         * 邮费费用	不可
         */
        public Double getDeliveryFee() {
            return deliveryFee;
        }

        /**
         * 商品详情	不可
         */
        public String getDetail() {
            return detail;
        }

        /**
         * 商品详情路径	不可
         */
        public String getDetailUrl() {
            return detailUrl;
        }

        /**
         * 商品卖点	可空
         */
        public String getSellingPoint() {
            return sellingPoint;
        }

        /**
         * 卖点展位图片路径	可空
         */
        public String getSellingImage() {
            return sellingImage;
        }

        /**
         * 品牌	可空
         */
        public String getBrandName() {
            return brandName;
        }

        /**
         * 商品标签	可空 如：爆品,满赠 逗号隔开
         */
        public String getTag() {
            return tag;
        }

        /**
         * 规格分类1 名称	可空
         */
        public String getSpecDesp1() {
            return specDesp1;
        }

        /**
         * 规格分类2 名称	可空
         */
        public String getSpecDesp2() {
            return specDesp2;
        }

        /**
         * 规格分类数量	不可
         */
        public Integer getSpecQuantity() {
            return specQuantity;
        }

        /**
         * 是否促销活动	不可
         */
        public Boolean getIsPromotion() {
            return isPromotion;
        }

        /**
         * 促销类型	不可
         */
        public Integer getPromotionType() {
            return promotionType;
        }

        /**
         * 促销类型说明	不可
         */
        public String getPromotionTypeDesp() {
            return promotionTypeDesp;
        }

        /**
         * 促销活动ID	不可
         */
        public Integer getPromotionId() {
            return promotionId;
        }

        /**
         * 促销开始时间	不可
         */
        public Integer getPromotionBeginTime() {
            return promotionBeginTime;
        }

        /**
         * 促销结束时间	不可
         */
        public Integer getPromotionEndTime() {
            return promotionEndTime;
        }

        /**
         * 每人限购数量	不可 0、表示不限购
         */
        public Integer getLimitEachNum() {
            return limitEachNum;
        }


    }

    public static class ProductImageList {
        /**
         * 商品图片ID	不可
         */
        @SerializedName("Id")
        private Integer id;

        /**
         * 图片	不可
         */
        @SerializedName("Src")
        private String src;

        /**
         * 缩略图	不可
         */
        @SerializedName("ThumbSrc")
        private String thumbSrc;


        /**
         * 商品图片ID	不可
         */
        public Integer getId() {
            return id;
        }

        /**
         * 图片	不可
         */
        public String getSrc() {
            return src;
        }

        /**
         * 缩略图	不可
         */
        public String getThumbSrc() {
            return thumbSrc;
        }


    }

    public static class ProductSpecList {
        /**
         * 商品图片ID	不可
         */
        @SerializedName("Id")
        private Integer id;

        /**
         * 规格1 ID 	不可
         */
        @SerializedName("Spec1ValueId")
        private Integer spec1ValueId;

        /**
         * 规格1 	可空
         */
        @SerializedName("Spec1Value")
        private String spec1Value;

        /**
         * 规格2 ID 	不可
         */
        @SerializedName("Spec2ValueId")
        private Integer spec2ValueId;

        /**
         * 规格2 	可空
         */
        @SerializedName("Spec2Value")
        private String spec2Value;

        /**
         * 库存	不可
         */
        @SerializedName("Stock")
        private Integer stock;

        /**
         * 销售价格	不可
         */
        @SerializedName("Price")
        private Double price;

        /**
         * 市场价	不可
         */
        @SerializedName("PriceMarket")
        private Double priceMarket;


        /**
         * 商品图片ID	不可
         */
        public Integer getId() {
            return id;
        }

        /**
         * 规格1 ID 	不可
         */
        public Integer getSpec1ValueId() {
            return spec1ValueId;
        }

        /**
         * 规格1 	可空
         */
        public String getSpec1Value() {
            return spec1Value;
        }

        /**
         * 规格2 ID 	不可
         */
        public Integer getSpec2ValueId() {
            return spec2ValueId;
        }

        /**
         * 规格2 	可空
         */
        public String getSpec2Value() {
            return spec2Value;
        }

        /**
         * 库存	不可
         */
        public Integer getStock() {
            return stock;
        }

        /**
         * 销售价格	不可
         */
        public Double getPrice() {
            return price;
        }

        /**
         * 市场价	不可
         */
        public Double getPriceMarket() {
            return priceMarket;
        }

        @Override
        public String toString() {
            return "ProductSpecList{" +
                    "id=" + id +
                    ", spec1ValueId=" + spec1ValueId +
                    ", spec1Value='" + spec1Value + '\'' +
                    ", spec2ValueId=" + spec2ValueId +
                    ", spec2Value='" + spec2Value + '\'' +
                    ", stock=" + stock +
                    ", price=" + price +
                    ", priceMarket=" + priceMarket +
                    '}';
        }
    }
}
