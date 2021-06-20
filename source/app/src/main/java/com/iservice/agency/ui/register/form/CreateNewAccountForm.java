package com.iservice.agency.ui.register.form;

import androidx.databinding.Bindable;

import com.iservice.agency.BR;
import com.iservice.agency.ui.base.BaseForm;

public class CreateNewAccountForm extends BaseForm {

    private String firstName;
    private String lastName;
    private String phoneOrEmail;
    private String createAccountPassword;
    private boolean isAgree;

    public CreateNewAccountForm(){
        this.firstName="";
        this.lastName="";
        this.phoneOrEmail="";
        this.createAccountPassword="";
        this.isAgree=false;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    @Bindable
    public String getPhoneOrEmail() {
        return phoneOrEmail;
    }

    @Bindable
    public String getCreateAccountPassword() {
        return createAccountPassword;
    }

    @Bindable
    public boolean isAgree(){
        return isAgree;
    }

    public void setAgree(boolean agree) {
        isAgree = agree;
        notifyPropertyChanged(BR.agree);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    public void setCreateAccountPassword(String createAccountPassword) {
        this.createAccountPassword = createAccountPassword;
        notifyPropertyChanged(BR.createAccountPassword);
    }

    public void setPhoneOrEmail(String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
        notifyPropertyChanged(BR.phoneOrEmail);
    }
}
