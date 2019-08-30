package com.wazinsure.qsure.UI;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.wazinsure.qsure.R;
import com.wazinsure.qsure.Adapters.RecyclerViewAdapter;
import com.wazinsure.qsure.Models.CustomerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


public class DisplayCustomersActivity extends AppCompatActivity {

    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<CustomerModel> lstCustomer ;
    private RecyclerView recyclerView ;
    private final String JSON_URL ="https://demo.wazinsure.com:4443/api/customers";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_customers);


        lstCustomer = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerview);
        jsonrequest();

    }



    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);
                        CustomerModel customerModel = new CustomerModel();
                        customerModel.setFirst_name(jsonObject.getString("first_name"));
                        customerModel.setLast_name(jsonObject.getString("last_name"));
                        customerModel.setDob(jsonObject.getString("dob"));
                        customerModel.setKra_pin(jsonObject.getString("kra_pin"));
                        customerModel.setOccupation(jsonObject.getString("occupation"));
                        customerModel.setMobile_no(jsonObject.getString("mobile_no"));
                        customerModel.setEmail(jsonObject.getString("email"));
                        customerModel.setLocation(jsonObject.getString("location"));
                        customerModel.setPostal_address(jsonObject.getString("postal_address"));
                        customerModel.setPostal_code(jsonObject.getString("postal_code"));
                        customerModel.setTown(jsonObject.getString("town"));
                        customerModel.setCountry(jsonObject.getString("country"));
                        customerModel.setPhoto_url(jsonObject.getString("photo_url"));
                        customerModel.setNok_fullname(jsonObject.getString("nok_fullname"));
                        customerModel.setNok_mobileno(jsonObject.getString("nok_mobileno"));
                        customerModel.setNok_relation(jsonObject.getString("nok_relation"));
                        customerModel.setAgent_code(jsonObject.getString("agent_code"));
                        customerModel.setAgent_usercode(jsonObject.getString("agent_usercode"));
                        customerModel.setSales_chanel(jsonObject.getString("sales_channel"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                setuprecyclerview(lstCustomer);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(DisplayCustomersActivity.this);
        requestQueue.add(request);


    }

    private void setuprecyclerview(List<CustomerModel> lstCustomer) {


        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,lstCustomer) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
