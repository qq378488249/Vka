package cc.chenghong.vka.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cc.chenghong.vka.adapter.CommonAdapter;
import cc.chenghong.vka.adapter.ViewHolder;
import cc.chenghong.vka.entity.AmountConfig;
import cc.chenghong.vka.entity.RechargeOrder;
import cc.chenghong.vka.entity.VIP.VIPData;
import cc.chenghong.vka.entity.VIPRechargePost;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.response.ListResponse;
import cc.chenghong.vka.response.ObjectResponse;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.util.DataUtils;
import cc.chenghong.vka.util.MathUtils;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;
import cc.chenghong.vka.view.RefreshFrameLayout;

/**
 * 会员充值2015 10 7
 *
 * @author hcl
 */
public class VipRechargeActivity extends BaseActivity implements
        OnClickListener {
    @ViewInject(R.id.et_money)
    EditText et_money;

    @ViewInject(R.id.lv)
    ListView lv;
    // 交易类型
    String type = "";
    // 当前第几页
    int page = 0;
    // 一页几条
    int limit = 10;
    // 上下下拉控件
    @ViewInject(R.id.rv)
    RefreshFrameLayout rv;
    // 数据list
    List<AmountConfig> list = new ArrayList<AmountConfig>();
    // 适配器
    CommonAdapter<AmountConfig> adapter;

    AmountConfig amount = null;

    VIPData data;

    private V1Printer printer;
    private ICallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_vip_recharge);
        ViewUtils.inject(this);
        setTitleName("会员充值");
        getData();
        initRv();
        initAdapter();
        printer = new V1Printer(this);
        callback = new ICallback() {
            /**
             * 返回执行结果
             *
             * @param isSuccess
             *            : true执行成功，false 执行失败
             */
            @Override
            public void onRunResult(boolean isSuccess) {// 打印后执行的返回码
                Log.i(TAG, "onRunResult:" + isSuccess);
            }

            /**
             * 返回结果(字符串数据)
             *
             * @param result
             *            : 结果
             */
            @Override
            public void onReturnString(String result) {
                Log.i(TAG, "onReturnString:" + result);
            }

            /**
             * 执行发生异常 code： 异常代码 msg: 异常描述
             */
            @Override
            public void onRaiseException(int code, String msg) {
                Log.i(TAG, "onRaiseException:" + code + ":" + msg);
            }

        };

        printer.setCallback(callback);// 设置打印机回调
        // et_money.setText("1001008");
    }

    @Override
    public void clickLeft() {
        super.clickLeft();
        Utils.closeJP(et_money);
    }

    private void initRv() {
        // TODO Auto-generated method stub
        rv.openPullDown();
        rv.openPullUp();
        rv.setListViewScrollListener(lv);
        rv.addOnSnapListener(new RefreshFrameLayout.OnSnapListener() {

            @Override
            public void onSnapToTop(int distance) {
                // TODO Auto-generated method stub
                page = 0;
                getData();
            }

            @Override
            public void onSnapToBottom(int distance) {
                // TODO Auto-generated method stub
                page++;
                getData();
            }
        });
    }

    private void initAdapter() {
        // TODO Auto-generated method stub
        adapter = new CommonAdapter<AmountConfig>(this, list,
                R.layout.item_vip_recharge) {

            @Override
            public void convert(ViewHolder helper, AmountConfig item,
                                int position) {
                // TODO Auto-generated method stub
                ImageView iv = helper.getView(R.id.iv);
                LinearLayout ll = helper.getView(R.id.ll);
                TextView tv_1 = helper.getView(R.id.tv_1);
                TextView tv_4 = helper.getView(R.id.tv_4);
                TextView tv_3 = helper.getView(R.id.tv_3);
                TextView tv_2 = helper.getView(R.id.tv_2);
                switch (position % 5) {
                    case 0:
                        ll.setBackgroundResource(R.drawable.shape_0);
                        // tv_1.setTextColor(getResources().getColor(R.color.color0));
                        // tv_2.setTextColor(getResources().getColor(R.color.color0));
                        // tv_3.setTextColor(getResources().getColor(R.color.color0));
                        // tv_4.setTextColor(getResources().getColor(R.color.color0));
                        break;
                    case 1:
                        ll.setBackgroundResource(R.drawable.shape_1);
                        // tv_1.setTextColor(getResources().getColor(R.color.color1));
                        // tv_2.setTextColor(getResources().getColor(R.color.color1));
                        // tv_3.setTextColor(getResources().getColor(R.color.color1));
                        // tv_4.setTextColor(getResources().getColor(R.color.color1));

                        break;
                    case 2:
                        ll.setBackgroundResource(R.drawable.shape_2);
                        // tv_1.setTextColor(getResources().getColor(R.color.sky_blue));
                        // tv_2.setTextColor(getResources().getColor(R.color.sky_blue));
                        // tv_3.setTextColor(getResources().getColor(R.color.sky_blue));
                        // tv_4.setTextColor(getResources().getColor(R.color.sky_blue));

                        break;
                    case 3:
                        ll.setBackgroundResource(R.drawable.shape_3);
                        // tv_1.setTextColor(getResources().getColor(R.color.color3));
                        // tv_2.setTextColor(getResources().getColor(R.color.color3));
                        // tv_3.setTextColor(getResources().getColor(R.color.color3));
                        // tv_4.setTextColor(getResources().getColor(R.color.color3));

                        break;
                    case 4:
                        ll.setBackgroundResource(R.drawable.shape_4);
                        // tv_1.setTextColor(getResources().getColor(R.color.color4));
                        // tv_2.setTextColor(getResources().getColor(R.color.color4));
                        // tv_3.setTextColor(getResources().getColor(R.color.color4));
                        // tv_4.setTextColor(getResources().getColor(R.color.color4));

                        break;
                    default:
                        break;
                }
                if (!item.isSelect) {
                    iv.setVisibility(View.GONE);
                } else {
                    iv.setVisibility(View.VISIBLE);
                }
                helper.setText(R.id.tv_money, "￥" + MathUtils.removeZero(item.amount));
//                if (item.amount.doubleValue() < 1) {
//                    int zeroIndex = 0;
//                    String s = item.amount.doubleValue() + "";
//                    if(s.indexOf(".") > 0){
//                        //正则表达
//                        s = s.replaceAll("0+?$", "");//去掉后面无用的零
//                        s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
//                    }
//                    for (int i = s.length(); i > -1; i--) {
//                        if (s.charAt(i) == '0') {//判断最后1个零出现的位置
//                            zeroIndex = i;
//                        }
//                    }
//                } else {
//                    String s = item.amount.doubleValue() + "";
//                    String[] s1 = s.split("\\.");
//                    if (s1.length > 1) {
//                        if (s1[1].equals("00") || s1[1].equals("0")) {
//                            helper.setText(R.id.tv_money, "￥" + s1[0]);
//                        }
//                    } else {
//                        helper.setText(R.id.tv_money, "￥" + s);
//                    }
//                }
//				helper.setText(R.id.tv_money, "￥" + item.amount + "");
                if (item.give.intValue() != 0) {
                    helper.setText(R.id.tv_1, "金额：" + item.give.intValue());
                } else {
                    tv_1.setVisibility(View.GONE);
                }
                if (item.point.intValue() != 0) {
                    helper.setText(R.id.tv_2, "积分：" + item.point.intValue());

                } else {
                    tv_2.setVisibility(View.GONE);
                }
                if (item.ticketNames != null && !item.ticketNames.equals("")) {
                    String s[] = item.ticketNames.split(",");
                    for (int i = 0; i < s.length; i++) {
                        if (i == 0) {
                            tv_3.setText("" + s[0]);
                            tv_4.setVisibility(View.GONE);
                        }
                        if (i == 1) {
                            tv_4.setVisibility(View.VISIBLE);
                            tv_4.setText("" + s[1]);
                        }
                    }
                } else {
                    tv_3.setVisibility(View.GONE);
                    tv_4.setVisibility(View.GONE);
                }
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Utils.closeKeyboard(VipRechargeActivity.this, et_money);
                if (list.get(arg2).isSelect) {
                    list.get(arg2).isSelect = false;
                    amount = null;
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).isSelect = false;
                    }
                    amount = list.get(arg2);
                    list.get(arg2).isSelect = true;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getData() {
        // TODO Auto-generated method stub
        String api = Api.FINDVIPCOMBO + page + "/" + limit;
        BaseRequest.get(api, getHeanders(), new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                ListResponse<AmountConfig> obj = new Gson().fromJson(o.toString(), new TypeToken<ListResponse<AmountConfig>>() {
                }.getType());
                if (obj.isSuccess()) {
                    if (page == 0 && list != null) {
                        list.clear();
                    }
                    if (obj.data.size() == 0) {
                        App.toast("已经加载完所有数据了噢");
                        return;
                    }
                    list.addAll(obj.data);
                    adapter.notifyDataSetChanged();
                } else {
                    App.toast(Utils.codeToString(obj.code));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError(volleyError);
            }
        });

    }

    @OnClick({R.id.iv_back, R.id.bt_submit})
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.bt_submit:
                if (Utils.isNull(et_money)) {
                    App.toast("请输入会员卡号");
                    return;
                }
                if (amount == null) {
                    App.toast("请选择充值套餐");
                    return;
                }
                Utils.closeKeyboard(this, et_money);
                find_submit();
                break;
            case R.id.iv_back:
                Utils.closeKeyboard(getApplicationContext(), et_money);
                finish();
                break;
        }
    }

    private void submit() {
        // TODO Auto-generated method stub
        String api = Api.VIPRECHARGE;
//        ParamPost head = new ParamPost();
//        head.add("Content-Type", "Application/JSON");
//        head.add("accessToken", App.getUser().data.accessToken);
//        head.add("code", Utils.getString(getApplicationContext(), "code"));
//        head.add("parentCode",
//                Utils.getString(getApplicationContext(), "parentCode"));

        VIPRechargePost post = new VIPRechargePost();
        post.mobile = Utils.getValue(et_money);
        // post.cashAmount = amount.amount + "";
        // post.cashAmount = amount.amount + "";
        // post.itemSubtotal = amount.amount + "";
        post.strPayType = "CASH";
        post.configId = amount.id;
        String json = Utils.objectToJson(post);
        progress("充值中...");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        BaseRequest.postJson(api, jsonObject, getHeanders(), new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                ObjectResponse<RechargeOrder> obj = new Gson().fromJson(o.toString(), new TypeToken<ObjectResponse<RechargeOrder>>() {
                }.getType());
                if (obj.isSuccess()) {
                    App.toast("充值成功");
                    Utils.setString(getApplicationContext(),
                            "isRecharge", "1");
                    Utils.setString(getApplicationContext(), "cardId",
                            data.mobile);
                    startActivity(new Intent(VipRechargeActivity.this,
                            VipFindActivity.class));
                    if (printer.getPrinterSerialNo() != null
                            && !printer.getPrinterSerialNo().equals("")) {
                        printRecharge(obj.data);
                    }
                    finish();
                } else {
                    App.toast("充值失败" + Utils.codeToString(obj.data.code));
                    // System.out.println(Utils.objectToJson(obj));
                    // App.toast(obj.data.code+"2");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError(volleyError);
            }
        });
