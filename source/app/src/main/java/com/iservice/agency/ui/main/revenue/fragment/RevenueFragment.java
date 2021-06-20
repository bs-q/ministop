package com.iservice.agency.ui.main.revenue.fragment;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.constant.Constants;
import com.iservice.agency.databinding.RevenueFragmentBinding;
import com.iservice.agency.di.component.FragmentComponent;
import com.iservice.agency.ui.base.fragment.BaseFragment;
import com.iservice.agency.ui.main.revenue.adapter.RevenueAdapter;
import com.iservice.agency.ui.main.revenue.adapter.RevenueItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RevenueFragment extends BaseFragment<RevenueFragmentBinding,RevenueViewModel> {


    @Override
    public int getBindingVariable() {
        return BR.mainRevenueViewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.revenue_fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void performDataBinding() {
        // do data binding here
        binding.mainRevenueRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<RevenueItem> revenueItems = new ArrayList<>();
        revenueItems.add(new RevenueItem(getString(R.string.ca_vien_chien_p), new Date(),new Date(),getString(R.string.ca_vien_chien_p),R.mipmap.ca_vien_chien));
        revenueItems.add(new RevenueItem(getString(R.string.coffe_p), new Date(),new Date(),getString(R.string.coffe_p),R.mipmap.coffe));
        revenueItems.add(new RevenueItem(getString(R.string.rice_p), new Date(),new Date(),getString(R.string.rice_p),R.mipmap.rice));
        revenueItems.add(new RevenueItem(getString(R.string.ice_p), new Date(),new Date(),getString(R.string.ice_p),R.mipmap.ice));
        revenueItems.add(new RevenueItem(getString(R.string.cheese_p), new Date(),new Date(),getString(R.string.cheese_p),R.mipmap.cheese));
        revenueItems.add(new RevenueItem(getString(R.string.bread_p), new Date(),new Date(),getString(R.string.bread_p),R.mipmap.bread));
        binding.mainRevenueRecyclerView.setAdapter(new RevenueAdapter(revenueItems,requireContext()));

    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}