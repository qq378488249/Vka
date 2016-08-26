//package cc.chenghong.vka.db;
//import java.io.File;
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.UUID;
//
//import android.os.Handler;
//import android.os.Message;
//
//import com.j256.ormlite.dao.Dao;
//import com.j256.ormlite.stmt.DeleteBuilder;
//import com.j256.ormlite.stmt.QueryBuilder;
//import com.j256.ormlite.stmt.UpdateBuilder;
//import com.j256.ormlite.stmt.Where;
//
//@SuppressWarnings("unchecked")
//public class FinancialGoalProductService  {
////	final String TAG = "FinancialGoalProductService";
//	private static FinancialGoalProductService mInstance;
//	private Dao<FinancialProductEntity, String> dao;
//
//	public static synchronized FinancialGoalProductService getInstance() {
//		if (mInstance == null) {
//			mInstance = new FinancialGoalProductService();
//		}
//		return mInstance;
//	}
//
//	public FinancialGoalProductService() {
//		super();
//		try {
//			dao = DBHelper.getInstance().getDao(FinancialProductEntity.class);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public FinancialProductEntity getByUuid(String uuid){
//		try {
//			return dao.queryForId(uuid);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public FinancialProductEntity getById(String id) {
//		FinancialProductEntity entity = null;
//		try {
//			QueryBuilder<FinancialProductEntity, String> qb = dao.queryBuilder();
//			qb.where().eq("ID", id);
//			entity = qb.queryForFirst();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return entity;
//	}
//	
//	/**
//	 * 根据收益率和收益周期，计算产品自添加起，至今的市值
//	 * @param product
//	 * @param handler 计算结果会在handler收到消息中返回message.obj=BigDecimal
//	 */
//	public void computeCurrentMarket(final FinancialProductEntity product, final Handler handler){
//		TaskLooper.getInstance().post(new Runnable() {
//			@Override
//			public void run() {
//				//起始日期
//				Date startDate = product.getInputTime();
//				//检查是否有收益率修改记录
//				List<EarningRateLogsEntity> logs = EarningRateLogsService.getInstance().getBy(product);
//				//如果没有修改过收益率，根据今天
//				Message msg = handler.obtainMessage();
//				msg.obj = new BigDecimal(0);
//				handler.sendEmptyMessage(0);
//			}
//		});
//	}
//	
//	/**
//	 * 根据收益率、天数计算市值增长
//	 * @param market 当前市值
//	 * @param rate 这些天的收益率
//	 * @param cycle 这些天的收益周期
//	 * @param days 天数
//	 */
//	private BigDecimal computeEarning(BigDecimal market, BigDecimal rate, int cycle, float days){
//		BigDecimal total = new BigDecimal(market.toPlainString());
//		for(float day=0; day<days; day+=cycle){
//			total = total.add(total.multiply(rate));
//		}
//		return total;
//	}
//
//	/**
//	 * 本地 删除目标产品 根据目标id
//	 * */
//	public int deleteLocalFromFinancialGoalId(String FinancialGoalID) {
//		//Log.i(TAG, "删除本地" + "FinancialGoalID为" + FinancialGoalID + "的目标产品");
//		try {
//			DeleteBuilder<FinancialProductEntity, String> deleteBuilder = dao
//					.deleteBuilder();
//			Where<FinancialProductEntity, String> where = deleteBuilder.where();
//			where.eq("FinancialGoalID", FinancialGoalID);
//			return deleteBuilder.delete();
//		} catch (Exception e) {
//			return 0;
//		}
//	}
//	
//	/**
//	 * 根据目标获取所有产品
//	 * 
//	 * @param entity
//	 * @return
//	 */
//	public List<FinancialProductEntity> getByGaol(FinancialGoalEntity goal) {
//		List<FinancialProductEntity> result = null;
//		try {
//			QueryBuilder<FinancialProductEntity, String> qb = dao.queryBuilder();
//			Where<FinancialProductEntity, String> where = qb.where();
//			where.and(where.eq("deleted", false),
//					where.eq("FinancialGoalLocalID", goal.getClientGuid()));
//			qb.orderBy("InputTime", false);
//			result = qb.query();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	/**
//	 * [本地]删除理财目标产品
//	 * 
//	 * @param FinancialProductEntity
//	 * @return
//	 */
//	public int deleteLocal(FinancialProductEntity entity) {
//		//Log.i(TAG, "删除本地" + entity.getClass().getSimpleName());
//		try {
//			return dao.delete(entity);
//		} catch (Exception e) {
//			return 0;
//		}
//	}
//	
//	/**
//	 * 根据ID删除
//	 * @param id
//	 * @return
//	 */
//	public int deleteById(String id) {
//		try {
//			DeleteBuilder<FinancialProductEntity, String> db = dao.deleteBuilder();
//			Where<FinancialProductEntity, String> where = db.where();
//			where.eq("ID", id);
//			return db.delete();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
//
//	/**
//	 * [本地]删除理财产品
//	 * 
//	 * @param entity
//	 * @return
//	 */
//	public int removeLocal(FinancialProductEntity entity) {
//		//Log.i(TAG, "删除本地产品" + entity.getClientGuid());
//		try {
//			return dao.delete(entity);
//		} catch (Exception e) {
//			return 0;
//		}
//	}
//
//	/**
//	 * [本地]更新理财目标产品
//	 * 
//	 * @param FinancialProductEntity
//	 * @return
//	 */
//	public int saveToLocal(FinancialProductEntity entity) {
//		if(entity.getClientGuid() == null){
//			entity.setClientGuid(UUID.randomUUID().toString());
//		}
//		try {
//			return dao.createOrUpdate(entity).getNumLinesChanged();
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
//		return 0;
//	}
//
//	/**
//	 * 获取本地理财目标产品记录(未标记删除)
//	 * 
//	 * @return
//	 */
//	public List<FinancialProductEntity> getLocalList() {
//		try {
//			return dao.queryForEq("deleted", false);
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	/**
//	 * 获取本地所有理财目标产品记录
//	 * 
//	 * @return
//	 */
//	public List<FinancialProductEntity> getAllLocal() {
//		try {
//			return dao.queryForAll();
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	
//	public List<FinancialProductEntity> getIDList(
//			String customerId, boolean deleted) {
//		List<FinancialProductEntity> result = null;
//		try {
//			QueryBuilder<FinancialProductEntity, String> qb = dao.queryBuilder();
//			Where<FinancialProductEntity, String> where = qb.where();
//			where.and(where.eq("deleted", deleted),
//					where.eq("CustomerID", customerId));
//			qb.selectColumns("ID");
//			result = qb.query();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	/**
//	 * 根据uuid获取ID
//	 * 
//	 * @return
//	 */
//	public FinancialProductEntity getID(String uuid) {
//		FinancialProductEntity goal = null;
//		try {
//			// Dao<FinancialProductEntity, String> dao = DBHelper.getInstance()
//			// .getDao(FinancialProductEntity.class);
//			QueryBuilder<FinancialProductEntity, String> qb = dao
//					.queryBuilder();
//			qb.selectColumns("ID");
//			qb.where().eq("ClientGuid", uuid);
//			goal = qb.queryForFirst();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return goal;
//	}
//
//	/**
//	 * 获取所有本地理财目标的ID,LastUpdateDate列表
//	 * 
//	 * @param customerId
//	 * @return
//	 */
//	public List<FinancialProductEntity> getLocalID(String customerId) {
////		Log.i(TAG, "本地理财产品的ID: customerId=" + customerId);
//		List<FinancialProductEntity> result = null;
//		try {
//			// Dao<FinancialProductEntity, String> dao = DBHelper.getInstance()
//			// .getDao(FinancialProductEntity.class);
//			QueryBuilder<FinancialProductEntity, String> qb = dao
//					.queryBuilder();
//			qb.selectColumns("ID", "LastUpdateDate");
//			result = qb.query();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (result != null) {
////			Log.i(TAG, ">>>>>本地理财产品的ID " + result.size());
//		}
//		return result;
//	}
//
//	/**
//	 * [远程]理财目标产品保存数据(新增、修改)
//	 * 
//	 * @param entity
//	 * @param files
//	 *            要上传的图片文件
//	 * @param attachIDs
//	 *            附件ID，情况1：上传不需要传这个参数，情况2：修改或删除需要传这个参数(补:改同样需要传HttpPostedFile)
//	 *            1,2,3。半角逗号隔开
//	 * @param versionIDs
//	 *            附件ID，情况1：上传需要传这个参数，情况2：修改需要这个参数 1,2,3。半角逗号隔开
//	 * @param callback
//	 * @param progressListener
//	 *            上传进度
//	 */
//	public void saveRemote(FinancialProductEntity entity, List<File> files,
//			int[] attachIDs, int[] versionIDs,
//			final ServiceCallback<SyncID> callback,
//			final ProgressListener progressListener) {
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("Cmd", "SaveFinancialProduct");
//		params.put("Data", entity.toString());
//		if (attachIDs != null) {
//			params.put("AttachIDs", StringUtils.arrayToString(attachIDs));
//		}
//		HashMap<String, File> postFiles = null;
//		if (files != null) {
//			postFiles = new HashMap<String, File>();
//			for (int i = 0; i < files.size(); i++) {
//				postFiles.put("HttpPostedFile" + i, files.get(i));
//			}
//		}
//
//		HttpHelper.asyncFormPost(API_ADDRESS, params, postFiles,
//				new HttpHandler() {
//					@Override
//					public void handleResponse(HttpResult result) {
//						if (result.getStatus() == HttpResult.STATUS_SUCCESS) {
//							callback.done(
//									0,
//									getGson(App.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss)
//											.fromJson(result.getData(),
//													SyncID.class), result);
//						} else {
//							callback.error(result.getMsg());
//						}
//					}
//				}, progressListener);
//	}
//
//	/**
//	 * [远程]理财目标产品保存数据(新增、修改)
//	 * 
//	 * @param entity
//	 * @param files
//	 *            要上传的图片文件
//	 * @param attachIDs
//	 *            附件ID，情况1：上传不需要传这个参数，情况2：修改或删除需要传这个参数(补:改同样需要传HttpPostedFile)
//	 *            1,2,3。半角逗号隔开
//	 */
//	public SyncID saveRemoteSync(FinancialProductEntity entity,
//			List<File> files, int[] attachIDs) {
//		SyncID syncID = null;
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("Cmd", "SaveFinancialProduct");
//		params.put("Data", entity.toString());
//		if (attachIDs != null) {
//			params.put("AttachIDs", StringUtils.arrayToString(attachIDs));
//		}
//		HashMap<String, File> postFiles = null;
//		if (files != null) {
//			postFiles = new HashMap<String, File>();
//			for (int i = 0; i < files.size(); i++) {
//				postFiles.put("HttpPostedFile" + i, files.get(i));
//			}
//		}
//
//		HttpResult result = HttpHelper.syncFormPost(API_ADDRESS, params,
//				postFiles);
//		if (result.getStatus() == HttpResult.STATUS_SUCCESS) {
//			syncID = getGson(App.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss).fromJson(
//					result.getData(), SyncID.class);
//		} else {
////			Log.e(TAG, "产品保存失败:" + result.getMsg());
//		}
//		return syncID;
//	}
//
//	/**
//	 * [远程]获取产品信息
//	 * 
//	 * @param id
//	 * @param callback
//	 */
//	public FinancialProductEntity getRemote(String id) {
//		FinancialProductEntity e = null;
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("Cmd", "GetFinancialProductInfo");
//		params.put("ID", id);
//		HttpResult result = HttpHelper.syncGet(API_ADDRESS, params);
//		if (result.getStatus() == HttpResult.STATUS_SUCCESS) {
//			e = getGson(App.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss).fromJson(
//					result.getData(), FinancialProductEntity.class);
//		}
//		return e;
//	}
//
//	/**
//	 * [远程]删除理财目标产品
//	 * 
//	 * @param id
//	 * @param callback
//	 */
//	public void deleteRemote(String id,
//			final ServiceCallback<EmptyEntity> callback) {
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("Cmd", "DelFinancialProduct");
//		params.put("ID", String.valueOf(id));
//
//		HttpHelper.asyncFormPost(API_ADDRESS, params, new HttpHandler() {
//			@Override
//			public void handleResponse(HttpResult result) {
//				if (result.getStatus() == HttpResult.STATUS_SUCCESS) {
//					callback.done(0, null, result);
//				} else {
//					callback.error(result.getMsg());
//				}
//			}
//		});
//	}
//	
//	public boolean deleteRemote(String id) {
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("Cmd", "DelFinancialProduct");
//		params.put("ID", String.valueOf(id));
//		HttpResult result = HttpHelper.syncFormPost(API_ADDRESS, params, null);
//		if (result.getStatus() == HttpResult.STATUS_SUCCESS) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	public int deleteOrMoveByGoal(FinancialGoalEntity financialGoalEntity) {
//		try {
//			//1.删除产品关联的附件
//			SysAttachService.getInstance().deleteOrMove(FinancialGoalEntity.TABLE_NAME, financialGoalEntity.getClientGuid());
//			// 2.删除对应产品
//			UpdateBuilder<FinancialProductEntity, String> ub =  dao.updateBuilder();
//			ub.updateColumnValue("deleted", true);
//			Where<FinancialProductEntity, String> where = ub.where();
//			where.and(where.eq("FinancialGoalLocalID", financialGoalEntity.getClientGuid()),
//					where.isNotNull("ID"));
//			int updateRes = ub.update();
////			Log.i(TAG, "标记逻辑删除:"+updateRes);
//
//			DeleteBuilder<FinancialProductEntity, String> db =  dao.deleteBuilder();
//			where = db.where();
//			where.and(where.eq("FinancialGoalLocalID", financialGoalEntity.getClientGuid()),
//					where.isNull("ID"));
//			int delRes = db.delete();
////			Log.i(TAG, "物理删除删除:"+delRes);
//			return updateRes+delRes;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
//
//	/**
//	 * 标记删除或者本地删除： 如果id为null 本地删除 ，如果id不为null 标记删除
//	 * */
//	public void deleteOrMove(FinancialProductEntity entity) {
//		
//		// 先删除附件
//		SysAttachService.getInstance().deleteOrMove(
//				FinancialProductEntity.TABLE_NAME, entity.getClientGuid());
//		if (entity != null && entity.getID() == null) {
//			removeLocal(entity);
//		} else {
//			deleteLocal(entity);
//		}
//	}
//}
