package com.iservice.agency.di.component;

import com.iservice.agency.di.module.ActivityModule;
import com.iservice.agency.di.scope.ActivityScope;
import com.iservice.agency.ui.food.FoodActivity;
import com.iservice.agency.ui.food.detail.FoodDetailActivity;
import com.iservice.agency.ui.promotion.PromotionActivity;
import com.iservice.agency.ui.register.CreateNewAccountActivity;
import com.iservice.agency.ui.register.phone.InputPhoneNumberActivity;
import com.iservice.agency.ui.login.LoginActivity;
import com.iservice.agency.ui.register.phone.VerifyCodeActivity;
import com.iservice.agency.ui.main.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

    void inject(VerifyCodeActivity activity);

    void inject(InputPhoneNumberActivity activity);

    void inject(CreateNewAccountActivity activity);

    void inject(FoodActivity activity);

    void inject(FoodDetailActivity activity);

    void inject(PromotionActivity activity);
}

