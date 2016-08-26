package cc.chenghong.vka.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 外卖菜品类别实体类
 * @author hcl
 *
 */
public class TakeType extends BaseEntity{
	
	private int id;

	private int parentId;

	private String name;

	private int storeId;

	private int delFlag;

	private String created;
	/**
	 * 是否选中
	 */
	private boolean isSelect;
	/**
	 * 当前菜品类别下包含的菜品数据集
	 */
	private List<TakeGood> list_goods = new ArrayList<TakeGood>();
	/**
	 * 判断该菜品是否被点击过
	 * @return
	 */
	private boolean isFirst;
	
	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
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

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public int getId() {
		return this.id;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getParentId() {
		return this.parentId;
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

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getDelFlag() {
		return this.delFlag;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getCreated() {
		return this.created;
	}
}
