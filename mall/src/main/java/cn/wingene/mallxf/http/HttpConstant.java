package cn.wingene.mallxf.http;

import cn.wingene.mall.BuildConfig;

/**
 * Created by Wingene on 2017/5/23.
 * <p>
 * 接口地址类
 */

public class HttpConstant {
    //    public static final String SERVER_ADDRESS = ;
    public static final String SERVER_ADDRESS = BuildConfig.DEBUG ? "http://guangheapp.usoft100.com/" :
            "http://api.52lime.cn/";

    public static final String HOST = "http://api.52lime.cn/";//SERVER_ADDRESS;


    /**
     * 首页推荐页
     */
    public static final String HOME_RECOMMEND = HOST + "Home/Index";

    /**
     * 所有商品分类
     */
    public static final String ALL_PRODUCT_CATEGOURY = HOST + "Mall/ProductCategory/List";

    /**
     * 商品分组详情
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

    /**
     * 个人中心
     */
    public static final String USER_INFO = HOST + "User";

    /**
     * 商品分组
     */
    public static final String PRODUCT_GROUP = HOST + "Mall/ProductCategory/ParentCodeList";

    /**
     * 获取个人信息
     */
    public static final String PSERON_INFO = HOST + "User/MyHome";

    /**
     * 编辑个人信息
     */
    public static final String EDITOR_PERSON_INFO = HOST + "User/Home/EditNickname";//"User/EditInfo";

    /**
     * 上传头像
     */
    public static final String UPLOAD_HEAD_IMG = HOST + "User/home/EditAvatar";

    /**
     * 专题详情
     */
    public static final String SPECIAL_DETAIL = HOST + "Article/SpecialDetail";

    /**
     * 周边详情
     */
    public static final String NEARBY_DETAIL = HOST + "Article/AmbitusDetail";

    /**
     * 热门搜索
     */
    public static final String HOT_SEARCH = HOST + "DictionaryItem/HotSearchList";

    /**
     * 版本更新
     */
    public static final String APP_VERSION = HOST + "SysVersion/Android";

    /**
     * 进入提现
     */
    public static final String ENTER_CASH = HOST + "User/Deposit/GetAmount";

    /**
     * 提交提现信息
     */
    public static final String COMMIT_CASH = HOST + "User/Deposit/CreateAmount";

    /**
     * 获取银行卡址列表
     */
    public static final String BANK_CARD_LIST = HOST + "User/BankCard/List";

    /**
     * 进入银行卡（添加/编辑)
     */
    public static final String ADD_CARD = HOST + "User/BankCard/GetEdit";

    /**
     * 提交添加银行卡信息
     */
    public static final String COMMIT_ADD_CARD = HOST + "User/BankCard/Edit";
}
