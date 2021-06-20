package com.iservice.agency.ui.promotion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.databinding.ActivityPromotionBinding;
import com.iservice.agency.di.component.ActivityComponent;
import com.iservice.agency.ui.base.activity.BaseActivity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import lombok.SneakyThrows;

public class PromotionActivity extends BaseActivity<ActivityPromotionBinding,PromotionViewModel> {

    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setPromotionActivity(this);
        setSupportActionBar(viewBinding.foodDetailToolBar);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            String promotion = extras.getString("promotion");
            viewModel.revenueItem.setPromotion(promotion);
            Integer image = extras.getInt("image");
            viewModel.revenueItem.setImage(image);
            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            String from = extras.getString("from");
            viewModel.revenueItem.setFrom(sdf3.parse(from));
            String to = extras.getString("to");
            viewModel.revenueItem.setTo(sdf3.parse(to));
            String description = extras.getString("description");
            viewModel.revenueItem.setDescription(description);
            viewBinding.foodDetailImg.setImageResource(viewModel.revenueItem.getImage());
            ;

        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Chi tiết khuyến mãi ");
        viewBinding.setEntity(viewModel.revenueItem);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_promotion;
    }

    @Override
    public int getBindingVariable() {
        return BR.promotionViewModel;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
