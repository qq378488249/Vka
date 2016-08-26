package cc.chenghong.vka.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单实体类2015 10 2
 * @author hcl
 *
 */
public class Order implements Serializable{
	public Long id;

    public Long storeId;	//门店id

    public String storeName;	//门店名称
    
    public Long cardId;

    public String mobile;	//手机

    public String address;	//送货地址

    public BigDecimal amount;	//总金额

    public Integer payType;	//支付方式   1：微信支付, 2：会员卡支付, 3：货到付款, 4：支付宝支付
    
    public Long transactionId;	//选择会员卡付款时，消费记录id，其中含有会员卡号（cardId）
    
    public Integer isTakeaway;	//是不是外卖，0不是 1是
    
    public Integer status;	//订单状态 0:未派送，1:已派送，2:已成功，3:已退单
    
    public BigDecimal discount;
    
    public Long tableId; //桌位ID
    
    public Integer tableNum; //桌位号
    
    /**
     * 付款_应付金额 (原始金额)
     * @author guoyu
     */
    public BigDecimal itemSubtotal;
    
    public Integer orderSource; //订单来源 0-pos机，1-微商城
    public String created;
    
    public Long userId;
    
    public String outTradeNo;//商品订单号
    
    public Integer payStatus;//付款状态 0-未付款，1-已付款，2-已退款
    public List<OrdersDetailsResponse> ordersDetailsList;
    public class OrdersDetailsResponse extends BaseEntity{
    	public Long id;

    	public Long ordersId;	//订单id

    	public Long productId;	//商品id

    	public String productName;	//商品名称

    	public String attributeIds;	//购买的产品对应的附加属性id（如加糖去冰）(1,2,3,4)

    	public String attributeNames;	//(加糖,去冰)

    	public BigDecimal price;	//单个商品及附加条件的价格（商品价格+商品属性价格）

    	public Integer count;	//商品购买个数

    	public Integer source;   //1 商品来自vcard  2商品来自vivipos

    	public Long categoriesId;  //商品类别ID

    	public String productId2;  //商品ID 字符串类型

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getIsTakeaway() {
        return isTakeaway;
    }

    public void setIsTakeaway(Integer isTakeaway) {
        this.isTakeaway = isTakeaway;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Integer getTableNum() {
        return tableNum;
    }

    public void setTableNum(Integer tableNum) {
        this.tableNum = tableNum;
    }

    public BigDecimal getItemSubtotal() {
        return itemSubtotal;
    }

    public void setItemSubtotal(BigDecimal itemSubtotal) {
        this.itemSubtotal = itemSubtotal;
    }

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public List<OrdersDetailsResponse> getOrdersDetailsList() {
        return ordersDetailsList;
    }

    public void setOrdersDetailsList(List<OrdersDetailsResponse> ordersDetailsList) {
        this.ordersDetailsList = ordersDetailsList;
    }
}
