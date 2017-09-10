package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/10.
 */

public class AskFavoritesSet {
    public static class Response extends MyBaseResponse {
        @Override
        protected void initData(JsonElement json) {
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 商品ID	不可
         */
        @SerializedName("ProductId")
        @RequestArgs
        private Integer productId;

        public Request(Integer productId) {
            super(HttpAddress.FAVORITES_SET, new Response());
            this.productId = productId;
        }
    }


}
