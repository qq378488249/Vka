package cc.chenghong.vka.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜品类别实体类
 * @author hcl
 *
 */
public class DishesType extends BaseEntity{
	/**
	 * 菜类编号
	 */
	private long id;
	
	/**
	 * 菜类名称
	 */
	private String name;
	
	/**
	 * 包含的菜品
	 */
	private List<Dishes> list = new ArrayList<Dishes>();
	
	/**
	 * 是否被选中
	 */
	private boolean isSelect;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Dishes> getList() {
		return list;
	}

	public void setList(List<Dishes> list) {
		this.list = list;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	
}
