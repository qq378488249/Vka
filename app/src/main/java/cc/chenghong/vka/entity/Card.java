package cc.chenghong.vka.entity;

import java.io.Serializable;


/**
 * 会员卡详细
 * @author guozhiwei 2014-12-25
 *
 */
public class Card implements Serializable{

	
	private static final long serialVersionUID = 7816254941008071823L;
	/**id*/
	public String id;
	/**手机号码*/
	public String mobile;
	/**昵称*/
	public String name;
	/**其他号码*/
	public String otherMobile;
	/**商店id*/
	public String storeId;
	/**店名*/
	public String storeName;
	/**用户id*/
	public String userId;
	/**身份证*/
	public String idCard;
	/**生日*/
	public String birthday;
	/**id*/
	public String totalRmb;
	/**余额*/
	public String balance;
	/**积分*/
	public String point;
	/***/
	public String locked;
	/**优惠券*/
	public String ticketsCount;
	
}
