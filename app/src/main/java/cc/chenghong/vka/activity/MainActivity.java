package cc.chenghong.vka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import cc.chenghong.vka.fragment.BaseFragment;
import cc.chenghong.vka.fragment.GetMoneyFragment;
import cc.chenghong.vka.fragment.OrderFragment;
import cc.chenghong.vka.fragment.SetFragment;
import cc.chenghong.vka.fragment.VipFragment1;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 主页2015 9 25
 *
 * @author hcl
 */
public class MainActivity extends BaseActivity implements OnClickListener {

    @ViewInject(R.id.rb_1)
    RadioButton rb_1;
    @ViewInject(R.id.rb_2)
    RadioButton rb_2;
    @ViewInject(R.id.rb_3)
    RadioButton rb_3;
    @ViewInject(R.id.rb_4)
    RadioButton rb_4;
    @ViewInject(R.id.vBar)
    View vBar;

    @ViewInject(R.id.fl)
    FrameLayout fl;
    FragmentManager fragmentManager;
    BaseFragment currentFragment;
    long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//		statusBar(llBar);
        // Utils.setString(getApplicationContext(), "login","1");
        ViewUtils.inject(this);
        statusBar(vBar);
        fragmentManager = getSupportFragmentManager();
//		updateFragment(new GetMoneyFragment(), false);
        showFragment(GetMoneyFragment.class);
        selectRb(rb_1);
//		rb_2.setVisibility(View.GONE);
//		rb_4.setVisibility(View.GONE);
    }

    @OnClick({R.id.rb_1, R.id.rb_2, R.id.rb_3, R.id.rb_4})
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.rb_1:
                if (rb_1.isSelected()) {
                    return;
                }
                selectRb(rb_1);
                showFragment(GetMoneyFragment.class);
                break;
            case R.id.rb_2:
                if (rb_2.isSelected()) {
                    return;
                }
                selectRb(rb_2);
                showFragment(OrderFragment.class);
                break;
            case R.id.rb_3:
                if (rb_3.isSelected()) {
                    return;
                }
                selectRb(rb_3);
                showFragment(SetFragment.class);
                break;
            case R.id.rb_4:
                if (rb_4.isSelected()) {
                    return;
                }
                selectRb(rb_4);
                showFragment(VipFragment1.class);
                break;
            default:
                break;
        }
    }

    private void updateFragment(android.support.v4.app.Fragment f, boolean b) {
        // TODO Auto-generated method stub
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fl, f);
        if (!b) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    // 设置选中的按钮
    public void selectRb(View v) {
        rb_1.setSelected(false);
        rb_2.setSelected(false);
        rb_3.setSelected(false);
        rb_4.setSelected(false);
        v.setSelected(true);
    }

    /**
     * 返回按钮事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
                System.exit(0);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 显示一个Fragment
     *
     * @param clazz
     * @return 是否已经是当前fragment
     */
    public boolean showFragment(Class<?> clazz) {
        try {
            FragmentTransaction t = fragmentManager.beginTransaction();
            if (currentFragment != null
                    && !(currentFragment.getClass() == clazz)) {
                t.hide(currentFragment);
            }
            BaseFragment f = (BaseFragment) fragmentManager
                    .findFragmentByTag(clazz.getName());
            if (f == null)
                f = (BaseFragment) clazz.newInstance();
            if (!f.isAdded()) {
                t.add(R.id.fl, f, f.getClass().getName());
                t.show(f);
            } else {
                t.show(f);
            }
            boolean b = currentFragment == f;
            currentFragment = f;
            t.setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                    R.anim.fade_in, R.anim.fade_out);
            t.commit();
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //resultCode就是在B页面中返回时传的parama，可以根据需求做相应的处理
    }
}
