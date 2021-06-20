package com.iservice.agency.ui.main.home.fragment.statistic;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.iservice.agency.ui.base.BaseForm;


public class StatisticForm extends BaseForm {
    private String accept;
    private String deny;
    private String rate;

    public StatisticForm(String accept, String deny, String rate) {
        this.accept = accept;
        this.deny = deny;
        this.rate = rate;
    }

    @Bindable
    public String getAccept() {
        return accept;
    }

    @Bindable
    public String getDeny() {
        return deny;
    }

    @Bindable
    public String getRate() {
        return rate;
    }

    public void setAccept(String accept) {
        this.accept = accept;
        notifyPropertyChanged(BR.accept);
    }

    public void setDeny(String deny) {
        this.deny = deny;
        notifyPropertyChanged(BR.deny);

    }

    public void setRate(String rate) {
        this.rate = rate;
        notifyPropertyChanged(BR.rate);
    }
}
