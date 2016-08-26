package cc.chenghong.vka.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员充值post类2015 9 29
 * 
 * @author hcl
 * 
 */
public class VIPRechargePost {
	public String mobile	;	//必填	会员卡号
	public  String cashAmount	;//	必填	现金金额，0.00
	public String total		;//必填	//现金金额，0.00
	public String itemSubtotal	;	//必填	现金金额，0.00
	public String strPayType	;	//必填	目前全部填”CASH”
	public Long configId	;	//有就填	充值套餐ID
}
