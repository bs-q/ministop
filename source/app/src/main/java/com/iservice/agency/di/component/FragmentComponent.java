package com.iservice.agency.di.component;


import com.iservice.agency.di.module.FragmentModule;
import com.iservice.agency.di.scope.FragmentScope;
import com.iservice.agency.ui.main.account.fragment.AccountFragment;
import com.iservice.agency.ui.main.activity.fragment.ActivityFragment;
import com.iservice.agency.ui.main.home.fragment.HomeFragment;
import com.iservice.agency.ui.main.home.fragment.statistic.StatisticFragment;
import com.iservice.agency.ui.main.home.fragment.status.StatusFragment;
import com.iservice.agency.ui.main.revenue.fragment.RevenueFragment;

import dagger.Component;

@FragmentScope
@Component(modules = {FragmentModule.class},dependencies = AppComponent.class)
public interface FragmentComponent {

    void inject(AccountFragment fragment);

    void inject(ActivityFragment fragment);

    void inject(StatisticFragment fragment);

    void inject(StatusFragment fragment);

    void inject(HomeFragment fragment);

    void inject(RevenueFragment fragment);
}
