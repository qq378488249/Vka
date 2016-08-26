package cc.chenghong.vka.util;

import android.app.Activity;
import android.widget.TextView;

public class Holder {
	public static Activity mactivity;
	public Holder holder;
	public Holder(Activity activity) {
		// TODO Auto-generated constructor stub
		this.mactivity=activity;
	}
	/**
	 * 为字符串设置文字
	 * @param id
	 */
	public static void setText(int id,String string){
		TextView tv=(TextView) mactivity.findViewById(id);
		tv.setText(string);
	}
}
