package com.iservice.agency.ui.food.detail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import androidx.annotation.Nullable;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.databinding.ActivityFoodDetailBinding;
import com.iservice.agency.di.component.ActivityComponent;
import com.iservice.agency.ui.base.activity.BaseActivity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

public class FoodDetailActivity extends BaseActivity<ActivityFoodDetailBinding,FoodDetailViewModel> {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setFoodDetailActivity(this);
        setSupportActionBar(viewBinding.foodDetailToolBar);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            String name = extras.getString("name");
            viewModel.foodEntity.setName(name);
            Integer price = extras.getInt("price");
            viewModel.foodEntity.setPrice(price);
            String description = extras.getString("description");
            viewModel.foodEntity.setDescription(description);
            String image = extras.getString("image");
            InputStream stream = new ByteArrayInputStream(Base64.decode(image.getBytes(), Base64.DEFAULT));
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            viewBinding.foodDetailImg.setImageBitmap(bitmap);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(viewModel.foodEntity.name);
        viewBinding.setEntity(viewModel.foodEntity);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_food_detail;
    }

    @Override
    public int getBindingVariable() {
        return BR.foodDetailViewModel;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
