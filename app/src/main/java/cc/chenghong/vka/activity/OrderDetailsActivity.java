package cc.chenghong.vka.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.chenghong.vka.adapter.CommonAdapter;
import cc.chenghong.vka.adapter.ViewHolder;
import cc.chenghong.vka.entity.Order;
import cc.chenghong.vka.entity.Order.OrdersDetailsResponse;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.response.BaseResponse;
import cc.chenghong.vka.response.ListResponse;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;

/**
 * 订单详情2015 9 23
 *
 * @author hcl
 */
public class OrderDetailsActivity extends BaseActivity implements
        OnClickListener {
    TextView tv_state;
    TextView tv_pay_type;
    TextView tv_order_id;
    TextView tv_money;
    TextView tv_time;
    TextView tv_name;
    TextView tv_address;
    LinearLayout ll_state;
    ImageView iv_state;

    ListView lv;
    ImageView iv_back;
    Button bt_submit;

    // 数据list
    List<OrdersDetailsResponse> list = new ArrayList<OrdersDetailsResponse>();
    // 适配器
    CommonAdapter<OrdersDetailsResponse> adapter;

    Order order;

    View lv_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_order_details);
        initUI();
//        initStatusBar();
    }

    private void initUI() {
        // TODO Auto-generated method stub
        setTitleName("订单详情");
        order = (Order) getIntent().getSerializableExtra("order");

        lv_head = getLayoutInflater().from(this).inflate(
                R.layout.head_order_details, null);
        tv_state = (TextView) lv_head.findViewById(R.id.tv_state);
        tv_pay_type = (TextView) lv_head.findViewById(R.id.tv_pay_type);
        tv_order_id = (TextView) lv_head.findViewById(R.id.tv_order_id);
        tv_money = (TextView) lv_head.findViewById(R.id.tv_money);
        tv_time = (TextView) lv_head.findViewById(R.id.tv_time);
        tv_name = (TextView) lv_head.findViewById(R.id.tv_name);
        tv_address = (TextView) lv_head.findViewById(R.id.tv_address);
        ll_state = (LinearLayout) lv_head.findViewById(R.id.ll_state);
        iv_state = (ImageView) lv_head.findViewById(R.id.iv_state);
        if (order.status != 0 && order.status != 1) {
            iv_state.setVisibility(View.GONE);
        } else {
            iv_state.setVisibility(View.VISIBLE);
        }
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_order);
        dialog.findViewById(R.id.tv_1).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        if (order.status == 1) {
                            App.toast("已经在派送中了噢");
                            dialog.dismiss();
                            return;
                        }
                        updataState(1);
                        dialog.dismiss();
                    }
                });

        dialog.findViewById(R.id.tv_2).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        updataState(2);
                        dialog.dismiss();
                    }
                });

        // 保存当前点击的订单状态
        String state = "";
        if (order.status == 0 || order.status == 1) {
            ll_state.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    dialog.show();
                }
            });
        }

        lv = (ListView) findViewById(R.id.lv);
//		iv_back = (ImageView) findViewById(R.id.iv_back);
        bt_submit = (Button) findViewById(R.id.bt_submit);
