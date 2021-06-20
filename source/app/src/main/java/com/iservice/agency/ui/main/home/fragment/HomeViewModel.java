package com.iservice.agency.ui.main.home.fragment;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.data.Repository;
import com.iservice.agency.ui.base.fragment.BaseFragmentViewModel;

public class HomeViewModel extends BaseFragmentViewModel {
    public HomeViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
