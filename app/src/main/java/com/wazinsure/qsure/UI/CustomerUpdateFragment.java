package com.wazinsure.qsure.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wazinsure.qsure.Adapters.CustomerPagerAdapter;
import com.wazinsure.qsure.Models.CustomerModel;
import com.wazinsure.qsure.Models.CustomerResponse;
import com.wazinsure.qsure.R;
import com.wazinsure.qsure.Service.APIUtils;
import com.wazinsure.qsure.Service.CustomerApiService;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class CustomerUpdateFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.id_no)
    EditText id_noText;
    @BindView(R.id.first_name) EditText firstNameText;
    @BindView(R.id.last_name) EditText lastNameText;
    @BindView(R.id.dob) EditText dobText;
    @BindView(R.id.kra_pin)
    EditText kra_PinText;
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
    @BindView(R.id.btn_updateCustomer)
    Button btn_updateText;
    @BindView(R.id.photo_url)
    ImageView photo_urlText;
    String CustomerID;
    CustomerApiService customerApiService;
    private JSONObject updateJSON;
    Uri resultUri;
    private static final String TAG = "CustomerUpdateFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerModel = Parcels.unwrap(getArguments().getParcelable("customerModel"));
        customerApiService = APIUtils.getCustomerApiService();
        CustomerID = customerModel.getCustomer_id();
    }

    public static CustomerUpdateFragment newInstance(CustomerModel customerModel) {
        CustomerUpdateFragment customerUpdateFragment = new CustomerUpdateFragment();
        Bundle args = new Bundle();
        args.putParcelable("customerModel", Parcels.wrap(customerModel));
        customerUpdateFragment.setArguments(args);
        return customerUpdateFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_update, container, false);
        ButterKnife.bind(this, view);
        btn_updateText.setOnClickListener(this);
        //         picking customer image from gallery
        photo_urlText.setOnClickListener(v -> chooseProfileImage());

        container.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fade_scale_animation));

        firstNameText.setText(customerModel.getFirst_name());
        lastNameText.setText(customerModel.getLast_name());
//        dobProfile.setText(customerModel.getDob().);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = fmt.parse(customerModel.getDob());

            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
            String newdate= fmtOut.format(date);

            dobText.setText(newdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }





        id_noText.setText(customerModel.getId_no());

        kra_PinText.setText(customerModel.getKra_pin());

        occupationText.setText(customerModel.getOccupation());

        mobile_noText.setText(customerModel.getMobile_no());

        emailText.setText(customerModel.getEmail());



        locationText.setText(customerModel.getLocation());


        postal_addressText.setText(customerModel.getPostal_address());

        postal_codeText.setText(customerModel.getPostal_code());

        countryText.setText(customerModel.getCountry());

//        imageViewProfile.setImageResource(customerModel.getPhoto_url());

        nok_fullnameText.setText(customerModel.getNok_fullname());

        nok_mobilenoText.setText(customerModel.getNok_mobileno());

        nok_relationText.setText(customerModel.getNok_relation());


        agent_codeText.setText(customerModel.getAgent_code());

        agent_usercodeText.setText(customerModel.getAgent_usercode());

        sales_channelText.setText(customerModel.getSales_chanel());

        return view;
    }




    private CustomerModel customerModel;

    public CustomerUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View view) {
        if (view == btn_updateText){
            try {
                updateCustomerDetails();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    private void chooseProfileImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            photo_urlText.setImageURI(resultUri);
        }
    }


    private void updateCustomerDetails() throws IOException, InterruptedException{
        String id_no = id_noText.getText().toString();
        String first_name = firstNameText.getText().toString();
        String last_name = lastNameText.getText().toString();
        String dob = dobText.getText().toString();
        String kra_pin = kra_PinText.getText().toString();
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



        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Updating customer...");
        progressDialog.show();
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        try {
                            updateCustomerRequest( first_name,  last_name, id_no,  dob,  kra_pin,  occupation,  mobile_no,  email,  location,  postal_address,  postal_code,  town,  country, resultUri ,  nok_fullname,  nok_mobileno,  nok_relation,  agent_code, agent_usercode, sales_channel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },4000);
    }

    //    adding  a  new customer
    public void updateCustomerRequest(String first_name, String last_name, String id_no, String dob, String kra_pin, String occupation, String mobile_no, String email, String location, String postal_address, String postal_code, String town, String country, Uri photo_url, String nok_fullname, String nok_mobileno, String nok_relation, String agent_code, String agent_usercode, String sales_channel) throws IOException {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url ="https://demo.wazinsure.com:4443/api/customers/" + CustomerID ;
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
                .put(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
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
                        onCustomerUpdateSuccess();
                    }
                    else if (status!="success"){
                        onCustomerUpdateFailed();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void onCustomerUpdateSuccess() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toasty.success(getContext(), "Customer Updated Successfully!", Toast.LENGTH_SHORT, true).show();

            }
        });
    }

    public void onCustomerUpdateFailed() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toasty.success(getContext(), "Customer Update Failed!", Toast.LENGTH_SHORT, true).show();
            }
        });
    }













}



