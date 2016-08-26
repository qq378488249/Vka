package cc.chenghong.vka.listener;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by 何成龙 on 2016/8/18.
 */
public abstract class VolleyListener<T> {
    private String TAG = "VolleyListener";
    private Type type;

    public VolleyListener() {
        Type mySuperClass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
        this.type = type;
    }

    private Response.Listener<Object> listener = new Response.Listener<Object>() {
        @Override
        public void onResponse(Object response) {
            Log.i(TAG, response.toString());
            onSuccess((T) new Gson().fromJson(response.toString(), type));
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            String result = "";
            if (volleyError != null) {
                if (volleyError.toString().indexOf("UnknownHostException") != -1) {
                    result = "网络未连接";
                } else if (volleyError.toString().indexOf("TimeOut") != -1) {
                    result = "网络连接超时";
                } else {
                    result = "连接错误";
                }
            } else {
                result = "连接错误";
            }
            onFail(volleyError);
        }
    };

    /**
     * 请求成功监听器
     *
     * @param respons
     */
    public abstract void onSuccess(T respons);

    /**
     * 请求失败监听器
     *
     * @param volleyError
     */
    public abstract void onFail(VolleyError volleyError);

    public Response.Listener<Object> getListener() {
        return listener;
    }

    public void setListener(Response.Listener<Object> listener) {
        this.listener = listener;
    }

    public Response.ErrorListener getErrorListener() {
        return errorListener;
    }

    public void setErrorListener(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

}
