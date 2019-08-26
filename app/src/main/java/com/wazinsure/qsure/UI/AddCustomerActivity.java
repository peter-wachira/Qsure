package com.wazinsure.qsure.UI;


import android.os.Bundle;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.wazinsure.qsure.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddCustomerActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.first_name) EditText firstName;
    @BindView(R.id.last_name) EditText lastName;
    @BindView(R.id.dob) EditText dob;
    @BindView(R.id.kra_pin)
    EditText kra_pin;
    @BindView(R.id.occupation) EditText occupation;
    @BindView(R.id.mobile_no) EditText mobile_no;
    @BindView(R.id.email) EditText  email;
    @BindView(R.id.location) EditText location;
    @BindView(R.id.postal_address) EditText postal_address;
    @BindView(R.id.postal_code) EditText postal_code;
    @BindView(R.id.town) EditText town;
    @BindView(R.id.country) EditText country;
    @BindView(R.id.nok_fullname) EditText nok_fullname;
    @BindView(R.id.nok_mobileno) EditText nok_mobileno;
    @BindView(R.id.nok_relation) EditText nok_relation;
    @BindView(R.id.agent_code) EditText agent_code;
    @BindView(R.id.btn_addcustomer) EditText btn_addCustomer;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcustomer);
        ButterKnife.bind(this);




    }








}