package com.iservice.agency.ui.register.phone.form;

import androidx.databinding.Bindable;

import com.iservice.agency.BR;
import com.iservice.agency.ui.base.BaseForm;

public class InputPhoneNumberForm extends BaseForm {
    private String inputNumber;

    @Bindable
    public String getInputNumber(){
        return inputNumber;
    }

    public void setInputNumber(String number){
        this.inputNumber=number;
        notifyPropertyChanged(BR.inputNumber);
    }
}
