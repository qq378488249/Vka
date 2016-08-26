package cc.chenghong.vka.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * 选择商品属性的商品列表
 * @author guozhiwei 2015-1-11
 *
 */
public class OrderMessageTitle implements Serializable{

	private static final long serialVersionUID = -8388387920125433097L;
	
	/**id*/
	public String id;
	/**商品分类id*/
	public String categoriesId;
	/**商品名*/
	public String title;
	/**同种商品的序号*/
	public int pos;
	/**该商品的总数*/
	public int num;
	/**价格*/
	public double price;
	/**图片地址*/
	public String imageUrl;
	
	/**该商品的属性*/
	public ArrayList<ShowGoodsMsg> msg = new ArrayList<ShowGoodsMsg>();

	public OrderMessageTitle(String id, String categoriesId, String title,
			int pos, int num, double price, String imageUrl) {
		super();
		this.id = id;
		this.categoriesId = categoriesId;
		this.title = title;
		this.pos = pos;
		this.num = num;
		this.price = price;
		this.imageUrl = imageUrl;
	}
	
	
	
	
	
	

	
	
	
}
