package cc.chenghong.vka.entity;

import java.io.Serializable;


/**
 * 提交订单商品详情请求参数
 * @author guozhiwei 2015-1-13
 *
 */
public class SubmitOrderMsgPost implements Serializable{

	
	private static final long serialVersionUID = 395330437662036069L;

	/**商品id*/
	public String productId;
	/**商品分类id*/
	public String categoriesId;
	/**商品名id*/
	public String productName;
	/**附加属性id*/
	public String attributeIds;
	/**附加属性名*/
	public String attributeNames;
	/**来源*/
	public String source = "1";
	/**价格*/
	public String price;
	public SubmitOrderMsgPost(String productId, String categoriesId,
			String productName, String attributeIds, String attributeNames,
			String price) {
		super();
		this.productId = productId;
		this.categoriesId = categoriesId;
		this.productName = productName;
		this.attributeIds = attributeIds;
		this.attributeNames = attributeNames;
		this.price = price;
	}
	
	
	
	
	
}
