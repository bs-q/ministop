package com.iservice.agency.ui.main.activity.adapter;

import androidx.databinding.Bindable;

import com.iservice.agency.BR;
import com.iservice.agency.ui.base.BaseForm;

import java.time.LocalDate;

public class ActivityItem extends BaseForm {

    private String type;
    private Integer image;

    public ActivityItem(String type, Integer image) {
        this.type = type;
        this.image = image;
    }

    public ActivityItem() {

    }

    void setType(String type){
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getType(){
        return type;
    }

    void setImage(Integer image){
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public Integer getImage(){
        return image;
    }
}
