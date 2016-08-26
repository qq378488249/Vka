package cc.chenghong.vka.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cc.chenghong.vka.code.qr_codescan.MipcaActivityCapture;
import cc.chenghong.vka.entity.FirstEvent;
import cc.chenghong.vka.entity.VIP;
import cc.chenghong.vka.entity.VipAccess;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.response.ObjectResponse;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;
import de.greenrobot.event.EventBus;

/**
 * 会员扫码2015 9 29
 *
 * @author hcl
 */
public class VipSweepCardActivity extends BaseActivity implements
        OnClickListener {
    /**
     * 支付码
     */
    String payCode;
    /**
     * 支付金额
     */
    String total_fee;
    /**
     * 密码
     */
    String password;
    @ViewInject(R.id.tv_money)
    TextView tv_money;
    /**
     * 会员卡号
     */
    @ViewInject(R.id.et_vip_card)
    EditText et_vip_card;
    @ViewInject(R.id.et_password)
    EditText et_password;
    @ViewInject(R.id.bt_submit)
    Button bt_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_vip_sweep);
        setTitleName("会员卡支付");
//		App1.getInstance().addActivity(this);
        ViewUtils.inject(this);
        EventBus.getDefault().register(this);
        // payCode = getIntent().getStringExtra("payCode");
        initUI();
    }

    private void initUI() {
        // TODO Auto-generated method stub
        ib_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Utils.closeJP(et_password.getContext(),et_password);
            }
        });
        Utils.openJP(et_vip_card);
        total_fee = getIntent().getStringExtra("total_fee");
        tv_money.setText("￥" + total_fee);
//		et_vip_card.setText("1001009");
        // et_password.setText("111111");
    }

    @OnClick({R.id.iv_back, R.id.iv_code, R.id.bt_submit})
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_code:
                Intent i = new Intent(this, MipcaActivityCapture.class);
                i.putExtra("payType", "4");// 支付类型
                startActivity(i);
                // startActivityForResult(i, 1);
                // finish();
                break;
            case R.id.bt_submit:
                if (Utils.isNull(et_vip_card)) {
                    App.toast("请输入会员卡号！");
                    return;
                }
                // showPassword();
                submit_check();
//			check_access();
                // App.toast("确认");
                break;
            default:
                break;
        }
    }

    //显示密码
    private void showPassword() {
        // TODO Auto-generated method stub
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_paddword);
        final EditText et_password = (EditText) dialog
                .findViewById(R.id.et_password);
        dialog.findViewById(R.id.tv_no).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        password = "";
                        closeInputMethod();
                        dialog.dismiss();
                    }
                });
        dialog.findViewById(R.id.tv_yes).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        if (Utils.isNull(et_password)) {
                            App.toast("请输入密码！");
                            return;
                        }
                        password = Utils.getValue(et_password);
                        dialog.dismiss();
                    }
                });
        openInputMethod(et_password);
        dialog.show();
    }

    private void check_access() {
        String api = Api.BY_CODE_VIP + Utils.getValue(et_vip_card) + "/0";
//		ParamPost headers=new ParamPost();
//		headers.add("accessToken", App.getUser().data.accessToken);
//		headers.add("code", App.getString("code"));
//		headers.add("parentCode", App.getString("parentCode"));
        progress("加载中...");
        BaseRequest.get(api, getHeanders(), new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                ObjectResponse<VipAccess> obj = new Gson().fromJson(o.toString(), new TypeToken<ObjectResponse<VipAccess>>() {
                }.getType());
                if (obj.isSuccess()) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError(volleyError);
            }
        });
