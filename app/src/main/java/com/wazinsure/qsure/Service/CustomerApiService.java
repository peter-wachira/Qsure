package com.wazinsure.qsure.Service;

import com.wazinsure.qsure.Models.CustomerModel;
import com.wazinsure.qsure.Models.CustomerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerApiService {


    @DELETE("api/customers/{id}")
    Call<CustomerResponse> delete(@Path("id") int id);

    @PUT("api/customers/{id}")
    Call<CustomerModel> update(@Path("id") int id, @Body CustomerModel Post);


}



