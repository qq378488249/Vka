package cc.chenghong.vka.entity;

import cc.chenghong.vka.response.ObjectResponse;
/**
 * 支付成功的订单类
 * @author hcl
 *
 */
public class SuccessOrder extends ObjectResponse<SuccessOrder>{
	
	private int id;

	private int cashFee;

	private int totalFee;

	private String storeName;

	private String transactionId;

	private String source;

	private String created;

	private String nickName;

	private int couponFee;

	private String parentName;

	public void setId(int id){
	this.id = id;
	}
	public int getId(){
	return this.id;
	}
	public void setCashFee(int cashFee){
	this.cashFee = cashFee;
	}
	public int getCashFee(){
	return this.cashFee;
	}
	public void setTotalFee(int totalFee){
	this.totalFee = totalFee;
	}
	public int getTotalFee(){
	return this.totalFee;
	}
	public void setStoreName(String storeName){
	this.storeName = storeName;
	}
	public String getStoreName(){
	return this.storeName;
	}
	public void setTransactionId(String transactionId){
	this.transactionId = transactionId;
	}
	public String getTransactionId(){
	return this.transactionId;
	}
	public void setSource(String source){
	this.source = source;
	}
	public String getSource(){
	return this.source;
	}
	public void setCreated(String created){
	this.created = created;
	}
	public String getCreated(){
	return this.created;
	}
	public void setNickName(String nickName){
	this.nickName = nickName;
	}
	public String getNickName(){
	return this.nickName;
	}
	public void setCouponFee(int couponFee){
	this.couponFee = couponFee;
	}
	public int getCouponFee(){
	return this.couponFee;
	}
	public void setParentName(String parentName){
	this.parentName = parentName;
	}
	public String getParentName(){
	return this.parentName;
	}
}
