package cc.chenghong.vka.entity;



public class CardPayPost {
	public String code;
	public String parentCode; // 头部参数 总店编码
	public String accessToken; // 头部参数 令牌
	public String password; // 必填 会员卡密码
	public String amount; // 必填 会员卡实际付款金额，如：0.00
	public String tickets; // 有就填 格式：123,456,789优惠券ids
	public String vkaAmount; // 必填 会员卡实际付款金额，如：0.00
	public String total;// 必填 会员卡实际付款金额，如：0.00
	public String ticketDiscount; // 有就填 优惠券折扣详情，格式：★优惠券ID☆折扣金额☆优惠券名称★优惠券ID☆折扣金额☆优惠券名称★优惠券ID☆折扣金额☆优惠券名称
	public String discount; // 有就填 优惠券折扣金额，如：0.00
	public String levelDiscount; // 有就填 会员等级折扣金额，如：0.00
	public String itemSubtotal; // 必填 应付总金额，如：0.00
	public String mobile;
}
