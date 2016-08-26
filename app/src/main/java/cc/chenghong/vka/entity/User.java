package cc.chenghong.vka.entity;

import cc.chenghong.vka.response.BaseResponse;

/**
 * 用户实体类2015 9 24
 * 
 * @author hcl
 * 
 */
public class User{
	public U data;
	public class U{
		public String accessToken;
		public long expireIn;
	}
	public String storeName;
	public String employeeName;
	public int code;
	//会员卡支付是否需要密码（1需要，0不需要）
	public String passwordPay;
}
