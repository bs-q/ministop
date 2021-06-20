package com.iservice.agency.ui.register.phone;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.data.Repository;
import com.iservice.agency.ui.base.activity.BaseViewModel;
import com.iservice.agency.ui.register.phone.form.LoginWithPhoneForm;

public class VerifyCodeViewModel extends BaseViewModel {

    LoginWithPhoneForm loginWithPhoneForm = new LoginWithPhoneForm();

    public VerifyCodeViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

}
