package cn.wingene.mallxm.purchase.bean;

import com.google.gson.annotations.SerializedName;

import cn.wingene.mallxm.purchase.bean.able.IOrderProductItem;

/**
 * Created by Wingene on 2017/8/27.
 */
public class OrderProductItem implements IOrderProductItem {
    /**
     * 商品ID	不可
     */
    @SerializedName("ProductId")
    private Integer productId;

    /**
     * 商品名称	不可
     */
    @SerializedName("ProductName")
    private String productName;

    /**
     * 图片路径	不可
     */
    @SerializedName("ProductImage")
    private String productImage;

    /**
     * 单价	不可
     */
    @SerializedName("SalePrice")
    private Double salePrice;

    /**
     * 总金额	不可
     */
    @SerializedName("SumPrice")
    private Double sumPrice;

    /**
     * 购买数量	不可
     */
    @SerializedName("BuyNumber")
    private Integer buyNumber;

    /**
     * 规格名称	可空
     */
    @SerializedName("SpecDesp")
    private String specDesp;

//    @Override
//    public Integer getId() {
//        return productId;
//    }
//
//    @Override
//    public String getName() {
//        return productName;
//    }
//
//    @Override
//    public String getDefaultImage() {
//        return productImage;
//    }
//
//    @Override
//    public Double getPrice() {
//        return p;
//    }
//
//    @Override
//    public Double getPriceMarket() {
//        return pro;
//    }
//
//    @Override
//    public Integer getStock() {
//        return null;
//    }
//
//    @Override
//    public Integer getBuyNum() {
//        return null;
//    }
//
//    @Override
//    public Integer getSpecId() {
//        return null;
//    }
//
//    @Override
//    public String getSpecValue() {
//        return null;
//    }
//
//    @Override
//    public Integer getPromotionId() {
//        return null;
//    }

    ////

    /**
     * 商品ID	不可
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 商品名称	不可
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 图片路径	不可
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     * 单价	不可
     */
    public Double getSalePrice() {
        return salePrice;
    }

    /**
     * 总金额	不可
     */
    public Double getSumPrice() {
        return sumPrice;
    }

    /**
     * 购买数量	不可
     */
    public Integer getBuyNumber() {
        return buyNumber;
    }

    /**
     * 规格名称	可空
     */
    public String getSpecDesp() {
        return specDesp;
    }


}
