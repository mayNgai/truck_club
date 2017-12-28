package com.dtc.sevice.truckclub.presenters;

import android.util.Log;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.NetworkUtils;
import com.dtc.sevice.truckclub.view.HistoryActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by May on 10/24/2017.
 */

public class HistoryPresenter {
    private ApiService mForum;
    private HistoryActivity mView;
    private DialogController dialogController;

    public HistoryPresenter(HistoryActivity view,ApiService forum){
        mForum = forum;
        mView = view;
        dialogController = new DialogController();
    }

    public void loadHistory(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                mForum.getApi()
                        .getHistory(mView.listMembers.get(0))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<TblTask>>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadHistory Error", e.getMessage());
                            }

                            @Override
                            public void onNext(List<TblTask> tblTasks) {
                                mView.updateTask(tblTasks);
                                Log.i("loadHistory", "OK");

                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
