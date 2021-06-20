package com.iservice.agency.ui.main.home.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.iservice.agency.ui.main.home.fragment.statistic.StatisticFragment;
import com.iservice.agency.ui.main.home.fragment.status.StatusFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new StatusFragment();
        }
        return new StatisticFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
