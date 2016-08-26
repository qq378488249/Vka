package cc.chenghong.vka.entity;

/**
 * 0刷新订单列表，1支付宝支付，2微信支付，3，会员支付，4QQ支付，5核销卡券，6核销会员券
 */
public class FirstEvent {
    private String mMsg;

    public FirstEvent(String msg) {
        // TODO Auto-generated constructor stub  
        mMsg = msg;
    }

    public FirstEvent(int code) {
        // TODO Auto-generated constructor stub
        this.code = code;
    }

    public FirstEvent(String msg, int code) {
        // TODO Auto-generated constructor stub  
        mMsg = msg;
        this.code = code;
    }

    public String getMsg() {
        return mMsg;
    }

    /**
     * 请求码(1支付宝，2微信，3会员，4核销卡券，0支付成功)
     */
    private int code;

    public String getmMsg() {
        return mMsg;
    }

    public void setmMsg(String mMsg) {
        this.mMsg = mMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
