package cc.chenghong.vka.db;


import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 我选择的商品
 * @author guozhiwei 2015-1-15
 *
 */
@DatabaseTable(tableName = "Product")
public class ProductEntity implements Serializable{
	private static final long serialVersionUID = -3381497683370200265L;
	public static final String TABLE_NAME = "Product";

	/**唯一id*/
	@DatabaseField(id=true)
	private String id;
	/**商品id*/
	@DatabaseField
	private String goodId;
	/**图片地址*/
	@DatabaseField
	private String iconUrl;
	/**商品名*/
	@DatabaseField
	private String name;
	/**商品价格，（包括商品附加属性在内）*/
	@DatabaseField
	private String price;
	/**序列号，当前商品是该类商品的第几份*/
	@DatabaseField
	private String number;
	/**商店id*/
	@DatabaseField
	private String storeId;
	/**商品分类id*/
	@DatabaseField
	private String typeId;
	/**商品详情介绍*/
	@DatabaseField
	private String desc;
	/**商品类型名*/
	@DatabaseField
	private String typeName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	public ProductEntity() {
		super();
	}
	public ProductEntity(String id, String goodId, String iconUrl, String name,
			String price, String number, String storeId, String typeId,
			String desc, String typeName) {
		super();
		this.id = id;
		this.goodId = goodId;
		this.iconUrl = iconUrl;
		this.name = name;
		this.price = price;
		this.number = number;
		this.storeId = storeId;
		this.typeId = typeId;
		this.desc = desc;
		this.typeName = typeName;
	}
	
	
	
	
}
