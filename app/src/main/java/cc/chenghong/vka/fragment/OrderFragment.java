package cc.chenghong.vka.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cc.chenghong.vka.activity.MainActivity;
import cc.chenghong.vka.activity.OrderDetailsActivity;
import cc.chenghong.vka.activity.R;
import cc.chenghong.vka.adapter.CommonAdapter;
import cc.chenghong.vka.adapter.ViewHolder;
import cc.chenghong.vka.entity.Order;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.listener.VolleyListener;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.response.ListResponse;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;
import cc.chenghong.vka.view.RefreshFrameLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 订单2015 9 24
 *
 * @author hcl
 */

public class OrderFragment extends BaseFragment implements OnClickListener {
    /**
     * 没数据
     */
    @ViewInject(R.id.ll_no_data)
    private LinearLayout ll_no_data;

    @ViewInject(R.id.tv_1)
    TextView tv_1;
    @ViewInject(R.id.tv_2)
    TextView tv_2;

    @ViewInject(R.id.lv)
    ListView lv;

    // 类型
    String type = "orders";
    // 当前第几页
    int page = 0;
    // 一页几条
    int limit = 10;
    // 上下下拉控件
    @ViewInject(R.id.rv)
    RefreshFrameLayout rv;

    List<Order> list = new ArrayList<Order>();
    CommonAdapter<Order> adapter;
    //判断是否是第一次加载
    int index = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ViewUtils.inject(this, view);
        initUI();
        return view;
    }

    // 初始化
    private void initUI() {
        // TODO Auto-generated method stub
        type = Utils.getString(getActivity(), "orderType");
        if (type.equals("")) {
            type = "eatIn";
        }
        if (type.equals("takeAway")) {
            selectTv(tv_2);
        } else {
            selectTv(tv_1);
        }
        getData();
        initAdatper();
        initLv();
        initRv();
    }

    private void initLv() {
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Object t = list.get(arg2);
                Intent intent = new Intent(getActivity(),
                        OrderDetailsActivity.class);
                intent.putExtra("order", list.get(arg2));
                startActivity(intent);
            }
        });
    }

    private void initRv() {
        rv.openPullDown();
        rv.openPullUp();
        rv.setListViewScrollListener(lv);
        rv.addOnSnapListener(new RefreshFrameLayout.OnSnapListener() {
            @Override
            public void onSnapToTop(int distance) {
                // 下拉刷新, 重新加载第一页
                page = 0;
                index = 0;
                getData();
            }

            @Override
            public void onSnapToBottom(int distance) {
                // 上拉加载更多
                page++;
                index = 0;
                getData();
            }
        });
    }

    // 初始化适配器
    private void initAdatper() {
        // TODO Auto-generated method stub
        adapter = new CommonAdapter<Order>(getActivity(), list,
                R.layout.item_order) {
            @Override
            public void convert(ViewHolder helper, Order item, int position) {
                // 堂食
                RelativeLayout rl_tangshi = helper.getView(R.id.rl_tangshi);
                // 外卖
                LinearLayout ll_waimai = helper.getView(R.id.ll_waimai);
                // 订单状态
                TextView tv_state;
                if (type.equals("eatIn")) {// 堂食
                    ll_waimai.setVisibility(View.GONE);
                    helper.setText(R.id.tv_money1, "￥" + item.amount + "");
                    helper.setText(R.id.tv_time1, item.created);
                    tv_state = helper.getView(R.id.tv_state1);
                    switch (item.payStatus) {
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
                } else {// 外卖
                    tv_state = helper.getView(R.id.tv_state2);
                    rl_tangshi.setVisibility(View.GONE);
                    helper.setText(R.id.tv_money2, "￥" + item.amount + "");
                    helper.setText(R.id.tv_time2, item.created);
                    helper.setText(R.id.tv_name, "姓名   " + item.mobile);
                    helper.setText(R.id.tv_address, item.address);
                    tv_state = helper.getView(R.id.tv_state2);
                    switch (item.status) {
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
                            break;
                        case 3:
                            tv_state.setBackgroundResource(R.drawable.shape_circle_skyblue);
                            tv_state.setText("已退单");
                            break;
                        default:
                            break;
                    }
                }
            }
        };

    }

    // 获取数据
    private void getData() {
        // TODO Auto-generated method stub
        String api = Api.GETORDER + type + "/" + page + "/" + limit;
        // ((MainActivity) getActivity()).progress("");
//		ParamPost headers = new ParamPost();
//		headers.add("accessToken", App.getUser().data.accessToken);
//		headers.add("code", App.getString("code"));
//		headers.add("parentCode", App.getString("parentCode"));
        if (index == 0) {
            index++;
            ((MainActivity) getActivity()).progress("加载中...");
        }
        BaseRequest.get(api, getBaseActivity().getHeanders(), new VolleyListener<ListResponse<Order>>() {
            @Override
            public void onSuccess(ListResponse<Order> obj) {
                getBaseActivity().hideProgress();
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
                    App.toast(obj.getMessage());
                }
            }

            @Override
            public void onFail(VolleyError volleyError) {
                getBaseActivity().volleyError(volleyError);
            }
        });
//        BaseRequest.get(api, getBaseActivity().getHeanders(), new Response.Listener() {
//            @Override
//            public void onResponse(Object o) {
//                getBaseActivity().hideProgress();
//                ListResponse<Order> obj = new Gson().fromJson(o.toString(), new TypeToken<ListResponse<Order>>() {
//                }.getType());
//                if (obj.isSuccess()) {
//                    if (page == 0) {
//                        list.clear();
//                    }
//                    if (obj.isSizeZero()) {
//                        if (page == 0) {
//                            App.toast("暂无订单信息！");
//                        } else {
//                            App.toast("没有更多订单信息了噢！");
//                        }
//                    } else {
//                        list.addAll(obj.data);
//                    }
//                    adapter.notifyDataSetChanged();
//                    if (list.size() == 0) {
//                        ll_no_data.setVisibility(View.VISIBLE);
//                    } else {
//                        ll_no_data.setVisibility(View.GONE);
//                    }
//                } else {
//                    App.toast(obj.getMessage());
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                getBaseActivity().volleyError(volleyError);
//            }
//        });
//		BaseService.get(api, new ParamPost(), headers,
//				new ServiceCallback<ArrayListResponse<Order>>() {
//					@Override
//					public void done(int what,
//							ServiceResponse<ArrayListResponse<Order>> obj) {
//						((MainActivity) getActivity()).hideProgress();
//						// TODO Auto-generated method stub
//						((MainActivity)getActivity()).hideProgress();
//						if (obj.isResponseSuccess()) {
//							if (page == 0 && list.size()!=0) {
//								list.clear();
//							} else {
//								if(obj.data.data.size()==0&&page==0){
////									App.toast("没有数据");
//								}else if (obj.data.data.size()==0&&page!=0) {
//									App.toast("没有更多订单信息了噢！");
//								} else {
//									list.addAll(obj.data.data);
//								}
//							}
//							adapter.notifyDataSetChanged();
//							if (list.size() == 0) {
//								ll_no_data.setVisibility(View.VISIBLE);
//							} else {
//								ll_no_data.setVisibility(View.GONE);
//							}
//						} else {
//							App.toast(obj.getMessage());
//						}
//					}
//
//					@Override
//					public void error(String msg) {
//						// TODO Auto-generated method stub
//						((MainActivity) getActivity()).hideProgress();
//						App.toast(msg);
//					}
//				});
    }

    @OnClick({R.id.tv_1, R.id.tv_2, R.id.iv_back, R.id.ll})
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.tv_1:
                if (tv_1.isSelected()) {
                    return;
                }
                page = 0;
                type = "eatIn";
                Utils.setString(getActivity(), "orderType", "eatIn");
                selectTv(tv_1);
                getData();
                break;
            case R.id.tv_2:
                if (tv_2.isSelected()) {
                    return;
                }
                page = 0;
                Utils.setString(getActivity(), "orderType", "takeAway");
//			type = "eatIn";
                type = "takeAway";
                selectTv(tv_2);
                getData();
                break;
            case R.id.ll://这2行代码千万别删，否则会有严重BUG
                break;
            default:
                break;
        }
    }

    void selectTv(View v) {
        tv_1.setSelected(false);
        tv_2.setSelected(false);
        v.setSelected(true);
        // ((MainActivity)getActivity()).progress("");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (Utils.getString(getActivity(), "isRefresh").equals("1")) {
            Utils.clearString(getActivity(), "isRefresh");
            getData();
        }

    }
}
