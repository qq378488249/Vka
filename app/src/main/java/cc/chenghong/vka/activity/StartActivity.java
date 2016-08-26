package cc.chenghong.vka.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import cc.chenghong.vka.util.Utils;
import cc.chenghong.vka.api.Api;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 启动页2015 9 24
 *
 * @author hcl
 */
public class StartActivity extends BaseActivity {
    @ViewInject(R.id.tv)
    TextView tv;
    @ViewInject(R.id.vBar)
    View vBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_welcome);
        ViewUtils.inject(this);
        statusBar(vBar);
        if (Api.MAIN.indexOf("v-ka") != -1) {
            tv.setText("V-KA云会员系统");
        } else {
            tv.setText("Vi-Ni云会员系统");
        }
        new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                // TODO Auto-generated method stub
                if (Utils.isNullString(getApplicationContext(), "login")) {
                    startActivity(new Intent(getApplicationContext(),
                            LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(),
                            MainActivity.class));
                    finish();
                }
                return false;
            }
        }).sendEmptyMessageDelayed(0, 1000);// 延迟1000毫秒执行
    }
}
