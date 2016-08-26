package cc.chenghong.vka.entity;

import java.math.BigDecimal;

/**
 * 会员充值金额实体类2015 10 8
 * 
 * @author hcl
 * 
 */
public class AmountConfig {
	public Long id;

	public String title;

	public BigDecimal amount;// 充值金额

	public BigDecimal give;// 赠送金额

	public Integer point;// 赠送积分

	public Long storeId;

	public String created;

	public String tickets;

	public String ticketNames;// 赠送优惠券名字，例：”10元甜品券x1,5元甜品券x1”

	public String startTime;

	public String endTime;

	public boolean isSelect;// 是否选中
}
