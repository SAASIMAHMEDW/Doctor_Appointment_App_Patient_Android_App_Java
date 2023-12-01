package com.example.appmai;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class  ListFragment extends Fragment implements ViewMoreInterfaceOfRecyclerView, CallDoctorInterface{

    int totalDoctors;
    String EMAIL,PASSWORD;

    ArrayList<String []> doctor_data;
    ProgressBar load;
    TextView Available_Doctor_List_TV,loading_text;
    LinearLayout No_Doctor_Available_Layout;
    RecyclerView recyclerview;

    List<ListFragmentRecyclerViewAdapterModel> items;

    public ListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        find_views_by_ids(view);
//        recyclerview.setVisibility(View.GONE);
        load.setVisibility(View.VISIBLE);
        loading_text.setVisibility(View.VISIBLE);
        get_bundle_data();
//        get_doctor_data_from_realtime_dbx();
        get_total_doctor_number(view);
//        get_doctor_data_from_realtime_db_delayed(view);
//        doctor_recycler_view_data(view);
        doctor_available_recycler_view_delayed(view);

        // Inflate the layout for this fragment
        return view;

    }

    public void find_views_by_ids(View view){
        recyclerview = view.findViewById(R.id.recycleview);
        No_Doctor_Available_Layout = view.findViewById(R.id.No_Doctor_Available_Layout);
        load = view.findViewById(R.id.prog_bar1);
        Available_Doctor_List_TV = view.findViewById(R.id.Available_Doctor_List_TV);
        loading_text = view.findViewById(R.id.loading_text);

    }

    public void doctor_available_recycler_view_delayed(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                doctor_recycler_view_data(view);
            }
        };
        Handler handler = new Handler(Objects.requireNonNull(Looper.myLooper()));
        handler.postDelayed(runnable,8000);
    }

    public void doctor_recycler_view_data(View view){
        recyclerview.setVisibility(View.VISIBLE);
        load.setVisibility(View.GONE);
        loading_text.setVisibility(View.GONE);
//        RecyclerView recyclerView = view.findViewById(R.id.recycleview);
        items = new ArrayList<>();

        for (String[] data:doctor_data) {
//            String[] data = {name,email,phone,gender,profile_pic_url,status};
             items.add(new ListFragmentRecyclerViewAdapterModel(data[0], data[1], data[2], data[3], data[4]));

        }
        recyclerview.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        recyclerview.setAdapter(
                new ListFragmentRecyclerViewAdapter(
                (requireActivity().getApplicationContext()),
                items,
                this,
                this));

    }

    public void get_total_doctor_number(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ADMINS ROOT");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalDoctors = (int)snapshot.getChildrenCount();
//                Toast.makeText(getContext().getApplicationContext(), Integer.toString(totalDoctors), Toast.LENGTH_LONG).show();
                if (totalDoctors == 0) {
                    load.setVisibility(View.GONE);
                    recyclerview.setVisibility(View.GONE);
                    No_Doctor_Available_Layout.setVisibility(View.VISIBLE);
                }else {
                    get_doctor_data_from_realtime_db();
//                    get_doctor_data_from_realtime_db_delayed(view);
                }

//                Toast.makeText(getContext().getApplicationContext(),Integer.toString(totalDoctors), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void get_doctor_data_from_realtime_db_delayed(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                get_doctor_data_from_realtime_db();
            }
        };
        Handler handler = new Handler(Objects.requireNonNull(Looper.myLooper()));
        handler.postDelayed(runnable,2000);
    }

    public void get_doctor_data_from_realtime_db(){
        doctor_data = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ADMINS ROOT");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String email, status, name,profile_pic_url,gender,phone;
                status = snapshot.child("status").getValue().toString();
                int notAvailableCount=0;
                if (status.equals("Available")){
                    name = snapshot.child("name").getValue().toString();
                    email = snapshot.child("email").getValue().toString();
                    phone = snapshot.child("phone_no").getValue().toString();
                    gender = snapshot.child("gender").getValue().toString();
                    profile_pic_url = snapshot.child("profile_pic_url").getValue().toString();
                    String[] data = {name,email,phone,gender,profile_pic_url,status};
//                    load.setVisibility(View.GONE);
                    doctor_data.add(data);
                }else {
                    notAvailableCount++;
                    if (notAvailableCount == totalDoctors){
                        recyclerview.setVisibility(View.GONE);
                        No_Doctor_Available_Layout.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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

    public void get_bundle_data(){
        Bundle b = getArguments();
        EMAIL = b.getString("EMAILX");
        PASSWORD=  b.getString("PASSWORDX");
    }

    @Override
    public void on_recycler_button_click(int position) {
        Intent intent = new Intent(getContext().getApplicationContext(),ViewActivity.class);
        String doctorEmail = items.get(position).getDoc_email();
        intent.putExtra("DOC_EMAIL",doctorEmail);
        intent.putExtra("USER_EMAIL",EMAIL);
//        Toast.makeText(getContext().getApplicationContext(), doctorEmail, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
    @Override
    public void on_recycler_callIMG_click(int position) {
        String number = items.get(position).getDoc_phoneNo();
        String call = "tel: "+number;
        Intent callDoc = new Intent(Intent.ACTION_DIAL);
        callDoc.setData(Uri.parse(call));
        startActivity(callDoc);

    }
}
