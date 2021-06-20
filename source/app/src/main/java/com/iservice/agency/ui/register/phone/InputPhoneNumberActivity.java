package com.iservice.agency.ui.register.phone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.databinding.ActivityInputPhoneNumberBinding;
import com.iservice.agency.di.component.ActivityComponent;
import com.iservice.agency.ui.base.activity.BaseActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class InputPhoneNumberActivity extends BaseActivity<ActivityInputPhoneNumberBinding, InputPhoneNumberViewModel> implements View.OnClickListener{

    private String mVerificationId;

    public static final String VERIFICATION_ID = "VERIFICATION_ID";
    public static final String FROM_INPUT_PHONE_NUMBER = "FROM_INPUT_PHONE_NUMBER";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setInputPhoneNumberActivity(this);
        viewBinding.setInputPhoneNumberForm(viewModel.inputPhoneNumberForm);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_input_phone_number;
    }

    @Override
    public int getBindingVariable() {
        return BR.inputPhoneNumberViewModel;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        if (checkPhoneNumber(viewModel.inputPhoneNumberForm.getInputNumber())){
            viewModel.showLoading();
            viewBinding.inputPhoneContinueBtn.setClickable(false);
            sendOTPNumber( getPhoneNumber());
        } else {
            viewModel.showErrorMessage(getString(R.string.enter_phone_number_message));
        }
    }

    private boolean checkPhoneNumber(String phone){
        return phone!=null && phone.length() == 10 && phone.matches("[0-9]+");

    }

    private String getPhoneNumber(){
        return "+84"+" "+viewModel.inputPhoneNumberForm.getInputNumber();
    }

    private void sendOTPNumber(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(createCallBack())          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks createCallBack(){
        return new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Timber.d("onVerificationCompleted:%s", phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                viewModel.hideLoading();
                viewBinding.inputPhoneContinueBtn.setClickable(true);
                viewModel.showErrorMessage(getString(R.string.send_OTP_fail_message));
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Timber.d("Invalid request");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Timber.d("The SMS quota for the project has been exceeded");
                }
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId=s;
                viewModel.hideLoading();
                navigationToLoginWithPhone();
            }
        };
    }

    private void navigationToLoginWithPhone(){
        Intent it = new Intent(InputPhoneNumberActivity.this, VerifyCodeActivity.class);
        it.putExtra(VERIFICATION_ID,mVerificationId);
        it.putExtra(FROM_INPUT_PHONE_NUMBER,true);
        it.putExtra(PHONE_NUMBER,getPhoneNumber());
        startActivity(it);
        finish();
    }

}