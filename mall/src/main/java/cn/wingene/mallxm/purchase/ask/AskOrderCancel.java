package cn.wingene.mallxm.purchase.ask;

import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/3.
 */

public class AskOrderCancel {
    public static class Request extends BaseSignRequest<MyBaseResponse> {
        /**
         * 订单单号	不可
         */
        @SerializedName("No")
        @RequestArgs
        private String no;

        public Request(String no) {
            super(HttpAddress.ORDER_CANCEL, new MyBaseResponse());
            this.no = no;
        }
    }


}
