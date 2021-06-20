package com.iservice.agency.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.databinding.ActivityMainBinding;
import com.iservice.agency.di.component.ActivityComponent;
import com.iservice.agency.ui.base.activity.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = null;
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        if (navController != null) {
            NavigationUI.setupWithNavController(viewBinding.bottomNavigation,navController);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getBindingVariable() {
        return BR.mainViewModel;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

}
