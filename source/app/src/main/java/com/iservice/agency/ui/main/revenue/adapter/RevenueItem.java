package com.iservice.agency.ui.main.revenue.adapter;

import androidx.databinding.Bindable;

import com.iservice.agency.BR;
import com.iservice.agency.ui.base.BaseForm;

import java.time.LocalDate;
import java.util.Date;

import lombok.Setter;

public class RevenueItem extends BaseForm {

    private String promotion;
    @Setter
    private Date from;

    @Setter
    private Date to;

    private String description;
    @Setter
    private Integer image;
    public RevenueItem() {

    }

    public RevenueItem(String promotion, Date from, Date to, String description, Integer image) {
        this.promotion = promotion;
        this.from = from;
        this.to = to;
        this.description = description;
        this.image = image;
    }

    @Bindable
    public Integer getImage() {
        return image;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public Date getFrom() {
        return from;
    }

    @Bindable
    public Date getTo() {
        return to;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
        notifyPropertyChanged(BR.promotion);
    }

    @Bindable
    public String getPromotion() {
        return promotion;
    }
}
