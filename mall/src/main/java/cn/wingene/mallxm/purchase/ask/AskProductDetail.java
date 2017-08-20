package cn.wingene.mallxm.purchase.ask;

import cn.wingene.mallxf.http.Ask.MyBaseRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/8/19.
 */

public class AskProductDetail {
    public static class Response extends MyBaseResponse{

        @Override
        protected void initData(String data) {

        }
    }


    public static class Request extends MyBaseRequest<Response>{

        public Request(String mUrl, Response mResponse) {
            super(mUrl, mResponse);
        }
    }
}
