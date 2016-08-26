package cc.chenghong.vka.db;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

/**
 * 商品DB service
 * @author guozhiwei 2015-1-15
 *
 */
public class ProductService  {
//	final String TAG = "FinancialGoalProductService";
	private static ProductService mInstance;
	private Dao<ProductEntity, String> dao;

	public static synchronized ProductService getInstance() {
		if (mInstance == null) {
			mInstance = new ProductService();
		}
		return mInstance;
	}

	public ProductService() {
		super();
		try {
			dao = DBHelper.getInstance().getDao(ProductEntity.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ProductEntity getByUuid(String uuid){
		try {
			return dao.queryForId(uuid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProductEntity getById(String id) {
		ProductEntity entity = null;
		try {
			QueryBuilder<ProductEntity, String> qb = dao.queryBuilder();
			qb.where().eq("ID", id);
			entity = qb.queryForFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	
	

	/**
	 * 本地 删除目标产品 根据目标id
	 * */
	public int deleteById(String id) {
		//Log.i(TAG, "删除本地" + "FinancialGoalID为" + FinancialGoalID + "的目标产品");
		try {
			DeleteBuilder<ProductEntity, String> deleteBuilder = dao
					.deleteBuilder();
			Where<ProductEntity, String> where = deleteBuilder.where();
			where.eq("id", id);
			return deleteBuilder.delete();
		} catch (Exception e) {
			return 0;
		}
	}
	
	

	
	/**
	 * 插入数据
	 * @param entity
	 */
	public void addProduct(ProductEntity entity) {
		try {
			dao.create(entity);
		} catch (Exception e) {
			return ;
		}
	}
	/**
	 * 插入数据
	 * @param entity
	 */
	public List<ProductEntity> queryProduct() {
		try {
			return dao.queryForAll();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 根据id，修改价格type：0减，1加
	 * @param goodsId
	 * @param price
	 * @param type
	 */
	public void updatePrice(String goodsId,String price,int type) {
		try {
			
			ProductEntity product=getById(goodsId);
			float nowPrice = 0;
			if(type == 1){
				nowPrice = Float.parseFloat(price)+Float.parseFloat(product.getPrice());
			}else{
				nowPrice = Float.parseFloat(product.getPrice())-Float.parseFloat(price);
			}
			
			UpdateBuilder<ProductEntity, String> updateBuilder = dao
					.updateBuilder();
			Where<ProductEntity, String> where = updateBuilder.where();
			where.eq("ID", goodsId);
			updateBuilder.updateColumnValue("price", String.valueOf(nowPrice));
		} catch (Exception e) {
			return ;
		}
	}
	
	/**
	 * 修改选择的商品数据
	 * @param product
	 */
	public void updateEntity(ProductEntity product){
		try {
			dao.createOrUpdate(product);
		} catch (SQLException e) {
			e.printStackTrace();
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
