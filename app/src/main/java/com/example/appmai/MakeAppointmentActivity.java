package com.example.appmai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MakeAppointmentActivity extends AppCompatActivity {

    String DOC_EMAIL,DOC_NAME,DOC_PHONENO="9669966996";

    String USER_EMAIL,USER_PROBLEM,USER_NAME,USER_GENDER,USER_PHONE_NO,USER_PROFILE_PIC_URL;

    EditText problem_arear;
    Button Make_The_Appointment_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        get_intent_data();
//        get_intent_data();
        find_views_by_ids();
//        find_views_by_ids();
        new Thread(() -> getUser_Info()).start();
//        getUser_Info();
//        get_DOCTOR_Info();

        Make_The_Appointment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                problem_arear.setEnabled(false);
                get_text_view_data();
                set_user_request_delayed();

            }
        });
    }

    public void get_text_view_data(){
        USER_PROBLEM= problem_arear.getText().toString();
    }

    public void get_intent_data(){
        Intent intent = getIntent();
        DOC_EMAIL=  intent.getStringExtra("DOC_EMAIL");
        DOC_NAME=  intent.getStringExtra("DOC_NAME");
        USER_EMAIL= intent.getStringExtra("USER_EMAIL");

    }

    public void find_views_by_ids(){
        problem_arear = findViewById(R.id.problem_arear);
        Make_The_Appointment_btn = findViewById(R.id.Make_The_Appointment_btn);
    }

    public void getUser_Info(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        DocumentReference dr =fb.collection("USERS").document(USER_EMAIL).collection(USER_EMAIL).document("PROFILE");
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                USER_NAME = documentSnapshot.get("name").toString();
                USER_GENDER = documentSnapshot.get("gender").toString();
                USER_PHONE_NO = documentSnapshot.get("phone_no").toString();
                USER_PROFILE_PIC_URL = documentSnapshot.get("profile_pic_URL").toString();
            }
        });
    }



    public void set_user_request_delayed()
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setUser_request_in_doctor();
            }
        };
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(runnable,2000);
    }

    public void setUser_request_in_doctor(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference XDR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY REQUESTING PATIENTS BOOK").document(USER_EMAIL);
        CollectionReference CR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY REQUESTING PATIENTS BOOK");
        MakeAppointmentDataModel data = new MakeAppointmentDataModel(USER_NAME,USER_EMAIL,USER_PROBLEM,"request");

        XDR.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                clear_text_area();
                new Thread(() -> update_user_appointment()).start();
                new Thread(() -> update_user_notification()).start();
//                update_user_appointment();
                set_delayed_home();
            }
        });
    }

    public void go_Home(){
        Intent intent = new Intent(MakeAppointmentActivity.this, MainPanel.class);
        intent.putExtra("USER_EMAIL",USER_EMAIL);
        startActivity(intent);
        finish();
    }

    public void set_delayed_home(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(MakeAppointmentActivity.this, "Done", Toast.LENGTH_SHORT).show();
                go_Home();
            }
        };
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(runnable,1500);
    }

    public void clear_text_area(){
        problem_arear.setText("");
    }

    public void get_DOCTOR_Info(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        DocumentReference dr =fb.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document("PROFILE");
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                DOC_NAME = documentSnapshot.get("name").toString();
            }
        });
    }
    public void update_user_appointment(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("USERS").document(USER_EMAIL).collection(USER_EMAIL).document("APPOINTMENT");
        MakeAppointmentDataModel data = new MakeAppointmentDataModel(DOC_NAME,DOC_EMAIL,DOC_PHONENO,USER_PROBLEM);
//        DR.set(data);
        DR.update("name",DOC_NAME,"email",DOC_EMAIL,"phoneno",DOC_PHONENO,"problem",USER_PROBLEM,"status","pending");
    }
    public void update_user_notification(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference NOTIFICATION_DR =db.collection("USERS").document(USER_EMAIL).collection(USER_EMAIL).document(USER_EMAIL).collection("NOTIFICATION").document("APPOINTMENT STATUS NOTIFICATION");
//        DocumentReference NOTIFICATION_DR = db.collection("USERS").document(USER_EMAIL).collection(USER_EMAIL).document("NOTIFICATION");
        NOTIFICATION_DR.update("status","pending");
    }

}