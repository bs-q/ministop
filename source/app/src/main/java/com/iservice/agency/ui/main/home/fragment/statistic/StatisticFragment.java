package com.iservice.agency.ui.main.home.fragment.statistic;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.databinding.StatisticFragmentBinding;
import com.iservice.agency.di.component.FragmentComponent;
import com.iservice.agency.ui.base.fragment.BaseFragment;


public class StatisticFragment extends BaseFragment<StatisticFragmentBinding,StatisticViewModel> {

    @Override
    public int getBindingVariable() {
        return BR.statisticViewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.statistic_fragment;
    }

    @Override
    protected void performDataBinding() {
        binding.setStatisticForm(viewModel.form);
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}