package com.example.testormlite

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.testormlite.ormlite.table_dao.TablePersonInfoDao
import com.example.testormlite.ormlite.table_entities.TablePersonInfo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : BaseActivity(), BaseActivity.PermissionRequestListener, View.OnClickListener {

    val TAG : String = javaClass.name
    var personInfoDao : TablePersonInfoDao? = null
    val REQUEST_QUERY : Int = 101
    val REQUEST_ADD : Int = 102
    val REQUEST_DELETE_ALL : Int = 103
    val REQUEST_UPDATE : Int = 104
    val permissions : Array<String> = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        personInfoDao = TablePersonInfoDao(this)
        main_query.setOnClickListener(this)
        main_add.setOnClickListener(this)
        main_delete_all.setOnClickListener(this)
        main_update.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.main_add ->{
                requestSelfPermissions(permissions , REQUEST_ADD , this)
            }
            R.id.main_query ->{
                requestSelfPermissions(permissions , REQUEST_QUERY , this)
            }
            R.id.main_delete_all ->{
                requestSelfPermissions(permissions , REQUEST_DELETE_ALL , this)
            }
            R.id.main_update ->{
                requestSelfPermissions(permissions , REQUEST_UPDATE , this)
            }
        }
    }

    override fun onPermissionGranted(allGrantedPermission: MutableList<String>?) {
        when(permissionRequestCode){
            REQUEST_QUERY ->{
                queryAllPersonData()
            }
            REQUEST_ADD ->{
                addPersonData()
            }
            REQUEST_DELETE_ALL ->{
                deleteAllPersonData()
            }
            REQUEST_UPDATE ->{
                updateByName()
            }
        }
    }

    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
        showSettingDialog(this)
    }

    private fun queryAllPersonData() {
        val allData : ArrayList<TablePersonInfo> = personInfoDao!!.queryAll() as ArrayList<TablePersonInfo>
        if (allData.size == 0){
            Log.e(TAG , "暂无数据")
            main_content.text = "暂无数据"
        }else{
            var queryData = StringBuffer()
            for (data in allData){
                queryData.append(data.toString())
                queryData.append("\n")
            }
            Log.e(TAG , queryData.toString())
            main_content.text = queryData.toString()
        }
    }

    private fun addPersonData(){
        val info = TablePersonInfo()
        info.name = "李四"
        info.gender = "男"
        info.age = "26"
        info.address = "暂未收录"
        personInfoDao!!.insertData(info)
    }

    private fun deleteAllPersonData(){
        personInfoDao!!.deleteAll()
    }

    private fun updateByName(){
        val info = TablePersonInfo()
        info.name = "月白"
        info.gender = "女"
        info.age = "21"
        info.address = "落魄山"
        personInfoDao!!.updateByName("李四" , info)
    }
}
