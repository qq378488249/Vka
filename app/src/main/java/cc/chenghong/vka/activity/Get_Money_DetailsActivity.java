package cc.chenghong.vka.activity;

import java.util.HashMap;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import cc.chenghong.vka.entity.FirstEvent;
import cc.chenghong.vka.entity.Trans;
import cc.chenghong.vka.entity.Trans.TransData;
import cc.chenghong.vka.fragment.GetMoneyFragment;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.response.BaseResponse;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;
import de.greenrobot.event.EventBus;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 收银详情2015 9 26
 *
 * @author hcl
 */
public class Get_Money_DetailsActivity extends BaseActivity implements
        OnClickListener {
    @ViewInject(R.id.tv_state)
    TextView tv_state;
    @ViewInject(R.id.tv_time)
    TextView tv_time;
    @ViewInject(R.id.tv_pay_name)
    TextView tv_pay_name;
    @ViewInject(R.id.tv_pay_type)
    TextView tv_pay_type;
    @ViewInject(R.id.tv_order_id)
    TextView tv_order_id;
    @ViewInject(R.id.tv_money)
    TextView tv_money;
    @ViewInject(R.id.tv_operation_name)
    TextView tv_operation_name;

    @ViewInject(R.id.bt_submit)
    Button bt_submit;

    @ViewInject(R.id.iv_back)
    ImageView iv_back;

    @ViewInject(R.id.iv_yfh)
    ImageView iv_yfh;

    @ViewInject(R.id.lv)
    ListView lv;
    //付款人布局
    @ViewInject(R.id.ll_pay_name)
    LinearLayout ll_pay_name;

    @ViewInject(R.id.ll_pay_type)
    LinearLayout ll_pay_type;
    Trans t = new Trans();
    TransData trans = null;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_order__details_);
//		App.getInstance().addActivity(this);
//        initStatusBar();
        ViewUtils.inject(this);

        initUI();
    }

    private void initUI() {
        // TODO Auto-generated method stub

        setTitleName("订单详情");
        trans = (TransData) getIntent().getSerializableExtra("trans");
        type = Utils.getString(getApplicationContext(), "payType");

        if (type.equals("cardPay")) {
            iv_yfh.setVisibility(View.VISIBLE);
            bt_submit.setText("退款");
            if (trans.nickName == null || trans.nickName.equals("")) {
                tv_pay_name.setText("暂无付款人名称");
                ll_pay_name.setVisibility(View.GONE);
            } else {
                tv_pay_name.setText(trans.nickName);
                ll_pay_name.setVisibility(View.VISIBLE);
            }
            if (trans.operationName == null) {
                tv_operation_name.setText("暂无操作人名称");
            } else {
                tv_operation_name.setText(trans.operationName);
            }
            lv.setVisibility(View.GONE);
            tv_pay_type.setText("会员卡支付");
            tv_money.setText("￥" + trans.total + "");
            tv_state.setText(Utils.codeToString((int) trans.status));
            tv_time.setText(trans.createdDate);
            // App.toast(trans.createdDate);
            tv_order_id.setText(trans.id + "");
            ll_pay_type.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(Get_Money_DetailsActivity.this,
                            Vip_pay_DetailsActivity.class);
                    i.putExtra("trans", trans);
                    startActivity(i);
                }
            });
        } else {
            bt_submit.setText("退款");
            lv.setVisibility(View.GONE);
            if (trans.nickName == null || trans.nickName.equals("")) {
                tv_pay_name.setText("暂无付款人名称");
                ll_pay_name.setVisibility(View.GONE);
            } else {
                tv_pay_name.setText(trans.nickName);
                ll_pay_name.setVisibility(View.VISIBLE);
            }
            if (trans.operationName == null) {
                tv_operation_name.setText("暂无操作人名称");
            } else {
                tv_operation_name.setText(trans.operationName);
            }
            tv_money.setText("￥" + trans.totalFee / 100.00 + " ");
            tv_state.setText(Utils.codeToString((int) trans.status));
            tv_time.setText(trans.created);
            if (trans.type == 1) {
                tv_pay_type.setText("支付宝支付");
            } else if (trans.type == 0) {
                tv_pay_type.setText("微信支付");
            } else if (trans.type == 2) {
                tv_pay_type.setText("QQ支付");
            }
            tv_order_id.setText(trans.transactionId);
        }
        if (trans.status == 1) {
            bt_submit.setVisibility(View.VISIBLE);
        } else {
            bt_submit.setVisibility(View.GONE);
            tv_state.setBackgroundResource(R.drawable.shape_circle_black_low);
        }

        if (trans.mobile == null && type.equals("cardPay")) {
            bt_submit.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_back, R.id.bt_submit})
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_submit:
                final Dialog dialog = new Dialog(this, R.style.MyDialog);
                dialog.setContentView(R.layout.dialog_update);
                TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
                tv_title.setText("确认退款？");
                dialog.findViewById(R.id.tv_no).setOnClickListener(
                        new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });
                dialog.findViewById(R.id.tv_yes).setOnClickListener(
                        new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub

                                submit();

                                dialog.dismiss();
                            }
                        });
                dialog.show();
                break;
            default:
                break;
        }
    }

    private void submit() {
        String url;
        if (type.equals("cardPay")) {
            url = Api.VIPPAYRTRANSCANCEL + trans.id;
        } else {
            url = Api.MAIN + "/pc/v1/" + type + "/transCancel/"
                    + trans.id;
        }
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("accessToken", App.getUser().data.accessToken);
        headers.put("code", App.getString("code"));
        headers.put("parentCode", App.getString("parentCode"));
        progress("退款中...");
        BaseRequest.get(url, headers, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgress();
                BaseResponse access = new Gson().fromJson(
                        response.toString(), BaseResponse.class);
                if (access.isSuccess()) {
//                    sendBroadcast(new Intent(GetMoneyFragment.REFRESH));
                    EventBus.getDefault().post(new FirstEvent(0));
                    App.toast("退款成功");
                    finish();
                } else {
                    if (access.getMessage().equals("")) {
                        App.toast("退款失败");
                    } else {
                        App.toast("退款失败，" + access.getMessage());
                    }
                    System.out.println(Utils.objectToJson(access));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgress();
                App.toast("网络未连接");
            }
        });
    }

}
