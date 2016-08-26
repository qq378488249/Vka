package cc.chenghong.vka.listener;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.widget.PopupWindow;

class PoponDismissListener implements PopupWindow.OnDismissListener {
	//上下文
	Context context;
	@Override
	public void onDismiss() {
		// TODO Auto-generated method stub
		// Log.v("List_noteTypeActivity:", "我是关闭事件");
		backgroundAlpha(1f);
	}

	// 设置背景透明度
	public void backgroundAlpha(float bgAlpha) {
//		context;
//		context.getApplicationContext().getwi;
		WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
		lp.alpha = bgAlpha;
		((Activity)context).getWindow().setAttributes(lp);
	}
	
	PoponDismissListener(Context context){
		this.context = context;
	}
}
