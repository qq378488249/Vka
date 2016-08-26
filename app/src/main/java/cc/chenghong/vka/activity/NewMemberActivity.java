package cc.chenghong.vka.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.chenghong.vka.api.Api;
import cc.chenghong.vka.listener.VolleyListener;
import cc.chenghong.vka.param.VolleyParam;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.response.BaseResponse;
import cc.chenghong.vka.util.DataUtils;
import cc.chenghong.vka.util.UITools;
import cc.chenghong.vka.util.Utils;

/**
 * 新建会员
 */
public class NewMemberActivity extends BaseActivity {

    @Bind(R.id.et1)
    EditText et1;
    @Bind(R.id.et2)
    EditText et2;
    @Bind(R.id.et3)
    EditText et3;
    @Bind(R.id.tv4)
    TextView tv4;
    @Bind(R.id.et5)
    EditText et5;
    @Bind(R.id.et6)
    EditText et6;
    @Bind(R.id.bt)
    Button bt;

    Calendar calendar;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_new_member);
        setTitleName("新建会员");
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();
        tv4.setText(DataUtils.getData(DataUtils.yyyyMMdd));
    }

    @Override
    public void clickLeft() {
        super.clickLeft();
        Utils.closeJP(et1);
    }

    //显示日期
    private void show_date() {
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(this, null,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            //手动设置按钮
            datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                    DatePicker datePicker = datePickerDialog.getDatePicker();
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth();
                    int day = datePicker.getDayOfMonth();
                    String m = (month + 1) + "";
                    String d = day + "";
                    if (month < 9) {
                        m = "0" + (month + 1);
                    }
                    if (day < 10) {
                        d = "0" + day;
                    }
                    tv4.setText(year + "-" + m + "-" + d);
                    String strData = DataUtils.getData("yyyy-MM-dd");
                    String strData2 = getValue(tv4);
                    boolean b = DataUtils.data1_compare_data2(strData, strData2, "yyyy-MM-dd");
                    if (!b) {
                        toask("生日不能大于当前时间");
                        tv4.setText(DataUtils.getData("yyyy-MM-dd"));
                        datePickerDialog.updateDate(Integer.valueOf(DataUtils.getData("yyyy")), Integer.valueOf(DataUtils.getData("MM")) - 1, Integer.valueOf(DataUtils.getData("dd")));
                    }
                }
            });
            //取消按钮，如果不需要直接不设置即可
            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    datePickerDialog.dismiss();
                }
            });
        }
        String[] sDate = getValue(tv4).split("-");
        datePickerDialog.updateDate(Integer.valueOf(sDate[0]), Integer.valueOf(sDate[1]) - 1, Integer.valueOf(sDate[2]));
        datePickerDialog.show();
//        datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//
//            }
//        });
    }

    @OnClick({R.id.tv4, R.id.bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv4:
                show_date();
                break;
            case R.id.bt:
                if (viewIsNull(et1)) {
                    toast("姓名不能为空");
                    return;
                }
                if (viewIsNull(et2)) {
                    toast("卡号不能为空");
                    return;
                }
                if (!viewIsNull(et3)) {
                    if (!isPhoneNum(et3)) {
                        toast("手机不正确");
                        return;
                    }
                }
                if (viewIsNull(tv4)) {
                    toast("生日不能为空");
                    return;
                }
                if (viewIsNull(et5) || viewIsNull(et6)) {
                    toast("密码不能为空");
                    return;
                }
                if (!getValue(et5).equals(getValue(et6))) {
                    toast("2次输入的密码不一致");
                    return;
                }
                submit();
                break;
        }
    }

    private void submit() {
        progress("创建中...");
        VolleyParam param = VolleyParam.New();
        param.putParam("name", getValue(et1)).
                putParam("mobile", getValue(et2)).
                putParam("birthday", getValue(tv4)).
                putParam("password", getValue(et5));
        if (!viewIsNull(et3)) {
            param.putParam("otherMobile", getValue(et3));
        }

        param.putAllHeaders(getHeanders());
        BaseRequest.postJson(Api.newMember, param, new VolleyListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse responsT) {
                if (responsT.isSuccess()) {
                    toast("新建会员成功");
                    finish();
                } else {
                    toast("新建会员失败， " + responsT.getMessage());
                }
            }

            @Override
            public void onFail(VolleyError volleyError) {
                volleyError(volleyError);
            }
        });
    }
}
