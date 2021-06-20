package com.iservice.agency.ui.login.form;

import androidx.databinding.Bindable;

import com.iservice.agency.BR;
import com.iservice.agency.ui.base.BaseForm;


public class LoginForm extends BaseForm {
    private String username;
    private String password;

    public LoginForm() {
    }

    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }
}