//		iv_back.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        list = order.ordersDetailsList;
        tv_money.setText("￥" + order.amount + "");
        tv_order_id.setText(order.outTradeNo);
        tv_time.setText(order.created);
        tv_name.setText(" " + order.mobile);
        tv_address.setText(order.address);
        switch (order.orderSource) {
            case 0:
                tv_pay_type.setText("POS机");
                break;
            case 1:
                tv_pay_type.setText("微商城");
                break;
            default:
                break;
        }
        if (order.isTakeaway == 1) {
            switch (order.status) {
                case 0:
                    tv_state.setBackgroundResource(R.drawable.shape_circle_theme);
                    tv_state.setText("未派送");
                    break;
                case 1:
                    tv_state.setBackgroundResource(R.drawable.shape_circle_purple);
                    tv_state.setText("派送中");
                    break;
                case 2:
                    tv_state.setBackgroundResource(R.drawable.shape_circle_black_low);
                    tv_state.setText("已成功");
                    bt_submit.setText("退款");
                    break;
                case 3:
                    tv_state.setBackgroundResource(R.drawable.shape_circle_skyblue);
                    tv_state.setText("已退单");
                    bt_submit.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } else {
            switch (order.payStatus) {
                case 0:
                    tv_state.setBackgroundResource(R.drawable.shape_circle_theme);
                    tv_state.setText("未付款");
                    break;
                case 1:
                    tv_state.setBackgroundResource(R.drawable.shape_circle_black_low);
                    tv_state.setText("已付款");
                    break;
                case 2:
                    tv_state.setBackgroundResource(R.drawable.shape_circle_skyblue);
                    tv_state.setText("已退单");
                    break;
                default:
                    break;
            }
        }
        lv.addHeaderView(lv_head);
        initAdapter();
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
//		case R.id.iv_back:
//			finish();
//			break;
            case R.id.bt_submit:
                submit();
                break;
            default:
                break;
        }
    }

    private void initAdapter() {
        // TODO Auto-generated method stub
        adapter = new CommonAdapter<OrdersDetailsResponse>(this, list,
                R.layout.item_order_details) {
            @Override
            public void convert(ViewHolder helper, OrdersDetailsResponse item,
                                int position) {
                // TODO Auto-generated method stub
                helper.setText(R.id.tv_name, item.productName);
                helper.setText(R.id.tv_count, "X" + item.count);
                helper.setText(R.id.tv_money, item.price + "");
            }
        };
        // 绑定适配器
        lv.setAdapter(adapter);
    }

    private void submit() {
        // TODO Auto-generated method stub
        String api = Api.ORDERCANCEL + order.id;
        progress("退单中...");
        Map map = new HashMap();
        map.put("accessToken", App.getUser().data.accessToken);
        map.put("code", Utils.getString(getApplicationContext(), "code"));
        map.put("parentCode",
                Utils.getString(getApplicationContext(), "parentCode"));
        BaseRequest.get(api, map, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                ListResponse<Order> obj = new Gson().fromJson(o.toString(), new TypeToken<ListResponse<Order>>() {
                }.getType());
                if (obj.isSuccess()) {
                    App.toast("订单已取消！");
                    finish();
                } else {
                    App.toast(obj.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideProgress();
                toask(volleyError.toString());
            }
        });
//        ParamPost headers = new ParamPost();
//        headers.add("accessToken", App.getUser().data.accessToken);
//        BaseService.get(api, new ParamPost(), headers,
//                new ServiceCallback<ArrayListResponse<Order>>() {
//                    @Override
//                    public void done(int what,
//                                     ServiceResponse<ArrayListResponse<Order>> obj) {
//                        hideProgress();
//                        // TODO Auto-generated method stub
//                        if (obj.isResponseSuccess()) {
//                            App.toast("订单已取消！");
//                            finish();
//                        } else {
//                            App.toast(obj.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void error(String msg) {
//                        // TODO Auto-generated method stub
//                        hideProgress();
//                        App.toast(msg);
//                    }
//                });
    }

    // 修改状态
    private void updataState(int i) {
        // TODO Auto-generated method stub
        final int a = i;
        String api = Api.UPDATAORDERSTATE + order.id + "/" + i;
        progress("修改中...");
        String s = App.getUser().data.accessToken;
        System.out.println(s);
        Map map = new HashMap();
        map.put("accessToken", App.getUser().data.accessToken);
        BaseRequest.get(api, map, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                BaseResponse obj = new Gson().fromJson(o.toString(), new TypeToken<BaseResponse>() {
                }.getType());
                if (obj.isSuccess()) {
                    App.toast("订单已取消！");
                    finish();
                } else {
                    App.toast(obj.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideProgress();
                toask(volleyError.toString());
            }
        });
//        BaseService.get(api, new ParamPost(), new ParamPost().add("accessToken",
//                App.getUser().data.accessToken),
//                new ServiceCallback<BeanResponse>() {
//
//                    @Override
//                    public void done(int what, ServiceResponse<BeanResponse> obj) {
//                        // TODO Auto-generated method stub
//                        hideProgress();
//                        if (obj.isResponseSuccess()) {
//                            App.toast("修改成功");
//                            Utils.setString(getApplicationContext(), "isRefresh", "1");
////							if (a == 1) {
////								tv_state.setText("派送中");
////							} else {
////								tv_state.setText("已成功");
////							}
//                            finish();
//                        } else {
//                            System.out.println(Utils.objectToJson(obj));
//                            App.toast(Utils.codeToString(obj.data.code));
//                        }
//                    }
//
//                    @Override
//                    public void error(String msg) {
//                        // TODO Auto-generated method stub
//                        hideProgress();
//                        App.toast(msg);
//                    }
//                });
    }
}
