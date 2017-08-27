package cn.wingene.mallxf.http;

/**
 * Created by Wingene on 2017/5/23.
 * <p>
 * 接口地址类
 */

public class HttpConstant {
    public static final String SERVER_ADDRESS = "http://guangheapp.usoft100.com/";

    public static final String HOST = "http://guangheapp.usoft100.com/";


    /**
     * 首页推荐页
     */
    public static final String HOME_RECOMMEND = HOST + "Home/Index";

    /**
     * 所有商品分类
     */
    public static final String ALL_PRODUCT_CATEGOURY = HOST + "Mall/ProductCategory/List";

    /**
     * 商品分页
     */
    public static final String PRODUCT_LIST = HOST + "Mall/Product/List";

    /**
     * 登陆
     */
    public static final String LOGIN = HOST + "Account/LoginPwd";

    /**
     * 注册
     */
    public static final String REGISTER = HOST + "Account/Register";

    /**
     * 忘记密码
     */
    public static final String FORGET_PWD = HOST + "Account/SetNewPassword";

    /**
     * 获取短信验证码
     */
    public static final String REQUEST_CODE = HOST + "Sms/SendSMS";

    /**
     * 专题分类
     */
    public static final String SPECIAL_MENU = HOST + "ArticleCategory/SpecialList";

    /**
     * 专题列表
     */
    public static final String SPECIAL_LIST = HOST + "Article/SpecialList";

    /**
     * 周边菜单
     */
    public static final String NEARBY_MENU = HOST + "ArticleCategory/AmbitusList";

    /**
     * 周边内容列表
     */
    public static final String NEARBY_LIST = HOST + "Article/AmbitusList";
}
