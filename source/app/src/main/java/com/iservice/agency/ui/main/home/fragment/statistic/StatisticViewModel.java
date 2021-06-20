package com.iservice.agency.ui.main.home.fragment.statistic;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.data.Repository;
import com.iservice.agency.ui.base.fragment.BaseFragmentViewModel;

public class StatisticViewModel extends BaseFragmentViewModel {
    StatisticForm form = new StatisticForm("100%","0%","0.0%");

    public StatisticViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
