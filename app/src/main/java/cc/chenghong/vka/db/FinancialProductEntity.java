package cc.chenghong.vka.db;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 理财产品
 * @author 
 *
 */
@DatabaseTable(tableName = "FinancialProduct")
public class FinancialProductEntity implements Serializable{
	private static final long serialVersionUID = 3099841829853865068L;
	public static final String TABLE_NAME = "FinancialProduct";

	@DatabaseField
	private boolean deleted;

	/**
	 * 	int	4	√		系统编号
	 */
	@DatabaseField
	private String ID;
	/**
	 * 	char	10	√		理财目标编号
	 */
	@DatabaseField
	private String FinancialGoalID;

	@DatabaseField
	private String FinancialGoalLocalID;

	/**
	 * 	int	4	√		客户编号
	 */
	@DatabaseField
	private String CustomerID;
	/**
	 * 	nvarchar	50	√		昵称
	 */
	@DatabaseField
	private String SName;
	/**
	 * 	nvarchar	100	√		产品名称
	 */
	@DatabaseField
	private String ProductName;
	public static final String COL_PRODUCT_NAME = "ProductName";
	/**
	 * 	nvarchar	100	√		产品类型(标签)
	 */
	@DatabaseField
	private String ProductType;
	public static final String COL_PRODUCT_TYPE = "ProductType";
	/**
	 * 	int	4	√		产品类型(标签)
	 */
	@DatabaseField
	private String ProductTypeID;
	/**
	 * 	decimal	9	√		目前市值
	 */
	@DatabaseField
	private BigDecimal CurrentMarket;
	public static final String COL_CURRENT_MARKET = "CurrentMarket";
	/**
	 * 	nvarchar	1000	√		备注
	 */
	@DatabaseField
	private String Remark;
	public static final String COL_PRODUCT_REMARK = "Remark";
	/**
	 * 	datetime	8	√	getdate()	最后更新日期
	 */
	@DatabaseField
	private Date LastUpdateDate;
	/**
	 * 	datetime	8	√	getdate()	创建日期
	 */
	@DatabaseField
	private Date InputTime;

	@DatabaseField(id = true)
	private String ClientGuid;
	/**
	 * 收益率 %
	 */
	@DatabaseField
	private BigDecimal Yield;
	/**
	 * 收益周期(天)
	 */
	@DatabaseField
	private int EarningCycle;

	/**
	 * 创建记录以后默认sync为false标记为未同步
	 */
	@DatabaseField
	private boolean sync = false;



	public void setClientGuid(String clientGuid) {
		ClientGuid = clientGuid;
	}
	
	public String getClientGuid() {
		return ClientGuid;
	}

	public String getID() {
		return ID;
	}
	
	public void setID(String iD) {
		ID = iD;
	}
	
	public String getFinancialGoalID() {
		return FinancialGoalID;
	}
	
	public void setFinancialGoalID(String financialGoalID) {
		FinancialGoalID = financialGoalID;
	}
	public String getCustomerID() {
		return CustomerID;
	}
	
	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}
	
	public String getSName() {
		return SName;
	}
	
	public void setSName(String sName) {
		SName = sName;
	}
	
	public String getProductName() {
		return ProductName;
	}
	
	public void setProductName(String productName) {
		ProductName = productName;
	}
	
	public String getProductType() {
		return ProductType;
	}
	
	public void setProductType(String productType) {
		ProductType = productType;
	}
	
	public String getProductTypeID() {
		return ProductTypeID;
	}
	
	public void setProductTypeID(String productTypeID) {
		ProductTypeID = productTypeID;
	}
	
	public BigDecimal getCurrentMarket() {
		return CurrentMarket;
	}
	
	public void setCurrentMarket(BigDecimal currentMarket) {
		CurrentMarket = currentMarket;
	}
	public String getRemark() {
		return Remark;
	}
	
	public void setRemark(String remark) {
		Remark = remark;
	}
	public Date getLastUpdateDate() {
		return LastUpdateDate;
	}
	
	public void setLastUpdateDate(Date lastUpdateDate) {
		LastUpdateDate = lastUpdateDate;
	}
	
	public Date getInputTime() {
		return InputTime;
	}
	
	public void setInputTime(Date inputTime) {
		InputTime = inputTime;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getFinancialGoalLocalID() {
		return FinancialGoalLocalID;
	}
	
	public void setFinancialGoalLocalID(String financialGoalLocalID) {
		FinancialGoalLocalID = financialGoalLocalID;
	}

	public BigDecimal getYield() {
		return Yield;
	}

	public void setYield(BigDecimal yield) {
		Yield = yield;
	}

	public int getEarningCycle() {
		return EarningCycle;
	}

	public void setEarningCycle(int earningCycle) {
		EarningCycle = earningCycle;
	}
	
	//根据产品的收益率和收益周期，和添加日期，计算当前的金额
}
