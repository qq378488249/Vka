package cc.chenghong.vka.entity;
/**
 * 卡券记录实体类
 * @author hcl
 *
 */
public class TicketRecord {
	private int id;

	private String toUserName;

	private String fromUserName;

	private int createTime;

	private String msgType;

	private String event;

	private String cardId;

	private String userCardCode;

	private String consumeSource;
	
	private String locationName;

	private String staffOpenId;

	private String created;

	private String wxCardCouponName;
	
	private String reduceCost;

	private int storeId;

	private String storeName;

	private int employeeId;

	private String employeeName;

	private int parentId;

	private String source;

	private String wxNickname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUserCardCode() {
		return userCardCode;
	}

	public void setUserCardCode(String userCardCode) {
		this.userCardCode = userCardCode;
	}

	public String getConsumeSource() {
		return consumeSource;
	}

	public void setConsumeSource(String consumeSource) {
		this.consumeSource = consumeSource;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getStaffOpenId() {
		return staffOpenId;
	}

	public void setStaffOpenId(String staffOpenId) {
		this.staffOpenId = staffOpenId;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getWxCardCouponName() {
		return wxCardCouponName;
	}

	public void setWxCardCouponName(String wxCardCouponName) {
		this.wxCardCouponName = wxCardCouponName;
	}

	public String getReduceCost() {
		return reduceCost;
	}

	public void setReduceCost(String reduceCost) {
		this.reduceCost = reduceCost;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getWxNickname() {
		return wxNickname;
	}

	public void setWxNickname(String wxNickname) {
		this.wxNickname = wxNickname;
	}
	
}
