package cc.chenghong.vka.entity;

import java.io.Serializable;


/**
 * 显示属性列表
 * @author guozhiwei 2015-1-11
 *
 */
public class ShowGoodsMsg implements Serializable{

	
	private static final long serialVersionUID = 395330437662036069L;

	/**id*/
	public String id;
	/**id*/
	public String parentId;
	/**属性名*/
	public String name;
	/**单价*/
	public int price;
	/**选择类型*/
	public int type;
	/**能否买多个*/
	public int buyNum;
	/**总共买了多少个*/
	public int totalNum = 0;
	public ShowGoodsMsg(String id, String parentId, String name, int price,
			int type, int buyNum) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.price = price;
		this.type = type;
		this.buyNum = buyNum;
	}
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
}
