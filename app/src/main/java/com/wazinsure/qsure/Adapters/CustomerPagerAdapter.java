package com.wazinsure.qsure.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wazinsure.qsure.Models.CustomerModel;
import com.wazinsure.qsure.UI.CustomerDetailFragment;
import com.wazinsure.qsure.UI.CustomerUpdateFragment;

import java.util.ArrayList;

public class CustomerPagerAdapter  extends FragmentPagerAdapter {
    private ArrayList<CustomerModel> customerModel;

    public CustomerPagerAdapter(FragmentManager fm,ArrayList<CustomerModel> customerModels) {
        super(fm);
        customerModel = customerModels;
    }

    @Override
    public Fragment getItem(int position) {
        return    CustomerUpdateFragment.newInstance(customerModel.get(position));
//       return CustomerDetailFragment.newInstance(customerModel.get(position));
    }

    @Override
    public int getCount() {
      return  customerModel.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return customerModel.get(position).getCustomer_id();
    }

}
