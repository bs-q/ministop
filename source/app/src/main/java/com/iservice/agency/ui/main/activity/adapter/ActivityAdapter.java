package com.iservice.agency.ui.main.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.iservice.agency.R;
import com.iservice.agency.databinding.ActivityListItemBinding;
import com.iservice.agency.ui.food.FoodActivity;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityItemViewHolder> {
    private List<ActivityItem> activityItems;
    private Context context;

    public ActivityAdapter(List<ActivityItem> activityItems, Context context) {
        this.activityItems = activityItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ActivityItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityListItemBinding activityListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.activity_list_item, parent, false);
        return new ActivityItemViewHolder(activityListItemBinding, new ActivityClickListener() {
            @Override
            public void click(int position) {
                Intent it = new Intent(context, FoodActivity.class);
                it.putExtra("CATEGORY",activityItems.get(position).getType());
                context.startActivity(it);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityItemViewHolder holder, int position) {
        ActivityItem activityItem = activityItems.get(position);
        holder.activityListItemBinding.setActivityItem(activityItem);
        holder.activityListItemBinding.foodCategoryImg.setImageResource(activityItem.getImage());
        holder.activityListItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return activityItems.size();
    }

    public class ActivityItemViewHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener {

        private ActivityListItemBinding activityListItemBinding;
        private ActivityClickListener activityClickListener;

        public ActivityItemViewHolder(ActivityListItemBinding activityListItemBinding,ActivityClickListener activityClickListener) {
            super(activityListItemBinding.getRoot());
            activityListItemBinding.getRoot().setOnClickListener(this);
            this.activityListItemBinding = activityListItemBinding;
            this.activityClickListener = activityClickListener;
        }

        @Override
        public void onClick(View v) {
            activityClickListener.click(getAdapterPosition());
        }
    }
    public interface ActivityClickListener{
        void click(int position);
    }
}
