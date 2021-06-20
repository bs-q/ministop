package com.iservice.agency.ui.main.activity.fragment;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.constant.Constants;
import com.iservice.agency.databinding.ActivityFragmentBinding;
import com.iservice.agency.di.component.FragmentComponent;
import com.iservice.agency.ui.base.fragment.BaseFragment;
import com.iservice.agency.ui.main.activity.adapter.ActivityItem;
import com.iservice.agency.ui.main.activity.adapter.ActivityAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActivityFragment extends BaseFragment<ActivityFragmentBinding,ActivityViewModel> {


    @Override
    public int getBindingVariable() {
        return BR.mainActivityViewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void performDataBinding() {
        // do data binding here
        binding.mainActivityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<ActivityItem> activityItems = new ArrayList<>();
        activityItems.add(new ActivityItem("sandwich",R.mipmap.cat_sandwich));
        activityItems.add(new ActivityItem("water",R.mipmap.cat_water));
        activityItems.add(new ActivityItem("sushi",R.mipmap.cat_sushi));
        activityItems.add(new ActivityItem("onigiri",R.mipmap.cat_onigiri));
        activityItems.add(new ActivityItem("dessert",R.mipmap.cat_dessert));
        binding.mainActivityRecyclerView.setAdapter(new ActivityAdapter(activityItems,requireContext()));
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}