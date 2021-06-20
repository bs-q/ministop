package com.iservice.agency.ui.food;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.data.model.db.FoodEntity;
import com.iservice.agency.databinding.ActivityFoodBinding;
import com.iservice.agency.di.component.ActivityComponent;
import com.iservice.agency.ui.base.activity.BaseActivity;
import com.iservice.agency.ui.food.adapter.FoodAdapter;

import java.util.ArrayList;
import java.util.List;


public class FoodActivity extends BaseActivity<ActivityFoodBinding,FoodViewModel> {

    private FoodAdapter foodAdapter;
    private List<FoodEntity> foodEntities;
    @Override
    public int getLayoutId() {
        return R.layout.activity_food;
    }

    @Override
    public int getBindingVariable() {
        return BR.foodViewModel;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(viewBinding.foodToolBar);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            String type = extras.getString("CATEGORY");
            if (type!=null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle(type);
                viewModel.foodEntityLiveData=viewModel.dbService.loadAllToLiveData(type);
            }
        }
        foodEntities = new ArrayList<>();
        foodAdapter = new FoodAdapter(foodEntities,this);
        viewModel.foodEntityLiveData.observe(this,entities -> {
            foodEntities.clear();
            foodEntities = entities;
            foodAdapter.setFoodEntityList(foodEntities);
            foodAdapter.notifyDataSetChanged();
        });

        viewBinding.foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.foodRecyclerView.setAdapter(foodAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
