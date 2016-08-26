package cc.chenghong.vka.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cc.chenghong.vka.adapter.CommonAdapter;
import cc.chenghong.vka.adapter.ViewHolder;
import cc.chenghong.vka.entity.TicketRecord;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.response.ListResponse;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.api.Api;
import cc.chenghong.vka.view.RefreshFrameLayout;

/**
 * 核销卡券记录2015 11 09
 *
 * @author hcl
 */
public class CheckTicketRecordActivity extends BaseActivity {
    @ViewInject(R.id.ll_no_data)
    private LinearLayout ll_no_data;

    // 当前第几页
    int page = 0;
    // 一页几条
    int limit = 10;
    // 上下下拉控件
    @ViewInject(R.id.rv)
    RefreshFrameLayout rv;
    //
    @ViewInject(R.id.lv)
    ListView lv;
    // 数据list
    List<TicketRecord> list = new ArrayList<TicketRecord>();
    // 适配器
    CommonAdapter<TicketRecord> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_check_ticket_record);
        ViewUtils.inject(this);
//        initStatusBar();
        initUI();
    }

    private void initUI() {
        // TODO Auto-generated method stub
        setTitleName("卡券核销记录");
        initAdapter();
        initRv();
        getData();
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
        adapter = new CommonAdapter<TicketRecord>(this, list,
                R.layout.item_ticket_record) {

            @Override
            public void convert(ViewHolder helper, TicketRecord item,
                                int position) {
                // TODO Auto-generated method stub
                helper.setText(R.id.tv1, "核销时间： " + item.getCreated());
                helper.setText(R.id.tv2, "核销卡券： " + item.getWxCardCouponName());
                helper.setText(R.id.tv3, "核销门店： " + item.getStoreName());
                helper.setText(R.id.tv4, "核销人： " + item.getEmployeeName());
                helper.setText(R.id.tv5, "核销来源： " + item.getSource());
            }

        };
        lv.setAdapter(adapter);
    }

    protected void getData() {
        // TODO Auto-generated method stub
        progress("加载中...");
        String api = Api.CHECK_TICKET + page + "/" + limit;
//        Map map = new HashMap();
//        map.put("accessToken", App.getUser().data.accessToken);
//        map.put("code", Utils.getString(getApplicationContext(), "code"));
//        map.put("parentCode",
//                Utils.getString(getApplicationContext(), "parentCode"));
        BaseRequest.get(api, getHeanders(), new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                hideProgress();
                ListResponse<TicketRecord> obj = new Gson().fromJson(o.toString(), new TypeToken<ListResponse<TicketRecord>>() {
                }.getType());
                if (obj.isSuccess()) {
                    if (page == 0) {
                        list.clear();
                    }
                    if (obj.isSizeZero()) {
                        if (page == 0) {
                            App.toast("暂无订单信息！");
                        } else {
                            App.toast("没有更多订单信息了噢！");
                        }
                    } else {
                        list.addAll(obj.data);
                    }
                    adapter.notifyDataSetChanged();
                    if (list.size() == 0) {
                        ll_no_data.setVisibility(View.VISIBLE);
                    } else {
                        ll_no_data.setVisibility(View.GONE);
                    }
                } else {
                    toask(obj.getMessage());
//                    App.toast("服务器错误");
//                    System.out.println(Utils.objectToJson(obj));
                    // App.toast(Utils.codeToString(obj.data.code));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideProgress();
                toask(volleyError.toString());
            }
        });
//        ParamPost head = new ParamPost();
//        head.add("accessToken", App.getUser().data.accessToken);
//        head.add("code", Utils.getString(getApplicationContext(), "code"));
//        head.add("parentCode",
//                Utils.getString(getApplicationContext(), "parentCode"));
//        progress("加载中...");
//        BaseService.get(api, new ParamPost(), head,
//                new ServiceCallback<ArrayListResponse<TicketRecord>>() {
//
//                    @Override
//                    public void done(int what,
//                                     ServiceResponse<ArrayListResponse<TicketRecord>> obj) {
//                        // TODO Auto-generated method stub
//                        hideProgress();
//                        if (obj.isResponseSuccess()) {
//                            if (page == 0 && list != null) {
//                                list.clear();
//                            }
//                            if (obj.data.data.size() == 0 && page == 0) {
//                                App.toast("暂无记录");
//                                list.clear();
//                                adapter.notifyDataSetChanged();
//                            } else if (obj.data.data.size() == 0) {
//                                App.toast("已经加载完所有数据了噢");
//                            }
//                            list.addAll(obj.data.data);
//                            adapter.notifyDataSetChanged();
//                            if (list.size() == 0) {
//                                ll_no_data.setVisibility(0);
//                            } else {
//                                ll_no_data.setVisibility(8);
//                            }
//                        } else {
//                            App.toast("服务器错误");
//                            System.out.println(Utils.objectToJson(obj));
//                            // App.toast(Utils.codeToString(obj.data.code));
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
