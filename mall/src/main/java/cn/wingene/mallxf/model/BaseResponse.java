package cn.wingene.mallxf.model;

/**
 * Created by wangcq on 2017/8/20.
 * 基本返回参数,其它各接口参数继承这个
 *
 */

public class BaseResponse {
    /**
     * 0正确
     *1错误
     *-1 重新登录
     2、微信授权OpenId
     */
    public int err;

    /**
     * 提示信息
     */
    public String msg;

    /**
     * 位置参数
     */
    public String act;
    /**
     * 请求数据
     */
    public String data;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "err=" + err +
                ", msg='" + msg + '\'' +
                ", act='" + act + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
