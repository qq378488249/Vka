package cc.chenghong.vka.entity;

import java.io.Serializable;


/**
 * 优惠券列表实体类
 * @author guozhiwei 2014-12-26
 *
 */
public class QuanList implements Serializable{

	
	private static final long serialVersionUID = 7816254941008071823L;
	/**id*/
	public String id;
	/***/
	public String templateId;
	/**优惠券名*/
	public String name;
	/**会员卡id*/
	public String cardId;
	/***/
	public String paperId;
	/***/
	public String state;
	/***/
	public String type;
	/***/
	public String firstNo;
	/***/
	public String lastNo;
	/***/
	public String no;
	/***/
	public String created;
	/***/
	public String templateType;
	/***/
	public String gifts;
	/***/
	public String gross;
	/**使用开始时间*/
	public String beginTime;
	/**到期时间*/
	public String endTime;
	/**商店名*/
	public String storeName;
	/**是否选中*/
	public int choose = 0;
	
	
	
	
	
	
	
	
//	/**优惠券名*/
//	public String name;
//	/**类型*/
//	public String type;
//	/**优惠*/
//	public String youhui;
//	/**有效期*/
//	public String useDate;
	
	
	
	
//	public QuanList(String name, String type, String youhui, String useDate) {
//		super();
//		this.name = name;
//		this.type = type;
//		this.youhui = youhui;
//		this.useDate = useDate;
//	}
	
	
	


	
	
	
	
	

	
	
	
	
	
	
	
}
