package cc.chenghong.vka.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import cc.chenghong.vka.activity.R;
import cc.chenghong.vka.entity.User;
import cc.chenghong.vka.request.BaseRequest;
import cc.chenghong.vka.util.SharedPreferencesHelper;
import cc.chenghong.vka.util.UITools;
import cc.chenghong.vka.api.Api;

public class App extends Application {
    public static App app;
    /**
     * 保存变量的文件名
     */
    static String fileName = "Vka";

    private static Toast toast;
    /**
     * 支付方式(1支付宝，2微信，3会员，4qq支付，5核销卡券,6核销优惠券)
     */
    public static int payType = 1;
    /**
     * 数据库名与版本号
     */
    public static final String DB_NAME = "product_db";
    public static final int DB_VERSION = 1;
    // 上下文
    static Application application;
    private List<Activity> mList = new LinkedList<Activity>();
    private static App instance;
    static final String TAG = App.class.getSimpleName();
    /**
     * 支付金额
     */
    public static String ap_money = "1";
    /**
     * 支付轮循的索引
     */
    public static int payIndex = 1;
    /**
     * 支付出错时查询的索引
     */
    public static int findPayIndex = 1;
    /**
     * 取消支付的索引
     */
    public static int cancelPayIndex = 1;
    /**
     * 列表中显示图片的选项
     */
    private DisplayImageOptions listViewDisplayImageOptions;
    /**
     * 是否可以发送请求
     */
    public static boolean isRequest = true;
    /**
     * 是否显示取消订单
     */
    public static boolean isShowCancelPay = false;
    /**
     * 订单的状态(1支付中，2查询中，3退单中, 0非支付状态)
     */
    public static int payState = 0;
    /**
     * 是否刷新列表
     */
    public static boolean isRefresh = false;
    /**
     * 是否有dialog
     */
    public static boolean isDialog = false;
    /**
     *
     */
    public static String payCode = "";
    /**
     * 铭牌信息
     */
    public static String brandName = "技术支持:上海澄泓信息科技有限公司\n商务电话:15001819885,18601694368\n";

    public static boolean isDeBug = false;

    /**
     * 添加一个activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    /**
     * 清空所有activity，完全退出程序
     */
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.application = this;
        isDeBug = true;
//		setDeBug(false);
        SharedPreferencesHelper.init(this);
        UITools.init(this);
        // 设置列表图片显示配置
        listViewDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_moren)
                .showImageForEmptyUri(R.drawable.icon_moren)
                .showImageOnFail(R.drawable.icon_moren).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                // .displayer(new RoundedVignetteBitmapDisplayer(10, 6))
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        app = this;
    }

    /**
     * 设置是否正式版
     *
     * @param b
     */
    void setDeBug(boolean b) {
        if (!b) {
            Api.MAIN = Api.OFFICIAL;
        } else {
            Api.MAIN = Api.DEBUG;
        }
    }

    public static Application getInstance() {
        return app;
    }

    private static User user;

    /**
     * 保存用户信息
     */
    public static void setUser(User user) {
        App.user = user;
        SharedPreferencesHelper.saveJSON("user", user);
    }

    /**
     * 获取用户信息
     */
    public static User getUser() {
        if (user == null) {
            user = SharedPreferencesHelper.getJSON("user", User.class);
        }
        return user;
    }

    /**
     * 保存一个变量
     */
    public static void setString(String name, String value) {
        SharedPreferences sharedPreferences = application.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(name, value).commit();
    }

    /**
     * 取出一个变量
     */
    public static String getString(String name) {
        return application.getSharedPreferences(fileName, Context.MODE_PRIVATE)
                .getString(name, "");
    }

    /**
     * 清除一个变量
     */
    public static void cleanString(String name) {
        application.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
                .remove(name).commit();
    }

    /**
     * 初始化ImageLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    public DisplayImageOptions getListViewDisplayImageOptions() {
        return listViewDisplayImageOptions;
    }

    /**
     * 获取当前进行中的订单状态（1支付中，2查询中，3退单）
     *
     * @return
     */
    public static int getStart() {
        return new BigDecimal(App.getString("start")).intValue();
    }

    /**
     * 取消支付
     */
    public static void stopPay() {
        BaseRequest.stop();
        App.payState = 0;
        App.yesRequest();
        App.payCode = "";
//		App.isRefresh = true;
    }

    public static void toast(String s) {
        toast(s, Toast.LENGTH_LONG);
    }

    public static void toast(Object o) {
        toast(o + "", Toast.LENGTH_LONG);
    }

    private static void toast(String s, int length) {
        try {
            if (toast != null) {
                toast.setText(s);
            } else {
                toast = Toast.makeText(getInstance(), s, length);
            }
            toast.show();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public boolean isRequest() {
        return isRequest;

    }

    /**
     * 获取是否可以支付(1可以，0不可以)
     *
     * @return
     */
    public static boolean getRequest() {
        if (App.getString("isRequest").equals("") || App.getString("isRequest").equals("0")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置为不可以支付
     */
    public static void noRequest() {
        App.setString("isRequest", "0");
    }

    /**
     * 设置为可以支付
     */
    public static void yesRequest() {
        App.setString("isRequest", "1");
    }

}
