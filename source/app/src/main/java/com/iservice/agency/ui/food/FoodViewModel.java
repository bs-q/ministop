package com.iservice.agency.ui.food;

import androidx.lifecycle.LiveData;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.data.Repository;
import com.iservice.agency.data.local.sqlite.DbService;
import com.iservice.agency.data.model.db.FoodEntity;
import com.iservice.agency.ui.base.activity.BaseViewModel;

import java.util.List;

public class FoodViewModel extends BaseViewModel {
    LiveData<List<FoodEntity>> foodEntityLiveData ;
    DbService dbService;
    public FoodViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
        dbService = repository.getSqliteService();
    }
}
