package com.example.appmai;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdopter extends RecyclerView.Adapter<MyAdopter.MyViewHolder> {

    private final view_more_interface_of_recyler_view view_more_interface_of_recyler_viewx;
    public MyAdopter(Context context, List<Home_info> items,view_more_interface_of_recyler_view view_more_interface_of_recyler_viewx) {
        this.context = context;
        this.items = items;
        this.view_more_interface_of_recyler_viewx = view_more_interface_of_recyler_viewx;
    }

    Context context;
    List<Home_info> items;



    @NonNull
    @Override
    public MyAdopter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.info_of_home,parent,false);
        return new MyAdopter.MyViewHolder(view, view_more_interface_of_recyler_viewx);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       holder.FOR_NAME.setText(items.get(position).getEdit_text_name());
       holder.FOR_EMAIL.setText(items.get(position).getDoctor_email());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

   public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView NAME;
        TextView FOR_NAME;
        TextView FOR_EMAIL;

        Button view_more;

        public MyViewHolder(@NonNull View itemView, view_more_interface_of_recyler_view view_more_interface_of_recyler_viewx) {
            super(itemView);
            NAME = itemView.findViewById(R.id.nametext);
            view_more = itemView.findViewById(R.id.view_more);
            FOR_NAME = itemView.findViewById(R.id.doctor_name);
            FOR_EMAIL = itemView.findViewById(R.id.doctor_email);
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

        }

        public void open_view_intent(View view){
            Intent view_activity = new Intent(view.getContext(), ViewActivity.class);
            view.getContext().startActivity(view_activity);
        }



    }
}
