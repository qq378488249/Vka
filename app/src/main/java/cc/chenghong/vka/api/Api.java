package cc.chenghong.vka.api;

/**
 * 访问接口类2015 9 24
 *
 * @author hcl
 */
public class Api {
    /**
     * 主页
     */
    public static String MAIN = "https://api.vi-ni.com";// 测试接口
//    public static String MAIN = "https://api.v-ka.com";// 正式接口
    /**
     * 正式
     */
    public static String OFFICIAL = "https://api.v-ka.com";
    /**
     * 测试
     */
    public static String DEBUG = "https://api.vi-ni.com";
    /**
     * 登陆
     */
    public static String LOGIN = MAIN + "/pc/v2/employee/login";
    /**
     * 获取TOKEN
     */
    public static String TOKEN = MAIN + "/pc/v2/getAccessToken";
    /**
     * 微信交易记录查询
     */
    public static String WXPAYRTRANS = MAIN + "/pc/v1/wxPay/trans";
    /**
     * 微信刷卡支付交易退款
     */
    public static String WXPAYRTRANSCANCEL = MAIN + "/pc/v1/wxPay/transCancel";
    /**
     * 微信刷卡支付
     */
    public static String WXPAYSCANPAY = MAIN + "/pc/v1/wxPay/scanPay";

    /**
     * 支付宝交易记录查询
     */
    public static String ALIPAYRTRANS = MAIN + "/pc/v1/aliPay/trans";
    /**
     * 支付宝刷卡支付交易退款
     */
    public static String ALIPAYRTRANSCANCEL = MAIN
            + "/pc/v1/aliPay/transCancel";
    /**
     * 支付宝刷卡支付
     */
    public static String ALIPAYSCANPAY = MAIN + "/pc/v1/aliPay/scanPay";
    /**
     * 会员支付交易退款
     */
    public static String VIPPAYRTRANSCANCEL = MAIN + "/pc/v2/trans/cancel/";
    /**
     * 会员付款码或会员卡号付款
     */
    public static String VIPPAY = MAIN + "/pc/v3/trans/mult_payment";
    /**
     * 获取订单
     */
    public static String GETORDER = MAIN + "/pc/v1/orders/";
    /**
     * 取消外卖订单
     */
    public static String ORDERCANCEL = MAIN + "/pc/v1/orders/cancel/";

    /**
     * 查询会员信息
     */
    public static String FINDVIP = MAIN + "/pc/v4/card/by/";

    /**
     * 查询会员充值套餐
     */
    public static String FINDVIPCOMBO = MAIN + "/pc/v2/amountConfigs/";

    /**
     * 会员充值
     */
    public static String VIPRECHARGE = MAIN + "/pc/v2/trans/recharge";

    /**
     * 修改订单状态
     */
    public static String UPDATAORDERSTATE = MAIN
            + "/pc/v1/orders/updateStatus/";
    /**
     * 核销卡券
     */
    public static String CHECK_TICKET = MAIN + "/pc/v1/cardCoupon/consume/";
    /**
     * 核销卡券记录
     */
    public static String CHECK_TICKET_ROCORD = MAIN
            + "/pc/v1/cardCoupon/consume/";
    /**
     * 根据付款码或会员卡号查询会员信息
     */
    public static String BY_CODE_VIP = MAIN + "/pc/v1/card/bycode/";
    /**
     * 查询微信订单状态
     */
    public static String FIND_ORDER_STATE = MAIN + "/pc/v1/wxPay/get/byQrCode/";
    /**
     * 取消微信订单
     */
    public static String CANCEL_ORDER = MAIN + "/pc/v1/wxPay/reverse/byQrCode/";
    /**
     * 查询支付宝订单状态
     */
    public static String FIND_ALI_ORDER_STATE = MAIN
            + "/pc/v1/aliPay/get/byQrCode/";
    /**
     * 取消支付宝订单
     */
    public static String CANCEL_ALI_ORDER = MAIN
            + "/pc/v1/aliPay/reverse/byQrCode/";
    /**
     * QQ支付
     */
    public static String QQPAYSCANPAY = MAIN + "/pc/v1/qqPay/scanPay";
    /**
     * 查询qq订单状态
     */
    public static String FIND_QQ_STATE = MAIN + "/pc/v1/qqPay/get/byQrCode/";
    /**
     * 取消QQ订单
     */
    public static String CANCEL_QQ_ORDER = MAIN
            + "/pc/v1/qqPay/reverse/byQrCode/";
    /**
     * 核销优惠券
     */
    public static String verifySales = MAIN + "/vipos/v1/cardCoupon/verifySales/";
    /**
     * 新建会员
     */
    public static String newMember = MAIN + "/pc/v2/card";
}
