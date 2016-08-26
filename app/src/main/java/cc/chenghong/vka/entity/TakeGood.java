package cc.chenghong.vka.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 外卖菜品实体类
 * 
 * @author hcl
 * 
 */
public class TakeGood extends BaseEntity{

	private int id;

	private String name;

	private int storeId;

	private int secondCategoriesId;

	private double price;
	//数量
	private int num;

	private String desc;

	private int delFlag;

	private int status;

	private String iconUrl;

	private String created;

	private String categoriesName;
	/**
	 * 保存菜品里点击的菜品
	 */
	private List<TakeGood> list_goods = new ArrayList<TakeGood>();
	/**
	 * 保存该菜品是否被选中
	 * @return
	 */
	private boolean isSelect;
	
	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public List<TakeGood> getList_goods() {
		return list_goods;
	}

	public void setList_goods(List<TakeGood> list_goods) {
		this.list_goods = list_goods;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getStoreId() {
		return this.storeId;
	}

	public void setSecondCategoriesId(int secondCategoriesId) {
		this.secondCategoriesId = secondCategoriesId;
	}

	public int getSecondCategoriesId() {
		return this.secondCategoriesId;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {
		return this.price;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getNum() {
		return this.num;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getDelFlag() {
		return this.delFlag;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getIconUrl() {
		return this.iconUrl;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getCreated() {
		return this.created;
	}

	public void setCategoriesName(String categoriesName) {
		this.categoriesName = categoriesName;
	}

	public String getCategoriesName() {
		return this.categoriesName;
	}

	public TakeGood(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}
	
	public TakeGood() {
		super();
	}

	public TakeGood(int id, String name, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public TakeGood(int id, String name,String iconUrl, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.iconUrl = iconUrl;
	}
	
	
}
