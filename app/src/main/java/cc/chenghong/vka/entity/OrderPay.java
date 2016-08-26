package cc.chenghong.vka.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * 选择商品，加属性后传到支付界面的对象！
 * @author guozhiwei 2015-1-13
 *
 */
public class OrderPay implements Serializable{

	private static final long serialVersionUID = -8388387920125433097L;
	
	/**商店id*/
	public String id;
	/**商店名*/
	public String title;
	/**会员卡id*/
	public String cardId;
	/**总价格*/
	public String totalMoney;
	/**所有商品的属性*/
	public ArrayList<OrderMessageTitle> msg = new ArrayList<OrderMessageTitle>();
	
	
}
