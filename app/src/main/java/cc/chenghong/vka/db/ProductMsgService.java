package cc.chenghong.vka.db;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;


/**
 * 商品属性DB service
 * @author guozhiwei 2015-1-15
 *
 */
public class ProductMsgService  {
//	final String TAG = "FinancialGoalProductService";
	private static ProductMsgService mInstance;
	private Dao<ProductMsgEntity, String> dao;

	public static synchronized ProductMsgService getInstance() {
		if (mInstance == null) {
			mInstance = new ProductMsgService();
		}
		return mInstance;
	}

	public ProductMsgService() {
		super();
		try {
			dao = DBHelper.getInstance().getDao(ProductMsgEntity.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ProductMsgEntity getByUuid(String uuid){
		try {
			return dao.queryForId(uuid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据商品唯一id查询所有的属性  返回arrayList
	 * @param id
	 * @return
	 */
	public ArrayList<ProductMsgEntity> getById(String id) {
		ArrayList<ProductMsgEntity> entitys = new ArrayList<ProductMsgEntity>();
		try {
			QueryBuilder<ProductMsgEntity, String> qb = dao.queryBuilder();
			qb.where().eq("goodid", id);
			List<ProductMsgEntity> list = qb.query();
			for (int i = 0; i < list.size(); i++) {
				entitys.add(list.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entitys;
	}
	
	
	
	

	/**
	 * 删除商品属性 根据目标商品id
	 * */
	public int deletegoodMsg(String goodsId) {
		try {
			DeleteBuilder<ProductMsgEntity, String> deleteBuilder = dao
					.deleteBuilder();
			Where<ProductMsgEntity, String> where = deleteBuilder.where();
			where.eq("goodId", goodsId);
			return deleteBuilder.delete();
		} catch (Exception e) {
			return 0;
		}
	}
	/**
	 * 根据两个id，修改数量
	 * */
	public void updateGoodMsg(String goodsId,String msgId,String totalNumber) {
		//Log.i(TAG, "删除本地" + "FinancialGoalID为" + FinancialGoalID + "的目标产品");
		try {
			UpdateBuilder<ProductMsgEntity, String> updateBuilder = dao
					.updateBuilder();
			Where<ProductMsgEntity, String> where = updateBuilder.where();
			where.eq("goodId", goodsId);
			where.eq("msgId", msgId);
			updateBuilder.updateColumnValue("totalNumber", totalNumber);
		} catch (Exception e) {
			return ;
		}
	}
	
	/**
	 * 修改商品属性数据，
	 * @param entity
	 */
	public void updateEntity(ProductMsgEntity entity){
		try {
			dao.createOrUpdate(entity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 根据传入的对象删除数据
	 * 
	 * @param FinancialProductEntity
	 * @return
	 */
	public int deleteLocal(ProductMsgEntity entity) {
		//Log.i(TAG, "删除本地" + entity.getClass().getSimpleName());
		try {
			return dao.delete(entity);
		} catch (Exception e) {
			return 0;
		}
	}
	/**
	 * 插入数据
	 * 
	 * @param FinancialProductEntity
	 * @return
	 */
	public void addByEntity(ArrayList<ProductMsgEntity> entitys) {
		try {
				for (int i = 0; i < entitys.size(); i++) {
					dao.create(entitys.get(i));
				}
		} catch (Exception e) {
			return ;
		}
	}
	
	
	/**
	 * 删除全部
	 */
	public void deleteAll(){
		try {
			dao.deleteBuilder().delete();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
