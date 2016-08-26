package cc.chenghong.vka.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cc.chenghong.vka.entity.Access;
import cc.chenghong.vka.entity.User;
import cc.chenghong.vka.listener.VolleyListener;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.response.BaseResponse;
import cc.chenghong.vka.response.ObjectResponse;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 登陆2015-9-23
 *
 * @author hcl
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    @ViewInject(R.id.et_1)
    EditText et_1;
    @ViewInject(R.id.et_2)
    EditText et_2;
    @ViewInject(R.id.et_3)
    EditText et_3;
    @ViewInject(R.id.et_4)
    EditText et_4;
    @ViewInject(R.id.bt_submit)
    Button bt_submit;
    @ViewInject(R.id.vBar)
    View vBar;

    String accessCode = "";
    long exitTime = 0;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        initUI();
    }

    private void initUI() {
        // TODO Auto-generated method stub
        statusBar(vBar);
        String parentCode = Utils.getString(getApplicationContext(),
                "parentCode");// 总店编号
        String code = Utils.getString(getApplicationContext(), "code");// 门店编号
        String account = Utils.getString(getApplicationContext(), "account");// 账号
        et_1.setText(parentCode);
        et_2.setText(code);
        et_3.setText(account);
        if (App.isDeBug) {
            int i = (Api.MAIN).indexOf("v-ka");
            boolean b = (Api.MAIN).indexOf("v-ka") != -1;
            Log.i(TAG, "initUI: " + b);
            if (b) {//正式服务器
                toask("正式地址" + Api.MAIN);
                et_1.setText("1078");
                et_2.setText("CC888");
                et_3.setText("oppo01");
                et_4.setText("111111");
            } else {
                toask("测试地址" + Api.MAIN);
                et_1.setText("1070");
                et_2.setText("CC888");
                et_3.setText("xmkl01");
                et_4.setText("111111");
            }
        }
    }

    @OnClick({R.id.et_1, R.id.bt_submit, R.id.et_2, R.id.et_3, R.id.et_4})
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.bt_submit:
                if (viewIsNull(et_1)) {
                    Utils.toask(this, "请输入总店编号 ");
                    return;
                }
                if (viewIsNull(et_2)) {
                    Utils.toask(this, "请输入门店编号  ");
                    return;
                }
                if (viewIsNull(et_3)) {
                    Utils.toask(this, "请输入账户  ");
                    return;
                }
                if (viewIsNull(et_4)) {
                    Utils.toask(this, "请输入密码 ");
                    return;
                }
                login();
                break;
            default:
                break;
        }
    }

    // 获取tiken
    void getAccessToken() {
        String url = Api.TOKEN;
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplicationContext());
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", accessCode);
        JSONObject jsonObject = new JSONObject(map);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("parentCode", Utils.getValue(et_1));
        headers.put("code", Utils.getValue(et_2));
        headers.put("Content-Type", "application/json");
        progress("登陆中...");
        BaseRequest.postJson(url, jsonObject, headers, new VolleyListener<User>() {
            @Override
            public void onSuccess(User user) {
                hideProgress();
                if (user.code == 200) {
                    App.setUser(user);
                    Utils.setString(getApplicationContext(), "login",
                            "1");
                    Utils.setString(getApplicationContext(), "account",
                            Utils.getValue(et_3));
                    Utils.setString(getApplicationContext(),
                            "parentCode", Utils.getValue(et_1));
                    Utils.setString(getApplicationContext(), "code",
                            Utils.getValue(et_2));
                    startActivity(new Intent(LoginActivity.this,
                            MainActivity.class));
                    App.toast("登录成功");
                    finish();
                } else {
                    App.toast(Utils.codeToString(u.code));
                }
            }

            @Override
            public void onFail(VolleyError volleyError) {
                volleyError(volleyError);
            }
        });
    }

    void login() {
        String url = Api.LOGIN;
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplicationContext());
        Map<String, String> map = new HashMap<String, String>();
        map.put("password", Utils.getValue(et_4));
        map.put("account", Utils.getValue(et_3));
        JSONObject jsonObject = new JSONObject(map);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("parentCode", Utils.getValue(et_1));
        headers.put("code", Utils.getValue(et_2));
        headers.put("Content-Type", "application/json");
        BaseRequest.postJson(url, jsonObject, headers,
                new VolleyListener<Access>() {
                    @Override
                    public void onSuccess(Access access) {
                        if (access.code == 200) {
                            accessCode = access.accessCode;
                            getAccessToken();
                        } else {
                            App.toast("登录失败\n"
                                    + Utils.codeToString(access.code));
                        }
                    }

                    @Override
                    public void onFail(VolleyError volleyError) {
                        volleyError(volleyError);
                    }
                });
//        new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        hideProgress();
//                        Access access = new Gson().fromJson(
//                                response.toString(), Access.class);
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        volleyError(error);
//                    }
//                });
        // JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
        // Method.POST, url, jsonObject,
        // new Response.Listener<JSONObject>() {
        // @Override
        // public void onResponse(JSONObject response) {
        // hideProgress();
        // Access access = new Gson().fromJson(
        // response.toString(), Access.class);
        // if (access.code == 200) {
        // accessCode = access.accessCode;
        // getAccessToken();
        // // App.toast("登陆成功");
        // } else {
        // App.toast(Utils.codeToString(access.code));
        // }
        // }
        // }, new Response.ErrorListener() {
        // @Override
        // public void onErrorResponse(VolleyError error) {
        // hideProgress();
        // error.getMessage();
        // App.toast(error + "");
        // }
        // }) {
        //
        // @Override
        // public Map<String, String> getHeaders() {
        // HashMap<String, String> headers = new HashMap<String, String>();
        // headers.put("parentCode", Utils.getValue(et_1));
        // headers.put("code", Utils.getValue(et_2));
        // headers.put("Content-Type", "application/json");
        // return headers;
        // }
        // };
        // progress("登录中...");
        // requestQueue.add(jsonRequest);
    }

    /**
     * 返回按钮事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
