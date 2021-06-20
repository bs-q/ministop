package com.iservice.agency.ui.main.home.fragment.status;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.databinding.StatusFragmentBinding;
import com.iservice.agency.di.component.FragmentComponent;
import com.iservice.agency.ui.base.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

import es.dmoral.toasty.Toasty;

public class StatusFragment extends BaseFragment<StatusFragmentBinding,StatusViewModel> implements View.OnClickListener {


    @Override
    public int getBindingVariable() {
        return BR.statusViewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.status_fragment;
    }

    @Override
    protected void performDataBinding() {
        // do data binding here
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setStatusFragment(this);
    }

    @Override
    public void onClick(View v) {

    }
}