package com.iservice.agency.ui.register.phone.form;



import android.widget.EditText;

import androidx.databinding.Bindable;

import com.iservice.agency.BR;
import com.iservice.agency.ui.base.BaseForm;


public class LoginWithPhoneForm extends BaseForm {
    private String verifyCode01="";
    private String verifyCode02="";
    private String verifyCode03="";
    private String phoneNumber;

    private EditText verifyCode02Txt;
    private EditText verifyCode03Txt;

    public void updateEditText(EditText verifyCode02Txt, EditText verifyCode03Txt){
        this.verifyCode02Txt=verifyCode02Txt;
        this.verifyCode03Txt=verifyCode03Txt;
    }

    @Bindable
    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber="Please type the verification code\n" +
                "sent to "+phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }

    public LoginWithPhoneForm(){
    }


    @Bindable
    public String getVerifyCode01() {
        if (verifyCode01!=null&&verifyCode01.length()==2){
            verifyCode02Txt.requestFocus();
        }
        return verifyCode01;
    }

    public void setVerifyCode01(String verifyCode01) {
        this.verifyCode01 = verifyCode01;
        notifyPropertyChanged(BR.verifyCode01);
    }

    @Bindable
    public String getVerifyCode02() {
        if (verifyCode02!=null&&verifyCode02.length()==2){
            verifyCode03Txt.requestFocus();
        }
        return verifyCode02;
    }

    public void setVerifyCode02(String verifyCode02) {
        this.verifyCode02 = verifyCode02;
        notifyPropertyChanged(BR.verifyCode02);
    }

    @Bindable
    public String getVerifyCode03() {
        return verifyCode03;
    }

    public void setVerifyCode03(String verifyCode03) {
        this.verifyCode03 = verifyCode03;
        notifyPropertyChanged(BR.verifyCode03);

    }
}
