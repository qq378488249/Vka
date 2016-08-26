package cc.chenghong.vka.fragment;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cc.chenghong.vka.activity.Get_Money_DetailsActivity;
import cc.chenghong.vka.activity.MainActivity;
import cc.chenghong.vka.activity.R;
import cc.chenghong.vka.activity.VipSweepCardActivity;
import cc.chenghong.vka.adapter.CommonAdapter;
import cc.chenghong.vka.adapter.ViewHolder;
import cc.chenghong.vka.code.qr_codescan.MipcaActivityCapture;
import cc.chenghong.vka.entity.FirstEvent;
import cc.chenghong.vka.entity.SuccessOrder;
import cc.chenghong.vka.entity.Trans;
import cc.chenghong.vka.entity.Trans.TransData;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.response.BaseResponse;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;

import cc.chenghong.vka.view.RefreshFrameLayout;
import de.greenrobot.event.EventBus;

/**
 * 收银详情2015 9 24
 *
 * @author hcl
 */
public class GetMoneyFragment extends BaseFragment implements OnClickListener {
    public static final String TAG = "GetMoneyFragment";
    /**
     * 没数据
     */
    @ViewInject(R.id.ll_no_data)
    private LinearLayout ll_no_data;
    // 收银按钮
    @ViewInject(R.id.bt_submit)
    Button bt_submit;
    // 收银金额
    @ViewInject(R.id.et_money)
    EditText et_money;
    //
    PopupWindow popupWindow;
    // 支付宝，微信，会员卡,qq
    @ViewInject(R.id.tv_1)
    TextView tv_1;
    @ViewInject(R.id.tv_2)
    TextView tv_2;
    @ViewInject(R.id.tv_3)
    TextView tv_3;
    @ViewInject(R.id.tv_4)
    TextView tv_4;
    @ViewInject(R.id.tv_title)
    TextView tv_title;

