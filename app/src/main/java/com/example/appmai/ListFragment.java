package com.example.appmai;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class  ListFragment extends Fragment implements view_more_interface_of_recyler_view {

    int totalDoctors;
    String EMAIL,PASSWORD;

    ArrayList<String []> doctor_data;
    ProgressBar load;
    LinearLayout No_Doctor_Available_Layout;
    RecyclerView recycleview;

    List<Home_info> items;

    public ListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        find_views_by_ids(view);
        recycleview.setVisibility(View.VISIBLE);
        load.setVisibility(View.VISIBLE);
        get_bundle_data();
//        get_doctor_data_from_realtime_dbx();
        get_total_doctor_number();
        get_doctor_data_from_realtime_db_delayed();
//        doctor_recycler_view_data(view);
        doctor_available_recyler_view_delayed(view);


        // Inflate the layout for this fragment
        return view;

    }

    public void find_views_by_ids(View view){
        recycleview = view.findViewById(R.id.recycleview);
        No_Doctor_Available_Layout = view.findViewById(R.id.No_Doctor_Available_Layout);
        load = view.findViewById(R.id.prog_bar1);

    }

    public void doctor_available_recyler_view_delayed(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                doctor_recycler_view_data(view);
            }
        };
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(runnable,8000);
    }

    public void doctor_recycler_view_data(View view){
//        RecyclerView recyclerView = view.findViewById(R.id.recycleview);
        items = new ArrayList<>();

        for (String[] data:doctor_data) {
        items.add(new Home_info(data[0].toString(),data[1].toString()));

        }
//        Toast.makeText(getContext().getApplicationContext(), doctor_data.get(0).toString(), Toast.LENGTH_SHORT).show();
//        items.add(new Home_info());
//        items.add(new Home_info("vvv"));
//        items.add(new Home_info("ddd"));
//        items.add(new Home_info("sss"));
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recycleview.setAdapter(new MyAdopter((getActivity().getApplicationContext()),items,this));

    }

    public void get_total_doctor_number(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ADMINS ROOT");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalDoctors = (int)snapshot.getChildrenCount();
//                Toast.makeText(getContext().getApplicationContext(),Integer.toString(totalDoctors), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void get_doctor_data_from_realtime_db_delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                get_doctor_data_from_realtime_db();
            }
        };
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(runnable,3000);
    }

    public void get_doctor_data_from_realtime_db(){

        doctor_data = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ADMINS ROOT");
//        DatabaseReference myRefX = database.getReference();
        load.setVisibility(View.VISIBLE);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String email, status, name;
                status = snapshot.child("status").getValue().toString();
//                Toast.makeText(getContext().getApplicationContext(), Integer.toString(totalDoctors), Toast.LENGTH_SHORT).show();
                int notAvailableCount=0;
                if (totalDoctors==0){
                    recycleview.setVisibility(View.GONE);
                    No_Doctor_Available_Layout.setVisibility(View.VISIBLE);
                }
                if (status.equals("Available")){
                    email = snapshot.child("email").getValue().toString();
                    name = snapshot.child("name").getValue().toString();
                    String data[] = {name,email,status};
                    load.setVisibility(View.GONE);
                    doctor_data.add(data);
                }else {
                    notAvailableCount++;
                    if (notAvailableCount == totalDoctors){
                        recycleview.setVisibility(View.GONE);
                        No_Doctor_Available_Layout.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

//                String email, status, name;
//                status = snapshot.child("status").getValue().toString();
//                if (status.equals("Available")){
//                    email = snapshot.child("email").getValue().toString();
//                    name = snapshot.child("name").getValue().toString();
//                    String data[] = {name,email,status};
////                    load.setVisibility(View.GONE);
//                    doctor_data.add(data);
//                doctor_available_recyler_view_delayed()
//                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void on_recycler_button_click(int position) {
        Intent intent = new Intent(getContext().getApplicationContext(),ViewActivity.class);
        String doctorEmail = items.get(position).doctor_email;
        intent.putExtra("DOC_EMAIL",doctorEmail);
        intent.putExtra("USER_EMAIL",EMAIL);
//        Toast.makeText(getContext().getApplicationContext(), doctorEmail, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void get_bundle_data(){
        Bundle b = getArguments();
        EMAIL = b.getString("EMAILX");
        PASSWORD=  b.getString("PASSWORDX");
    }

//    public void doctor_info_view_more(){
//        Button doctor_view;
//
//        doctor_view.setOnClickListener();
//
//    }

    }
