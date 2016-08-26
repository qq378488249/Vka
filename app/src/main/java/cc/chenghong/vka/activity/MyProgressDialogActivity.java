package cc.chenghong.vka.activity;

import java.util.HashMap;
import java.util.Map;

import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MyProgressDialogActivity extends FragmentActivity{
	public final static String TAG = "MyProgressFragmentActivity";
	SonProgressDialog progressDialog;

	class SonProgressDialog extends MyProgressDialog {

		public SonProgressDialog(Context context) {
			super(context);
		}

		/**
		 * 返回按钮事件
		 */
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// App.toast(keyCode+"/n"+KeyEvent.KEYCODE_HOME);
			if (keyCode == KeyEvent.KEYCODE_BACK
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				if(App.payState ==1){
					showCanel(1);
					App.isDialog= true;
					return false;
				}else if(App.payState ==2 || App.payState ==3){//交易中或者取消中不能点击返回键
					return false;
				}else{
					hideProgress();
					return true;
				}
			}
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressDialog = new SonProgressDialog(this);
//		EventBus.getDefault().register(this);
	}

	/**
	 * 显示对话框
	 * 
	 * @param message
	 */
	public void progress(String message) {
		progressDialog.show(message);
	}

	/**
	 * UI现成显示对话框
	 * 
	 * @param message
	 */
	public void postProgress(final String message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progress(message);
			}
		});
	}

	public void hideProgress() {
		progressDialog.hide();
	}

	@Override
	public void finish() {
		progressDialog.dismiss();
		super.finish();
	}

	/**
	 * 弹窗
	 */
	Dialog dialog;

	/**
	 * 显示弹窗
	 * 
	 * @param i
	 */
	public void showCanel(final int i) {
		if (dialog == null) {
			dialog = new Dialog(this, R.style.MyDialog);
			dialog.setContentView(R.layout.dialog_update);
			TextView tv_no = (TextView) dialog.findViewById(R.id.tv_no);
			TextView tv_yes = (TextView) dialog.findViewById(R.id.tv_yes);
			TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
			tv_title.setText("MyProgressFragmentActivity是否取消该订单？");
			tv_no.setText("否");
			tv_yes.setText("是");
			tv_title.setText("是否取消支付？");
			dialog.findViewById(R.id.tv_no).setOnClickListener(
					new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							App.isDialog = false;
							dialog.dismiss();
						}
					});
			dialog.findViewById(R.id.tv_yes).setOnClickListener(
					new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							cancelPay(1);
							App.isDialog = false;
							dialog.dismiss();
						}
					});
		}
		dialog.show();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		EventBus.getDefault().unregister(this);
	}
	/**
	 * 取消支付
	 */
	public void cancelPay(final int i) {
		progress("交易取消中...");
		String url = "";
		if(App.payType == 1){
			url = Api.CANCEL_ALI_ORDER + App.payCode;
		}else if(App.payType == 2){
			url = Api.CANCEL_ORDER + App.payCode;
		}else if(App.payType == 5){
			url = Api.CANCEL_QQ_ORDER + App.payCode;
		}
		StringRequest stringRequest = new StringRequest(Method.GET, url,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						hideProgress();
						cc.chenghong.vka.response.BaseResponse obj = new Gson().fromJson(
								response.toString(),
								cc.chenghong.vka.response.BaseResponse.class);
						if (obj.code == 200) {
							App.toast("订单已成功取消");
							App.stopPay();
//							EventBus.getDefault().post(new FirstEvent("",0)); //发送支付完成消息
						} else {
							Log.e(TAG, Utils.objectToJson(obj));
							switch (obj.code) {
							case 1284:
								App.stopPay();
								App.toast(obj.getMessage());
								break;
							case 1290:
								App.stopPay();
								App.toast(obj.getMessage());
								break;
							case 1285:
								App.stopPay();
								App.toast(obj.getMessage());
								break;
							case 1286:
								App.stopPay();
								App.toast(obj.getMessage());
								break;
							case 1287:
								App.stopPay();
								App.toast(obj.getMessage());
								break;
							default:
								if(i == 1){
									cancelPay(2);
								}else if(i == 2){
									cancelPay(3);
								}else{
									App.stopPay();
									App.toast("取消订单失败，请稍后刷新订单列表退单");
								}
								break;
							}
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						hideProgress();
						switch (i) {
						case 1:
							progress("正在取消订单...");
							new Thread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {
										Thread.sleep(5000);
										cancelPay(2);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}).start();
							break;
						case 2:
							progress("正在取消订单...");
							new Thread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {
										Thread.sleep(10000);
										cancelPay(3);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}).start();
							break;
						case 3:// 第三次取消任然失败，则说明网络异常，无法取消
							App.stopPay();
							App.toast("取消订单失败，请稍后刷新订单列表退款");
							break;
						default:
							break;
						}
					}
				}) {
			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("accessToken", App.getUser().data.accessToken);
				headers.put("code", App.getString("code"));
				headers.put("parentCode", App.getString("parentCode"));
				Log.e(TAG, "cancelPay");
				return headers;
			}
		};
		BaseRequest.addRequest(stringRequest);
	}

}
