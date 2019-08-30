package com.wazinsure.qsure.Adapters;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  {

    private Context mContext;
    private List<CustomerModel> mData;
    RequestOptions option;


    public RecyclerViewAdapter(Context mContext,List<CustomerModel> mData){
        this.mContext = mContext;
        this.mData = mData;

        //Request options for Glide

//        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.customer_item,parent,false);

        return  new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.first_name.setText(mData.get(position).getFirst_name());
        holder.last_name.setText(mData.get(position).getLast_name());
        holder.dob.setText(mData.get(position).getDob());
        holder.kra_pin.setText(mData.get(position).getKra_pin());
        holder.occupation.setText(mData.get(position).getOccupation());
        holder.mobile_no.setText(mData.get(position).getMobile_no());
        holder.email.setText(mData.get(position).getEmail());
        holder.location.setText(mData.get(position).getLocation());

        //setting up glide to retrieve images  from the internet and insert them to the image thunbnail
        Glide.with(mContext).load(mData.get(position).getPhoto_url()).apply(option).into(holder.photo_url);


    }

    @Override
    public int getItemCount() {
       return mData.size();
    }


    public static class  MyViewHolder extends RecyclerView.ViewHolder{


        TextView first_name;
        TextView last_name;
        TextView dob;
        TextView kra_pin;
        TextView occupation;
        TextView mobile_no;
        TextView email;
        TextView location;
        TextView postal_address;
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
            location = itemView.findViewById(R.id.location);
//            postal_address = itemView.findViewById(R.id.);
//            photo_url = itemView.findViewById(R.id.);
//            nok_fullname = itemView.findViewById(R.id.);
//            nok_mobileno = itemView.findViewById(R.id.);
//            nok_relation = itemView.findViewById(R.id.);
//            agent_code = itemView.findViewById(R.id.);


        }
    }
}
