package cc.chenghong.vka.entity;

import java.util.Date;
import java.util.List;

import android.R.integer;

/**
 * 优惠券实体类2015 9 29
 * 
 * @author hcl
 * 
 */
public class Ticket {
	public int code;
	public List<TicketData> data;
	public String message;
	public int count;
	public class TicketData {
		
		public TicketData() {
			super();
		}
		public Long id;// 主键ID

		public Long templateId;// 模板ID

		public String name;// 优惠券名字

		public Long cardId;// 会员主键ID

		public Long paperId;

		public Integer state;// 券状态 0未发放,1已发放,2已使用

		public Integer type;// 0纸制券 1电子优惠券

		public String firstNo;

		public Long lastNo;

		public String no;

		public String created;

		public Integer templateType;// 券类型 0 现金券 1折扣券 2商品券

		public String gifts;// 赠品 templateType 为 0 奖励的现金；1 折扣数；2 赠送的商品

		public Integer gross;

		public String beginTime;// 起始时间(例：2015-09-29)

		public String endTime;// 结束时间(例：2015-09-29)

		public String storeName;

		public Integer reportType;

		public Integer ticketType;
		/**
		 * 是否被选中
		 */
		public boolean isSelect;
	}
}
