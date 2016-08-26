package cc.chenghong.vka.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会员实体类2015 9 29
 * 
 * @author hcl
 * 
 */
public class FindVIP {
	public List<VIPData> data;
	public int code;
	public int total;
	public class VIPData implements Serializable{
		public Long id;// 主键ID

		public String mobile;// 卡号

		public String name;// 名字

		public Long userId;// 用户编号

		public String idCard;// 身份证号码

		public String birthday;// 生日

		public BigDecimal balance;// 余额

		public Integer point;// 积分

		public String levelName;// 等级名称

		public Float discount;// 等级折扣，如：9.5就表示乘以9.5，10.0表示原价

	}
	
}
