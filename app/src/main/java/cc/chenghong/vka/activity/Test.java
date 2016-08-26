package cc.chenghong.vka.activity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

import cc.chenghong.vka.api.Api;
import cc.chenghong.vka.app.App;

/**
 * Created by 何成龙 on 2016/8/16.
 */
public class Test {
    public static void main(String[] args) {
        int i = Api.MAIN.indexOf("v-ka");
        System.out.println(i+"xxxxxxxxxxxxxxxxxxxxxxx");
        RequestQueue requestQueue = Volley.newRequestQueue(App.getInstance());
        StringRequest request = new StringRequest("www.LiBlue.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        requestQueue.add(request);
    }
}
