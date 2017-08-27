package cn.wingene.mallxm.purchase.bean.able;

/**
 * Created by Wingene on 2017/8/27.
 */
public interface IOrderProductItem {
        /**
         * 商品ID	不可
         */
        Integer getProductId();

        /**
         * 商品名称	不可
         */
        String getProductName();

        /**
         * 图片路径	不可
         */
        String getProductImage();

        /**
         * 单价	不可
         */
        Double getSalePrice();

        /**
         * 总金额	不可
         */
        Double getSumPrice();

        /**
         * 购买数量	不可
         */
        Integer getBuyNumber();

        /**
         * 规格名称	可空
         */
        String getSpecDesp();
}
