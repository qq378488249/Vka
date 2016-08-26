package cc.chenghong.vka.response;

import java.io.Serializable;

import org.json.JSONObject;

import android.hardware.Camera.Size;
import android.util.Log;

import com.google.gson.Gson;

/**
 * 响应基类
 * @author hcl
 *
 */
public class BaseResponse implements Serializable{
	public String TAG = this.getClass().getSimpleName();

	public int getCode() {
		return code;
	}

	private static final long serialVersionUID = 1L;
	
	public static final int CODE_SUCCESS = 200;
	
	/**
	 * 返回码 200:成功 300或其他: 失败
	 */
	public int code;
	/** 信息 */
	private String message;
	/**
	 * 总数
	 */
	public int total;
	/**
	 * 返回码是否为200
	 * @return 200返回true,否则false
	 */
	public boolean isSuccess(){
//		Log.i(TAG, new Gson().toJson(this));
		return code == CODE_SUCCESS;
	}
	/**
	 * 返回服务器的响应消息
	 * @return
	 */
	public String getMessage(){
		if(message == null) return "";
		else
			return message;
	}
	
	public String value;
	public String app;
	/**
	 * 支付状态
	 * @return
	 */
	public int state;
}
