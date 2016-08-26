package cc.chenghong.vka.db;


import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import cc.chenghong.vka.app.App;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper{
	static final String TAG = "DBHelper";

	private static DBHelper dbHelper;

	public DBHelper(Context context, int databaseVersion) {
		super(context, App.DB_NAME, null, databaseVersion);
	}

	public synchronized static DBHelper getInstance(){
		if(dbHelper == null){
			dbHelper = new DBHelper(App.getInstance(), App.DB_VERSION);
		}
		return dbHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource arg1) {
		try {
			TableUtils.createTable(connectionSource, ProductEntity.class);
			TableUtils.createTable(connectionSource, ProductMsgEntity.class);
		} catch (SQLException e) {
			Log.e(TAG, "创建数据库失败", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource arg1, int arg2,
			int arg3) {
		try {
			TableUtils.dropTable(connectionSource, ProductEntity.class, true);
			TableUtils.dropTable(connectionSource, ProductMsgEntity.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(TAG, "更新数据库失败", e);
			e.printStackTrace();
		}
	}
}
