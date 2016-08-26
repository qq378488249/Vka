package cc.chenghong.vka.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import cc.chenghong.vka.entity.Trans.TransData;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 会员支付详情2015 9 28
 *
 * @author hcl
 */
public class Vip_pay_DetailsActivity extends BaseActivity implements
        OnClickListener {
    @ViewInject(R.id.sv)
    ScrollView sv;

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

    @ViewInject(R.id.tv_discount)
    TextView tv_discount;

    @ViewInject(R.id.iv_back)
    ImageView iv_back;

    @ViewInject(R.id.iv)
    ImageView iv;

    @ViewInject(R.id.ll_ticket)
    LinearLayout ll_ticket;

    @ViewInject(R.id.ll_grade)
    LinearLayout ll_grade;

    @ViewInject(R.id.ll_discount)
    LinearLayout ll_discount;

    @ViewInject(R.id.ll_phone)
    LinearLayout ll_phone;

    @ViewInject(R.id.lv)
    ListView lv;

    TransData trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_vip_pay_details);
        ViewUtils.inject(this);
        trans = (TransData) getIntent().getSerializableExtra("trans");
        initUI();
//		initStatusBar();
    }

    private void initUI() {
        // TODO Auto-generated method stub
        setTitleName("会员支付详情");
        if (trans.name == null || trans.name.equals("")) {
            tv_name.setText("");
        } else {
            tv_name.setText(trans.name + "");
        }
        tv_balance.setText(trans.balance + "");
        tv_integral.setText(trans.pointBalance + "");
        if (trans.otherMobile == null || trans.otherMobile.equals("")) {
            ll_phone.setVisibility(View.GONE);
        } else {
            tv_phone.setText(trans.otherMobile + "");
        }
        if (trans.mobile == null || trans.equals("")) {
            tv_card_id.setText("");
        }
        if (trans.levelName == null || trans.levelName.toString().equals("")) {
            ll_grade.setVisibility(View.GONE);
            ll_discount.setVisibility(View.GONE);
        } else {
            ll_grade.setVisibility(View.VISIBLE);
            tv_grade.setText(trans.levelName);
            ll_discount.setVisibility(View.VISIBLE);
            tv_discount.setText(trans.discount + "");
        }
        if (trans.ticketNames == null || trans.ticketNames.equals("")) {
            ll_ticket.setVisibility(View.GONE);
            lv.setVisibility(View.GONE);
        } else {
            ll_ticket.setVisibility(View.VISIBLE);
            String titcks[] = trans.ticketNames.split(",");
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            int count = 0;
            for (int i = 0; i < titcks.length; i++) {
                String s1[] = titcks[i].split("x");
                count += Integer.valueOf(s1[1]);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", titcks[i]);
                list.add(map);
            }
            tv_ticket.setText(count + "张");
            lv.setAdapter(new SimpleAdapter(context, list,
                    R.layout.item_ticket, new String[]{"name"},
                    new int[]{R.id.tv}));
            lv.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_ticket})
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_ticket:
                if (!ll_ticket.isSelected()) {
                    lv.setVisibility(View.GONE);
                    ll_ticket.setSelected(true);
                    iv.setBackgroundResource(R.drawable.icon_xjt);
                } else {
                    lv.setVisibility(View.VISIBLE);
                    ll_ticket.setSelected(false);
                    iv.setBackgroundResource(R.drawable.icon_sjt);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            sv.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    public int getTitckCount(String string) {
        String s[] = string.split(",");
        int count = 0;
        for (int i = 0; i < s.length; i++) {
            String s2 = "";
            s2 = s[i].substring(s[i].length() - 1);
            int a = Integer.valueOf(s2).intValue();
            count += a;
        }
        return count;
    }
}
