package com.iservice.agency.ui.register;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.data.Repository;
import com.iservice.agency.data.model.api.request.CreateAccountRequest;
import com.iservice.agency.ui.base.activity.BaseViewModel;
import com.iservice.agency.ui.register.form.CreateNewAccountForm;
import com.iservice.agency.utils.NetworkUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CreateNewAccountViewModel extends BaseViewModel {

    CreateNewAccountForm createNewAccountForm = new CreateNewAccountForm();

    public CreateNewAccountViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }


    public boolean checkAgreement(){
        return  createNewAccountForm.isAgree();
    }

    public boolean checkPassword(){
        if (createNewAccountForm.getCreateAccountPassword()==null){
            return false;
        } else {
            return createNewAccountForm.getCreateAccountPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        }
    }

    public boolean checkFirstName(){
        return (createNewAccountForm.getFirstName().length()<3);
    }

    public boolean checkLastName(){
        return (createNewAccountForm.getLastName().length()<3);
    }

    public void doRegister(CreateNewAccountCallback callback){
        showLoading();
        CreateAccountRequest request = new CreateAccountRequest();
        request.setAvatarPath("");
        request.setBirthday("2021-05-30T09:54:52.993Z");
        request.setCommuneId(0);
        request.setDistrictId(0);
        request.setEmail(createNewAccountForm.getPhoneOrEmail());
        request.setFullName(createNewAccountForm.getLastName()+" "+createNewAccountForm.getFirstName());
        request.setLang("VI");
        request.setOrganizeId(39);
        request.setPassword(createNewAccountForm.getCreateAccountPassword());
        request.setPhone(createNewAccountForm.getPhoneOrEmail());
        request.setProvinceId(0);
        request.setUsername(createNewAccountForm.getPhoneOrEmail());

        compositeDisposable.add(repository.getApiService().createAccount(request)
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
                .subscribe(createAccountResponseResponseWrapper -> {
                    if (createAccountResponseResponseWrapper.isResult()){
                        if (callback!=null){
                            callback.doSuccess();
                        }
                        showSuccessMessage("Create new account success");
                    } else {
                        showErrorMessage("Create new account error");
                    }
                    hideLoading();
                },throwable -> {
                    if (callback!=null){
                        callback.doError(throwable);
                    }
                    showErrorMessage("Create new account error");
                    hideLoading();
                }));
    }

}
