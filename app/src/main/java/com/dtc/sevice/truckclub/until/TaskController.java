package com.dtc.sevice.truckclub.until;

import android.app.Activity;

import com.dtc.sevice.truckclub.helper.DatabaseHelper;
import com.dtc.sevice.truckclub.model.TblCarGroup;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by May on 9/27/2017.
 */

public class TaskController {
    protected static DatabaseHelper databaseHelper = null;
    private RuntimeExceptionDao<TblMember, String> tblMemberRuntimeDao;
    private RuntimeExceptionDao<TblCarGroup, String> tblCarGroupRuntimeDao;
    private RuntimeExceptionDao<TblTask, String> tblTaskRuntimeDao;
    private Activity _activity;
    private void getConnectDatabaseHelper() {
        try {
            _activity = ApplicationController.getAppActivity();
            databaseHelper  = DatabaseHelper.getHelper(_activity);
            tblMemberRuntimeDao = databaseHelper.getTblMember();
            tblCarGroupRuntimeDao = databaseHelper.getTblCarGroup();
            tblTaskRuntimeDao = databaseHelper.getTblTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TblMember createMemberDriver(String user,String first,String last,String email,String tel,String date,String pass , boolean sex){
        TblMember member = new TblMember();
        try {
            member.setGuid(UUID.randomUUID().toString());
            member.setUser_name(user);
            member.setFirst_name(first);
            member.setLast_name(last);
            member.setEmail(email);
            member.setTel(tel);
            member.setBirth_date(date);
            if(sex)
                member.setSex("M");
            else
                member.setSex("W");
        }catch (Exception e){
            e.printStackTrace();
            return member;
        }
        return member;
    }

    public boolean createMember(TblMember member){
        try {
            getConnectDatabaseHelper();
            List<TblMember> list = new ArrayList<TblMember>();
            list = checkMember();
            if(list.size()>0){
                deleteMember(list);

            }
            member.setGuid(UUID.randomUUID().toString());
            tblMemberRuntimeDao.create(member);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean createTask(List<TblTask> taskList){
        try {
            getConnectDatabaseHelper();
            List<TblTask> list = new ArrayList<TblTask>();
            list = checkTask();
            if(list.size()>0){
                deleteTask(list);

            }
            for(TblTask t : taskList){
                t.setGuid(UUID.randomUUID().toString());
                tblTaskRuntimeDao.create(t);
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean createCarGroup(List<TblCarGroup> groups){
        try {
            getConnectDatabaseHelper();
            List<TblCarGroup> list = new ArrayList<TblCarGroup>();
            list = getCarGroup();
            if(list.size()>0){
                deleteCarGroup(list);

            }
            for(int i = 0;i<groups.size();i++){
                groups.get(i).setGuid(UUID.randomUUID().toString());
                if(i==0)
                    groups.get(i).setIsSelect(1);
                else
                    groups.get(i).setIsSelect(0);
                tblCarGroupRuntimeDao.create(groups.get(i));
            }
//            for(TblCarGroup c: groups){
//                c.setGuid(UUID.randomUUID().toString());
//                tblCarGroupRuntimeDao.create(c);
//            }


        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<TblCarGroup> getCarGroup(){
        List<TblCarGroup> list = new ArrayList<TblCarGroup>();
        try {
            getConnectDatabaseHelper();
            list = tblCarGroupRuntimeDao.queryForAll();

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<TblMember> getMember(){
        List<TblMember> list = new ArrayList<TblMember>();
        try {
            getConnectDatabaseHelper();
            list = tblMemberRuntimeDao.queryForAll();

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<TblTask> getTask(){
        List<TblTask> list = new ArrayList<TblTask>();
        try {
            getConnectDatabaseHelper();
            list = tblTaskRuntimeDao.queryForAll();

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateMember(TblMember member){
        try {
            getConnectDatabaseHelper();
            tblMemberRuntimeDao.update(member);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<TblMember> checkMember(){
        List<TblMember> list = new ArrayList<TblMember>();
        try {
            getConnectDatabaseHelper();
            list = tblMemberRuntimeDao.queryForAll();

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<TblTask> checkTask(){
        List<TblTask> list = new ArrayList<TblTask>();
        try {
            getConnectDatabaseHelper();
            list = tblTaskRuntimeDao.queryForAll();

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<TblTask> getTaskByID(String id){
        List<TblTask> list = new ArrayList<TblTask>();
        try {
            getConnectDatabaseHelper();
            list = tblTaskRuntimeDao.queryBuilder().where().eq("id", id).query();

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteMember(List<TblMember> member){
        try {
            getConnectDatabaseHelper();
            tblMemberRuntimeDao.delete(member);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteTask(List<TblTask> taskList){
        try {
            getConnectDatabaseHelper();
            tblTaskRuntimeDao.delete(taskList);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteAllTask(){
        try {
            getConnectDatabaseHelper();
            List<TblTask> tblTasks = getTask();
            tblTaskRuntimeDao.delete(tblTasks);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteCarGroup(List<TblCarGroup> groups){
        try {
            getConnectDatabaseHelper();
            tblCarGroupRuntimeDao.delete(groups);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
