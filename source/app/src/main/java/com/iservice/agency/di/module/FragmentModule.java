package com.iservice.agency.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.iservice.agency.MVVMApplication;
import com.iservice.agency.ViewModelProviderFactory;
import com.iservice.agency.data.Repository;
import com.iservice.agency.di.scope.FragmentScope;
import com.iservice.agency.ui.base.fragment.BaseFragment;
import com.iservice.agency.ui.main.account.fragment.AccountViewModel;
import com.iservice.agency.ui.main.activity.fragment.ActivityViewModel;
import com.iservice.agency.ui.main.home.fragment.HomeViewModel;
import com.iservice.agency.ui.main.home.fragment.statistic.StatisticViewModel;
import com.iservice.agency.ui.main.home.fragment.status.StatusViewModel;
import com.iservice.agency.ui.main.revenue.fragment.RevenueViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private BaseFragment<?, ?> fragment;

    public FragmentModule(BaseFragment<?, ?> fragment) {
        this.fragment = fragment;
    }

    @Named("access_token")
    @Provides
    @FragmentScope
    String provideToken(Repository repository) {
        return repository.getToken();
    }


    @Provides
    @FragmentScope
    AccountViewModel provideAccountViewModel(Repository repository, Context application){
        Supplier<AccountViewModel> supplier = ()-> new AccountViewModel(repository,(MVVMApplication)application);
        ViewModelProviderFactory<AccountViewModel> factory = new ViewModelProviderFactory<>(AccountViewModel.class,supplier);
        return new ViewModelProvider(fragment,factory).get(AccountViewModel.class);
    }

    @Provides
    @FragmentScope
    ActivityViewModel provideActivityViewModel(Repository repository, Context application){
        Supplier<ActivityViewModel> supplier = ()-> new ActivityViewModel(repository,(MVVMApplication)application);
        ViewModelProviderFactory<ActivityViewModel> factory = new ViewModelProviderFactory<>(ActivityViewModel.class,supplier);
        return new ViewModelProvider(fragment,factory).get(ActivityViewModel.class);
    }

    @Provides
    @FragmentScope
    StatisticViewModel provideStatisticViewModel(Repository repository, Context application){
        Supplier<StatisticViewModel> supplier = ()-> new StatisticViewModel(repository,(MVVMApplication)application);
        ViewModelProviderFactory<StatisticViewModel> factory = new ViewModelProviderFactory<>(StatisticViewModel.class,supplier);
        return new ViewModelProvider(fragment,factory).get(StatisticViewModel.class);
    }

    @Provides
    @FragmentScope
    StatusViewModel provideStatusViewModel(Repository repository, Context application){
        Supplier<StatusViewModel> supplier = ()-> new StatusViewModel(repository,(MVVMApplication)application);
        ViewModelProviderFactory<StatusViewModel> factory = new ViewModelProviderFactory<>(StatusViewModel.class,supplier);
        return new ViewModelProvider(fragment,factory).get(StatusViewModel.class);
    }

    @Provides
    @FragmentScope
    HomeViewModel provideHomeViewModel(Repository repository, Context application){
        Supplier<HomeViewModel> supplier = ()-> new HomeViewModel(repository,(MVVMApplication)application);
        ViewModelProviderFactory<HomeViewModel> factory = new ViewModelProviderFactory<>(HomeViewModel.class,supplier);
        return new ViewModelProvider(fragment,factory).get(HomeViewModel.class);
    }

    @Provides
    @FragmentScope
    RevenueViewModel provideRevenueViewModel(Repository repository, Context application){
        Supplier<RevenueViewModel> supplier = ()-> new RevenueViewModel(repository,(MVVMApplication)application);
        ViewModelProviderFactory<RevenueViewModel> factory = new ViewModelProviderFactory<>(RevenueViewModel.class,supplier);
        return new ViewModelProvider(fragment,factory).get(RevenueViewModel.class);
    }
}
