package cc.chenghong.vka.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cc.chenghong.vka.response.BaseResponse;

import com.google.gson.Gson;

/**
 * 安卓工具类2015 9 24
 *
 * @author hcl
 */
public class Utils {

    private static String file = "Vka";

    /**
     * 获取控件的值
     *
     * @param 控件名
     * @return 控件值
     */
    public static String getValue(View v) {
        TextView tv = (TextView) v;
        if (tv == null) {
            return null;
        } else {
            return tv.getText().toString();
        }
    }

    /**
     * 判断控件是否为空
     *
     * @param v
     * @return
     */
    public static boolean isNull(View v) {
        TextView tv = (TextView) v;
        if (tv == null) {
            return false;
        }
        if (tv.getText().toString().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * toask弹窗
     *
     * @param context
     * @param s
     */
    public static void toask(Context context, String s) {
        Toast.makeText(context, s, 3).show();
    }

    /**
     * 保存一个变量
     *
     * @param context 上下文
     * @param name    变量名
     * @param value   变量值ֵ
     */
    public static void setString(Context context, String name, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                file, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(name, value).commit();
    }

    /**
     * 获取变量的值
     *
     * @param context
     * @param name    变量名
     * @return 变量值
     */
    public static String getString(Context context, String name) {
        return context.getSharedPreferences(file, Context.MODE_PRIVATE)
                .getString(name, "");
    }

    /**
     * 判断变量是否为空
     *
     * @param context 上下文
     * @param name    变量名
     * @return 为空返回true 不为空返回false
     */
    public static boolean isNullString(Context context, String name) {
        if (context.getSharedPreferences(file, Context.MODE_PRIVATE)
                .getString(name, "").equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 清除一个变量
     *
     * @param context 上下文
     * @param name    变量名
     */
    public static void clearString(Context context, String name) {
        context.getSharedPreferences(file, Context.MODE_PRIVATE).edit()
                .remove(name).commit();
    }

    /**
     * 把返回码转换为字符串
     *
     * @param 返回码
     * @return 字符串
     */
    public static String codeToString(int i) {
        String s = "";
        switch (i) {
            case 0:
                s = "交易中";
                break;
            case 1:
                s = "交易成功";
                break;
            case 2:
                s = "已退款";
                break;
            case 1040:
                s = "门店编码不存在";
                break;
            case 1001:
                s = "账号或密码不正确";
                break;
            case 1041:
                s = "该员工不是本店员工";
                break;
            case 1002:
                s = "账号锁定";
                break;
            case 1007:
                s = "余额不足";
                break;
            case 1013:
                s = "积分不足";
                break;
            case 1018:
                s = "该订单已经超过最后退单时间！";
                break;
            case 1022:
                s = "账号信息验证失败，无法操作！";
                break;
            case 1023:
                s = "登陆过期，请重新登陆！";
                break;
            case 1025:
                s = "密码为空！";
                break;
            case 1027:
                s = "订单已取消，请不要重复操作！";
                break;
            case 1064:
                s = "不是分店账号，无法进行相关操作！";
                break;
            case 1231:
                s = "此次退款存在安全问题，请重新操作一次！";
                break;
            case 1232:
                s = "此次退款存在安全问题，请重新操作一次！";
                break;
            case 1240:
                s = "退款失败，请走投诉渠道进行投诉！";
                break;
            case 1237:
                s = "找不到商户信息,请确认是否有录入商户信息！";
                break;
            case 1238:
                s = "找不到平台信息,请确认是否有录入平台信息！";
                break;
            case 1243:
                s = "该笔交易已经退过，请不要重复操作，如有疑问请联系管理员！";
                break;
            case 1250:
                s = "授权码过期或无效！";
                break;
            case 1253:
                s = "请求超时，请检查网络连接！";
                break;
            case 1254:
                s = "等待用户输入密码时间过长，已取消该笔订单，请重新下单！";
                break;
            case 1255:
                s = "订单已关闭，无法付款，请重新下单!";
                break;
            case 1256:
                s = "订单已关闭，无法付款，请重新下单!";
                break;
            case 1005:
                s = "账号不存在!";
                break;
            case 1008:
                s = "没有权限!";
                break;
            case 1012:
                s = "无效参数!";
                break;
            case 1054:
                s = "支付码不存在!";
                break;
            case 1044:
                s = "优惠券已使用，无法退单!";
                break;
            case 1055:
                s = "支付码已过期!";
                break;
            case 1057:
                s = "本次消费会员卡与之前消费的会员卡不一致(不能同时消费多张卡)!";
                break;
            case 1091:
                s = "目前暂未对总店开放该操作权限!";
                break;
            case 1202:
                s = "取消订单时,赠送物品已使用，无法退单!";
                break;
            case 1060:
                s = "此订单不支持退单!";
                break;
            default:
                s = "系统错误，请联系管理员";
                break;
        }
        return s;
    }

    /**
     * 对象转化成JSON
     *
     * @param obj 要转换的对象
     * @return 对象的JOSN格式
     */
    public static String objectToJson(Object obj) {
        return new Gson().toJson(obj);
    }

    /**
     * JSON转化成对象
     *
     * @param class1   要转换的对象
     * @param response &nbsp;&nbsp;&nbsp;JSON格式的对象
     * @return 需要clazz的对象
     */
    public static <T> T jsonToObject(Class<T> clazz, String response) {
        return new Gson().fromJson(response, clazz);
    }

    /**
     * 获取意图里的字符串的值
     *
     * @param intent 意图
     * @param name   字符串名
     * @return 字符串值
     */
    public static String getIntentValue(Intent intent, String name) {
        return intent.getStringExtra(name);
    }

    /**
     * 拨打电话
     *
     * @param context 上下文
     * @param phone   电话号码
     */
    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    /**
     * 发送短信
     *
     * @param context 上下文
     * @param phone   接受短信号码
     * @param content 短信内容
     */
    public static void sendSMS(Context context, String phone, String content) {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SENDTO);
        intent2.setData(Uri.parse("smsto:" + phone));
        intent2.putExtra("sms_body", content);
        context.startActivity(intent2);
    }

    /**
     * 字符串转为成时间
     *
     * @param string 需要转换的字符串
     * @return 时间的字符串格式
     */
    public static String stringToDate(String string) {
        Date date = new Date(Long.valueOf(string));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    /**
     * 把int类型转化为小数点后面只有2位数的double型
     *
     * @param i 需要转换的int
     * @return String类型的int
     */
    public static String intToDouble(int i) {
        int b = (int) (i * 100);
        double a = (double) b / 100;
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(a);
    }

    /**
     * 把double类型转化为小数点后面只有2位数的double型
     *
     * @param i 需要转换的double
     * @return double
     */
    public static double doubleToTwo(double i) {
        double d1 = 100.00;
        d1 = i * d1;
        double a = (double) d1 / 100;
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(a));
    }

    /**
     * 关闭键盘
     *
     * @param context  上下文
     * @param editText 需要关闭键盘的编辑框
     */
    public static void closeKeyboard(Context context, EditText editText) {
        // 得到InputMethodManager的实例
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        // if (imm.isActive()) {
        // // 如果开启
        // imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
        // InputMethodManager.HIDE_NOT_ALWAYS);
        // // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        // }

    }

    public static void closeJP(Context context, EditText editText) {
        try {

            ((InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE))

                    .hideSoftInputFromWindow(editText.getWindowToken(),

                            InputMethodManager.HIDE_NOT_ALWAYS);

        } catch (Exception e) {
        } finally {
        }
    }

    /**
     * 关闭键盘
     *
     * @param editText
     */
    public static void closeJP(EditText editText) {
        try {

            ((InputMethodManager) editText.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE))

                    .hideSoftInputFromWindow(editText.getWindowToken(),

                            InputMethodManager.HIDE_NOT_ALWAYS);

        } catch (Exception e) {
        } finally {
        }
    }

    /**
     * 打开键盘
     *
     * @param context
     * @param editText
     */
    public static void openJP(EditText editText) {
        try {
            editText.setFocusable(true);
            editText.requestFocus();
            ((InputMethodManager) editText.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .showSoftInput(editText, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提取字符串中的数字，包含小数点
     *
     * @param strNum 包含数字的字符串
     * @return
     */
    public static BigDecimal extractNumber(String strNum) {
        BigDecimal result = null;
        char[] b = strNum.toCharArray();
        String s = "";
        for (int i = 0; i < b.length; i++) {
            if (("0123456789.").indexOf(b[i] + "") != -1) {
                s += b[i];
            }
        }
        return result = new BigDecimal(s);
    }
}
