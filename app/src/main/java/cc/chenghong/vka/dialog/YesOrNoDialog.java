package cc.chenghong.vka.dialog;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cc.chenghong.vka.activity.R;
import cc.chenghong.vka.app.App;

public class YesOrNoDialog {
	Dialog dialog;
	TextView tv_no;
	TextView tv_yes;
	TextView tv_title;
	
	YesOrNoDialog() {
		onclickLeft();
		onclickRight();
		initDialog();
	}

	private void initDialog() {
		// TODO Auto-generated method stub
		dialog = new Dialog(App.getInstance(), R.style.MyDialog);
		dialog.setContentView(R.layout.dialog_update);
		tv_no = (TextView) dialog.findViewById(R.id.tv_no);
		tv_yes = (TextView) dialog.findViewById(R.id.tv_yes);
		tv_title = (TextView) dialog.findViewById(R.id.tv_title);
		dialog.findViewById(R.id.tv_no).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						onclickLeft();
						dialog.dismiss();
					}
				});
		dialog.findViewById(R.id.tv_yes).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						onclickLeft();
						dialog.dismiss();
					}
				});
	}
	
	/**
	 * 左边按钮点击事件
	 */
	public static void onclickLeft() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 右边按钮点击事件
	 */
	public static void onclickRight() {
		// TODO Auto-generated method stub
		
	}
	
}
