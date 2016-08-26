package cc.chenghong.vka.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cc.chenghong.vka.adapter.CommonAdapter;
import cc.chenghong.vka.adapter.ViewHolder;
import cc.chenghong.vka.entity.CardPayPost;
import cc.chenghong.vka.entity.FirstEvent;
import cc.chenghong.vka.entity.Ticket;
import cc.chenghong.vka.entity.Ticket.TicketData;
import cc.chenghong.vka.entity.VIP.VIPData;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.response.ListResponse;
import cc.chenghong.vka.response.ObjectResponse;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;
import cc.chenghong.vka.view.RefreshFrameLayout;
import de.greenrobot.event.EventBus;

/**
 * 会员卡支付2015 9 29
 *
 * @author hcl
 */
public class VipPayActivity extends BaseActivity implements OnClickListener {
    @ViewInject(R.id.lv)
    ListView lv;
    @ViewInject(R.id.tv_money)
    TextView tv_money;
    @ViewInject(R.id.tv_integral)
    TextView tv_integral;
    @ViewInject(R.id.tv_balance)
    TextView tv_balance;
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.tv_ticket)
    TextView tv_ticket;

    CommonAdapter<Ticket.TicketData> adapter;
    List<TicketData> list = new ArrayList<Ticket.TicketData>();
    /**
     * 索引list
     */
    List<TicketData> list_index = new ArrayList<TicketData>();

    // 当前第几页
    int page = 0;
    // 一页几条
    int limit = 5;
    // 上下下拉控件
    @ViewInject(R.id.rv)
    RefreshFrameLayout rv;

    VIPData vip;

    /**
     * 是否是扫码支付
     */
    String isCode;
    String password = "";
    /**
     * 原始金额
     */
    Double realMoney;
    /**
     * 折扣后的金额
     */
    Double money;

    private V1Printer printer;
    private ICallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_vip_pay);
        setTitleName("会员卡支付");
        // App1.getInstance().addActivity(this);
        ViewUtils.inject(this);
        initUI();

    }

    private void initAdapter() {
        // TODO Auto-generated method stub
        adapter = new CommonAdapter<TicketData>(this, list,
                R.layout.item_pay_vip) {
            @Override
            public void convert(ViewHolder helper, TicketData item, int position) {
                // TODO Auto-generated method stub
                helper.setText(R.id.tv_ticket, item.name);
                helper.setText(R.id.tv_start_time, item.beginTime + "");
                helper.setText(R.id.tv_end_time, item.endTime + "");
                RelativeLayout rl = helper.getView(R.id.rl);
                if (item.isSelect) {
                    rl.setSelected(true);
                } else {
                    rl.setSelected(false);
                }
            }

        };
        lv.setAdapter(adapter);
    }

    double discount = 1;

    private void initUI() {
        vip = (VIPData) getIntent().getSerializableExtra("vip");
        initRv();
        initAdapter();
        getData();
        password = "";
        String s_money = (String) getIntent().getStringExtra("money")
                .subSequence(1, getIntent().getStringExtra("money").length());
        s_money = getIntent().getStringExtra("money");
        // s_money = s_money.substring(1, s_money.length());
        System.out.println(getIntent().getStringExtra("money"));
        System.out.println("原始金额" + s_money);
        System.out.println("折扣金额" + vip.discount);
        realMoney = new BigDecimal(s_money).doubleValue();
        // 会员折扣后的金额
        if (vip.discount == null || vip.discount.equals("0.0")) {

        } else {
            discount = toTwo(Double.valueOf(vip.discount).doubleValue() / 10);
        }
        money = toTwo(realMoney * discount);
        isCode = getIntent().getStringExtra("isCode");
        if (vip.name.equals("")) {
            tv_name.setVisibility(View.GONE);
        } else {
            tv_name.setText(vip.name + "");
        }
        tv_money.setText("￥" + money + "");
        tv_balance.setText("余额: " + vip.balance);
        tv_integral.setText("积分: " + vip.point + "");

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                TicketData t = list.get(arg2);
                if (t.isSelect) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).isSelect = false;
                    }
                    list_index.clear();
                    money = toTwo(realMoney * discount);
                    tv_money.setText("￥" + money + "");
                } else {
                    if (money == 0.00) {
                        App.toast("支付金额不能小于0！");
                        return;
                    }
                    double d = Double.valueOf(t.gifts);
                    switch (t.ticketType) {
                        case 0:// 抵扣券
                            money -= d;
                            if (money < 0) {
                                money = 0.00;
                            }
                            break;
                        case 1:// 折扣券
                            money = money * d;
                            if (money < 0) {
                                money = 0.00;
                            }
                            break;
                        case 2:// 商品券
                            break;
                        default:
                            break;
                    }
                    tv_money.setText("￥" + toTwo(money) + "");
                    list.get(arg2).isSelect = true;
                    list_index.add(list.get(arg2));
                }
                adapter.notifyDataSetChanged();
            }
        });

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
    }

    private void initRv() {
        rv.openPullDown();
        rv.openPullUp();
        rv.setListViewScrollListener(lv);
        rv.addOnSnapListener(new RefreshFrameLayout.OnSnapListener() {

            @Override
            public void onSnapToTop(int distance) {
                // TODO Auto-generated method stub
                money = toTwo(realMoney * discount);
                tv_money.setText("￥" + money + "");
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

    @OnClick({R.id.iv_back, R.id.bt_submit})
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.iv_back:
                Intent i = new Intent(VipPayActivity.this,
                        VipSweepCardActivity.class);
                setResult(1, i);
                finish();
                break;
            case R.id.bt_submit:
                submit();
                break;
            default:
                break;
        }
    }

    double moneys = 0;

    private void submit() {
        if (getIntent().getStringExtra("isCard").equals("no")) {
            tv_money.setSelected(true);
        } else {
            if (password.equals("")) {
                String passwordPay = App.getUser().passwordPay;
                if (passwordPay == null || passwordPay.equals("1")) {
                    showPassword();
                    return;
                } else {

                }
//				showPassword();
//				return;
            }
//			if (App.getUser().passwordPay == null
//					|| App.getUser().passwordPay.equals("0")) {
//				showPassword();
//				return;
//			}
        }
        String url = Api.VIPPAY;
        // progress("");
        // 头部参数
//		ParamPost headers = new ParamPost();
//		headers.add("Content-Type", "application/json");
//		headers.add("accessToken", App.getUser().data.accessToken);
//		headers.add("parentCode",
//				Utils.getString(getApplicationContext(), "parentCode"));
//		headers.add("code", Utils.getString(getApplicationContext(), "code"));
//		// 提交的参数
//		ParamPost parameter = new ParamPost();
//        if (!password.equals("")) {
//            parameter.add("password", password);
//        }
//        parameter.add("amount", formatDouble(money));
        String tickets = "";
        String ticketDiscount = "";
        moneys = realMoney * discount;
        if (list_index.size() != 0) {
            if (list_index.size() == 1) {
                tickets += list_index.get(0).id;
                ticketDiscount += objectToString(list_index.get(0), moneys);
            } else {
                for (int i = 0; i < list_index.size(); i++) {
                    ticketDiscount += objectToString(list_index.get(i), moneys);
                    if (i == list_index.size() - 1) {
                        tickets += list_index.get(i).id;
                    } else {
                        tickets += list_index.get(i).id + ",";
                    }
                }
            }
//            parameter.add("\"tickets\"", "\"+tickets+\"");
//            parameter.add("\"ticketDiscount", "\"+ticketDiscount+\"");
        }
        CardPayPost cardPost = new CardPayPost();
        cardPost.password = password;
        cardPost.mobile = vip.mobile;
        cardPost.amount = money + "";
        cardPost.tickets = tickets;
        cardPost.vkaAmount = money + "";
        cardPost.total = money + "";
        cardPost.ticketDiscount = ticketDiscount + "";
        cardPost.discount = realMoney * discount - money + "";
        cardPost.levelDiscount = realMoney - (realMoney * discount) + "";
        cardPost.itemSubtotal = money + "";
        String content = Utils.objectToJson(cardPost);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // parameter.add("vkaAmount", formatDouble(money));
        // parameter.add("total", formatDouble(money));
        // parameter.add("discount", formatDouble(realMoney * discount -
        // money));
        // parameter.add("levelDiscount", formatDouble(realMoney
        // - (realMoney * discount)));
        // parameter.add("itemSubtotal", formatDouble(money));
        progress("支付中...");
        BaseRequest.postJson(url, jsonObject, getHeanders(), new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                Log.i(TAG, "onResponse: " + o.toString());
                ObjectResponse<TicketData> obj = new Gson().fromJson(o.toString(), new TypeToken<ObjectResponse<TicketData>>() {
                }.getType());
                if (obj.isSuccess()) {
                    App.toast("支付成功");
                    App.isRefresh = true;
                    App.payType = 3;
//                    sendBroadcast(new Intent(GetMoneyFragment.REFRESH));
                    EventBus.getDefault().post(new FirstEvent(0));
                    // Utils.setString(getApplicationContext(),
                    // "isRefresh", "1");
                    Intent i = new Intent(VipPayActivity.this,
                            VipSweepCardActivity.class);
                    setResult(0, i);
                    App.setString("money_type", "3");
                    // 保存需要打印一次会员支付信息
                    App.setString("cardPrint", "1");
                    // printTicket(obj.data.data);
                    finish();
                } else {
                    if (obj.getMessage() == null || obj.getMessage().equals("")) {
                        toast("支付失败,请检查用户名或密码是否正确");
                    } else {
                        toast("支付失败," + obj.getMessage());
                    }
                    password = "";
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError(volleyError);
            }
        });
    }

    private void getData() {
        // TODO Auto-generated method stub
        String url = "";
        url = Api.MAIN + "/pc/v3/ticket/by/" + vip.mobile + "/" + page
                + "/" + limit;
        progress("加载中...");
        BaseRequest.get(url, getHeanders(), new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                ListResponse<Ticket.TicketData> obj = new Gson().fromJson(o.toString(), new TypeToken<ListResponse<Ticket.TicketData>>() {
                }.getType());
                if (obj.isSuccess()) {
                    if (page == 0 && list != null) {
                        list.clear();
                    }
                    if (obj.data.size() == 0 && page == 0) {
                        App.toast("您还没有任何优惠券噢");
                        return;
                    }
                    if (obj.data.size() == 0) {
                        App.toast("已经加载完所有优惠券了噢");
                        return;
                    }
                    list.addAll(obj.data);
                    adapter.notifyDataSetChanged();
                    tv_ticket.setText("优惠券： " + obj.total + "张");
                } else {
                    App.toast("加载失败" + obj.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError(volleyError);
            }
        });
    }

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
                        // App.toast("密码" + password);
                        tv_money.setSelected(true);
                        submit();
                        dialog.dismiss();
                    }
                });
        openInputMethod(et_password);
        dialog.show();
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

    /**
     * 把double 类型转化为小数点后2位数
     */
    void pointTwo(double d) {
        DecimalFormat df = new DecimalFormat("##.00");
        d = Double.parseDouble(df.format(d));
    }

    /**
     * 把double 类型转化为小数点后2位数
     */
    public double toTwo(double i) {
        double d1 = 100.00;
        d1 = i * d1;
        double a = (double) d1 / 100;
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(a));
    }

    String objectToString(TicketData t, double d) {
        System.out.println("金额变化前" + moneys);
        String s = "";
        s += "★" + t.id + "☆";
        double d1 = 0;
        double d2 = d;
        switch (t.ticketType) {
            case 0:// 抵用券
                if (Double.valueOf(t.gifts) > d) {
                    s += d + "";
                    moneys = 0;
                } else {
                    moneys = d - Double.valueOf(t.gifts).doubleValue();
                    s += (d - Double.valueOf(t.gifts).doubleValue()) + "";
                }
                break;
            case 1:// 折扣券
                // 折扣率
                d1 = Double.valueOf(t.gifts).doubleValue();
                System.out.println("折扣率" + Double.valueOf(t.gifts).doubleValue());
                d2 = d - (d * (d1));
                BigDecimal b = new BigDecimal(d2);
                double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                System.out.println("折扣的钱" + f1);
                s += f1;
                moneys = d * d1;
                System.out.println("金额变化后" + moneys);
                break;
            default:
                break;
        }
        s += "☆" + t.name;
        return s;
    }

    String formatDouble(double d) {
        BigDecimal b = new BigDecimal(d);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1 + "";
    }

    /**
     * 返回按钮事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent i = new Intent(VipPayActivity.this,
                    VipSweepCardActivity.class);
            setResult(1, i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // /**
    // * 打印会员小票
    // */
    // void printCardTicket(CardOrder order) {
    //
    // String body = "会员卡支付";
    // //铭牌名称
    // String brandName = "澄泓信息科技";
    // StringBuilder sb = new StringBuilder();
    // sb.append("   ***" + order.getStoreName() + "*** \n");
    // sb.append("--------------------------------\n");
    // sb.append("消费 \n");
    // sb.append("项目:" + body + "\n");
    // Double dMoney = (double) (order.getTotalFee()/100.00);
    // sb.append("应付金额:" + dMoney + "\n");
    // sb.append("实付金额:" + dMoney + "\n");
    // // sb.append("折扣金额:" + couponFeeText + "\n");
    // sb.append("折扣金额:0\n");
    // sb.append("订单号:" + order.getTransactionId() + "\n");
    // if(order.getNickName() == null){
    // sb.append("付款用户:\n");
    // }else{
    // sb.append("付款用户:" + order.getNickName() + "\n");
    // }
    // sb.append("时间:" + DataUtils.getData() + "\n");
    // sb.append("状态:下单成功并且支付成功" + "\n");
    // sb.append("*" + brandName + "*\n");
    // sb.append("--------------------------------\n");
    // sb.append("  \n");
    // sb.append("*************客户联*************\n");
    // sb.append("   ***" + order.getStoreName() + "*** \n");
    // sb.append("--------------------------------\n");
    // sb.append("消费 \n");
    // sb.append("项目:" + body + "\n");
    // sb.append("应付金额:" + dMoney + "\n");
    // sb.append("实付金额:" + dMoney + "\n");
    // // sb.append("折扣金额:" + couponFeeText + "\n");
    // sb.append("折扣金额:0\n");
    // sb.append("订单号:" + order.getTransactionId() + "\n");
    // if(order.getNickName() == null){
    // sb.append("付款用户:\n");
    // }else{
    // sb.append("付款用户:" + order.getNickName() + "\n");
    // }
    // sb.append("时间:" + order.getCreated() + "\n");
    // sb.append("状态:下单成功并且支付成功" + "\n");
    // sb.append("*" + brandName + "*\n");
    // sb.append("--------------------------------\n");
    // sb.append("  \n");
    // System.out.println(sb.toString());
    // //打印机编号
    // String printerSerialNo = printer.getPrinterSerialNo();
    // if(printerSerialNo == null||printerSerialNo.equals("")){
    // return;
    // }
    // printer.beginTransaction();// 开启事务
    // printer.setAlignment(0);
    // printer.printText(sb.toString());
    // /**
    // * 打印机走纸(强制换行，结束之前的打印内容后走纸n行)
    // *
    // * @param n
    // * : 走纸行数
    // */
    // printer.lineWrap(2);
    // printer.commitTransaction();// 提交事务
    // }
}
