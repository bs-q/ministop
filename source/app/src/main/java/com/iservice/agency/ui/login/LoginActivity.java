package com.iservice.agency.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.databinding.ActivityLoginBinding;
import com.iservice.agency.di.component.ActivityComponent;
import com.iservice.agency.ui.base.activity.BaseActivity;
import com.iservice.agency.ui.register.CreateNewAccountActivity;
import com.iservice.agency.ui.register.phone.VerifyCodeActivity;
import com.iservice.agency.ui.main.MainActivity;
import com.iservice.agency.utils.LogService;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;
    public static final int LOGIN_WITH_GOOGLE = 1;
    public static final String EMAIL_FROM_LOGIN = "EMAIL_FROM_LOGIN";
    public static final String FROM_LOGIN = "FROM_LOGIN";
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private boolean isLoginWithFacebook = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setLoginActivity(this);
        viewBinding.setLoginForm(viewModel.loginForm);

        viewModel.doCheckOnStartup(new LoginCallback() {

            @Override
            public void doError(Throwable error) {
                //do later
            }

            @Override
            public void doSuccess() {
                navigationToMain();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        // login with google initialization
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // login with facebook initialization
        setLoginWithFacebook();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public int getBindingVariable() {
        return BR.loginViewModel;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButtonLogin:
                viewBinding.loginButtonLogin.setClickable(false);
                doLogin();
                break;
            case R.id.loginForgotPwd:
                break;
            case R.id.loginWithGoogle:
                doLoginWithGoogle();
                viewModel.showLoading();
                break;
            case R.id.loginWithFacebook:
                doLoginWithFacebook();
                viewModel.showLoading();
                break;
            case R.id.loginWithPhone:
                doLoginWithPhone();
                break;
            default:
                break;
        }
    }
    private void doLoginWithPhone() {
        Intent intent = new Intent(this, VerifyCodeActivity.class);
        startActivity(intent);
    }

    private void doLogin(){

        viewModel.doLogin(new LoginCallback() {
            @Override
            public void doError(Throwable error) {
                error.printStackTrace();
                LogService.e(error);
                viewBinding.loginButtonLogin.setClickable(true);
            }

            @Override
            public void doSuccess() {
                navigationToMain();
            }
        });

    }


    private void doLoginWithGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, LOGIN_WITH_GOOGLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_WITH_GOOGLE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    if (account.getIdToken()==null){

                        viewModel.hideLoading();
                        viewModel.showErrorMessage(getString(R.string.login_google_error));
                    } else {
                        firebaseAuthWithGoogle(account.getIdToken(),account.getEmail());
                    }
                }
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                viewModel.hideLoading();
                viewModel.showErrorMessage(getString(R.string.login_google_error));
            }
        }
        // get intent from facebook
        if (isLoginWithFacebook){
            isLoginWithFacebook=false;
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void firebaseAuthWithGoogle(String idToken,String email) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        viewModel.hideLoading();
                        navigationToCreateAccount(email);
                    } else {
                        // If sign in fails, display a message to the user.
                        viewModel.hideLoading();
                        viewModel.showErrorMessage(getString(R.string.login_error));
                    }
                });
    }

    private void doLoginWithFacebook(){
        isLoginWithFacebook=true;
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }
    private void setLoginWithFacebook(){
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        viewModel.hideLoading();
                        viewModel.showErrorMessage(getString(R.string.login_error));
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        viewModel.hideLoading();
                        viewModel.showErrorMessage(getString(R.string.login_error));
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        viewModel.hideLoading();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user!=null){
                            navigationToCreateAccount(user.getEmail());
                        } else  {
                            viewModel.showErrorMessage("Can not login with facebook");
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        viewModel.hideLoading();
                        viewModel.showErrorMessage(getString(R.string.login_error));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginManager.getInstance().unregisterCallback(callbackManager);
    }

    private void navigationToMain(){
        Intent it = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(it);
        finish();
    }

    private void navigationToCreateAccount(String email){
        Intent it = new Intent(LoginActivity.this, CreateNewAccountActivity.class);
        it.putExtra(FROM_LOGIN,true);
        it.putExtra(EMAIL_FROM_LOGIN,email);
        startActivity(it);
        finish();
    }
}