package cc.chenghong.vka.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.chenghong.vka.adapter.CommonAdapter;
import cc.chenghong.vka.adapter.ViewHolder;
import cc.chenghong.vka.entity.VipDeal;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.response.ListResponse;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;
import cc.chenghong.vka.view.RefreshFrameLayout;

/**
 * 会员交易记录2015 10 13
 *
 * @author hcl
 */
public class VipDealActivity extends BaseActivity implements OnClickListener {
    /**
     * 没数据
     */
    @ViewInject(R.id.ll_no_data)
    private LinearLayout ll_no_data;

    @ViewInject(R.id.et_money)
    EditText et_money;

    @ViewInject(R.id.tv_1)
    TextView tv_1;
    @ViewInject(R.id.tv_2)
    TextView tv_2;

    @ViewInject(R.id.lv)
    ListView lv;

    // 交易类型
    int type = 2;
    // 当前第几页
    int page = 0;
    // 一页几条
    int limit = 10;
    // 上下下拉控件
    @ViewInject(R.id.rv)
    RefreshFrameLayout rv;
    // 数据list
    List<VipDeal> list = new ArrayList<VipDeal>();
    // 适配器
    CommonAdapter<VipDeal> adapter;

    boolean isfirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_vip_deal);
        ViewUtils.inject(this);
        initUI();
    }

    private void initUI() {
        // TODO Auto-generated method stub
        setTitleName("会员交易记录");
        tv_1.setSelected(true);
        ib_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Utils.closeJP(getContext(),et_money);
            }
        });
        initAdapter();
        initRv();
    }

    Object getMoney(VipDeal data) {
        Object o = "";
        if (data.cashAmount != 0)
            return data.cashAmount;
        if (data.weixinAmount != 0)
            return data.weixinAmount;
        if (data.amount != 0)
            return data.amount;
        if (data.itemSubtotal != 0)
            return data.itemSubtotal;
        return o;
    }

    private void initAdapter() {
        // TODO Auto-generated method stub
        adapter = new CommonAdapter<VipDeal>(this, list, R.layout.item_vip_deal) {

            @Override
            public void convert(ViewHolder helper, VipDeal item, int position) {
                // TODO Auto-generated method stub
                // item
                LinearLayout ll = helper.getView(R.id.ll);
                // 内容
                LinearLayout ll_content = helper.getView(R.id.ll_content);
                // ViewGroup parent = (ViewGroup) ll_content.getParent();
                // if (parent != null) {
                // parent.removeAllViews();
                // }

                //清空内容ll里的所有子控件
                if (((ViewGroup) helper.getView(R.id.ll_content))
                        .getChildCount() > 0) {
                    ((ViewGroup) helper.getView(R.id.ll_content))
                            .removeAllViews();
                }
                if (item.status == 2 && item.type == 2 || item.status == 2
                        && item.point != 0) {// 已退单
                    LinearLayout ll_quit = (LinearLayout) VipDealActivity.this
                            .getLayoutInflater().inflate(R.layout.view_ll_quit,
                                    null);
                    ll_content.addView(ll_quit);
                    ll.setBackgroundResource(R.color.gray);
                } else {// 未退单
                    ll.setBackgroundResource(R.color.white);
                }
                // TextView tv_money = (TextView) VipDealActivity.this
                // .getLayoutInflater().inflate(R.layout.view_tv_money,
                // null);
                //
                // TextView tv = (TextView) VipDealActivity.this
                // .getLayoutInflater().inflate(R.layout.view_tv,
                // null);
                //
                // TextView tv = (TextView) VipDealActivity.this
                // .getLayoutInflater().inflate(R.layout.view_tv,
                // null);
                if (item.type == 1) {// 充值
                    // 钱
                    TextView tv_money = (TextView) VipDealActivity.this
                            .getLayoutInflater().inflate(
                                    R.layout.view_tv_money, null);
                    tv_money.setText("充值￥ " + getMoney(item) + "");
                    ll_content.addView(tv_money);
                    if (item.give != 0) {// 赠送金额
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_money, null);
                        tv.setText("赠送￥ " + item.give);
                        ll_content.addView(tv);
                    }
                    if (item.point != 0) {// 积分
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_money, null);
                        tv.setText("赠送积分 " + item.point);
                        ll_content.addView(tv);
                    }
                    if (item.ticketNames.length() > 0) {//优惠券
                        String s[] = item.ticketNames.split(",");
                        for (int i = 0; i < s.length; i++) {
                            TextView tv = (TextView) VipDealActivity.this
                                    .getLayoutInflater().inflate(
                                            R.layout.view_tv_ticket, null);
                            tv.setText("赠送" + s[i]);
                            ll_content.addView(tv);
                        }
                    }

                } else if (item.type == 2) {// 消费
                    // if(type==2){
                    // return;
                    // }
                    if (item.amount != 0) {// 会员卡
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_money, null);
                        tv.setText("会员卡消费￥ " + item.amount);
                        ll_content.addView(tv);
                    }
                    if (item.cashAmount != 0) {// 现金
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_money, null);
                        tv.setText("现金消费￥ " + item.cashAmount);
                        ll_content.addView(tv);
                    }
                    if (item.creditAmount != 0) {// 信用卡
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_money, null);
                        tv.setText("信用卡消费￥ " + item.creditAmount);
                        ll_content.addView(tv);
                    }
                    if (item.weixinAmount != 0) {// 微信消费
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_money, null);
                        tv.setText("微信消费￥ " + item.weixinAmount);
                        ll_content.addView(tv);
                    }
                    if (item.alipayAmount != 0) {// 支付宝消费
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_money, null);
                        tv.setText("支付宝消费￥ " + item.alipayAmount);
                        ll_content.addView(tv);
                    }
                    if (item.point != 0) {// 积分
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_integral, null);
                        tv.setText("积分 " + item.point);
                        ll_content.addView(tv);
                    }
                    if (item.ticketNames.length() > 0) {//优惠券
                        String s[] = item.ticketNames.split(",");
                        for (int i = 0; i < s.length; i++) {
                            TextView tv = (TextView) VipDealActivity.this
                                    .getLayoutInflater().inflate(
                                            R.layout.view_tv_ticket, null);
                            tv.setText(s[i]);
                            ll_content.addView(tv);
                        }
                    }
                } else if (item.type == 3) {// 冲销
                    if (item.amount != 0) {// 钱
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_money, null);
                        tv.setText("积分 " + item.amount);
                        ll_content.addView(tv);
                    }
                    if (item.point != 0) {// 积分
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_money, null);
                        tv.setText("赠送积分 " + item.point);
                        ll_content.addView(tv);
                    }
                } else {
                    if (item.amount != 0) {// 钱
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_money, null);
                        tv.setText("赠送￥  " + item.amount);
                        ll_content.addView(tv);
                    }
                    if (item.point != 0) {// 积分
                        TextView tv = (TextView) VipDealActivity.this
                                .getLayoutInflater().inflate(
                                        R.layout.view_tv_integral, null);
                        tv.setText("赠送积分 " + item.point);
                        ll_content.addView(tv);
                    }
                    if (item.ticketNames.length() > 0) {//消费券
                        String s[] = item.ticketNames.split(",");
                        for (int i = 0; i < s.length; i++) {
                            TextView tv = (TextView) VipDealActivity.this
                                    .getLayoutInflater().inflate(
                                            R.layout.view_tv_ticket, null);
                            tv.setLayoutParams(new LayoutParams(
                                    LayoutParams.MATCH_PARENT,
                                    LayoutParams.MATCH_PARENT));
                            tv.setText("赠送" + s[i]);
                            ll_content.addView(tv);
                        }
                    }
                }
                helper.setText(R.id.tv_time, item.createdDate);//消费时间
                helper.setText(R.id.tv_name, item.storeName);//消费店名

                // if(type==1){//消费
                // if(item.status==1){
                // ll.setVisibility(View.GONE);
                // }else{
                // ll.setVisibility(View.VISIBLE);
                // }
                // }else{//充值
                // if(item.status!=1){
                // ll.setVisibility(View.GONE);
                // }else{
                // ll.setVisibility(View.VISIBLE);
                // }
                // }
            }
        };
        lv.setAdapter(adapter);
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
                isfirst = true;
                getData();
            }

            @Override
            public void onSnapToBottom(int distance) {
                // TODO Auto-generated method stub
                page++;
                isfirst = true;
                getData();
            }
        });

    }

    // 获取数据
    protected void getData() {
        // TODO Auto-generated method stub
        String api = Api.MAIN + "/pc/v1/trans/type/"
                + Utils.getValue(et_money) + "/" + type + "/" + page + "/"
                + limit;
        if (isfirst) {
            isfirst = false;
            progress("加载中...");
        }
        Map map = new HashMap();
        map.put("accessToken", App.getUser().data.accessToken);
        map.put("code", Utils.getString(getApplicationContext(), "code"));
        map.put("parentCode", Utils.getString(getApplicationContext(), "parentCode"));
        BaseRequest.get(api, map, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                ListResponse<VipDeal> obj = new Gson().fromJson(o.toString(), new TypeToken<ListResponse<VipDeal>>() {
                }.getType());
                if (obj.isSuccess()) {
                    if (page == 0 && list != null) {
                        list.clear();
                    }
                    if (obj.data.size() == 0 && page == 0) {
                        App.toast("暂无记录");
                        list.clear();
                        adapter.notifyDataSetChanged();
                    } else if (obj.data.size() == 0) {
                        App.toast("已经加载完所有数据了噢");
                    }
                    list.addAll(obj.data);
                    adapter.notifyDataSetChanged();
                    if (list.size() == 0) {
                        ll_no_data.setVisibility(View.VISIBLE);
                    } else {
                        ll_no_data.setVisibility(View.GONE);
                    }
                } else {
                    toask("加载失败" + obj.getMessage());
                    System.out.println(obj);
                    // App.toast(Utils.codeToString(obj.data.code));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError(volleyError);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_1, R.id.tv_2, R.id.bt_submit})
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.iv_back:
                Utils.closeKeyboard(getApplicationContext(), et_money);
                finish();
                break;
            case R.id.bt_submit:
                if (Utils.isNull(et_money)) {
                    App.toast("请输入会员卡号");
                    return;
                }
                page = 0;
                isfirst = true;
                Utils.closeKeyboard(getApplicationContext(), et_money);
                getData();
                break;
            case R.id.tv_1:
                tv_1.setSelected(true);
                tv_2.setSelected(false);
                type = 2;
                page = 0;
                if (Utils.isNull(et_money)) {
                    App.toast("请输入会员卡号");
                    return;
                }
                getData();

                break;
            case R.id.tv_2:
                tv_2.setSelected(true);
                tv_1.setSelected(false);
                type = 1;
                page = 0;
                if (Utils.isNull(et_money)) {
                    App.toast("请输入会员卡号");
                    return;
                }
                getData();

                break;
            default:
                break;
        }
    }

    // 提交
    private void subtmit() {
        // TODO Auto-generated method stub
    }

}
