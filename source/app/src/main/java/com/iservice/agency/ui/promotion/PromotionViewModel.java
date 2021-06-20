package com.iservice.agency.ui.promotion;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.data.Repository;
import com.iservice.agency.ui.base.activity.BaseViewModel;
import com.iservice.agency.ui.main.revenue.adapter.RevenueItem;

public class PromotionViewModel  extends BaseViewModel {
    RevenueItem revenueItem = new RevenueItem();
    public PromotionViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
