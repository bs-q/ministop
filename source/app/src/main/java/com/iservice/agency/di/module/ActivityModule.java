package com.iservice.agency.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.ViewModelProviderFactory;
import com.iservice.agency.data.Repository;
import com.iservice.agency.di.scope.ActivityScope;
import com.iservice.agency.ui.base.activity.BaseActivity;
import com.iservice.agency.ui.food.FoodViewModel;
import com.iservice.agency.ui.food.detail.FoodDetailViewModel;
import com.iservice.agency.ui.promotion.PromotionViewModel;
import com.iservice.agency.ui.register.CreateNewAccountViewModel;
import com.iservice.agency.ui.register.phone.InputPhoneNumberViewModel;
import com.iservice.agency.ui.login.LoginViewModel;
import com.iservice.agency.ui.register.phone.VerifyCodeViewModel;
import com.iservice.agency.ui.main.MainViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private BaseActivity<?, ?> activity;

    public ActivityModule(BaseActivity<?, ?> activity) {
        this.activity = activity;
    }

    @Named("access_token")
    @Provides
    @ActivityScope
    String provideToken(Repository repository){
        return repository.getToken();
    }

    @Provides
    @ActivityScope
    LoginViewModel provideLoginViewModel(Repository repository, Context application) {
        Supplier<LoginViewModel> supplier = () -> new LoginViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<LoginViewModel> factory = new ViewModelProviderFactory<>(LoginViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(LoginViewModel.class);
    }


    @Provides
    @ActivityScope
    MainViewModel provideMainViewModel(Repository repository, Context application) {
        Supplier<MainViewModel> supplier = () -> new MainViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<MainViewModel> factory = new ViewModelProviderFactory<>(MainViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    @Provides
    @ActivityScope
    VerifyCodeViewModel provideLoginWithPhoneViewModel(Repository repository, Context application) {
        Supplier<VerifyCodeViewModel> supplier = () -> new VerifyCodeViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<VerifyCodeViewModel> factory = new ViewModelProviderFactory<>(VerifyCodeViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(VerifyCodeViewModel.class);
    }

    @Provides
    @ActivityScope
    InputPhoneNumberViewModel provideInputPhoneNumberViewModel(Repository repository, Context application) {
        Supplier<InputPhoneNumberViewModel> supplier = () -> new InputPhoneNumberViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<InputPhoneNumberViewModel> factory = new ViewModelProviderFactory<>(InputPhoneNumberViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(InputPhoneNumberViewModel.class);
    }

    @Provides
    @ActivityScope
    CreateNewAccountViewModel provideCreateNewAccountViewModel(Repository repository, Context application) {
        Supplier<CreateNewAccountViewModel> supplier = () -> new CreateNewAccountViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<CreateNewAccountViewModel> factory = new ViewModelProviderFactory<>(CreateNewAccountViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(CreateNewAccountViewModel.class);
    }

    @Provides
    @ActivityScope
    FoodViewModel provideFoodViewModel(Repository repository, Context application) {
        Supplier<FoodViewModel> supplier = () -> new FoodViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<FoodViewModel> factory = new ViewModelProviderFactory<>(FoodViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(FoodViewModel.class);
    }

    @Provides
    @ActivityScope
    FoodDetailViewModel provideFoodDetailViewModel(Repository repository, Context application) {
        Supplier<FoodDetailViewModel> supplier = () -> new FoodDetailViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<FoodDetailViewModel> factory = new ViewModelProviderFactory<>(FoodDetailViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(FoodDetailViewModel.class);
    }

    @Provides
    @ActivityScope
    PromotionViewModel providePromotionViewModel(Repository repository, Context application) {
        Supplier<PromotionViewModel> supplier = () -> new PromotionViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<PromotionViewModel> factory = new ViewModelProviderFactory<>(PromotionViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(PromotionViewModel.class);
    }
}
