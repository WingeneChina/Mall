package cn.wingene.mallxm.purchase.bean;

import com.google.gson.annotations.SerializedName;

import cn.wingene.mallxm.purchase.bean.able.IProduct;

/**
 * Created by Wingene on 2017/8/27.
 */
public class Product implements IProduct {
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
     * 市场价格	不可
     */
    @SerializedName("PriceMarket")
    private Double priceMarket;

    /**
     * 库存数量	不可
     */
    @SerializedName("Stock")
    private Integer stock;

    /**
     * 购买数量	不可
     */
    @SerializedName("BuyNum")
    private Integer buyNum;

    /**
     * 规格ID	不可
     */
    @SerializedName("SpecId")
    private Integer specId;

    /**
     * 规格名称	可空
     */
    @SerializedName("SpecValue")
    private String specValue;

    /**
     * 促销活动ID	可空
     */
    @SerializedName("PromotionId")
    private Integer promotionId;


    /**
     * 商品ID	不可
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * 商品名称	不可
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 图片路径	不可
     */
    @Override
    public String getDefaultImage() {
        return defaultImage;
    }

    /**
     * 销售价格	不可
     */
    @Override
    public Double getPrice() {
        return price;
    }

    /**
     * 市场价格	不可
     */
    @Override
    public Double getPriceMarket() {
        return priceMarket;
    }

    /**
     * 库存数量	不可
     */
    @Override
    public Integer getStock() {
        return stock;
    }

    /**
     * 购买数量	不可
     */
    @Override
    public Integer getBuyNum() {
        return buyNum;
    }

    /**
     * 规格ID	不可
     */
    @Override
    public Integer getSpecId() {
        return specId;
    }

    /**
     * 规格名称	可空
     */
    @Override
    public String getSpecValue() {
        return specValue;
    }

    /**
     * 促销活动ID	可空
     */
    @Override
    public Integer getPromotionId() {
        return promotionId;
    }


}
