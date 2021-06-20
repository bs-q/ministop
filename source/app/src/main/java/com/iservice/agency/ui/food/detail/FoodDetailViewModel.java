package com.iservice.agency.ui.food.detail;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.data.Repository;
import com.iservice.agency.data.model.db.FoodEntity;
import com.iservice.agency.ui.base.activity.BaseViewModel;


public class FoodDetailViewModel extends BaseViewModel {
    public FoodEntity foodEntity = new FoodEntity();
    public FoodDetailViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

}
