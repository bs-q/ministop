package com.iservice.agency.ui.register.phone;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.data.Repository;
import com.iservice.agency.ui.base.activity.BaseViewModel;
import com.iservice.agency.ui.register.phone.form.InputPhoneNumberForm;

public class InputPhoneNumberViewModel extends BaseViewModel {

    InputPhoneNumberForm inputPhoneNumberForm = new InputPhoneNumberForm();

    public InputPhoneNumberViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
