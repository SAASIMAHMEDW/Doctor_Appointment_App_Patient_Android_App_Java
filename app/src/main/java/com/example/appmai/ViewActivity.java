package com.example.appmai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

public class ViewActivity extends AppCompatActivity {

    String DOC_NAME,DOC_AGE,DOC_GENDER,DOC_EMAIL,DOC_PHONE_NO,DOC_SPECIALIZATION;
    String USER_EMAIL;

    ProgressBar getting_doctor_data_progress_bar;
    LinearLayout doctor_data_layout;
    Button Take_Appointment_btn;
    TextView selected_doctor_name,selected_doctor_age,selected_doctor_gender,selected_doctor_email,selected_doctor_phoneno,selected_doctor_specialization;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        find_views_by_id();
        Intent intent = getIntent();
        DOC_EMAIL = intent.getStringExtra("DOC_EMAIL");
        USER_EMAIL = intent.getStringExtra("USER_EMAIL");
        progress_bar(false);
        get_doctaor_details();

        Take_Appointment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_make_appointment();
//                Toast.makeText(ViewActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void go_make_appointment(){
        Intent intent = new Intent(ViewActivity.this,MakeAppointmentActivity.class);
        intent.putExtra("DOC_EMAIL",DOC_EMAIL);
        intent.putExtra("USER_EMAIL",USER_EMAIL);
        startActivity(intent);
    }

    public void get_doctaor_details(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document("PROFILE");
        DR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                DOC_NAME = documentSnapshot.get("name").toString();
                DOC_AGE = documentSnapshot.get("age").toString();
                DOC_GENDER = documentSnapshot.get("gender").toString();
                DOC_SPECIALIZATION = documentSnapshot.get("doctorSpeci").toString();

                delay();
                progress_bar(true);
            }
        });
    }

    public void find_views_by_id(){
        selected_doctor_name = findViewById(R.id.selected_doctor_name);
        selected_doctor_age = findViewById(R.id.selected_doctor_age);
        selected_doctor_gender = findViewById(R.id.selected_doctor_gender);
        selected_doctor_email = findViewById(R.id.selected_doctor_email);
        selected_doctor_phoneno = findViewById(R.id.selected_doctor_phoneno);
        selected_doctor_specialization = findViewById(R.id.selected_doctor_specialization);
        getting_doctor_data_progress_bar = findViewById(R.id.getting_doctor_data_progress_bar);
        doctor_data_layout = findViewById(R.id.doctor_data_layout);
        Take_Appointment_btn = findViewById(R.id.Take_Appointment_btn);
    }

    public void set_doctor_data(){
        selected_doctor_name.setText(DOC_NAME);
        selected_doctor_age.setText(DOC_AGE);
        selected_doctor_gender.setText(DOC_GENDER);
        selected_doctor_email.setText(DOC_EMAIL);
        selected_doctor_phoneno.setText("9669966996");
        selected_doctor_specialization.setText(DOC_SPECIALIZATION);

    }

    public void delay(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                set_doctor_data();
            }
        };
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(runnable,1500);
    }

    public void progress_bar(boolean flag){
        if(flag){
            getting_doctor_data_progress_bar.setVisibility(View.GONE);
        }else {
            getting_doctor_data_progress_bar.setVisibility(View.VISIBLE);
        }
    }

}