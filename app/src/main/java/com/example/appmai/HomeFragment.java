package com.example.appmai;

import android.content.ClipData;
import android.media.RouteListingPreference;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchUIUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    LinearLayout user_dont_have_appointment,user_handlig_appointment;
    TextView home_doctor_name,home_doctor_phoneno,home_doctor_email,home_problem_area;
    Button home_patient_appointment_status;

    String EMAIL,PASSWORD;
    String APPOINTMENT_NAME,APPOINTMENT_EMAIL,APPOINTMENT_PHONE,APPOINTMENT_PROBLEM,APPOINTMENT_STATUS;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        get_bundle_data();
        find_views_by_ids(view);
        delay();
        new Thread(this::getNotificationStatus).start();
//        getNotificationStatus_thread();
//        Toast.makeText(getContext().getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
//        new Thread(this::getNotificationStatus).start();
        return view;
    }
    public void getNotificationStatus(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        CollectionReference NOTIFICATION_STATUS_CR = fb.collection("USERS").document(EMAIL).collection(EMAIL).document(EMAIL).collection("NOTIFICATION");
        NOTIFICATION_STATUS_CR.addSnapshotListener((value, error) -> {
            if (error != null) {
                // Handle error
                return;
            }
            // Update TextView with data from the collection
            assert value != null;
            for (DocumentChange dc : value.getDocumentChanges()) {
                APPOINTMENT_STATUS = dc.getDocument().getString("status");
                if (APPOINTMENT_STATUS.equals("pending"))
                    home_patient_appointment_status.setText(APPOINTMENT_STATUS);
                else if (APPOINTMENT_STATUS.equals("NULL"));
                else
                    home_patient_appointment_status.setText(APPOINTMENT_STATUS);
            }
        });
    }

    public void find_views_by_ids(View view){
        user_dont_have_appointment = view.findViewById(R.id.user_dont_have_appointment);
        user_handlig_appointment = view.findViewById(R.id.user_handlig_appointment);
        home_doctor_name = view.findViewById(R.id.home_doctor_name);
        home_doctor_phoneno = view.findViewById(R.id.home_doctor_phoneno);
        home_doctor_email = view.findViewById(R.id.home_doctor_email);
        home_problem_area = view.findViewById(R.id.home_problem_area);
        home_patient_appointment_status = view.findViewById(R.id.home_patient_appointment_status);

    }


    public void get_bundle_data(){
        Bundle b = getArguments();

        String email = b.getString("EMAILX");
        if (email==null){
            EMAIL = b.getString("XX_EMAIL");
        }else {
            EMAIL = b.getString("EMAILX");
        }

        PASSWORD=  b.getString("PASSWORDX");
    }

    public void delay(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getUser_Info();
            }
        };
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(runnable,4000);
    }
    public void getUser_Info(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        DocumentReference dr =fb.collection("USERS").document(EMAIL).collection(EMAIL).document("APPOINTMENT");
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                APPOINTMENT_NAME = documentSnapshot.get("name").toString();
                APPOINTMENT_EMAIL = documentSnapshot.get("email").toString();
                APPOINTMENT_PHONE = documentSnapshot.get("phoneno").toString();
                APPOINTMENT_PROBLEM = documentSnapshot.get("problem").toString();
//                APPOINTMENT_STATUS = documentSnapshot.get("status").toString();
                handle_visibility(!APPOINTMENT_NAME.equals("NULL"));

            }
        });
    }

    public void getPatientsAppointmentStatus(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        DocumentReference NOTIFICATION_DR =fb.collection("USERS").document(EMAIL).collection(EMAIL).document(EMAIL).collection("NOTIFICATION").document("APPOINTMENT STATUS NOTIFICATION");
        NOTIFICATION_DR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                APPOINTMENT_STATUS = documentSnapshot.get("status").toString();
            }
        });

    }

    public void handle_visibility(boolean flag){
        if(flag){
            user_dont_have_appointment.setVisibility(View.GONE);
            user_handlig_appointment.setVisibility(View.VISIBLE);
            home_doctor_name.setText(APPOINTMENT_NAME);
            home_doctor_email.setText(APPOINTMENT_EMAIL);
            home_doctor_phoneno.setText(APPOINTMENT_PHONE);
            home_problem_area.setText(APPOINTMENT_PROBLEM);
            home_patient_appointment_status.setText(APPOINTMENT_STATUS);

        }else {
            user_dont_have_appointment.setVisibility(View.VISIBLE);
            user_handlig_appointment.setVisibility(View.GONE);
        }
    }

}