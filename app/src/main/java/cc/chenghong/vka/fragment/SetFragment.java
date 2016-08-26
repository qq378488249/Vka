package cc.chenghong.vka.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import cc.chenghong.vka.activity.LoginActivity;
import cc.chenghong.vka.activity.R;
import cc.chenghong.vka.app.App;
import cc.chenghong.vka.util.Utils;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 设置2015 9 24
 * 
 * @author hcl
 * 
 */
public class SetFragment extends BaseFragment implements OnClickListener {
	@ViewInject(R.id.ll_exit)
	LinearLayout ll_exit;
	
	@ViewInject(R.id.ll_update)
	LinearLayout ll_updata;
	
	@ViewInject(R.id.ll)
	LinearLayout ll;

	@ViewInject(R.id.tv_update)
	TextView tv_update;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_set, container, false);
		ViewUtils.inject(this, view);
		initUI();
		return view;
	}

	private void initUI() {
		// TODO Auto-generated method stub
		tv_update.setText("检查更新（" + getVersionName() + "）");
	}

	@OnClick({ R.id.ll_exit, R.id.ll_update, R.id.ll,})
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ll_exit:
			final Dialog dialog = new Dialog(getActivity(), R.style.MyDialog);
			dialog.setContentView(R.layout.dialog_update);
			dialog.findViewById(R.id.tv_no).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
			dialog.findViewById(R.id.tv_yes).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Utils.clearString(getActivity(), "login");
							// Utils.clearString(getActivity(), "code");
							// Utils.clearString(getActivity(), "parentCode");
							startActivity(new Intent(getActivity(),
									LoginActivity.class));
							getActivity().finish();
							dialog.dismiss();
						}
					});
			dialog.show();
			break;
		case R.id.ll_update:
			App.toast("当前已是最新版本！");
			break;
		case R.id.ll:
			break;
		default:
			break;
		}
	}

	public String getVersionName() {

		// 用于管理安装的apk和未安装的apk
		PackageManager packageManager = getActivity().getPackageManager();

		try {
			// 得到apk的功能清单文件:为了防止出错直接使用getPackageName()方法获得包名
			// packageManager.getPackageInfo("com.xuliugen.mobilesafe", 0);
			PackageInfo packageInfo = packageManager.getPackageInfo(
					getActivity().getPackageName(), 0);

			// 返回版本名称
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
}
