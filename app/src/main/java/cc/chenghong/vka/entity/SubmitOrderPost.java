package cc.chenghong.vka.entity;

import java.io.Serializable;
import java.util.List;


/**
 * 提交订单请求参数
 * @author guozhiwei 2015-1-13
 *
 */
public class SubmitOrderPost implements Serializable{

	
	private static final long serialVersionUID = 395330437662036069L;

	/**用户id*/
	public String userId;
	/**会员卡*/
	public String cardId;
	/**店家id*/
	public String storeId;
	/**店家名*/
	public String storeName;
	/**电话*/
	public String mobile;
	/**详细地址*/
	public String address;
	/**支付总金额*/
	public String amount;
	/**支付方式*/
	public String payType;
	/**是否外卖*/
	public String isTakeaway; 
	/**优惠的金额*/
	public String discount; 
	/**优惠券id*/
	public String ticketsId; 
	/**优惠券名字*/
	public String ticketsName; 
	/**未优惠之前的总金额*/
	public String totalPrice; 
	
	
	/**附加属性json*/
	public List<SubmitOrderMsgPost> ordersDetailsList;




	public SubmitOrderPost(String userId, String cardId, String storeId,
			String storeName, String mobile, String address, String amount,
			String payType, String isTakeaway, String discount,
			String ticketsId, String ticketsName, String totalPrice,
			List<SubmitOrderMsgPost> ordersDetailsList) {
		super();
		this.userId = userId;
		this.cardId = cardId;
		this.storeId = storeId;
		this.storeName = storeName;
		this.mobile = mobile;
		this.address = address;
		this.amount = amount;
		this.payType = payType;
		this.isTakeaway = isTakeaway;
		this.discount = discount;
		this.ticketsId = ticketsId;
		this.ticketsName = ticketsName;
		this.totalPrice = totalPrice;
		this.ordersDetailsList = ordersDetailsList;
	}

	
	

}
