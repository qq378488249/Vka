package cc.chenghong.vka.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.chenghong.vka.app.App;
import cc.chenghong.vka.util.Utils;

/**
 * Activity基类
 *
 * @author guozhiwei 2015-6-15
 */
public class BaseActivity extends ProgressFragmentActivity {
    public String TAG = getClass().getSimpleName();
    PopupWindow p;
    public TextView tv_right, tv_right_share, tv_right_fav,
            tv_right_share_left, tv_left;
    public TextView tv_center;
    private LinearLayout ll_content;
    public ImageButton ib_left, ib_right;
    public RelativeLayout rl_top;
    public LinearLayout ll_right;
    protected View contentView;
    // public static View shareView;
    public String shareText;
    public Context context;
    public LinearLayout llBar;
    public LinearLayout llBase;
    /**
     * 通知栏控件
     */
    public View vBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_base);
        context = this;
        // ShareSDK.initSDK(context);
        initView();
        // initShareView();
        registerReceiver(logout, new IntentFilter("logout"));
    }

    BroadcastReceiver logout = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            finish();
        }
    };

    public ImageView findImageViewById(int resId) {
        return (ImageView) findViewById(resId);
    }

    public TextView findTextViewById(int resId) {
        return (TextView) findViewById(resId);
    }

    public ListView findListViewById(int resId) {
        return (ListView) findViewById(resId);
    }

    public RelativeLayout findRelativeLayoutById(int resId) {
        return (RelativeLayout) findViewById(resId);
    }

    public LinearLayout findLinearLayoutById(int resId) {
        return (LinearLayout) findViewById(resId);
    }

    public ScrollView findScrollViewById(int resId) {
        return (ScrollView) findViewById(resId);
    }

    public EditText findEditTextById(int resId) {
        return (EditText) findViewById(resId);
    }

    public Button findButtonById(int resId) {
        return (Button) findViewById(resId);
    }

    public void initView() {
        ib_left = (ImageButton) findViewById(R.id.ib_left);
        ib_right = (ImageButton) findViewById(R.id.ib_right);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right_share = (TextView) findViewById(R.id.tv_right_share);
        tv_right_fav = (TextView) findViewById(R.id.tv_right_fav);
        tv_right_share_left = findTextViewById(R.id.tv_right_share_left);
        tv_left = findTextViewById(R.id.tv_left);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        ll_right = (LinearLayout) findViewById(R.id.ll_right);
        rl_top = (RelativeLayout) findViewById(R.id.rl_top);
        llBar = findLinearLayoutById(R.id.llBar);
        vBar = findViewById(R.id.vBar);
        llBase = findLinearLayoutById(R.id.llBase);

        ib_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clickLeft();
            }
        });
        tv_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clickLeft();
            }
        });
        tv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clickRight();
            }
        });
        ib_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ibClickRight();
            }
        });
//        statusBar(vBar);
//        statusBar(llBar);
    }

    /**
     * 沉浸状态栏
     */
