package cc.chenghong.vka.entity;

import cc.chenghong.vka.response.ObjectResponse;

/**
 * 会员卡支付订单
 * 
 * @author hcl
 * 
 */
public class CardOrder extends ObjectResponse<CardOrder>{
	
	private int id;

	private String mobile;

	private String name;

	private String otherMobile;

	private int storeId;

	private String storeName;

	private String userId;

	private String idCard;

	private String birthday;

	private String birthday2;

	private double totalRmb;

	private double balance;

	private int point;

	private int locked;

	private String sex;

	private String activated;

	private String activationStore;

	private String ticketName;

	private int type;

	private String province;

	private String city;

	private String district;

	private String openId;

	private String lastDay;

	private int totalTimes;

	private double totalAmount;

	private int bindOpenId;

	private String wxCardCode;

	private int attribute;

	private double discount;

	private String created;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setOtherMobile(String otherMobile) {
		this.otherMobile = otherMobile;
	}

	public String getOtherMobile() {
		return this.otherMobile;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getStoreId() {
		return this.storeId;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreName() {
		return this.storeName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday2(String birthday2) {
		this.birthday2 = birthday2;
	}

	public String getBirthday2() {
		return this.birthday2;
	}

	public void setTotalRmb(double totalRmb) {
		this.totalRmb = totalRmb;
	}

	public double getTotalRmb() {
		return this.totalRmb;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return this.balance;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getPoint() {
		return this.point;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public int getLocked() {
		return this.locked;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return this.sex;
	}

	public void setActivated(String activated) {
		this.activated = activated;
	}

	public String getActivated() {
		return this.activated;
	}

	public void setActivationStore(String activationStore) {
		this.activationStore = activationStore;
	}

	public String getActivationStore() {
		return this.activationStore;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public String getTicketName() {
		return this.ticketName;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvince() {
		return this.province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return this.city;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setLastDay(String lastDay) {
		this.lastDay = lastDay;
	}

	public String getLastDay() {
		return this.lastDay;
	}

	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}

	public int getTotalTimes() {
		return this.totalTimes;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getTotalAmount() {
		return this.totalAmount;
	}

	public void setBindOpenId(int bindOpenId) {
		this.bindOpenId = bindOpenId;
	}

	public int getBindOpenId() {
		return this.bindOpenId;
	}

	public void setWxCardCode(String wxCardCode) {
		this.wxCardCode = wxCardCode;
	}

	public String getWxCardCode() {
		return this.wxCardCode;
	}

	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}

	public int getAttribute() {
		return this.attribute;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getDiscount() {
		return this.discount;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getCreated() {
		return this.created;
	}
}
