package cc.chenghong.vka.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import cc.chenghong.vka.activity.CheckTicketRecordActivity;
import cc.chenghong.vka.activity.MainActivity;
import cc.chenghong.vka.activity.R;
import cc.chenghong.vka.activity.VipDealActivity;
import cc.chenghong.vka.activity.VipFindActivity;
import cc.chenghong.vka.activity.VipRechargeActivity;
import cc.chenghong.vka.code.qr_codescan.MipcaActivityCapture;
import cc.chenghong.vka.entity.FirstEvent;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.response.BaseResponse;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 会员2015 10 12
 * 
 * @author hcl
 * 
 */
public class VipFragment extends BaseFragment implements OnClickListener {
	// @ViewInject(R.id.ll_find)
	// LinearLayout ll_find;
	// @ViewInject(R.id.ll_recharge)
	// LinearLayout ll_recharge;
	// @ViewInject(R.id.ll_ticket)
	// LinearLayout ll_ticket;
	// @ViewInject(R.id.ll)
	// LinearLayout ll;
	PopupWindow popupWindow;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_vip, container, false);
		ViewUtils.inject(this, view);
		EventBus.getDefault().register(this);//初始化EventBus
		return view;
	}

	@OnClick({ R.id.ll_find, R.id.ll, R.id.ll_recharge, R.id.ll_ticket,
			R.id.ll_record })
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {

		case R.id.ll_find:
			startActivity(new Intent(getActivity(), VipFindActivity.class));
			break;
		case R.id.ll_recharge:
			startActivity(new Intent(getActivity(), VipRechargeActivity.class));
			break;
		case R.id.ll_ticket:
			// App.toast("核销卡券");
			// Intent i=new Intent(getActivity(),MipcaActivityCapture.class);
			// i.putExtra("payType", "3");
			// startActivity(i);
			showWindow(arg0);
			break;
		case R.id.ll_record:
			// App.toast("会员交易记录");
			startActivity(new Intent(getActivity(), VipDealActivity.class));
			break;
		case R.id.ll:
			break;
		default:
			break;
		}
	}

	// 核销卡券
	void submit(String resultString) {
		String url = Api.CHECK_TICKET + resultString;
		System.out.println(url);
		((MainActivity) getActivity()).progress("核销中...");
//		ParamPost head=new ParamPost();
//		head.add("accessToken", App.getUser().data.accessToken);
//		head.add("source", "app");
//		head.add("code",App.getString("code"));
//		head.add("parentCode",App.getString("parentCode"));
		Log.e(TAG, "核销卡券");
		Map map = getBaseActivity().getHeanders();
		map.put("source", "app");
		BaseRequest.get(url, map, new Response.Listener() {
			@Override
			public void onResponse(Object o) {
				BaseResponse obj = new Gson().fromJson(o.toString(),BaseResponse.class);
				if (obj.isSuccess()) {
					App.toast("核销成功");
				} else {
					if (!obj.getMessage().equals("")) {
						App.toast(obj.getMessage());
					} else {
						App.toast(Utils.objectToJson(obj));
					}

				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				getBaseActivity().volleyError(volleyError);
			}
		});
//		BaseService.get(url, new ParamPost(), head,
//				new SimpleServiceCallback<BeanResponse>() {
//
//					@Override
//					public void done(int what, ServiceResponse obj) {
//						// TODO Auto-generated method stub
//						((MainActivity) getActivity()).hideProgress();
//						if (obj.isResponseSuccess()) {
//							App.toast("核销成功");
//						} else {
//							if (!obj.getMessage().equals("")) {
//								App.toast(obj.getMessage());
//							} else {
//								App.toast(Utils.objectToJson(obj));
//							}
//
//						}
//					}
//				});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	View view_popupWindow = null;
	private void showWindow(View parent) {
		WindowManager windowManager = (WindowManager) getActivity()
				.getSystemService(Context.WINDOW_SERVICE);
		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view_popupWindow = layoutInflater.inflate(R.layout.dialog_ticket, null);
			popupWindow = new PopupWindow(view_popupWindow, windowManager.getDefaultDisplay()
					.getWidth(), 300, true);
		}
		// 使其聚焦
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		// ColorDrawable dw = new ColorDrawable(0Xffffff);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.popwin_anim_style);
		popupWindow.setOnDismissListener(new poponDismissListener());
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		backgroundAlpha(0.5f);
		// 核销卡券
		view_popupWindow.findViewById(R.id.tv_1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent i=new Intent(getActivity(),MipcaActivityCapture.class);
				 App.payType = 5;
				 startActivity(i);
				 popupWindow.dismiss();
			}
		});

		// 核销卡券记录
		view_popupWindow.findViewById(R.id.tv_2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(),CheckTicketRecordActivity.class);
				 startActivity(i);
				 popupWindow.dismiss();
			}
		});

		// 取消
		view_popupWindow.findViewById(R.id.tv_3).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});

		// popupWindow.showAtLocation(parent, Gravity.LEFT, 0, 0);

	}

	// 设置背景透明度
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getActivity().getWindow()
				.getAttributes();
		lp.alpha = bgAlpha;
		getActivity().getWindow().setAttributes(lp);
	}

	// 监听ppwindows是否关闭
	class poponDismissListener implements PopupWindow.OnDismissListener {

		@Override
		public void onDismiss() {
			// TODO Auto-generated method stub
			// Log.v("List_noteTypeActivity:", "我是关闭事件");
			backgroundAlpha(1f);
		}
	}
	
	/**
	 * 监听消息
	 * @param event
	 */
	public void onEventMainThread(FirstEvent event) {  
	    Log.e("harvic", event.getMsg());
	    if(event.getCode()==5){
	    	submit(App.payCode);
	    }
	} 
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
