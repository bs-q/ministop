package com.iservice.agency.ui.main;

import androidx.lifecycle.LiveData;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.data.Repository;
import com.iservice.agency.data.local.sqlite.DbService;
import com.iservice.agency.data.model.db.FoodEntity;
import com.iservice.agency.ui.base.activity.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {
    LiveData<List<FoodEntity>> foodEntityLiveData ;
    DbService dbService;
    public MainViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
        dbService = repository.getSqliteService();
        foodEntityLiveData=dbService.loadAllToLiveData("junk");
    }
}
