package com.iservice.agency.ui.register;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.databinding.ActivityCreateNewAccountBinding;
import com.iservice.agency.di.component.ActivityComponent;
import com.iservice.agency.ui.base.activity.BaseActivity;
import com.iservice.agency.ui.login.LoginActivity;
import com.iservice.agency.ui.main.MainActivity;
import com.iservice.agency.ui.register.phone.VerifyCodeActivity;
import com.iservice.agency.utils.LogService;

public class CreateNewAccountActivity extends BaseActivity<ActivityCreateNewAccountBinding,CreateNewAccountViewModel> implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setCreateNewAccountActivity(this);
        viewBinding.setCreateNewAccountForm(viewModel.createNewAccountForm);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            if (extras.getBoolean(VerifyCodeActivity.FROM_LOGIN_WITH_PHONE)){
                viewModel.createNewAccountForm.setPhoneOrEmail(extras.getString(VerifyCodeActivity.PHONE_NUMBER_FROM_LOGIN));
            }
            if (extras.getBoolean(LoginActivity.FROM_LOGIN)){
                viewModel.createNewAccountForm.setPhoneOrEmail(extras.getString(LoginActivity.EMAIL_FROM_LOGIN));
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (viewModel.checkFirstName()){
            viewModel.showErrorMessage(getString(R.string.input_first_name_error_message));
            return;
        }

        if (viewModel.checkLastName()){
            viewModel.showErrorMessage(getString(R.string.input_last_name_error_message));
        }

        if (!viewModel.checkPassword()){
            viewModel.showErrorMessage(getString(R.string.input_message_error_message));
            return;
        }

        if (!viewModel.checkAgreement()){
            viewModel.showErrorMessage(getString(R.string.check_term_message));
        }
        doRegister();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_new_account;
    }

    @Override
    public int getBindingVariable() {
        return BR.createNewAccountViewModel;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void doRegister(){
        viewModel.doRegister(new CreateNewAccountCallback() {
            @Override
            public void doError(Throwable error) {
                error.printStackTrace();
                LogService.e(error);
                navigationToMain();
            }

            @Override
            public void doSuccess() {
                navigationToMain();
            }
        });
    }

    public void navigationToMain(){
        Intent it = new Intent(CreateNewAccountActivity.this, MainActivity.class);
        startActivity(it);
        finish();
    }
}