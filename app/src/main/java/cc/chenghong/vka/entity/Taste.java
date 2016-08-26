package cc.chenghong.vka.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 菜品口味实体类
 * @author hcl
 *
 */
public class Taste extends BaseEntity{
	/**
	 * 口味编号
	 */
	private long id;
	
	/**
	 * 口味名称
	 */
	private String name;
	
	/**
	 * 口味数量
	 */
	private int count;
	/**
	 * 价格
	 */
	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

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

	public void setCount(int count) {
		this.count = count;
	}

	public Taste(long id, String name, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	public Taste() {
		super();
		this.id = id;
		this.name = name;
		this.count = count;
	}
}
