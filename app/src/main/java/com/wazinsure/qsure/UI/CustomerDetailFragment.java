package com.wazinsure.qsure.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.wazinsure.qsure.Models.CustomerModel;
import com.wazinsure.qsure.Models.CustomerResponse;
import com.wazinsure.qsure.R;
import com.wazinsure.qsure.Service.APIUtils;
import com.wazinsure.qsure.Service.CustomerApiService;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

public class CustomerDetailFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.first_nameProfile)
    TextView firstNameProfile;
    @BindView(R.id.last_nameProfile) TextView lastNameProfile;
    @BindView(R.id.dobProfile) TextView dobProfile;
    @BindView(R.id.kra_pinProfile)
    TextView kra_PinProfile;
    @BindView(R.id.occupationProfile) TextView occupationProfile;
    @BindView(R.id.mobile_noProfile) TextView mobile_noProfile;
    @BindView(R.id.emailProfile) TextView  emailProfile;
    @BindView(R.id.locationProfile) TextView locationProfile;
    @BindView(R.id.postal_addressProfile) TextView postal_addressProfile;
    @BindView(R.id.postal_codeProfile) TextView postal_codeProfile;
    @BindView(R.id.townProfile) TextView townProfile;
    @BindView(R.id.photo_urlProfile) ImageView imageViewProfile;
    @BindView(R.id.countryProfile) TextView countryProfile;
    @BindView(R.id.nok_fullnameProfile) TextView nok_fullnameProfile;
    @BindView(R.id.nok_mobilenoProfile) TextView nok_mobilenoProfile;
    @BindView(R.id.nok_relationProfile) TextView nok_relationProfile;
    @BindView(R.id.agent_codeProfiile) TextView agent_codeProfile;
    @BindView(R.id.id_noProfile) TextView id_noProfile;
    @BindView(R.id.customer_idProfile) TextView customerIdProfile;

    @BindView(R.id.agent_usercodeProfile) TextView agent_usercodeProfile;
    @BindView(R.id.sales_channelProfile) TextView sales_channelProfile;
    @BindView(R.id.btn_update)
    Button btn_updateProfile;
    @BindView(R.id.btn_delete)
    Button btn_deleteProfile;
    CustomerApiService customerApiService;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerModel = Parcels.unwrap(getArguments().getParcelable("customerModel"));

        customerApiService = APIUtils.getCustomerApiService();


    }

//    private OnFragmentInteractionListener mListener;
    private CustomerModel customerModel;


    public CustomerDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CustomerDetailFragment newInstance(CustomerModel customerModel) {
        CustomerDetailFragment customerDetailFragment = new CustomerDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("customerModel", Parcels.wrap(customerModel));
        customerDetailFragment.setArguments(args);
        return customerDetailFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_detail, container, false);
        ButterKnife.bind(this, view);


        btn_updateProfile.setOnClickListener(this);
        btn_deleteProfile.setOnClickListener(this);



        firstNameProfile.setText(customerModel.getFirst_name());
        lastNameProfile.setText(customerModel.getLast_name());
//        dobProfile.setText(customerModel.getDob().);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = fmt.parse(customerModel.getDob());

            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
            String newdate= fmtOut.format(date);

            dobProfile.setText(newdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }




        id_noProfile.setText(customerModel.getId_no());

        kra_PinProfile.setText(customerModel.getKra_pin());

        occupationProfile.setText(customerModel.getOccupation());

        mobile_noProfile.setText(customerModel.getMobile_no());

        emailProfile.setText(customerModel.getEmail());


        customerIdProfile.setText(customerModel.getCustomer_id());

        locationProfile.setText(customerModel.getLocation());


        postal_addressProfile.setText(customerModel.getPostal_address());

        postal_codeProfile.setText(customerModel.getPostal_code());

        countryProfile.setText(customerModel.getCountry());

//        imageViewProfile.setImageResource(customerModel.getPhoto_url());

        nok_fullnameProfile.setText(customerModel.getNok_fullname());

        nok_mobilenoProfile.setText(customerModel.getNok_mobileno());

        nok_relationProfile.setText(customerModel.getNok_relation());


        agent_codeProfile.setText(customerModel.getAgent_code());

        agent_usercodeProfile.setText(customerModel.getAgent_usercode());

        sales_channelProfile.setText(customerModel.getSales_chanel());

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == btn_updateProfile){

            CustomerUpdateFragment customerUpdateFragment = new CustomerUpdateFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentCustomerDetails, customerUpdateFragment);
            fragmentTransaction.commit();

        }
        if (view == btn_deleteProfile){
            deleteUser(Integer.parseInt(customerModel.getCustomer_id()));
        }
    }

    private void deleteUser(int id) {
        retrofit2.Call<CustomerResponse> call = customerApiService.delete(id);
        call.enqueue(new Callback<CustomerResponse>() {
            JsonObject jsonResponse = null;

            @Override
            public void onResponse(Call<CustomerResponse> call, retrofit2.Response<CustomerResponse> response) {

             String status = response.body().toString();

                if(response.isSuccessful()){
                    onCustomerDeleteSuccess();
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                Log.e("TAG", t.toString());
                onCustomerDeleteFail();

            }
        });
    }

    public void onCustomerDeleteSuccess() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toasty.success(getContext(), "Customer Deleted Successfully!", Toast.LENGTH_SHORT, true).show();

            }
        });
        Intent intent = new Intent( getContext(), DisplayCustomersActivity.class);
        startActivity(intent);
    }


    public void onCustomerDeleteFail() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toasty.error(getContext(), "Customer Delete Failed!", Toast.LENGTH_SHORT, true).show();

            }
        });
        Intent intent = new Intent( getContext(), DisplayCustomersActivity.class);
        startActivity(intent);
    }
}

