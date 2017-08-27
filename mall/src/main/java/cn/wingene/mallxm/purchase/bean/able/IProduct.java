package cn.wingene.mallxm.purchase.bean.able;

/**
 * Created by Wingene on 2017/8/26.
 */
public interface IProduct {
    /**
     * 商品ID	不可
     */
    Integer getId();

    /**
     * 商品名称	不可
     */
    String getName();

    /**
     * 图片路径	不可
     */
    String getDefaultImage();

    /**
     * 销售价格	不可
     */
    Double getPrice();

    /**
     * 市场价格	不可
     */
    Double getPriceMarket();

    /**
     * 库存数量	不可
     */
    Integer getStock();

    /**
     * 购买数量	不可
     */
    Integer getBuyNum();

    /**
     * 规格ID	不可
     */
    Integer getSpecId();

    /**
     * 规格名称	可空
     */
    String getSpecValue();

    /**
     * 促销活动ID	可空
     */
    Integer getPromotionId();
}
