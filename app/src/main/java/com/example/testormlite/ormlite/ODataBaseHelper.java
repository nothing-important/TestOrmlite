package com.example.testormlite.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import com.example.testormlite.ormlite.table_entities.TablePersonInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ODataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATA_BASH_PATH = Environment.getExternalStorageDirectory()+"/ormliteData/database.db";
    private static final int DATA_BASH_VERSION = 1;
    private static ODataBaseHelper instance;
    private Map<String, Dao> daos = new HashMap();

    public ODataBaseHelper(Context context) {
        super(context, DATA_BASH_PATH , null , DATA_BASH_VERSION);
    }

    public static ODataBaseHelper getInstance(Context context){
        if (instance == null){
            synchronized (ODataBaseHelper.class){
                if (instance == null){
                    instance = new ODataBaseHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource , TablePersonInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource , TablePersonInfo.class , false);

            TableUtils.createTable(connectionSource , TablePersonInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
        instance.close();
        instance = null;
    }
}
