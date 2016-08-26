package cc.chenghong.vka.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

import cc.chenghong.vka.adapter.CommonAdapter;
import cc.chenghong.vka.adapter.ViewHolder;
import cc.chenghong.vka.entity.Entity;
import cc.chenghong.vka.entity.Ticket;
import cc.chenghong.vka.entity.VIP.VIPData;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.response.ListResponse;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;

/**
 * 会员查询 2015 10 7
 *
 * @author hcl
 */
public class VipFindActivity extends BaseActivity implements OnClickListener {
    @ViewInject(R.id.et_money)
    EditText et_money;

    @ViewInject(R.id.tv_name)
    TextView tv_name;

    @ViewInject(R.id.tv_balance)
    TextView tv_balance;

    @ViewInject(R.id.tv_integral)
    TextView tv_integral;

    @ViewInject(R.id.tv_phone)
    TextView tv_phone;

    @ViewInject(R.id.tv_card_id)
    TextView tv_card_id;

    @ViewInject(R.id.tv_grade)
    TextView tv_grade;

    @ViewInject(R.id.tv_ticket)
    TextView tv_ticket;

    @ViewInject(R.id.tv_experience)
    TextView tv_experience;

    @ViewInject(R.id.ll_content1)
    LinearLayout ll_content1;
    @ViewInject(R.id.llFind)
    LinearLayout llFind;

    @ViewInject(R.id.lv)
    ListView lv;

    @ViewInject(R.id.ll_ticket)
    LinearLayout ll_ticket;

    @ViewInject(R.id.iv)
    ImageView iv;

    @ViewInject(R.id.sv)
    ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_vip_find);
        setTitleName("会员查询");
        ViewUtils.inject(this);
        if (Utils.getString(getApplicationContext(), "isRecharge").equals("1")) {
            Utils.setString(getApplicationContext(), "isRecharge", "0");
            et_money.setText(Utils.getString(context, "cardId"));
            find_submit();
            Utils.closeJP(this, et_money);

//			Utils.closeKeyboard(this, et_money);
        }
