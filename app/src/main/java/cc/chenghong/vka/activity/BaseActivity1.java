package cc.chenghong.vka.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import cc.chenghong.vka.app.App;
import cc.chenghong.vka.util.Utils;

/**
 * Activity基类
 *
 * @author hcl 2015-10-13
 */
public class BaseActivity1 extends ProgressFragmentActivity {
    public String TAG = getClass().getSimpleName();
    //子容器
    protected View contentView;
    //父容器
    private LinearLayout ll_content;

    public TextView tv_center;//标题
    public ImageView iv_back;//后退按钮
    public Context context;//上下文

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_base1);
        context = this;
        initView();
        registerReceiver(logout, new IntentFilter("logout"));
    }

    /**
     * 广播接受器，当收到logout广播时关闭页面
     */
    BroadcastReceiver logout = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            finish();
        }
    };

    private void initView() {
        // TODO Auto-generated method stub
        tv_center = (TextView) findViewById(R.id.tv_center);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
//				App.toast("后退按钮");
                onclick_iv_back();
            }
        });
    }

//	BroadcastReceiver logout = new BroadcastReceiver() {
//		@Override
//		public void onReceive(Context arg0, Intent arg1) {
//			finish();
//		}
//	};

    /**
     * 后退按钮事件
     */
    public void onclick_iv_back() {
        // TODO Auto-generated method stub
        finish();
    }

    /**
     * 设置标题
     *
     * @param string
     */
    public void setTitleName(String string) {
        tv_center.setText(string);
    }

    /**
     * 加入页面内容布局
     */
    protected void contentView(int layoutId) {
        contentView = getLayoutInflater().inflate(layoutId, null);
        if (ll_content.getChildCount() > 0) {
            ll_content.removeAllViews();
        }
        if (contentView != null) {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            ll_content.addView(contentView, params);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(logout);
        super.onDestroy();
    }

    /**
     * 判断控件是否为null
     *
     * @param view 控件
     * @return true控件为null, false控件非null
     */
    public boolean viewIsNull(View view) {
        TextView tv = (TextView) view;
        return tv.getText().toString().equals("");
    }

    public void toask(Object o) {
        if (o != null) {
            Toast.makeText(this, o + "", Toast.LENGTH_LONG).show();
        }
    }

    public Map getHeanders() {
        Map map = new HashMap();
        map.put("accessToken", App.getUser().data.accessToken);
        map.put("code", Utils.getString(getApplicationContext(), "code"));
        map.put("parentCode", Utils.getString(getApplicationContext(), "parentCode"));
        return map;
    }

    public void volleyError(VolleyError volleyError) {
        hideProgress();
        if (volleyError != null) {
            toask(volleyError.toString());
        } else {
            toask("网络连接失败");
        }
    }

    protected Context getContext(){
        return this;
    }
}
