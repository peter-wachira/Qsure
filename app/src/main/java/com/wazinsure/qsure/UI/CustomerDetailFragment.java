package com.wazinsure.qsure.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wazinsure.qsure.Models.CustomerModel;
import com.wazinsure.qsure.R;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerDetailFragment extends Fragment {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerModel = Parcels.unwrap(getArguments().getParcelable("customerModel"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_detail, container, false);
        ButterKnife.bind(this, view);

        firstNameProfile.setText(customerModel.getFirst_name());
        lastNameProfile.setText(customerModel.getLast_name());
        dobProfile.setText(customerModel.getDob());

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

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }


//    public interface OnFragmentInteractionListener {
//
//    }
}
