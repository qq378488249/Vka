package cc.chenghong.vka.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cc.chenghong.vka.entity.Trans.TransData;

/**
 * 交易记录实体类2015 9 25
 * 
 * @author hcl
 * 
 */
public class Trans {

	public long total;

	public List<TransData> data=new ArrayList<Trans.TransData>();

	public long code;
	
	private String message;

	
	public class TransData implements Serializable{
		public long id;

		public  String openId;
		
		public  String nickName;

		public long transId;

		public  String created;
		
		public  String createdDate;

		public long status;
		
		public long orderId;
		
		public long cardId;

		public long storeId;

		public  String storeName;

		public long parentId;

		public  String transactionId;

		public long operationId;

		public  String operationName;

		public  String tradeType;

		public double totalFee;

		public double cashFee;

		public long couponFee;

		public long type;

		
		public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
		public String name;//会员姓名(有可能为空)
		public String mobile;//会员卡号
		public String otherMobile;//手机号码(有可能为空)
		public BigDecimal amount;	//交易金额
		public BigDecimal give;	//赠送金额
		public BigDecimal total;	//本次交易总金额
		public BigDecimal balance;	//卡内余额
		public String operatorName;	//操作员工姓名(有可能为空)
		public Integer payType;
		public Integer point;		//积分
		public Integer pointBalance;	//积分余额
		public String tickets;	//优惠券ID(例:1234,1235)
		public String ticketNames;//优惠券名称列表(例：十元优惠券，二十元优惠券)
		public BigDecimal cashAmount;	//现金付款
		public BigDecimal creditAmount;	//信用卡
		public BigDecimal discount;//优惠券折扣
		public BigDecimal pointDiscount;//积分折扣
		public BigDecimal levelDiscount;//会员等级折扣
		public BigDecimal itemSubtotal; //订单原始金额
		public String remark;
		public BigDecimal weixinAmount;//微信付款金额
		public BigDecimal alipayAmount;//支付宝付款金额
		public String levelName;//会员等级名称
		
		
	}
	
	public String getMessage(){
		if(message==null){
			return "";
		}else{
			return message;
		}
	}
}
