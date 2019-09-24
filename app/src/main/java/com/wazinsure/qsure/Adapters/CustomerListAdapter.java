package com.wazinsure.qsure.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.widget.Filter;
import android.widget.Filterable;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder>  implements Filterable {

    private ArrayList<CustomerModel> mCustomers = new ArrayList<>();
    ArrayList<CustomerModel> mDataFiltrered;

    boolean  isDark = false;

    RequestOptions option;

    Context mContext;




    public  CustomerListAdapter(Context mContext,ArrayList<CustomerModel> mCustomers,boolean isDark){
        this.mContext = mContext;
        this.mCustomers = mCustomers;
        this.isDark = isDark;
        this.mDataFiltrered = mCustomers;

    }


    public  CustomerListAdapter(Context mContext,ArrayList<CustomerModel> mCustomers){
        this.mContext =mContext;
        this.mCustomers = mCustomers;
        this.isDark = isDark;
        this.mDataFiltrered = mCustomers;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.customer_item, parent, false);

        return  new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder customerViewHolder, int position) {

        customerViewHolder.bindCustomer(mCustomers.get(position));
        customerViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
    }


    @Override
    public int getItemCount() {
       return mDataFiltrered.size();
    }




    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if(Key.isEmpty()){
                    mDataFiltrered = mCustomers;
                }else{
                    ArrayList<CustomerModel> lstFiltered = new ArrayList<>();
                    for (CustomerModel row: mCustomers){
                        if (row.getFirst_name().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }
                    }
                    mDataFiltrered = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltrered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                mDataFiltrered = (ArrayList<CustomerModel>) results.values;
                notifyDataSetChanged();
            }
        };
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
        RelativeLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            first_name = itemView.findViewById(R.id.first_nameItem);
            last_name = itemView.findViewById(R.id.last_nameItem);
            photo_url = itemView.findViewById(R.id.photo_url);
            container = itemView.findViewById(R.id.container);
            mobile_no = itemView.findViewById(R.id.mobile_numberItem);

            id_no = itemView.findViewById(R.id.id_numberItem);

            location = itemView.findViewById(R.id.locationItem);

            if (isDark) {
                setDarkTheme();
            }
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);

        }


        private void setDarkTheme (){
            container.setBackgroundResource(R.drawable.card_bg_dark);
        }

        public void bindCustomer(CustomerModel customer) {
            first_name.setText(customer.getFirst_name());
            last_name.setText(customer.getLast_name());


//            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                Date date = fmt.parse(customer.getDob());
//
//                SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
//                String newdate= fmtOut.format(date);
//
//                dob.setText(newdate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

//            kra_pin.setText(customer.getKra_pin());
//            occupation.setText(customer.getOccupation());
            mobile_no.setText(customer.getMobile_no());
//            email.setText(customer.getEmail());
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
