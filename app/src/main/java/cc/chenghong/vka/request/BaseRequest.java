package cc.chenghong.vka.request;

import java.util.Map;

import org.json.JSONObject;

import android.util.Log;

import cc.chenghong.vka.app.App;
import cc.chenghong.vka.listener.VolleyListener;
import cc.chenghong.vka.param.VolleyParam;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

/**
 * 网络连接基类
 *
 * @author hcl
 */
public class BaseRequest {
    private static final String TAG = "BaseRequest";
    private static RequestQueue requestQueue;

    /**
     * 构造方法
     */
    BaseRequest() {
        requestQueue = Volley.newRequestQueue(App.getInstance());
    }

    /**
     * 发送请求
     *
     * @param request
     */
    public static void addRequest(Request request) {
        getRequest();
        // 打印url
        String method = "";
        switch (request.getMethod()) {
            case 0:
                method = "GET";
                break;
            case 1:
                method = "POST";
                break;
            case 2:
                method = "PUT";
                break;
            case 3:
                method = "DELETE";
                break;
            default:
                break;
        }
        i("请求方式：" + method);
        Log.i(TAG, "请求地址：" + request.getUrl());
        // 打印头部参数
        try {
            Map<String, String> headers = request.getHeaders();
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    Log.i(TAG, "头部参数键：" + entry.getKey() +
                            ",头部参数值：" + entry.getValue());
                }
            }
        } catch (AuthFailureError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//		stop();
        //设置为只发送一次请求
        request.setRetryPolicy(new RetryPolicy() {

            @Override
            public void retry(VolleyError error) throws VolleyError {
                // TODO Auto-generated method stub

            }

            @Override
            public int getCurrentTimeout() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int getCurrentRetryCount() {//若网络连接失败时重试的次数
                // TODO Auto-generated method stub
                return 0;
            }
        });
        request.setRequestQueue(requestQueue);
        request.setTag("tag");
        requestQueue.add(request);
    }

    /**
     * 关闭所有请求
     */
    public static void stop() {
        getRequest();
        requestQueue.cancelAll("tag");
    }

    /**
     * 获取一个RequestQueue
     */
    public static void getRequest() {
        if (requestQueue == null) {
            new BaseRequest();
        }
    }

    /**
     * 发送json格式的post请求
     *
     * @param url      请求地址
     * @param param    请求参数
     * @param listener 请求监听器
     */
    public static void postJson(String url, final VolleyParam param,

                                VolleyListener listener) {
        JSONObject jsonObject = new JSONObject(param.getParams());
        i("发送的json参数：" + jsonObject);
        JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, jsonObject, listener.getListener(), listener.getErrorListener()) {
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                return param.getHeaders();
            }
        };
        addRequest(request);
    }

    /**
     * 发送json格式的post请求
     *
     * @param url           请求地址
     * @param jsonParam     json格式的参数
     * @param headers       头部参数
     * @param listener      请求成功监听器
     * @param errorListener 请求错误监听器
     */
    public static void postJson(String url, JSONObject jsonParam, final Map<String, String> headers,
                                Listener listener, ErrorListener errorListener) {
        i("发送的json参数：" + jsonParam);
        JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, jsonParam, listener, errorListener) {
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        addRequest(request);
    }
    /**
     * 发送json格式的post请求
     *
     * @param url           请求地址
     * @param jsonParam     json格式的参数
     * @param headers       头部参数
     * @param listener      请求成功监听器
     */
    public static void postJson(String url, JSONObject jsonParam, final Map<String, String> headers,
                                VolleyListener listener) {
        i("发送的json参数：" + jsonParam);
        JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, jsonParam, listener.getListener(), listener.getErrorListener()) {
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        addRequest(request);
    }

    /**
     * 发送需要header参数的get请求
     *
     * @param url           请求地址
     * @param headers       头部参数
     * @param listener      请求成功监听器
     * @param errorListener 请求错误监听器
     */
    public static void get(String url, final Map<String, String> headers,
                           Listener listener, ErrorListener errorListener) {
        //打印请求方式
//        i("请求方式：get");
//        if (headers != null) {
//            for (Map.Entry<String, String> entry : headers.entrySet()) {
//                System.out.println("头部参数键：" + entry.getKey() + "，头部参数值： " + entry.getValue());
//            }
//        }
        StringRequest stringRequest = new StringRequest(Method.GET, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // TODO Auto-generated method stub
                return headers;
            }
        };
        addRequest(stringRequest);
    }

    /**
     * 发送需要header参数的get请求
     *
     * @param url            请求地址
     * @param headers        头部参数
     * @param volleyListener 监听器
     */
    public static void get(String url, final Map<String, String> headers, VolleyListener volleyListener) {
        StringRequest stringRequest = new StringRequest(Method.GET, url, volleyListener.getListener(), volleyListener.getErrorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // TODO Auto-generated method stub
                return headers;
            }
        };
        addRequest(stringRequest);
    }

    /**
     * 发送普通get请求
     *
     * @param url           请求地址
     * @param listener      请求成功监听器
     * @param errorListener 请求错误监听器
     */
    public static void get(String url,
                           Listener listener, ErrorListener errorListener) {
        //打印请求方式
//		i("请求方式：get");
        StringRequest stringRequest = new StringRequest(Method.GET, url, listener, errorListener) {
        };
        addRequest(stringRequest);
    }

    static void i(String s) {
        Log.i(TAG, s);
    }
}
