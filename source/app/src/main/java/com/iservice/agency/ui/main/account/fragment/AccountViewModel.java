package com.iservice.agency.ui.main.account.fragment;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.data.Repository;
import com.iservice.agency.ui.base.fragment.BaseFragmentViewModel;

public class AccountViewModel extends BaseFragmentViewModel {
    public AccountViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public void logout(){
        repository.setToken(null);
    }
}