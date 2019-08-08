package com.example.testormlite.ormlite.table_dao;

import android.content.Context;
import com.example.testormlite.ormlite.DataBaseOperation;
import com.example.testormlite.ormlite.ODataBaseHelper;
import com.example.testormlite.ormlite.table_entities.TablePersonInfo;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablePersonInfoDao implements DataBaseOperation<TablePersonInfo> {

    private Dao tableDao;

    private Dao createDao(Context context){
        ODataBaseHelper baseHelper = ODataBaseHelper.getInstance(context);
        Dao dao = null;
        try {
            dao = baseHelper.getDao(TablePersonInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dao;
    }

    private void getDao(Context context){
        if (tableDao == null){
            tableDao = createDao(context);
        }
    }

    public TablePersonInfoDao (Context context){
        getDao(context);
    }

    @Override
    public boolean insertData(TablePersonInfo tablePersonInfo) {
        try {
            int result = tableDao.create(tablePersonInfo);
            return result != -1;
        } catch (SQLException e) {//插入失败则回滚
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteData(TablePersonInfo tablePersonInfo) {
        try {
            int result = tableDao.delete(tablePersonInfo);
            return result != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<TablePersonInfo> queryAll() {
        List<TablePersonInfo> dataList = new ArrayList<>();
        try {
            dataList = tableDao.queryBuilder().query();
            return dataList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteAll() {
        try {
            int result = tableDao.delete(queryAll());
            return result != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteByAge(String age){
        try {
            List<TablePersonInfo> dataList = tableDao.queryBuilder().where()
                    .eq("age", age)
                    .query();
            if (dataList.size() == 0){
                return true;
            }else {
                for (int i = 0; i < dataList.size(); i++) {
                    tableDao.delete(dataList.get(i));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateByName(String name , TablePersonInfo tablePersonInfo){
        try {
            UpdateBuilder<TablePersonInfo, Integer> updateBuilder = tableDao.updateBuilder();
            updateBuilder.updateColumnValue("name" , tablePersonInfo.getName());
            updateBuilder.updateColumnValue("gender" , tablePersonInfo.getGender());
            updateBuilder.updateColumnValue("age" , tablePersonInfo.getAge());
            updateBuilder.updateColumnValue("address" , tablePersonInfo.getAddress());
            updateBuilder.where().eq("name" , name);
            int result = updateBuilder.update();
            return result != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
