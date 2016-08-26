package cc.chenghong.vka.param;

import java.util.HashMap;
import java.util.Map;


/**
 * volley请求参数类（包含头部参数跟内容参数）
 * Created by 何成龙 on 2015/7/14.
 */
public class VolleyParam {
    private Map<String, String> headers = new HashMap<String, String>();

    public VolleyParam(Map headers, Map params) {
        this.headers = headers;
        this.params = params;
    }

    public VolleyParam() {
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    private Map<String, Object> params = new HashMap<String, Object>();

    /**
     * 构造方法
     *
     * @return
     */
    public static VolleyParam New() {
        return new VolleyParam();
    }

    /**
     * 存入头部参数
     *
     * @param key
     * @param val
     * @return
     */
    public VolleyParam putHeader(String key, String val) {
        if (key != null && val != null) {
            headers.put(key, val);
        }
        return this;
    }

    /**
     * 存入内容参数
     *
     * @param key
     * @param val
     * @return
     */
    public VolleyParam putParam(String key, Object val) {
        if (key != null && val != null) {
            params.put(key, val);
        }
        return this;
    }

    /**
     * 清空头部参数
     *
     * @return
     */
    private void clearHeaders() {
        headers.clear();
    }

    /**
     * 清空内容参数
     *
     * @return
     */
    private void clearParam() {
        params.clear();
    }

    /**
     * 添加头部参数
     *
     * @param map
     */
    public void putAllHeaders(Map map) {
        headers.putAll(map);
    }

    /**
     * 添加内容参数
     *
     * @param map
     */
    public void putAllParams(Map map) {
        params.putAll(map);
    }

}
