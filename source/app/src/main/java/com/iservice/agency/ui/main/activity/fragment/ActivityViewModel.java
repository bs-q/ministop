package com.iservice.agency.ui.main.activity.fragment;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.data.Repository;
import com.iservice.agency.ui.base.fragment.BaseFragmentViewModel;

public class ActivityViewModel extends BaseFragmentViewModel {
    public ActivityViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}