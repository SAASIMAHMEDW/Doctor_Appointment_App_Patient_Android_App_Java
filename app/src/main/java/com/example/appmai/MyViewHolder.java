package com.example.appmai;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView NAME;
    TextView FOR_NAME;
    TextView FOR_EMAIL;

    Button view_more;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        NAME = itemView.findViewById(R.id.nametext);
        view_more = itemView.findViewById(R.id.view_more);
        FOR_NAME = itemView.findViewById(R.id.doctor_name);
        FOR_EMAIL = itemView.findViewById(R.id.doctor_email);
        view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void open_view_intent(View view){
        Intent view_activity = new Intent(view.getContext(), ViewActivity.class);
        view.getContext().startActivity(view_activity);
    }



}
/*
Toast.makeText(v.getContext().getApplicationContext(), FOR_EMAIL.getText().toString()+" "+FOR_NAME.getText().toString(), Toast.LENGTH_SHORT).show();
//                open_view_intent(v);
 */