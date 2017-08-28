package cn.wingene.mallxm.purchase.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Wingene on 2017/8/27.
 */
public class Account {
    /**
     * 游币	不可 使用比例 1:1
     */
    @SerializedName("Amount")
    private Double amount;

    /**
     * 应币	不可 使用比例 100:1
     */
    @SerializedName("Integral")
    private Integer integral;


    /**
     * 游币	不可 使用比例 1:1
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 应币	不可 使用比例 100:1
     */
    public Integer getIntegral() {
        return integral;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
}
