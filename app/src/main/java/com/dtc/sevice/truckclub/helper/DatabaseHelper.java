package com.dtc.sevice.truckclub.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dtc.sevice.truckclub.model.TblCarGroup;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by May on 9/27/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "truckclub.db";
    private static final int DATABASE_VERSION = 1;
    private RuntimeExceptionDao<TblMember, String> tblMember;
    private RuntimeExceptionDao<TblTask, String> tblTask;
    private RuntimeExceptionDao<TblCarGroup, String> tblCarGroup;
    private static DatabaseHelper instance;
    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);

        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, TblMember.class);
            TableUtils.createTable(connectionSource, TblCarGroup.class);
            TableUtils.createTable(connectionSource, TblTask.class);
        }catch (Exception e){
            e.printStackTrace();
            Log.i("DatabaseHelper.onCreate",(e.getMessage() != null) ? e.getMessage() : "error");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            onDropTable(connectionSource);
            TableUtils.createTable(connectionSource, TblMember.class);
            TableUtils.createTable(connectionSource, TblCarGroup.class);
            TableUtils.createTable(connectionSource, TblTask.class);
        }catch (Exception e){
            e.printStackTrace();
            Log.i("DatabaseHelper.onUpgrade",(e.getMessage() != null) ? e.getMessage() : "error");
        }
        onCreate(db, connectionSource);

    }

    public void onDropTable(ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "dropTable");
            TableUtils.dropTable(connectionSource, TblMember.class, true);
            TableUtils.dropTable(connectionSource, TblCarGroup.class, true);
            TableUtils.dropTable(connectionSource, TblTask.class, true);
        }catch (Exception e){
            e.printStackTrace();
            Log.i("DatabaseHelper.onDropTable",(e.getMessage() != null) ? e.getMessage() : "error");
        }
    }

    public RuntimeExceptionDao<TblMember, String> getTblMember() {
        if (tblMember == null) {
            tblMember = getRuntimeExceptionDao(TblMember.class);
        }
        return tblMember;
    }

    public RuntimeExceptionDao<TblCarGroup, String> getTblCarGroup() {
        if (tblCarGroup == null) {
            tblCarGroup = getRuntimeExceptionDao(TblCarGroup.class);
        }
        return tblCarGroup;
    }

    public RuntimeExceptionDao<TblTask, String> getTblTask() {
        if (tblTask == null) {
            tblTask = getRuntimeExceptionDao(TblTask.class);
        }
        return tblTask;
    }
}
