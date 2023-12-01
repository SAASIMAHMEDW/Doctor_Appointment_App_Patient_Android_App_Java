package com.example.appmai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListFragmentRecyclerViewAdapter extends RecyclerView.Adapter<ListFragmentRecyclerViewAdapter.MyViewHolder> {

    private final ViewMoreInterfaceOfRecyclerView view_more_interface_of_recyler_viewx;
    private final CallDoctorInterface callDoctorInterface;
    public ListFragmentRecyclerViewAdapter(Context context, List<ListFragmentRecyclerViewAdapterModel> items, ViewMoreInterfaceOfRecyclerView view_more_interface_of_recyler_viewx, CallDoctorInterface callDoctorInterface) {
        this.context = context;
        this.items = items;
        this.view_more_interface_of_recyler_viewx = view_more_interface_of_recyler_viewx;
        this.callDoctorInterface = callDoctorInterface;
    }

    Context context;
    List<ListFragmentRecyclerViewAdapterModel> items;



    @NonNull
    @Override
    public ListFragmentRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.available_doctor_list_recycler_view,parent,false);
        return new ListFragmentRecyclerViewAdapter.MyViewHolder(view, view_more_interface_of_recyler_viewx,callDoctorInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       holder.DOC_NAME.setText(items.get(position).getDoc_name());
       holder.DOC_EMAIL.setText(items.get(position).getDoc_email());
       holder.DOC_PHONENO.setText(items.get(position).getDoc_phoneNo());
       holder.DOC_GENDER.setText(items.get(position).getDoc_gender());
        String url = items.get(position).getDoc_profile_pic_url();
        Picasso.get().load(url).into(holder.DOC_PROFILE_PIC);
//       Thread thread = new Thread(() -> {
//           String url = items.get(position).getDoc_profile_pic_url();
//           Picasso.get().load(url).into(holder.DOC_PROFILE_PIC);
//       });thread.start();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

   public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView NAME;
        TextView DOC_NAME,DOC_EMAIL,DOC_PHONENO,DOC_GENDER;
        ImageView doctor_phoneNo_img;
        CircleImageView DOC_PROFILE_PIC;

        Button view_more;

        public MyViewHolder(@NonNull View itemView, ViewMoreInterfaceOfRecyclerView view_more_interface_of_recyler_viewx, CallDoctorInterface callDoctorInterface) {
            super(itemView);
//            NAME = itemView.findViewById(R.id.nametext);
            view_more = itemView.findViewById(R.id.view_more);
            DOC_NAME = itemView.findViewById(R.id.doctor_name);
            DOC_EMAIL = itemView.findViewById(R.id.doctor_email);
            DOC_PHONENO = itemView.findViewById(R.id.doctor_phoneNo);
            DOC_GENDER = itemView.findViewById(R.id.doctor_gender);
            DOC_PROFILE_PIC = itemView.findViewById(R.id.doctor_profile_pic);
            doctor_phoneNo_img = itemView.findViewById(R.id.doctor_phoneNo_img);
            view_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (view_more_interface_of_recyler_viewx != null){
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION){
                                view_more_interface_of_recyler_viewx.on_recycler_button_click(position);
                            }
                        }
                }
            });
            doctor_phoneNo_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (view_more_interface_of_recyler_viewx != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            callDoctorInterface.on_recycler_callIMG_click(position);
                        }
                    }
//                    Toast.makeText(itemView.getContext(),DOC_PHONENO.getText().toString() , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
