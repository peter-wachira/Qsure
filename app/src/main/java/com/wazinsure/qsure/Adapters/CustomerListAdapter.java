package com.wazinsure.qsure.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wazinsure.qsure.Models.CustomerModel;
import com.wazinsure.qsure.R;
import com.wazinsure.qsure.UI.CustomerDetailActivity;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder>  {

    private ArrayList<CustomerModel> mCustomers = new ArrayList<>();
    RequestOptions option;

    Context mContext;




    public  CustomerListAdapter(Context context,ArrayList<CustomerModel> customers){
        mContext =context;
        mCustomers = customers;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        view = inflater.inflate(R.layout.customer_item,parent,false);

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);

        return  new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {




        holder.bindCustomer(mCustomers.get(position));


    }


    @Override
    public int getItemCount() {
       return mCustomers.size();
    }


    public class  MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{


        TextView first_name;
        TextView last_name;
        TextView dob;
        TextView kra_pin;
        TextView occupation;
        TextView mobile_no;
        TextView email;
        TextView location;
        TextView postal_address;
        TextView id_no;
        TextView town;
        TextView country;
        ImageView photo_url;
        TextView nok_fullname;
        TextView nok_mobileno;
        TextView nok_relation;
        TextView agent_code;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            first_name = itemView.findViewById(R.id.first_nameItem);
            last_name = itemView.findViewById(R.id.last_nameItem);
            dob = itemView.findViewById(R.id.dobItem);
            kra_pin = itemView.findViewById(R.id.kra_pinItem);
            occupation = itemView.findViewById(R.id.occupationItem);
            mobile_no = itemView.findViewById(R.id.mobile_numberItem);
            email = itemView.findViewById(R.id.emailItem);
            photo_url = itemView.findViewById(R.id.photo_url);
            id_no = itemView.findViewById(R.id.id_numberItem);

            location = itemView.findViewById(R.id.locationItem);
//            postal_address = itemView.findViewById(R.id.);
//            photo_url = itemView.findViewById(R.id.);
//            nok_fullname = itemView.findViewById(R.id.);
//            nok_mobileno = itemView.findViewById(R.id.);
//            nok_relation = itemView.findViewById(R.id.);
//            agent_code = itemView.findViewById(R.id.);

            mContext = itemView.getContext();
            itemView.setOnClickListener(this);

        }

        public void bindCustomer(CustomerModel customer) {
            first_name.setText(customer.getFirst_name());
            last_name.setText(customer.getLast_name());
            dob.setText(customer.getDob());
            kra_pin.setText(customer.getKra_pin());
            occupation.setText(customer.getOccupation());
            mobile_no.setText(customer.getMobile_no());
            email.setText(customer.getEmail());
            location.setText(customer.getLocation());
            id_no.setText(customer.getId_no());


        }
        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, CustomerDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("customerModel", Parcels.wrap(mCustomers));
            mContext.startActivity(intent);
        }

    }
}