//		BaseService.get(api, new ParamPost(), headers, new ServiceCallback<VipAccess>() {
//
//			@Override
//			public void done(int what, ServiceResponse<VipAccess> obj) {
//				// TODO Auto-generated method stub
//				hideProgress();
//				App.toast(Utils.objectToJson(obj.data));
//				System.out.println(Utils.objectToJson(obj.data));
////				if(obj.data.id.toString().length()>1){//如果有会员编号，说明存在此会员
////					Intent i = new Intent(VipSweepCardActivity.this,VipPayActivity.class);
////					i.putExtra("vip", obj.data);
////					System.out.println(Utils.objectToJson(obj.data));
////					i.putExtra("money", total_fee);
////					if(et_vip_card.getText().toString().length()!=20){
////						i.putExtra("isCard", "yes");
////					}else{
////						i.putExtra("isCard", "no");
////					}
////					startActivityForResult(i, 0);
////				}else{
////					System.out.println(Utils.objectToJson(obj));
////					App.toast(Utils.codeToString(obj.responseCode));
////				}
//			}
//
//			@Override
//			public void error(String msg) {
//				// TODO Auto-generated method stub
//				hideProgress();
//				App.toast(msg);
//			}
//		});
    }

    // 检查用户
    private void submit_check() {
        // TODO Auto-generated method stub
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = url = Api.MAIN + "/pc/v1/card/bycode/" + Utils.getValue(et_vip_card) + "/0";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        hideProgress();
                        VIP v = Utils.jsonToObject(VIP.class, response);
                        if (v.code == 200) {
                            Intent i = new Intent(VipSweepCardActivity.this, VipPayActivity.class);
                            i.putExtra("vip", v.data);
                            System.out.println(Utils.objectToJson(v.data));
                            i.putExtra("money", total_fee);
                            if (et_vip_card.getText().toString().length() != 20) {
                                i.putExtra("isCard", "yes");
                            } else {
                                i.putExtra("isCard", "no");
                            }
                            startActivityForResult(i, 0);
                            // finish();
                        } else {
                            if (v.data == null) {
                                App.toast("服务器错误");
                            }
                            App.toast(Utils.codeToString(v.code));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                App.toast(error + "");
                hideProgress();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("accessToken", App.getUser().data.accessToken);
                headers.put("parentCode", Utils.getString(getApplicationContext(), "parentCode"));
                headers.put("code", Utils.getString(getApplicationContext(), "code"));
                return headers;
            }
        };

        progress("加载中...");
        requestQueue.add(stringRequest);
    }

    public void submitTicket() {

    }

    /**
     * 当activity扫码结束从新获得焦点时会调用此方法(重绘方法)
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        // finish();
        super.onResume();
        // 如果当前的账号不等于扫描的账号，则刷新账号
        if (Utils.getString(getApplicationContext(), "isPay").equals("1")) {
            Utils.setString(getApplicationContext(), "isPay", "0");
            et_vip_card.setText(Utils.getString(getApplicationContext(),
                    "paycode"));
        }
        // if(!Utils.isNull(et_vip_card)){
        // if (!Utils.getValue(et_vip_card).equals(
        // Utils.getString(getApplicationContext(), "paycode"))) {
        // et_vip_card.setText(Utils.getString(getApplicationContext(),
        // "paycode"));
        // Utils.clearString(getApplicationContext(), "paycode");
        // }
        // }
    }

    /**
     * 关闭系统软键盘
     */
    public void closeInputMethod() {

        try {

            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))

                    .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),

                            InputMethodManager.HIDE_NOT_ALWAYS);

        } catch (Exception e) {
        } finally {
        }

    }

    /**
     * 打开系统软键盘
     *
     * @param editText
     */
    public void openInputMethod(final EditText editText) {

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            public void run() {

                InputMethodManager inputManager = (InputMethodManager) editText

                        .getContext().getSystemService(

                                Context.INPUT_METHOD_SERVICE);

                inputManager.showSoftInput(editText, 0);

            }

        }, 200);

    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 0 && arg1 == 0) {
            //会员卡支付成功，清空输入框
            EventBus.getDefault().post(new FirstEvent("", 0));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Utils.closeJP(this, et_vip_card);
    }

    /**
     * 监听消息
     *
     * @param event
     */
    public void onEventMainThread(FirstEvent event) {
        Log.e("harvic", event.getMsg());
        if (event.getCode() == 3) {
            et_vip_card.setText(App.payCode);
        }
    }
}
