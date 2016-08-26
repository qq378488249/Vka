package cc.chenghong.vka.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;

import cc.chenghong.vka.activity.CheckTicketRecordActivity;
import cc.chenghong.vka.activity.MainActivity;
import cc.chenghong.vka.activity.NewMemberActivity;
import cc.chenghong.vka.activity.R;
import cc.chenghong.vka.activity.VipDealActivity;
import cc.chenghong.vka.activity.VipFindActivity;
import cc.chenghong.vka.activity.VipRechargeActivity;
import cc.chenghong.vka.adapter.LvAdapter;
import cc.chenghong.vka.adapter.LvViewHolder;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.code.qr_codescan.MipcaActivityCapture;
import cc.chenghong.vka.entity.FirstEvent;
import cc.chenghong.vka.entity.Page;
import cc.chenghong.vka.entity.VipGv;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.response.BaseResponse;
import cc.chenghong.vka.response.ListResponse;
import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;
import de.greenrobot.event.EventBus;

/**
 * 会员2015 10 12
 *
 * @author hcl
 */
public class VipFragment1 extends BaseFragment {
    // @ViewInject(R.id.ll_find)
    // LinearLayout ll_find;
    // @ViewInject(R.id.ll_recharge)
    // LinearLayout ll_recharge;
    // @ViewInject(R.id.ll_ticket)
    // LinearLayout ll_ticket;
    // @ViewInject(R.id.ll)
    // LinearLayout ll;
    @ViewInject(R.id.gv)
    GridView gv;
    PopupWindow popupWindow;
    View view;
    Page<VipGv> page = new Page<VipGv>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_vip1, container, false);
        ViewUtils.inject(this, view);
        init();
        EventBus.getDefault().register(this);//初始化EventBus
        return view;
    }

    private void init() {
        page.adapter = new LvAdapter<VipGv>(getActivity(), page.list, R.layout.item_gv_vip) {
            @Override
            public void convert(LvViewHolder helper, VipGv item, int position) {
                helper.setImageResource(R.id.iv, item.ivId);
                helper.setText(R.id.tv, item.name);
            }
        };
        gv.setAdapter(page.adapter);
        page.add(new VipGv(R.drawable.a_0000_search, "会员查询"));
        page.add(new VipGv(R.drawable.a_0002_coin, "会员充值"));
        page.add(new VipGv(R.drawable.jiaoyi_03, "会员交易记录"));
        page.add(new VipGv(R.drawable.a_0001_cards, "核销卡券"));
        page.add(new VipGv(R.drawable.a_0001_cards, "核销会员券"));
        page.add(new VipGv(R.drawable.a_0001_cards, "新建会员"));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VipGv item = page.get(i);
                switch (i) {
                    case 0:
                        startActivity(new Intent(getActivity(), VipFindActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), VipRechargeActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), VipDealActivity.class));
                        break;
                    case 3:
                        showWindow(view);
                        break;
                    case 4:
                        App.payType = 6;
                        startActivity(new Intent(getActivity(), MipcaActivityCapture.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), NewMemberActivity.class));
                        break;
                }
            }
        });
    }

    /**
     * 核销优惠券
     */
    private void submitCoupons(String payCode) {
        getBaseActivity().progress("核销中...");
        Map map = new HashMap();
        map.put("accessToken", App.getUser().data.accessToken);
        map.put("code", Utils.getString(getActivity().getApplicationContext(), "code"));
        map.put("parentCode", Utils.getString(getActivity().getApplicationContext(), "parentCode"));
        String url = Api.verifySales + payCode;

        BaseRequest.get(url, map, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                getBaseActivity().hideProgress();
                new TypeToken<ListResponse<String>>() {
                };
                BaseResponse obj = new Gson().fromJson(o.toString(), BaseResponse.class);
                if (obj.isSuccess()) {
                    toask("核销会员券成功");
                } else {
                    toask("核销会员券失败，" + obj.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getBaseActivity().volleyError(volleyError);
            }
        });
    }

    @OnClick({R.id.ll_find, R.id.ll, R.id.ll_recharge, R.id.ll_ticket,
            R.id.ll_record})
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.ll_find:
                startActivity(new Intent(getActivity(), VipFindActivity.class));
                break;
            case R.id.ll_recharge:
                startActivity(new Intent(getActivity(), VipRechargeActivity.class));
                break;
            case R.id.ll_ticket:
                showWindow(arg0);
                break;
            case R.id.ll_record:
                startActivity(new Intent(getActivity(), VipDealActivity.class));
                break;
            case R.id.ll:
                break;
            default:
                break;
        }
    }

    // 核销卡券
    void submit(String resultString) {
        String url = Api.CHECK_TICKET + resultString;
        System.out.println(url);
        getBaseActivity().progress("核销中...");
//		ParamPost head=new ParamPost();
//		head.add("accessToken", App.getUser().data.accessToken);
//		head.add("source", "app");
//		head.add("code",App.getString("code"));
//		head.add("parentCode",App.getString("parentCode"));
        Log.e(TAG, "核销卡券");
        Map map = getBaseActivity().getHeanders();
        map.put("source", "app");
        BaseRequest.get(url, map, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                getBaseActivity().hideProgress();
                BaseResponse obj = new Gson().fromJson(o.toString(), BaseResponse.class);
                if (obj.isSuccess()) {
                    App.toast("核销卡券成功");
                } else {
                    if (obj.getMessage().equals("")) {
                        App.toast("核销卡券失败");
                    } else {
                        App.toast("核销卡券失败，" + obj.getMessage());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getBaseActivity().volleyError(volleyError);
            }
        });
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    View view_popupWindow = null;

    private void showWindow(View parent) {
        WindowManager windowManager = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view_popupWindow = layoutInflater.inflate(R.layout.dialog_ticket, null);
            popupWindow = new PopupWindow(view_popupWindow, windowManager.getDefaultDisplay()
                    .getWidth(), 300, true);
        }
        // 使其聚焦
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        // ColorDrawable dw = new ColorDrawable(0Xffffff);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.5f);
        // 核销卡券
        view_popupWindow.findViewById(R.id.tv_1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(), MipcaActivityCapture.class);
                App.payType = 5;
                startActivity(i);
                popupWindow.dismiss();
            }
        });

        // 核销卡券记录
        view_popupWindow.findViewById(R.id.tv_2).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(), CheckTicketRecordActivity.class);
                startActivity(i);
                popupWindow.dismiss();
            }
        });

        // 取消
        view_popupWindow.findViewById(R.id.tv_3).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });

        // popupWindow.showAtLocation(parent, Gravity.LEFT, 0, 0);

    }

    // 设置背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    // 监听ppwindows是否关闭
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            // Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }

    /**
     * 监听消息
     *
     * @param event
     */
    public void onEventMainThread(FirstEvent event) {
        Log.e("harvic", event.getMsg());
        if (event.getCode() == 5) {
            submit(App.payCode);
        } else if (event.getCode() == 6) {
            submitCoupons(event.getmMsg());
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
