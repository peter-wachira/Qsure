package com.wazinsure.qsure.Service;

import com.wazinsure.qsure.Models.CustomerRegistrationModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CustomerService {

    @GET("basic")
    Call<CustomerRegistrationModel> getCustomers(@Header("Authorization") String authHeader);


}
