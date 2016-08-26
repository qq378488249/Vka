package cc.chenghong.vka.util;

/**
 * 数学工具类
 * Created by 何成龙 on 2016/8/17.
 */
public class MathUtils {
    /**
     * 清除小数点后面无用的零
     *
     * @return
     */
    public static String removeZero(Object object) {
        String result = "";
        if (object == null) {
            new NullPointerException("MathUtils param not null");
        } else {
            result = object + "";
            if (result.indexOf(".") > 0) {
                //正则表达
                result = result.replaceAll("0+?$", "");//去掉后面无用的零
                result = result.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
            }
        }
        return result;
    }
}
