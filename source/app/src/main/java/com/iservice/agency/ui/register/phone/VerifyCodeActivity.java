package com.iservice.agency.ui.register.phone;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.databinding.ActivityLoginWithPhoneBinding;
import com.iservice.agency.di.component.ActivityComponent;
import com.iservice.agency.ui.base.activity.BaseActivity;
import com.iservice.agency.ui.register.CreateNewAccountActivity;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class VerifyCodeActivity extends BaseActivity<ActivityLoginWithPhoneBinding, VerifyCodeViewModel> implements View.OnClickListener {

    private static final int RESOLVE_PHONE_NUMBER_HINT = 1008;
    public static final String PHONE_NUMBER_FROM_LOGIN = "PHONE_NUMBER_FROM_LOGIN";
    public static final String FROM_LOGIN_WITH_PHONE = "FROM_LOGIN_WITH_PHONE";
    private String mVerificationId;
    private String mPhoneNumber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setVerifyCodeActivity(this);
        viewBinding.setLoginWithPhoneForm(viewModel.loginWithPhoneForm);
        viewModel.loginWithPhoneForm.updateEditText(viewBinding.verifyCode02Txt,viewBinding.verifyCode03Txt);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            if (extras.getBoolean(InputPhoneNumberActivity.FROM_INPUT_PHONE_NUMBER)){
                mVerificationId=extras.getString(InputPhoneNumberActivity.VERIFICATION_ID);
                viewModel.loginWithPhoneForm.setPhoneNumber(extras.getString(InputPhoneNumberActivity.PHONE_NUMBER));
                mPhoneNumber=extras.getString(InputPhoneNumberActivity.PHONE_NUMBER).substring(3);
            }
        } else {
        requestPhoneNumberHint(this);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.verifyButton:
                viewBinding.verifyButton.setClickable(false);
                doLoginWithPhone();
                break;
            case R.id.phoneResentCode:
                viewBinding.phoneResentCode.setClickable(false);
                requestPhoneNumberHint(this);
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_with_phone;
    }

    @Override
    public int getBindingVariable() {
        return BR.loginWithPhoneViewModel;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    public void requestPhoneNumberHint(Activity currentActivity) {
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();
        CredentialsClient credentialsClient = Credentials.getClient(currentActivity);

        PendingIntent intent = credentialsClient.getHintPickerIntent(hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(),
                    RESOLVE_PHONE_NUMBER_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_PHONE_NUMBER_HINT){
            if (resultCode == RESULT_OK){
                viewBinding.phoneResentCode.setClickable(true);
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                Timber.d("onActivityResult() returned: %s", credential.getId());
                viewModel.showSuccessMessage(getString(R.string.login_with_phone_success));
                viewModel.loginWithPhoneForm.setPhoneNumber(credential.getId());

                mPhoneNumber=credential.getId().substring(3);
                sendOTPNumber(new StringBuilder(credential.getId()).insert(1," ").toString());
            }

            if (resultCode == CredentialsApi.ACTIVITY_RESULT_OTHER_ACCOUNT){
                navigationToInputPhoneNumber();
            }

        }
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
                navigationToCreateAccount();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
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
            }
        };
    }


    private boolean checkOTP(){
        return !viewModel.loginWithPhoneForm.getVerifyCode01().equals("") && !viewModel.loginWithPhoneForm.getVerifyCode02().equals("") && !viewModel.loginWithPhoneForm.getVerifyCode03().equals("");
    }

    private String getOTP(){
        return viewModel.loginWithPhoneForm.getVerifyCode01()+viewModel.loginWithPhoneForm.getVerifyCode02()+viewModel.loginWithPhoneForm.getVerifyCode03();
    }

    private void signInWithPhoneAuthCredetial(PhoneAuthCredential credential){
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(
                this, task -> {
                    if (task.isSuccessful()){
                        Timber.d("signInWithCredential:success");
                        viewModel.hideLoading();
                        navigationToCreateAccount();
                    } else {
                        Timber.d(task.getException(), "signInWithCredential:failure");
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            viewModel.hideLoading();
                            viewBinding.verifyButton.setClickable(true);
                            viewModel.showErrorMessage(getString(R.string.login_with_phone_failed));
                        }
                    }
                }
        );
    }

    private void navigationToCreateAccount(){
        Intent it = new Intent(VerifyCodeActivity.this, CreateNewAccountActivity.class);
        it.putExtra(FROM_LOGIN_WITH_PHONE,true);
        it.putExtra(PHONE_NUMBER_FROM_LOGIN,mPhoneNumber);
        startActivity(it);
        finish();
    }

    private void navigationToInputPhoneNumber(){
        Intent it = new Intent(VerifyCodeActivity.this, InputPhoneNumberActivity.class);
        startActivity(it);
        finish();
    }

    private void doLoginWithPhone(){
        if (checkOTP()&&mVerificationId!=null){
            viewModel.showLoading();
            signInWithPhoneAuthCredetial(PhoneAuthProvider.getCredential(mVerificationId,getOTP()));
        } else {
            viewBinding.verifyButton.setClickable(true);
            viewModel.showErrorMessage(getString(R.string.login_with_phone_OTP_required));
        }
    }
}