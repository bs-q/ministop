package com.iservice.agency.ui.main.revenue.adapter;

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
import com.iservice.agency.databinding.RevenueListItemBinding;
import com.iservice.agency.ui.food.FoodActivity;
import com.iservice.agency.ui.promotion.PromotionActivity;

import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.RevenueItemViewHolder> {
    private List<RevenueItem> revenueItems;
    private Context context;

    public RevenueAdapter(List<RevenueItem> revenueItems, Context context) {
        this.revenueItems = revenueItems;
        this.context = context;
    }

    @NonNull
    @Override
    public RevenueItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RevenueListItemBinding revenueListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.revenue_list_item, parent, false);
        return new RevenueItemViewHolder(revenueListItemBinding, new ActivityClickListener() {
            @Override
            public void click(int position) {
                Intent it = new Intent(context, PromotionActivity.class);
                it.putExtra("promotion",revenueItems.get(position).getPromotion());
                it.putExtra("from",revenueItems.get(position).getFrom().toString());
                it.putExtra("to",revenueItems.get(position).getTo().toString());
                it.putExtra("image",revenueItems.get(position).getImage());
                it.putExtra("description",revenueItems.get(position).getDescription());
                context.startActivity(it);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueItemViewHolder holder, int position) {
        RevenueItem revenueItem = revenueItems.get(position);
        holder.revenueListItemBinding.setRevenueItem(revenueItem);
        holder.revenueListItemBinding.promotionImg.setImageResource(revenueItem.getImage());
        holder.revenueListItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return revenueItems.size();
    }

    public class RevenueItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RevenueListItemBinding revenueListItemBinding;
        private ActivityClickListener activityClickListener;
        public RevenueItemViewHolder(RevenueListItemBinding revenueListItemBinding, ActivityClickListener activityClickListener) {
            super(revenueListItemBinding.getRoot());
            this.revenueListItemBinding = revenueListItemBinding;
            this.activityClickListener= activityClickListener;
            revenueListItemBinding.getRoot().setOnClickListener(this);
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
