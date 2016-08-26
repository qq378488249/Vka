package cc.chenghong.vka.entity;

import java.math.BigDecimal;

/**
 * 菜品实体类
 * @author hcl
 *
 */
public class Dishes extends BaseEntity{
	/**
	 * 菜品编号
	 */
	private long id;
	
	/**
	 * 菜品名称
	 */
	private String name;
	
	/**
	 * 菜品数量
	 */
	private int count;
	
	/**
	 * 菜品价格
	 */
	private double price;

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

	public int getCount() {
		return count;
	}

	public void setCount(int i) {
		this.count = i;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
