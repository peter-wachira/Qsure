package com.wazinsure.qsure.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerResponse {

    @SerializedName("data")
    private List<CustomerModel> data = null;

    public List<CustomerModel> getData() {
        return data;
    }

    public void setData(List<CustomerModel> data) {
        this.data = data;
    }
}