//        BaseService.post(api, new ParamPost(), head, "POST", json,
//                new ServiceCallback<RechargeOrder>() {
//
//                    @Override
//                    public void done(int what,
//                                     ServiceResponse<RechargeOrder> obj) {
//                        // TODO Auto-generated method stub
//                        hideProgress();
//                        if (obj.data.isSuccess()) {
//                            App.toast("充值成功");
//                            Utils.setString(getApplicationContext(),
//                                    "isRecharge", "1");
//                            Utils.setString(getApplicationContext(), "cardId",
//                                    data.mobile);
//                            startActivity(new Intent(VipRechargeActivity.this,
//                                    VipFindActivity.class));
//                            if (printer.getPrinterSerialNo() != null
//                                    && !printer.getPrinterSerialNo().equals("")) {
//                                printRecharge(obj.data.data);
//                            }
//                            finish();
//                        } else {
//                            App.toast(Utils.codeToString(obj.data.code));
//                            // System.out.println(Utils.objectToJson(obj));
//                            // App.toast(obj.data.code+"2");
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

    private void find_submit() {
        // TODO Auto-generated method stub
//        ParamPost headers = new ParamPost();
//        headers.add("accessToken", App.getUser().data.accessToken);
//        headers.add("parentCode",
//                Utils.getString(getApplicationContext(), "parentCode"));
//        headers.add("code", Utils.getString(getApplicationContext(), "code"));
        String api = Api.FINDVIP + Utils.getValue(et_money) + "/mobile";
        // progress("正在查询...");

        BaseRequest.get(api, getHeanders(), new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                ListResponse<VIPData> obj = new Gson().fromJson(o.toString(), new TypeToken<ListResponse<VIPData>>() {
                }.getType());
                if (obj.isSuccess()) {
                    if (obj.data == null
                            || obj.data.size() == 0) {
                        App.toast("未找到该会员！");
                        return;
                    }
                    data = obj.data.get(0);
                    showDialog();
                } else {
                    App.toast(obj.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError(volleyError);
            }
        });
//        BaseService.get(api, new ParamPost(), headers,
//                new ServiceCallback<ArrayListResponse<VIPData>>() {
//                    @Override
//                    public void done(int what,
//                                     ServiceResponse<ArrayListResponse<VIPData>> obj) {
//                        // TODO Auto-generated method stub
//                        if (obj.isResponseSuccess()) {
//                            if (obj.data.data == null
//                                    || obj.data.data.size() == 0) {
//                                App.toast("未找到该会员！");
//                                return;
//                            }
//                            data = obj.data.data.get(0);
//                            showDialog();
//                        } else {
//                            App.toast(obj.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void error(String msg) {
//                        // TODO Auto-generated method stub
//                        App.toast(msg);
//                    }
//                });
    }

    Dialog dialog;

    void showDialog() {
        dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_affirm);
        TextView tv_name = (TextView) dialog.findViewById(R.id.tv_name);
        TextView tv_card = (TextView) dialog.findViewById(R.id.tv_card);
        TextView tv_money = (TextView) dialog.findViewById(R.id.tv_money);
        if (data.name == null || data.name.equals("")) {
            tv_name.setVisibility(View.GONE);
        } else {
            tv_name.setText("会员姓名：" + data.name);
        }
        tv_card.setText("会员卡号：" + data.mobile);
        tv_money.setText("充值金额：" + amount.amount);
        dialog.findViewById(R.id.tv_yes).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        submit();
                        closeDialog();
                    }
                });
        dialog.findViewById(R.id.tv_no).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        closeDialog();
                    }
                });
        dialog.show();
    }

    void closeDialog() {
        dialog.dismiss();
    }

    /**
     * 打印会员充值小票
     */
    void printRecharge(RechargeOrder order) {
        // 充值金额
        String rechargeMoney = "";
        // 赠送金额
        String giveMoney = "";
        // 赠送券
        String giveTicket = "";

        for (int i = 0; i < list.size(); i++) {
            AmountConfig amountConfig = list.get(i);
            if (amountConfig.isSelect) {
                rechargeMoney = (amountConfig.amount.doubleValue())
                        + "";
                if (amountConfig.ticketNames != null) {
                    giveTicket = amountConfig.ticketNames;
                }
                if (giveMoney != null) {
                    giveMoney = amountConfig.give + "";
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("   ***" + order.getStoreName() + "*** \n");
        sb.append("--------------------------------\n");
        sb.append("会员卡号:" + order.getMobile() + "\n");
        sb.append("充值金额:" + rechargeMoney + "\n");
        sb.append("赠送金额:" + giveMoney + "\n");
        sb.append("卡内余额:" + order.getBalance() + "\n");
        sb.append("卡内积分:" + order.getPoint() + "\n");
        sb.append("赠送券:" + giveTicket + "\n");
        sb.append("充值时间:" + DataUtils.getData() + "\n");
        sb.append("状态:充值成功" + "\n");
        sb.append(App.brandName);
        sb.append("--------------------------------\n");
        sb.append("\n");
        sb.append("*************客户联*************\n");
        sb.append("   ***" + order.getStoreName() + "*** \n");
        sb.append("--------------------------------\n");
        sb.append("会员卡号:" + order.getMobile() + "\n");
        sb.append("充值金额:" + rechargeMoney + "\n");
        sb.append("赠送金额:" + giveMoney + "\n");
        sb.append("卡内余额:" + order.getBalance() + "\n");
        sb.append("卡内积分:" + order.getPoint() + "\n");
        sb.append("赠送券:" + giveTicket + "\n");
        sb.append("充值时间:" + DataUtils.getData() + "\n");
        sb.append("状态:充值成功" + "\n");
        sb.append(App.brandName);
        sb.append("--------------------------------\n");
        sb.append("\n");

        printer.beginTransaction();
        printer.printText(sb.toString());
        printer.lineWrap(2);
        printer.commitTransaction();
    }
}
