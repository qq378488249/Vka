package cc.chenghong.vka.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员实体类2015 9 29
 * 
 * @author hcl
 * 
 */
public class VIP {
	public VIPData data;
	public int code;
	public int total;

	public class VIPData implements Serializable {
		
		public Long id;// 主键ID

		public String mobile;// 卡号

		public String otherMobile;// 卡号

		public String name;// 名字

		public Long userId;// 用户编号

		public String idCard;// 身份证号码

		public String birthday;// 生日

		public BigDecimal balance;// 余额

		public Integer point;// 积分

		public String levelName;// 等级名称

		public String experience;// 经验值

		public String discount;// 等级折扣，如：9.5就表示乘以9.5，10.0表示原价

		public Long storeId;

		public String storeName;

		public BigDecimal totalRmb;

		public Integer locked;

		public Integer ticketsCount; // 劵数量

		public Integer sex;

		/**
		 * 所在省
		 */
		public String province;
		/**
		 * 所在城市
		 */
		public String city;

		/**
		 * 所在地区
		 */
		public String district;

		/**
		 * 优惠券名称
		 */
		public String ticketName;

		/**
		 * 微信用户开放唯一标识码
		 * 
		 * @author guoyu
		 */
		public String openId;

	}

}
