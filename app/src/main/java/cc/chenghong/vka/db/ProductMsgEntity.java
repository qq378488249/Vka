package cc.chenghong.vka.db;


import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 我选择的商品
 * @author guozhiwei 2015-1-15
 *
 */
@DatabaseTable(tableName = "ProductMsg")
public class ProductMsgEntity implements Serializable{
	private static final long serialVersionUID = -3381497683370200265L;
	public static final String TABLE_NAME = "Product";

	
	/**对应商品的对应属性的id（唯一）*/
	@DatabaseField(id=true)
	private String id;
	/**商品的唯一id*/
	@DatabaseField
	private String goodId;
	/**商品属性id*/
	@DatabaseField
	private String msgId;
	/**属性分组id*/
	@DatabaseField
	private String parentId;
	/**选择类型*/
	@DatabaseField
	private String choose;
	/**属性名*/
	@DatabaseField
	private String msgName;
	/**属性价格*/
	@DatabaseField
	private String price;
	/**属性数量*/
	@DatabaseField
	private String totalNumber;
	/**是否可买多个*/
	@DatabaseField
	private String buyNum;
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getChoose() {
		return choose;
	}
	public void setChoose(String choose) {
		this.choose = choose;
	}
	public String getMsgName() {
		return msgName;
	}
	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(String totalNumber) {
		this.totalNumber = totalNumber;
	}
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	
	public ProductMsgEntity() {
		super();
	}
	public ProductMsgEntity(String id, String goodId, String msgId,
			String parentId, String choose, String msgName, String price,
			String totalNumber, String buyNum) {
		super();
		this.id = id;
		this.goodId = goodId;
		this.msgId = msgId;
		this.parentId = parentId;
		this.choose = choose;
		this.msgName = msgName;
		this.price = price;
		this.totalNumber = totalNumber;
		this.buyNum = buyNum;
	}
	
	
	
	
	
	
	
}