//    public void initStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            llBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
//            llBar.setVisibility(View.VISIBLE);
////            vBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
////            vBar.setVisibility(View.VISIBLE);
//            getWindow().setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        } else {
//            llBar.setVisibility(View.GONE);
////            vBar.setVisibility(View.GONE);
//        }
//    }
//
//    /**
//     * 获取系统状态栏高度
//     *
//     * @return 系统状态栏高度
//     */
//    public int getStatusBarHeight() {
//        int result = 0;
//        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = getResources().getDimensionPixelSize(resourceId);
//        }
//        Log.i(TAG, "statrsBar:" + result);
//        int i = UITools.px2dip(result);
//        Log.i(TAG, "statrsBar:" + i);
//        return result;
//    }
    protected void statusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams layoutParams = vBar.getLayoutParams();
            layoutParams.height = getStatusBarHeight();
            vBar.setLayoutParams(layoutParams);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 初始化沉浸状态栏
     */
    protected void statusBar(View ll) {
        //透明状态栏
        if (ll != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ViewGroup.LayoutParams layoutParams = ll.getLayoutParams();
                layoutParams.height = getStatusBarHeight();
                ll.setLayoutParams(layoutParams);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * 获取系统状态栏高度
     *
     * @return 系统状态栏高度
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 加入页面内容布局
     */
    protected void contentView(int layoutId) {
        statusBar(vBar);
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

    public void clickLeft() {
        finish();
    }

    public void clickRight() {
    }

    public void ibClickRight() {

    }

    public void setIbRightImg(int imgId) {
        ib_right.setImageResource(imgId);
        ib_right.setVisibility(View.VISIBLE);
    }

    public void setIbLeftImg(int imgId) {
        ib_left.setImageResource(imgId);
        ib_left.setVisibility(View.VISIBLE);
    }

    public void setRightImg(int imgId) {
        tv_right.setBackgroundResource(imgId);
        tv_right.setVisibility(View.VISIBLE);
    }

    public void setLeftImg(int imgId) {
        tv_left.setBackgroundResource(imgId);
        tv_left.setVisibility(View.VISIBLE);
        ib_left.setVisibility(View.GONE);

    }

    public void setLeftText(String imgId) {
        tv_left.setText(imgId);
        tv_left.setVisibility(View.VISIBLE);

    }

    public void setRightName(String name) {
        Log.i("BaseActivity", "tv_right----------- setRight>>>>");
        tv_right.setText(name);
        tv_right.setVisibility(View.VISIBLE);
    }

    public void setTitleName(String title) {
        tv_center.setText(title);
    }

    public void showRightLinear(int imgShare, int imgfav) {

        if (ll_right.getVisibility() == View.GONE) {

            ll_right.setVisibility(View.VISIBLE);
        }
        tv_right_share.setBackgroundResource(imgShare);
        tv_right_share.setVisibility(View.VISIBLE);
        tv_right_fav.setBackgroundResource(imgfav);
        tv_right_fav.setVisibility(View.VISIBLE);
        // tv_right_fav tv_right_share
    }

    /**
     * 收藏
     *
     * @param imgShare
     */
    public void showRightLinear(int imgShare) {

        if (ll_right.getVisibility() == View.GONE) {

            ll_right.setVisibility(View.VISIBLE);
        }
        tv_right_share.setBackgroundResource(imgShare);
        tv_right_share.setVisibility(View.VISIBLE);
    }

    /**
     * 设置分享左边的图�?
     */
    public void showRightShareLeft(int imgSrc) {
        if (ll_right.getVisibility() == View.GONE) {
            ll_right.setVisibility(View.VISIBLE);
        }
        tv_right_share_left.setBackgroundResource(imgSrc);
        tv_right_share_left.setVisibility(View.VISIBLE);
    }

    /**
     * 加入分享�?�? 初始化此方法
     */
    /*
     * public void initShareView(){ shareView =
	 * LayoutInflater.from(context).inflate(R.layout.share_layout, null); tv_qq
	 * = (TextView)shareView.findViewById(R.id.tv_qq); tv_weixin =
	 * (TextView)shareView.findViewById(R.id.tv_weixin); tv_sina =
	 * (TextView)shareView.findViewById(R.id.tv_sina); tv_tencent =
	 * (TextView)shareView.findViewById(R.id.tv_tencent); tv_share =
	 * (TextView)shareView.findViewById(R.id.tv_share);
	 * tv_qq.setOnClickListener(shareClick);
	 * tv_weixin.setOnClickListener(shareClick);
	 * tv_sina.setOnClickListener(shareClick);
	 * tv_tencent.setOnClickListener(shareClick);
	 * tv_share.setOnClickListener(shareClick);
	 *
	 * shareView.setVisibility(View.GONE); //
	 * tv_qq,tv_weixin,tv_sina,tv_tencent,tv_share
	 *
	 * }
	 */

	/*
     * private View.OnClickListener shareClick = new OnClickListener() {
	 *
	 * @Override public void onClick(View v) { ShareModel sharOk = null;
	 * switch(v.getId()){ case R.id.tv_qq: sharOk = new ShareModel(); break;
	 * case R.id.tv_weixin: break; case R.id.tv_sina: sharOk = new ShareModel();
	 * //sharOk.setTitle("title"); sharOk.setText(shareText);
	 * ShareUtil.showShare(true, "SinaWeibo", BaseActivity.this, sharOk); break;
	 * case R.id.tv_tencent: sharOk = new ShareModel();
	 * sharOk.setText(shareText); ShareUtil.showShare(true, "TencentWeibo",
	 * BaseActivity.this, sharOk); break; case R.id.tv_share:
	 * shareView.setVisibility(View.GONE); break; }
	 *
	 * } };
	 */
    public void toast(String msg) {
        App.toast(msg);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(logout);
        super.onDestroy();
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
            if (volleyError.toString().indexOf("UnknownHostException") != -1) {
                toast("网络未连接");
            } else if (volleyError.toString().indexOf("TimeOut") != -1) {
                toast("网络连接超时");
            } else {
                toast("连接错误");
            }
        } else {
            toast("连接错误");
        }
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
        if (o == null) {
            toast("");
        } else {
            toast(o + "");
        }
    }

    /**
     * 2016 03 15
     * 判断控件里的值是否为手机号
     *
     * @param view
     * @return false 手机号正确
     */
    boolean isPhoneNum(View view) {
        String value = getValue(view);
//        String regExp = "^1[3-8]\\\\d{9}$";
        String regExp = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
//        String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(value);
//        if (!m.find()) {
//            App.toask("请输入正确的手机号");
//        }
        return m.find();
    }

    public String getValue(View view) {
        TextView tv = (TextView) view;
        return tv.getText().toString();
    }
    protected Context getContext(){
        return this;
    }
}
