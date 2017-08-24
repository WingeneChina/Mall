package cn.wingene.mallxm.purchase.ask;

import java.lang.reflect.Type;

import cn.wingene.mallxf.http.Ask.MyBaseRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;
import cn.wingene.mallxf.http.Ask.Result;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import junze.androidxf.http.requestargs.RequestArgs;

/**
 * Created by Wingene on 2017/8/24.
 */

public class AskCartAdd {

    public static class Response extends MyBaseResponse<Object> {

        @Override
        public Type getTypeOfResult() {
            return new TypeToken<Result<Object>>(){}.getType();
        }
    }

    public static class Request extends MyBaseRequest<Response> {
        /**
         * 商品ID	不可
         */
        @SerializedName("ProductId")
        @RequestArgs
        private Integer productId;

        /**
         * 购买数量	不可
         */
        @SerializedName("ProductNumber")
        @RequestArgs
        private Integer productNumber;

        /**
         * 商品规格ID	不可
         */
        @SerializedName("ProductSpecId")
        @RequestArgs
        private Integer productSpecId;

        public Request(Integer productId, Integer productNumber, Integer productSpecId) {
            super(HttpAddress.CART_ADD, new Response());
            this.productId = productId;
            this.productNumber = productNumber;
            this.productSpecId = productSpecId;
        }
    }
}