//		et_money.setText("1001008");
        ll_content1.setVisibility(View.GONE);
        ib_left.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
                Utils.closeJP(VipFindActivity.this,et_money);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.bt_submit, R.id.ll_ticket})
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
                Utils.closeJP(this, et_money);
                find_submit();
                break;
            case R.id.ll_ticket:
                if (ll_ticket.isSelected()) {
                    lv.setVisibility(View.VISIBLE);
                    ll_ticket.setSelected(false);
                    iv.setBackgroundResource(R.drawable.icon_sjt);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            sv.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                } else {
                    lv.setVisibility(View.GONE);
                    ll_ticket.setSelected(true);
                    iv.setBackgroundResource(R.drawable.icon_xjt);
                }
                break;
            default:
                break;
        }
    }

    private void find_submit() {
        // TODO Auto-generated method stub
//        ParamPost headers = new ParamPost();
//        headers.add("accessToken", App.getUser().data.accessToken);
//        headers.add("parentCode", Utils.getString(getApplicationContext(), "parentCode"));
//        headers.add("code", Utils.getString(getApplicationContext(), "code"));
        String api = Api.FINDVIP + Utils.getValue(et_money) + "/mobile";
        progress("查询中...");
        BaseRequest.get(api, getHeanders(), new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                ListResponse<VIPData> obj = new Gson().fromJson(o.toString(), new TypeToken<ListResponse<VIPData>>() {
                }.getType());
                if (obj.isSuccess()) {
                    if (obj.data == null || obj.data.size() == 0) {
                        App.toast("未找到该会员！");
                        ll_content1.setVisibility(View.GONE);
                        llFind.setVisibility(View.VISIBLE);
                        return;
                    }
                    ll_content1.setVisibility(View.VISIBLE);
                    VIPData data = obj.data.get(0);
                    if (data.name == null || data.name.equals("")) {
                        tv_name.setText("");
                    } else {
                        tv_name.setText(data.name);
                    }
                    tv_balance.setText(data.balance + "");
                    tv_integral.setText(data.point + "");
                    tv_card_id.setText(data.mobile + "");
                    tv_phone.setText(data.otherMobile);
                    tv_grade.setText(data.levelName);
                    tv_experience.setText(data.experience);
                    if (data.ticketName == null
                            || data.ticketName.equals("")) {
                        return;
                    }
                    String titcks[] = data.ticketName.split(",");
                    List<Entity> list1 = new ArrayList<Entity>();
                    int count = 0;
                    for (int i = 0; i < titcks.length; i++) {
                        String s1[] = titcks[i].split("x");
                        int a = Integer.valueOf(s1[1]);
                        count += a;
                        System.out.println(a);
                        Entity t = new Entity();
                        t.name = titcks[i];
                        list1.add(t);
                    }
                    tv_ticket.setText(count + "张");
                    CommonAdapter<Entity> adapter = new CommonAdapter<Entity>(
                            VipFindActivity.this, list1,
                            R.layout.item_ticket) {

                        @Override
                        public void convert(ViewHolder helper,
                                            Entity item, int position) {
                            // TODO Auto-generated method stub
                            helper.setText(R.id.tv, item.name);
                        }
                    };
                    lv.setAdapter(adapter);
                    // lv.setAdapter(new SimpleAdapter(context, list,
                    // R.layout.item_ticket, new String[] { "name" },
                    // new int[] { R.id.tv }));
                } else {
                    ll_content1.setVisibility(View.GONE);
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
//                        hideProgress();
//                        // TODO Auto-generated method stub
//                        if (obj.isResponseSuccess()) {
//                            if (obj.data.data == null || obj.data.data.size() == 0) {
//                                App.toast("未找到该会员！");
//                                ll_content.setVisibility(View.GONE);
//                                return;
//                            }
//                            ll_content.setVisibility(View.VISIBLE);
//                            VIPData data = obj.data.data.get(0);
//                            if (data.name == null || data.name.equals("")) {
//                                tv_name.setText("");
//                            } else {
//                                tv_name.setText(data.name);
//                            }
//                            tv_balance.setText(data.balance + "");
//                            tv_integral.setText(data.point + "");
//                            tv_card_id.setText(data.mobile + "");
//                            tv_phone.setText(data.otherMobile);
//                            tv_grade.setText(data.levelName);
//                            tv_experience.setText(data.experience);
//                            if (data.ticketName == null
//                                    || data.ticketName.equals("")) {
//                                return;
//                            }
//                            String titcks[] = data.ticketName.split(",");
//                            List<Entity> list1 = new ArrayList<Entity>();
//                            int count = 0;
//                            for (int i = 0; i < titcks.length; i++) {
//                                String s1[] = titcks[i].split("x");
//                                int a = Integer.valueOf(s1[1]);
//                                count += a;
//                                System.out.println(a);
//                                Entity t = new Entity();
//                                t.name = titcks[i];
//                                list1.add(t);
//                            }
//                            tv_ticket.setText(count + "张");
//                            CommonAdapter<Entity> adapter = new CommonAdapter<Entity>(
//                                    VipFindActivity.this, list1,
//                                    R.layout.item_ticket) {
//
//                                @Override
//                                public void convert(ViewHolder helper,
//                                                    Entity item, int position) {
//                                    // TODO Auto-generated method stub
//                                    helper.setText(R.id.tv, item.name);
//                                }
//                            };
//                            lv.setAdapter(adapter);
//                            // lv.setAdapter(new SimpleAdapter(context, list,
//                            // R.layout.item_ticket, new String[] { "name" },
//                            // new int[] { R.id.tv }));
//                        } else {
//                            ll_content.setVisibility(View.GONE);
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

    private void getData() {
        // TODO Auto-generated method stub
        String url = "";
        url = Api.MAIN + "/pc/v3/ticket/by/" + et_money.getText().toString() + "/" + 0
                + "/" + 30;
        progress("");
        BaseRequest.get(url, getHeanders(), new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                ListResponse<Ticket.TicketData> obj = new Gson().fromJson(o.toString(), new TypeToken<ListResponse<Ticket.TicketData>>() {
                }.getType());
                if (obj.isSuccess()) {
                    if (obj.data.size() == 0) {
                        return;
                    }
                    tv_ticket.setText("优惠券： " + obj.data + "张");
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

    }
}
