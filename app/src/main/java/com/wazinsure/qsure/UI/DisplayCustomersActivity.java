package com.wazinsure.qsure.UI;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.wazinsure.qsure.Adapters.CustomerListAdapter;
import com.wazinsure.qsure.Constants.Constants;
import com.wazinsure.qsure.R;
import com.wazinsure.qsure.Models.CustomerModel;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DisplayCustomersActivity extends AppCompatActivity {


    private RecyclerView recyclerView ;
    public static final String TAG = DisplayCustomersActivity.class.getSimpleName();
    ArrayList<CustomerModel> allCustomers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_customers);



        recyclerView = findViewById(R.id.recyclerview);
        getAllCustomers();
    }







    private void getAllCustomers() {
        final DisplayCustomersActivity getService = new DisplayCustomersActivity();
        getService.allCustomers(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                allCustomers =  getService.processResults(response);
                DisplayCustomersActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        setuprecyclerview(allCustomers);
                    }
                });
            }
        });
    }



    public static void allCustomers(Callback callback) {

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.CUSTOMERS + "customers").newBuilder();

        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public ArrayList<CustomerModel> processResults(Response response) {
        //create empty Arraylist that will be used to store customer details from the response
        allCustomers = new ArrayList<>();
        try {

            String jsonData = response.body().string();
            //JSONObject
            JSONObject customersJSON = new JSONObject(jsonData);
            String status = customersJSON.getString("status");
            JSONArray arrayList = customersJSON.getJSONArray("data");
            Log.v(TAG, "Response " + customersJSON.toString());

            if (status.equals("success")) {
                for (int i = 0; i < arrayList.length(); i++) {

                    JSONObject resultJSON = arrayList.getJSONObject(i);
                    String customer_id = resultJSON.getString("customer_id");
                    String id_no = resultJSON.getString("id_no");
                    String first_name = resultJSON.getString("first_name");
                    String last_name = resultJSON.getString("last_name");
                    String dob = resultJSON.getString("dob");
                    String kra_pin = resultJSON.getString("kra_pin");
                    String occupation = resultJSON.getString("occupation");
                    String mobile_no = resultJSON.getString("mobile_no");
                    String email = resultJSON.getString("email");
                    String location = resultJSON.getString("location");
                    String postal_address = resultJSON.getString("postal_address");
                    String postal_code = resultJSON.getString("postal_code");
                    String town = resultJSON.getString("town");
                    String country = resultJSON.getString("country");
                    String photo_url = resultJSON.getString("photo_url");
                    String nok_fullname = resultJSON.getString("nok_fullname");
                    String nok_mobileno = resultJSON.getString("nok_mobileno");
                    String nok_relation = resultJSON.getString("nok_relation");
                    String agent_code = resultJSON.getString("agent_code");
                    String agent_usercode = resultJSON.getString("agent_usercode");
                    String sales_channel = resultJSON.getString("sales_channel");


                    CustomerModel customerModel =  new CustomerModel( customer_id,id_no, first_name, last_name, dob, kra_pin, occupation, mobile_no, email,
                            location, postal_address, postal_code, town, country, photo_url, nok_fullname, nok_mobileno, nok_relation, agent_code, agent_usercode, sales_channel);
                    //adding customer objects to allCustomers list
                    allCustomers.add(customerModel);

                }
            }else {
                Toasty.error(getBaseContext(), "Error !", Toast.LENGTH_SHORT, true).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }


    private void setuprecyclerview(ArrayList<CustomerModel> allCustomers) {
        CustomerListAdapter myadapter = new CustomerListAdapter(this,allCustomers) ;
        recyclerView.setAdapter(myadapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DisplayCustomersActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

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