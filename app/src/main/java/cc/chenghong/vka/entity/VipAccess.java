package cc.chenghong.vka.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员信息实体类
 * 
 * @author hcl
 * 
 */
public class VipAccess  implements Serializable{
	
	public int id;

	public String mobile;

	public String name;

	public String idCard;

	public String birthday;

	public double balance;

	public double point;
	
	public String userId;

	public String levelName;

	public double discount;

}
