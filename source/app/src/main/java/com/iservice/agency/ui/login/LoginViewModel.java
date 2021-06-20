package com.iservice.agency.ui.login;

import androidx.databinding.ObservableBoolean;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.R;
import com.iservice.agency.constant.Constants;
import com.iservice.agency.data.Repository;
import com.iservice.agency.data.local.prefs.PreferencesService;
import com.iservice.agency.data.model.api.request.LoginRequest;
import com.iservice.agency.ui.base.activity.BaseViewModel;
import com.iservice.agency.ui.login.form.LoginForm;
import com.iservice.agency.utils.NetworkUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {

    LoginForm loginForm = new LoginForm("admin", "admin123654");
    private final ObservableBoolean splashScreenObservable = new ObservableBoolean();

    public LoginViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public ObservableBoolean getIsShowSplashScreen() {
        return splashScreenObservable;
    }


    public void doCheckOnStartup(LoginCallback callback){
        if(!token.equals(Constants.VALUE_BEARER_TOKEN_DEFAULT)){
            splashScreenObservable.set(true);
            showLoading();

            compositeDisposable.add(repository.getApiService().profile()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .retryWhen(throwable ->
                            throwable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                                @Override
                                public ObservableSource<?> apply(Throwable throwable) throws Throwable {
                                    if (NetworkUtils.checkNetworkError(throwable)) {
                                        return application.showDialogNoInternetAccess();
                                    }else{
                                        return Observable.error(throwable);
                                    }
                                }
                            })
                    )
                    .subscribe(loginResponseResponseWrapper -> {
                        if(callback!=null){
                            callback.doSuccess();
                        }
                        hideLoading();
                    }, throwable -> {
                        repository.getSharedPreferences().removeKey(PreferencesService.KEY_BEARER_TOKEN);
                        splashScreenObservable.set(false);
                        hideLoading();
                    }));
        }
    }

    public void doLogin(LoginCallback callback){
        showLoading();
        LoginRequest request = new LoginRequest();
        request.setUsername(loginForm.getUsername());
        request.setPassword(loginForm.getPassword());
        compositeDisposable.add(repository.getApiService().login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwable ->
                        throwable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Throwable {
                                if (NetworkUtils.checkNetworkError(throwable)) {
                                    return application.showDialogNoInternetAccess();
                                }else{
                                    return Observable.error(throwable);
                                }
                            }
                        })
                )
                .subscribe(loginResponseResponseObj -> {
                    if(loginResponseResponseObj.isResult()){
                        repository.setToken(loginResponseResponseObj.getData().getToken());
                        if(callback!=null){
                            callback.doSuccess();
                        }
                        showSuccessMessage(application.getResources().getString(R.string.login_success));
                    }else{
                        showErrorMessage(application.getResources().getString(R.string.login_error));
                    }
                    hideLoading();
                }, throwable -> {
                    if(callback!=null){
                        callback.doError(throwable);
                    }
                    showErrorMessage(application.getResources().getString(R.string.login_error));
                    hideLoading();
                }));
    }

}
