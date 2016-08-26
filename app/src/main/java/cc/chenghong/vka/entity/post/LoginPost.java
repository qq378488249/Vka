package cc.chenghong.vka.entity.post;

import java.io.Serializable;

/**
 * 
 * @author gzw
 *
 */
public class LoginPost implements Serializable{

	private static final long serialVersionUID = 1810402860395112956L;

	public String account;
	public String password;
	public LoginPost(String account, String password) {
		super();
		this.account = account;
		this.password = password;
	}
	
}