    @ViewInject(R.id.lv)
    ListView lv;
    // 交易类型（默认微信）
    String type = "wxPay";
    // 当前第几页
    int page = 0;
    // 一页几条
    int limit = 10;
    // 上下下拉控件
    @ViewInject(R.id.rv)
    RefreshFrameLayout rv;
    // 数据list
    List<Trans.TransData> list = new ArrayList<Trans.TransData>();
    // 适配器
    CommonAdapter<Trans.TransData> adapter;
    // 判断是否是第一次加载
    int index = 0;
    // 付款金额（以分为单位）
    String total_fee;
    /**
     * 支付码
     */
    private String payCode = "";
    private V1Printer printer;
    private ICallback callback;
    /**
     * 刷新广播
     */
    public static String REFRESH = "refresh";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_get_money, container,
                false);
        ViewUtils.inject(this, view);// 初始化xUtils
        initView();
        return view;
    }

    private void select_tv(final int i) {
        switch (i) {
            case 1:
                selectTv(tv_1);
                break;
            case 2:
                selectTv(tv_2);
                break;
            case 3:
                selectTv(tv_3);
                break;
            case 5:
                selectTv(tv_4);
                break;
            default:
                selectTv(tv_1);
                break;
        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1.getAction().equals(REFRESH)) {
                page = 0;
                getListViewData(false);
            }
        }
    };

    // 初始化控件
    private void initView() {
        // TODO Auto-generated method stub
        getActivity().registerReceiver(mBroadcastReceiver,
                new IntentFilter(REFRESH));
        if (Api.MAIN.indexOf("v-ka") != -1) {
            tv_title.setText("收银（正式版）");
        } else {
            tv_title.setText("收银（测试版）");
        }
        String money_type = App.getString("money_type");
        int my_index = 1;
        App.payType = 2;
        if (money_type != null && !"".equals(money_type)) {
            my_index = Integer.valueOf(money_type);
            App.payType = my_index;
        }
        switch (my_index) {
            case 1:
                tv_1.setSelected(true);
                break;
            case 2:
                tv_2.setSelected(true);
                break;
            case 3:
                tv_3.setSelected(true);
                break;
            case 4:
                tv_4.setSelected(true);
                break;
            default:
                tv_1.setSelected(true);
                break;
        }
        EventBus.getDefault().register(this);// 注册EventBus
        select_tv(App.payType);
        initAdapter();
        lv.setAdapter(adapter);
        // 列表点击事件
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Utils.setString(getActivity().getApplicationContext(),
                        "payType", type);
                Intent i = new Intent(getActivity(),
                        Get_Money_DetailsActivity.class);
                i.putExtra("trans", list.get(arg2));
                startActivity(i);
            }
        });
        getListViewData(true);
        rv.openPullDown();
        rv.openPullUp();
        rv.setListViewScrollListener(lv);
        rv.addOnSnapListener(new RefreshFrameLayout.OnSnapListener() {
            @Override
            public void onSnapToTop(int distance) {
                // 下拉刷新, 重新加载第一页
                page = 0;
                index = 0;
                getListViewData(true);
            }

            @Override
            public void onSnapToBottom(int distance) {
                // 上拉加载更多
                page++;
                index = 0;
                getListViewData(true);
            }
        });

        printer = new V1Printer(getActivity());
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

    // 初始化适配器
    private void initAdapter() {
        // TODO Auto-generated method stub
        adapter = new CommonAdapter<TransData>(getActivity(), list,
                R.layout.item_get_money) {
            @Override
            public void convert(ViewHolder helper, TransData item, int position) {
                // TODO Auto-generated method stub
                ImageView iv = helper.getView(R.id.iv);
                RelativeLayout rv = helper.getView(R.id.rv);
                if (type.equals("aliPay")) {
                    iv.setBackgroundResource(R.drawable.zfb);
                } else if (type.equals("wxPay")) {
                    iv.setBackgroundResource(R.drawable.wx);
                } else if (type.equals("cardPay")) {
                    iv.setBackgroundResource(R.drawable.zh);
                } else if (type.equals("qqPay")) {
                    iv.setBackgroundResource(R.drawable.qq1);
                }
                if (type.equals("cardPay")) {
                    if (item.mobile == null || item.mobile.equals("")) {
                        helper.setText(R.id.tv_number, "该会员已被删除");
                        rv.setBackgroundResource(R.color.gray_low);
                    } else {
                        if (item.name == null || item.name.equals("")) {
                            helper.setText(R.id.tv_number, item.mobile);
                        } else {
                            helper.setText(R.id.tv_number, item.mobile
                                    + formatVipName(item.name));
                        }
                        rv.setBackgroundResource(R.color.white);
                    }
                    helper.setText(R.id.tv_time, item.createdDate);
                    helper.setText(R.id.tv_money, item.total + "");
                } else {
                    rv.setBackgroundResource(R.color.white);
                    if (item.transactionId != null) {
                        helper.setText(R.id.tv_number,
                                formatId(item.transactionId));
                    } else {
                        helper.setText(R.id.tv_number, item.transactionId);
                    }
                    if (item.nickName == null) {
                        item.nickName = "";
                    }
                    helper.setText(R.id.tv_time, item.created + "  "
                            + formatName(item.nickName));
                    helper.setText(R.id.tv_money, item.totalFee / 100.00 + "");
                }
                switch ((int) item.status) {
                    case 0:
                        helper.setText(R.id.tv_state, "交易中");
                        break;
                    case 1:
                        helper.setText(R.id.tv_state, "交易成功");
                        break;
                    case 2:
                        helper.setText(R.id.tv_state, "已退款");
                        break;
                    default:
                        break;
                }
            }
        };
    }

    // 获取列表数据
    private void getListViewData(final boolean b) {
        // TODO Auto-generated method stub
        int payType = App.payType;
        select_tv(payType);
        switch (payType) {
            case 1:
                type = "aliPay";
                selectTv(tv_1);
                break;
            case 2:
                type = "wxPay";
                selectTv(tv_2);
                break;
            case 3:
                type = "cardPay";
                selectTv(tv_3);
                break;
            case 4:
                type = "qqPay";
                selectTv(tv_4);
                break;
            default:
                type = "wxPay";
                selectTv(tv_2);
                break;
        }
        String url = Api.MAIN + "/pc/v1/" + type + "/trans/" + page
                + "/" + limit;
        if (b) {
            ((MainActivity) getActivity()).progress("加载中...");
        }
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("accessToken", App.getUser().data.accessToken);
        headers.put("code", App.getString("code"));
        headers.put("parentCode", App.getString("parentCode"));

        BaseRequest.get(url, getBaseActivity().getHeanders(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ((MainActivity) getActivity()).hideProgress();
                System.out.println(response);
                Trans access = new Gson().fromJson(response, Trans.class);
                if (access.code == 200) {
                    if (adapter == null) {
                        initAdapter();
                    }
                    if (access.data.size() == 0) {
                        if (page == 0) {
                            list.clear();
                        }
                        // if (b)
                        App.toast("暂无订单信息");
                    } else {
                        if (page == 0) {
                            list.clear();
                            list.addAll(access.data);
                        } else {
                            list.addAll(access.data);
                        }
                        // if (b)
                        // App.toast("加载成功");
                    }
                    if (list.size() == 0) {
                        ll_no_data.setVisibility(View.VISIBLE);
                    } else {
                        ll_no_data.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                    if (App.getString("cardPrint").equals("1")) {
                        et_money.setText("");
                        if (printer.getPrinterSerialNo() != null
                                && !printer.getPrinterSerialNo().equals("")) {
                            printCardTicket((TransData) list.get(0));
                        }
                    }
                } else {
                    if (access.getMessage().indexOf("token") != -1) {
                        App.toast("登录信息失效，请重新登录");
                    } else {
                        App.toast(access.getMessage());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgress();
                App.toast("连接错误，" + error.getMessage());
            }
        });
        if (index == 0) {
            ((MainActivity) getActivity()).progress("加载中...");
            index++;
        }
    }

    @OnClick({R.id.bt_submit, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.ll,
            R.id.tv_4})
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.bt_submit:
                if (Utils.isNull(et_money)) {
                    App.toast("请输入收银金额");
                    return;
                }
                if (!checkMoney()) {
                    App.toast("您的输入有误噢（最多只能输入2位小数）");
                    return;
                }
                // if (!App.getRequest()) {
                // App.toast("上一笔订单还没有支付完成噢");
                // return;
                // }
                App.ap_money = et_money.getText().toString();
                hintKb();
                showWindow(arg0);
                break;
            case R.id.tv_1:
                if (tv_1.isSelected()) {
                    return;
                }
                App.setString("money_type", "1");
                App.payType = 1;
                page = 0;
                selectTv(tv_1);
                type = "aliPay";
                getListViewData(true);
                break;
            case R.id.tv_2:
                if (tv_2.isSelected()) {
                    return;
                }
                App.setString("money_type", "2");
                App.payType = 2;
                page = 0;
                selectTv(tv_2);
                type = "wxPay";
                getListViewData(true);
                break;
            case R.id.tv_3:
                if (tv_3.isSelected()) {
                    return;
                }
                App.setString("money_type", "3");
                App.payType = 3;
                page = 0;
                type = "cardPay";
                selectTv(tv_3);
                getListViewData(true);
                break;
            case R.id.tv_4:
                if (tv_4.isSelected()) {
                    return;
                }
                App.setString("money_type", "4");
                App.payType = 4;
                page = 0;
                type = "qqPay";
                selectTv(tv_4);
                getListViewData(true);
                break;
            case R.id.ll:// 这2行代码千万别删，否则会有严重BUG
                break;
            default:
                break;
        }

    }

    private String stringToDouble() {
        // TODO Auto-generated method stub
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(Double.valueOf(Utils.getValue(et_money)));
    }

    View v;

    /**
     * 显示选择支付方式
     *
     * @param parent
     */
    private void showWindow(View parent) {
        WindowManager windowManager = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.dialog_select_pay, null);
            popupWindow = new PopupWindow(v, windowManager.getDefaultDisplay()
                    .getWidth(),
                    windowManager.getDefaultDisplay().getHeight() / 2, true);
        }
        TextView tv_money = (TextView) v.findViewById(R.id.tv_money);
        tv_money.setText("￥" + stringToDouble());
        // 使其获取焦点
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 显示的位置为:屏幕的高度的10分之4
        int xPos = (windowManager.getDefaultDisplay().getHeight()) / 5;
        BigDecimal b1 = new BigDecimal(Utils.getValue(et_money))
                .multiply(new BigDecimal(100));
        total_fee = String.valueOf(b1.intValue());
        // 支付宝支付
        v.findViewById(R.id.ll_zfb).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                App.payType = 1;
                type = "aliPay";
                Intent i = new Intent(getActivity(), MipcaActivityCapture.class);
                startActivity(i);
                popupWindow.dismiss();
            }
        });
        // 微信支付
        v.findViewById(R.id.ll_wx).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                App.payType = 2;
                type = "wxPay";
                Intent i = new Intent(getActivity(), MipcaActivityCapture.class);
                startActivity(i);
                popupWindow.dismiss();
            }
        });
        // qq支付
        v.findViewById(R.id.ll_qq).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                App.payType = 4;
                type = "qqPay";
                Intent i = new Intent(getActivity(), MipcaActivityCapture.class);
                startActivity(i);
                popupWindow.dismiss();
            }
        });
        // 会员卡支付
        final String s = App.ap_money;
        v.findViewById(R.id.ll_hyk).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                App.payType = 3;// 保存当前选择的支付方式
                Intent i = new Intent(getActivity(), VipSweepCardActivity.class);
                i.putExtra("total_fee", s);
                startActivity(i);
                popupWindow.dismiss();
            }
        });
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        int y_pos = windowManager.getDefaultDisplay().getHeight() / 2;
        popupWindow.showAtLocation(getActivity().getWindow()
                .getDecorView(), Gravity.TOP, 0, y_pos);
        // App.toast(y_pos+"");
        // popupWindow.showAsDropDown(parent, 0, xPos);
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    // 隐藏键盘
    private void hintKb() {
        // 得到InputMethodManager的实例
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_money.getWindowToken(), 0);
    }

    // 检查金额是否有误
    boolean checkMoney() {
        if (Utils.getValue(et_money).equals("0.0")
                || Utils.getValue(et_money).equals("0.00")
                || Utils.getValue(et_money).equals("0.")) {
            return false;
        } else {
            String str = Utils.getValue(et_money);
            String regex = "^[+]?(([1-9]\\d*[.]?)|(0.))(\\d{0,2})?$";//
            return str.matches(regex);
        }
    }

    // 设置背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    void selectTv(View v) {
        tv_1.setSelected(false);
        tv_2.setSelected(false);
        tv_3.setSelected(false);
        tv_4.setSelected(false);
        v.setSelected(true);
    }

    String formatId(String id) {
        String s = id;
        if (id.length() > 20) {
            s = id.subSequence(0, 5) + ".."
                    + id.substring(id.length() - 10, id.length());
        }
        return s;
    }

    String formatVipName(String str) {
        return "(" + str + ")";
    }

    String formatName(String str) {
        String s = str;
        if (str.length() > 4) {
            s = str.subSequence(0, 4) + "..";
        }
        return s;
    }

    /**
     * 支付宝支付/微信支付
     */
    public void paySubmit(final int i) {
        String url = "";
        if (App.payType == 1) {
            url = Api.ALIPAYSCANPAY;
        } else if (App.payType == 2) {
            url = Api.WXPAYSCANPAY;
        } else if (App.payType == 4) {
            url = Api.QQPAYSCANPAY;
        }
        // 支付金额
        int submit_money = 0;
        submit_money = new BigDecimal(App.ap_money).multiply(
                new BigDecimal(100)).intValue();
        App.payState = 1;
        final String pay = payCode;
        if (pay.equals("")) {// 如果没有支付码，则取消支付
            return;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("authCode", pay);
        map.put("body", getString(R.string.app_name));
        map.put("totalFee", "" + submit_money);
        map.put("source", "安卓移动端");
        JSONObject jsonParams = new JSONObject(map);

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("accessToken", App.getUser().data.accessToken);
        headers.put("code", App.getString("code"));
        headers.put("parentCode", App.getString("parentCode"));
        headers.put("Content-Type", "application/json");

        progress("支付中...");
        BaseRequest.postJson(url, jsonParams, headers,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgress();
                        Log.i(TAG, response.toString());
                        SuccessOrder obj = new Gson().fromJson(
                                response.toString(), SuccessOrder.class);
                        if (obj.code == 200) {
                            App.toast("支付成功");
                            noPayCode();
                            et_money.setText("");
                            App.stopPay();
                            page = 0;
                            EventBus.getDefault().post(new FirstEvent(0));
                            App.setString("money_type", App.payType + "");
//                            getActivity().sendBroadcast(new Intent(GetMoneyFragment.REFRESH));
//							getListViewData(true);
                            // refresh(2000);
                            // 判断是否有有打印机，有则打印
                            if (printer.getPrinterSerialNo() != null
                                    && !printer.getPrinterSerialNo().equals("")) {
                                printTicket(obj.data);
                            }
                            // adapter.notifyDataSetChanged();
                        } else {
                            hideProgress();
                            App.stopPay();
                            noPayCode();
                            if (obj.getMessage().equals("")) {
                                App.toast("支付失败");
                            } else {
                                App.toast("支付失败\n" + obj.getMessage());
                            }
                            refresh(2000);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgress();
                        if (App.isDialog) {// 如果dialog正在显示，则不需要处理错误信息
                            App.isDialog = false;
                            Log.e(TAG, "dialog正在显示");
                            return;
                        } else {
                            payError(1, 10000);
                        }
                    }
                });
    }

    /**
     * 支付过程中如果出现网络断开或者其他问题，收银请求意外终止了
     */
    public void payError(final int i) {
        String url = "";
        if (App.payType == 1) {
            url = Api.FIND_ALI_ORDER_STATE + App.payCode;
        } else if (App.payType == 2) {
            url = Api.FIND_ORDER_STATE + App.payCode;
        } else if (App.payType == 4) {
            url = Api.FIND_QQ_STATE + App.payCode;

        }
        progress("交易中...");
        // App.toast(i+"交易中...");
        App.noRequest();
        App.payState = 2;
        Log.e(TAG, url);
        StringRequest stringRequest = new StringRequest(Method.GET, url,
                new Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        hideProgress();
                        Log.i(TAG, response);
                        BaseResponse obj = new Gson().fromJson(
                                response.toString(), BaseResponse.class);
                        if (obj.code == 200) {
                            App.toast("支付成功");
                            et_money.setText("");
                            App.stopPay();
                            noPayCode();
                            refresh(2000);
                        } else {
                            switch (obj.code) {
                                case 1280:
                                    switch (i) {
                                        case 1:
                                            payError(2, 10000);
                                            break;
                                        case 2:
                                            payError(3, 10000);
                                            break;
                                        case 3:
                                            showPay(1);
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case 1321:// 查询qq订单失败
                                    switch (i) {
                                        case 1:
                                            payError(2, 10000);
                                            break;
                                        case 2:
                                            payError(3, 10000);
                                            break;
                                        case 3:
                                            showPay(1);
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                default:// 其他情况直接显示错误信息
                                    App.stopPay();
                                    App.toast(obj.getMessage());
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
                        payError(2, 10000);
                        break;
                    case 2:
                        payError(3, 10000);
                        break;
                    case 3:
                        App.stopPay();
                        noPayCode();
                        App.toast("查询订单信息失败，请稍后刷新订单列表");
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
                return headers;
            }
        };
        BaseRequest.addRequest(stringRequest);
    }

    /**
     * 取消支付
     */
    public void cancelPay(final int i) {
        String url = "";
        if (App.payType == 1) {
            url = Api.CANCEL_ALI_ORDER + App.payCode;
        } else if (App.payType == 2) {
            url = Api.CANCEL_ORDER + App.payCode;
        } else if (App.payType == 4) {
            url = Api.CANCEL_QQ_ORDER + App.payCode;
        }
        Log.e(TAG, url);
        App.payState = 3;
        App.noRequest();
        progress("交易取消中...");
        StringRequest stringRequest = new StringRequest(Method.GET, url,
                new Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        hideProgress();
                        cc.chenghong.vka.response.BaseResponse obj = new Gson().fromJson(
                                response.toString(),
                                cc.chenghong.vka.response.BaseResponse.class);
                        Log.i(TAG, response);
                        if (obj.code == 200) {
                            App.toast("订单已成功取消");
                            App.stopPay();
                            refresh(2000);
                        } else {
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
                                    if (i == 1) {
                                        cancelPay(2);
                                    } else if (i == 2) {
                                        cancelPay(3);
                                    } else {
                                        App.stopPay();
                                        noPayCode();
                                        App.toast("取消订单失败，请稍后刷新订单列表退款");
                                        refresh(2000);
                                    }
                                    break;
                            }
                            refresh(2000);
                        }
                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                hideProgress();
                switch (i) {
                    case 1:
                        cancelPay(2, 5000);
                        break;
                    case 2:
                        cancelPay(3, 10000);
                        break;
                    case 3:// 第三次取消订单仍然失败，说明网络异常或其他原因
                        App.stopPay();
                        noPayCode();
                        App.toast("取消订单失败，请稍后刷新订单列表退款");
                        refresh(2000);
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
                return headers;
            }
        };
        BaseRequest.addRequest(stringRequest);
    }

    /**
     * 刷新列表
     *
     * @param time 延迟多久刷新
     */
    public void refresh(final int time) {
        select_tv(App.payType);
        page = 0;
        index = 0;
        progress("加载中...");
        new Handler() {
            public void handleMessage(Message msg) {
                getListViewData(true);
            }
        }.sendEmptyMessageDelayed(0, time);
    }

    /**
     * 是否重新支付
     */
    Dialog payDialog;

    /**
     * 询问是否继续支付
     */
    public void showPay(final int i) {
        if (payDialog == null) {
            payDialog = new Dialog(getActivity(), R.style.MyDialog);
            payDialog.setContentView(R.layout.dialog_update);
            TextView tv_no = (TextView) payDialog.findViewById(R.id.tv_no);
            TextView tv_yes = (TextView) payDialog.findViewById(R.id.tv_yes);
            TextView tv_title = (TextView) payDialog
                    .findViewById(R.id.tv_title);
            tv_no.setText("否(取消支付)");
            tv_yes.setText("是");
            tv_title.setText("网络不稳定，是否继续支付？");
            payDialog.findViewById(R.id.tv_no).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            cancelPay(1);
                            payDialog.dismiss();
                        }
                    });
            payDialog.findViewById(R.id.tv_yes).setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            payError(1, 5000);
                            payDialog.dismiss();
                        }
                    });
        }
        payDialog.show();
    }

    /**
     * 显示进度框
     *
     * @param str
     */
    public void progress(String str) {
        ((MainActivity) getActivity()).progress(str);
    }

    /**
     * 隐藏进度框
     */
    public void hideProgress() {
        ((MainActivity) getActivity()).hideProgress();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        EventBus.getDefault().unregister(this);// 解除注册EventBus\
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * 交易中
     *
     * @param time 延迟时间
     * @param i    轮循的计数器
     */
    public void payError(final int i, final int time) {
        progress("交易中...");
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(time);
                    payError(i);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 交易取消
     *
     * @param time 延迟时间
     * @param i    轮循的计数器
     */
    public void cancelPay(final int i, final int time) {
        progress("交易取消中...");
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(time);
                    cancelPay(i);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 监听消息
     *
     * @param event
     */
    public void onEventMainThread(FirstEvent event) {
//        Log.e("harvic", event.getMsg());
        if (event.getCode() == 1 || event.getCode() == 2
                || event.getCode() == 4) {
            payCode = event.getmMsg();
            paySubmit(1);
        }
        if (event.getCode() == 0) {
            et_money.setText("");
            getListViewData(false);
        }
    }

    private void noPayCode() {
        payCode = "";
    }

    /**
     * 打印小票
     */
    void printTicket(SuccessOrder order) {

        String body = "微信扫码支付";
        // 铭牌名称
        String brandName = "澄泓信息科技";
        if (App.payType == 1) {
            body = "支付宝扫码支付";
        } else if (App.payType == 2) {
            body = "微信扫码支付";
        } else if (App.payType == 4) {
            body = "QQ扫码支付";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("   ***" + order.getStoreName() + "*** \n");
        sb.append("--------------------------------\n");
        sb.append("消费 \n");
        sb.append("项目:" + body + "\n");
        Double dMoney = (double) (order.getTotalFee() / 100.00);
        sb.append("应付金额:" + dMoney + "\n");
        sb.append("实付金额:" + dMoney + "\n");
        // sb.append("折扣金额:" + couponFeeText + "\n");
        sb.append("折扣金额:0\n");
        sb.append("订单号:" + order.getTransactionId() + "\n");
        if (order.getNickName() == null) {
            sb.append("付款用户:\n");
        } else {
            sb.append("付款用户:" + order.getNickName() + "\n");
        }
        sb.append("时间:" + order.getCreated() + "\n");
        sb.append("状态:下单成功并且支付成功" + "\n");
        sb.append(App.brandName);
        sb.append("--------------------------------\n");
        sb.append("  \n");

        sb.append("*************客户联*************\n");
        sb.append("   ***" + order.getStoreName() + "*** \n");
        sb.append("--------------------------------\n");
        sb.append("消费 \n");
        sb.append("项目:" + body + "\n");
        sb.append("应付金额:" + dMoney + "\n");
        sb.append("实付金额:" + dMoney + "\n");
        // sb.append("折扣金额:" + couponFeeText + "\n");
        sb.append("折扣金额:0\n");
        sb.append("订单号:" + order.getTransactionId() + "\n");
        if (order.getNickName() == null) {
            sb.append("付款用户:\n");
        } else {
            sb.append("付款用户:" + order.getNickName() + "\n");
        }
        sb.append("时间:" + order.getCreated() + "\n");
        sb.append("状态:下单成功并且支付成功" + "\n");
        sb.append(App.brandName);
        sb.append("--------------------------------\n");
        sb.append("  \n");
        System.out.println(sb.toString());
        // 打印机编号
        String printerSerialNo = printer.getPrinterSerialNo();
        if (printerSerialNo == null || printerSerialNo.equals("")) {
            return;
        }
        printer.beginTransaction();// 开启事务
        printer.setAlignment(0);
        printer.printText(sb.toString());
        /**
         * 打印机走纸(强制换行，结束之前的打印内容后走纸n行)
         *
         * @param n
         *            : 走纸行数
         */
        printer.lineWrap(2);
        printer.commitTransaction();// 提交事务
    }

    /**
     * 打印会员小票
     */
    void printCardTicket(Trans.TransData order) {

        String body = "会员卡支付";
        // 铭牌名称
        String brandName = "技术支持：上海澄泓信息科技有限公司\n商务电话：15001819885,18601694369";
        StringBuilder sb = new StringBuilder();
        sb.append("   ***" + order.storeName + "*** \n");
        sb.append("--------------------------------\n");
        sb.append("消费 \n");
        sb.append("项目:" + body + "\n");
        sb.append("应付金额:" + order.itemSubtotal + "\n");
        sb.append("实付金额:" + order.amount + "\n");
        DecimalFormat df = new DecimalFormat("######0.00");
        String discountMoney = df
                .format((order.itemSubtotal.doubleValue() - order.total
                        .doubleValue()));
        sb.append("折扣金额:" + discountMoney + "\n");
        // sb.append("订单号:" + order.id + "\n");
        if (order.name == null) {
            sb.append("付款用户:\n");
        } else {
            sb.append("付款用户:" + order.name + "\n");
        }
        sb.append("时间:" + order.createdDate + "\n");
        sb.append("状态:下单成功并且支付成功" + "\n");
        sb.append(App.brandName);
        sb.append("--------------------------------\n");
        sb.append("  \n");
        sb.append("*************客户联*************\n");
        sb.append("   ***" + order.storeName + "*** \n");
        sb.append("--------------------------------\n");
        sb.append("消费 \n");
        sb.append("项目:" + body + "\n");
        sb.append("应付金额:" + order.itemSubtotal + "\n");
        sb.append("实付金额:" + order.amount + "\n");
        sb.append("折扣金额:" + discountMoney + "\n");
        // sb.append("订单号:" + order.id + "\n");
        if (order.name == null) {
            sb.append("付款用户:\n");
        } else {
            sb.append("付款用户:" + order.name + "\n");
        }
        sb.append("时间:" + order.createdDate + "\n");
        sb.append("状态:下单成功并且支付成功" + "\n");
        sb.append(App.brandName);
        sb.append("--------------------------------\n");
        sb.append("  \n");
        // 打印机编号
        String printerSerialNo = printer.getPrinterSerialNo();
        if (printerSerialNo == null || printerSerialNo.equals("")) {
            return;
        }
        printer.beginTransaction();// 开启事务
        printer.setAlignment(0);
        printer.printText(sb.toString());
        /**
         * 打印机走纸(强制换行，结束之前的打印内容后走纸n行)
         *
         * @param n
         *            : 走纸行数
         */
        printer.lineWrap(2);
        printer.commitTransaction();// 提交事务
        App.setString("cardPrint", "0");
    }
}
