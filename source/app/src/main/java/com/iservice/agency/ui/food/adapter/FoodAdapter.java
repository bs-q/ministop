package com.iservice.agency.ui.food.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.iservice.agency.R;
import com.iservice.agency.data.model.db.FoodEntity;
import com.iservice.agency.databinding.FoodListItemBinding;
import com.iservice.agency.ui.food.FoodActivity;
import com.iservice.agency.ui.food.detail.FoodDetailActivity;
import com.iservice.agency.utils.ImageUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.Setter;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodItemViewHolder> {

    @Setter
    private List<FoodEntity> foodEntityList;
    private Context context;

    public FoodAdapter(List<FoodEntity> foodEntityList, Context context) {
        this.foodEntityList = foodEntityList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodListItemBinding foodListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.food_list_item, parent, false);
        return new FoodItemViewHolder(foodListItemBinding, position -> {
            Intent it = new Intent(context, FoodDetailActivity.class);
            it.putExtra("name",foodEntityList.get(position).getName());
            it.putExtra("image",foodEntityList.get(position).getImage());
            it.putExtra("description",foodEntityList.get(position).getDescription());
            it.putExtra("price",foodEntityList.get(position).getPrice());
            context.startActivity(it);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodItemViewHolder holder, int position) {
        FoodEntity entity = foodEntityList.get(position);
        holder.foodListItemBinding.setEntity(entity);
        InputStream stream = new ByteArrayInputStream(Base64.decode(entity.getImage().getBytes(), Base64.DEFAULT));
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        holder.foodListItemBinding.foodImage.setImageBitmap(bitmap);
        holder.foodListItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return foodEntityList.size();
    }

    public class FoodItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private FoodListItemBinding foodListItemBinding;
        private FoodClickListener foodClickListener;

        public FoodItemViewHolder(@NonNull FoodListItemBinding foodListItemBinding, FoodClickListener foodClickListener) {
            super(foodListItemBinding.getRoot());
            this.foodListItemBinding = foodListItemBinding;
            this.foodClickListener = foodClickListener;
            foodListItemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            foodClickListener.click(getAdapterPosition());
        }
    }

    public interface FoodClickListener {
        void click(int position);
    }
}
