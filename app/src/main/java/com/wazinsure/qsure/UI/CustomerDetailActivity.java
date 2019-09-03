package com.wazinsure.qsure.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.wazinsure.qsure.Adapters.CustomerPagerAdapter;
import com.wazinsure.qsure.Models.CustomerModel;
import com.wazinsure.qsure.R;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerDetailActivity extends AppCompatActivity {
    ArrayList<CustomerModel> customerModel = new ArrayList<>();
    private CustomerPagerAdapter adapterViewPager;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        ButterKnife.bind(this);

        customerModel = Parcels.unwrap(getIntent().getParcelableExtra("customerModel"));

        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new CustomerPagerAdapter(getSupportFragmentManager(), customerModel);

        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
