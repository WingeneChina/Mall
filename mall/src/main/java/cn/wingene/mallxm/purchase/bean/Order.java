package cn.wingene.mallxm.purchase.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import cn.wingene.mallxm.purchase.bean.able.IEasyOrder;
import cn.wingene.mallxm.purchase.bean.able.IOrder;
import cn.wingene.mallxm.purchase.bean.able.IOrderProductItem;

/**
 * Created by Wingene on 2017/8/27.
 */
public class Order implements IOrder, IEasyOrder {
    /**
     * 订单单号	不可
     */
    @SerializedName("No")
    private String no;

    /**
     * 总金额	不可
     */
    @SerializedName("SumPrice")
    private Double sumPrice;

    /**
     * 总数量	不可
     */
    @SerializedName("SumNumber")
    private Double sumNumber;

    /**
     * 折扣率	不可
     */
    @SerializedName("Discount")
    private Double discount;

    /**
     * 折扣金额	不可
     */
    @SerializedName("DiscountPrice")
    private Double discountPrice;

    /**
     * 运费	不可
     */
    @SerializedName("DeliveryFee")
    private Double deliveryFee;

    /**
     * 支付金额	不可
     */
    @SerializedName("PayPrice")
    private Double payPrice;

    /**
     * 可使用券金额	不可
     */
    @SerializedName("AcceptCouponPrice")
    private Double acceptCouponPrice;

    /**
     * 订单状态	不可
     */
    @SerializedName("State")
    private Integer state;

    /**
     * 状态说明	不可 详见1.6订单状态
     */
    @SerializedName("StateDesp")
    private String stateDesp;

    /**
     * 商品信息列表	不可
     */
    @SerializedName("ProductList")
    private List<OrderProductItem> productList;

    /**
     * 会员账户	不可
     */
    @SerializedName("Account")
    private Account account;

    /**
     * 收货地址	不可
     */
    @SerializedName("Address")
    private Address4 address;

    /**
     * 可使用应币支付
     */
    @SerializedName("AcceptIntegral")
    private Integer acceptIntegral;


    /**
     * 订单单号	不可
     */
    @Override
    public String getNo() {
        return no;
    }

    /**
     * 总金额	不可
     */
    @Override
    public Double getSumPrice() {
        return sumPrice;
    }

    /**
     * 总数量	不可
     */
    @Override
    public Double getSumNumber() {
        return sumNumber;
    }

    /**
     * 折扣率	不可
     */
    @Override
    public Double getDiscount() {
        return discount;
    }

    /**
     * 折扣金额	不可
     */
    @Override
    public Double getDiscountPrice() {
        return discountPrice;
    }

    /**
     * 运费	不可
     */
    @Override
    public Double getDeliveryFee() {
        return deliveryFee;
    }

    /**
     * 支付金额	不可
     */
    @Override
    public Double getPayPrice() {
        return payPrice;
    }

    /**
     * 可使用券金额	不可
     */
    @Override
    public Double getAcceptCouponPrice() {
        return acceptCouponPrice;
    }

    /**
     * 订单状态	不可
     */
    @Override
    public Integer getState() {
        return state;
    }

    /**
     * 状态说明	不可 详见1.6订单状态
     */
    @Override
    public String getStateDesp() {
        return stateDesp;
    }

    /**
     * 商品信息列表	不可
     */
    @Override
    public List<OrderProductItem> getProductList() {
        return productList;
    }

    /**
     * 会员账户	不可
     */
    @Override
    public Account getAccount() {
        return account;
    }

    /**
     * 收货地址	不可
     */
    @Override
    public Address4 getAddress() {
        return address;
    }

    @Override
    public Integer getAcceptIntegral() {
        return acceptIntegral != null ? acceptIntegral : 0;
    }

    @Override
    public List<? extends IOrderProductItem> getOrderProductItem() {
        return productList;
    }

}
