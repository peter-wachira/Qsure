package com.wazinsure.qsure.UI;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wazinsure.qsure.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AddCustomerActivity extends AppCompatActivity {
    private static final String TAG = "AddCustomerActivity";
    Uri resultUri;

    @BindView(R.id.id_no) EditText id_noText;
    @BindView(R.id.first_name) EditText firstNameText;
    @BindView(R.id.last_name) EditText lastNameText;
    @BindView(R.id.dob) EditText dobText;
    @BindView(R.id.kra_pin)
    EditText kra_Pin;
    @BindView(R.id.occupation) EditText occupationText;
    @BindView(R.id.mobile_no) EditText mobile_noText;
    @BindView(R.id.email) EditText  emailText;
    @BindView(R.id.location) EditText locationText;
    @BindView(R.id.postal_address) EditText postal_addressText;
    @BindView(R.id.postal_code) EditText postal_codeText;
    @BindView(R.id.town) EditText townText;
    @BindView(R.id.country) EditText countryText;
    @BindView(R.id.nok_fullname) EditText nok_fullnameText;
    @BindView(R.id.nok_mobileno) EditText nok_mobilenoText;
    @BindView(R.id.nok_relation) EditText nok_relationText;
    @BindView(R.id.agent_code) EditText agent_codeText;
    @BindView(R.id.agent_usercode) EditText agent_usercodeText;
    @BindView(R.id.sales_channel) EditText sales_channelText;
    @BindView(R.id.btn_addcustomer)
    Button btn_addCustomer;
    @BindView(R.id.photo_url)
    ImageView photo_urlText;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcustomer);
        ButterKnife.bind(this);



        btn_addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addNewCustomer();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

//         picking customer image from gallery
        photo_urlText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfileImage();
            }
        });
    }

    private void chooseProfileImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            photo_urlText.setImageURI(resultUri);
        }
    }

    private void addNewCustomer() throws IOException, InterruptedException{
        String id_no = id_noText.getText().toString();
         String first_name = firstNameText.getText().toString();
        String last_name = lastNameText.getText().toString();
        String dob = dobText.getText().toString();
        String kra_pin = kra_Pin.getText().toString();
        String occupation = occupationText.getText().toString();
        String mobile_no = mobile_noText.getText().toString();
        String email = emailText.getText().toString();
        String location = locationText.getText().toString();
        String postal_address = postal_addressText.getText().toString();
        String postal_code = postal_codeText.getText().toString();
        String town = townText.getText().toString();
        String country= countryText.getText().toString();
        String nok_fullname = nok_fullnameText.getText().toString();
        String nok_mobileno = nok_mobilenoText.getText().toString();
        String nok_relation = nok_relationText.getText().toString();
        String agent_code = agent_codeText.getText().toString();
        String agent_usercode = agent_usercodeText.getText().toString();
        String sales_channel = sales_channelText.getText().toString();



        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Adding customer...");
        progressDialog.show();
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        try {
                            addNewCustomerRequest( first_name,  last_name, id_no,  dob,  kra_pin,  occupation,  mobile_no,  email,  location,  postal_address,  postal_code,  town,  country, resultUri ,  nok_fullname,  nok_mobileno,  nok_relation,  agent_code, agent_usercode, sales_channel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },4000);



    }


    public void onCustomerAddSuccess() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toasty.success(getBaseContext(), "Customer added Successfully!", Toast.LENGTH_SHORT, true).show();

            }
        });
        Intent intent = new Intent( this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onCustomerAddFailed() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toasty.warning(getBaseContext(), "Customer add Failed", Toast.LENGTH_SHORT, true).show();

            }
        });
        btn_addCustomer.setEnabled(true);
    }

    //    adding  a  new customer
    public void addNewCustomerRequest(String first_name, String last_name, String id_no, String dob, String kra_pin, String occupation, String mobile_no, String email, String location, String postal_address, String postal_code, String town, String country, Uri photo_url, String nok_fullname, String nok_mobileno, String nok_relation, String agent_code, String agent_usercode, String sales_channel) throws IOException {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url ="https://demo.wazinsure.com:4443/api/customers";
        OkHttpClient client = new OkHttpClient();
        JSONObject postData = new JSONObject();
        try {
            postData.put("first_name", first_name);
            postData.put("last_name", last_name);
            postData.put("id_no", id_no);
            postData.put("dob", dob);
            postData.put("kra_pin", kra_pin);
            postData.put("occupation", occupation);
            postData.put("mobile_no", mobile_no);
            postData.put("email", email);
            postData.put("location", location);
            postData.put("postal_address", postal_address);
            postData.put("postal_code", postal_code);
            postData.put("town", town);
            postData.put("country", country);
            postData.put("photo_url", photo_url);
            postData.put("nok_fullname", nok_fullname);
            postData.put("nok_mobileno", nok_mobileno);
            postData.put("nok_relation", nok_relation);
            postData.put("agent_code", agent_code);
            postData.put("agent_usercode",agent_usercode);
            postData.put("sales_channel", sales_channel);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //creating a new post request
        RequestBody body = RequestBody.create(MEDIA_TYPE, postData.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                Log.e(TAG, mMessage);

                try {
                    JSONObject responseJSON = new JSONObject(mMessage);

                    String customerAddStatus = responseJSON.getString("status");
                    String status = customerAddStatus;
                    if (status.equals("success")){
                        onCustomerAddSuccess();
                    }
                    else if (status!="success"){
                        onCustomerAddFailed();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

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








