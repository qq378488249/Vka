package cc.chenghong.vka.entity.post;

import java.io.Serializable;

/**
 * 
 * @author gzw
 *
 */
public class WXPayPost implements Serializable{

	private static final long serialVersionUID = 1810402860395112956L;

	/**付款码*/
	public String authCode;
	/**商品描述*/
	public String body;
	/**付款金额*/
	public int totalFee;
	
	public WXPayPost(String auth_code, String body, int total_fee) {
		super();
		this.authCode = auth_code;
		this.body = body;
		this.totalFee = total_fee;
	}
	
	
}
