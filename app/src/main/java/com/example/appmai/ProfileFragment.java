package com.example.appmai;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {


    String EMAIL,PASSWORD,NAME,AGE,PASSWORD_HINT,GENDER;

    EditText editName,editEmail,editAge,editPassword,editGender,editHint;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        get_bundle_data();
        setProfile(view);
        getUser_Info();

        Button edit; Button update;
        edit = view.findViewById(R.id.Edit_btn);
        update = view.findViewById(R.id.update_btn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_profile(view);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_profile(view);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void get_bundle_data(){
        Bundle b = getArguments();
        EMAIL = b.getString("EMAILX");
        PASSWORD=  b.getString("PASSWORDX");
    }

    public void setProfile(View view){
        EditText emailET,passwordET;
        emailET = view.findViewById(R.id.Profile_user_Email);
        emailET.setHint(EMAIL);
        passwordET = view.findViewById(R.id.Profile_user_password);
        passwordET.setHint(PASSWORD);

        editName = view.findViewById(R.id.Profile_user_name);
        editEmail = view.findViewById(R.id.Profile_user_Email);
        editAge=view.findViewById(R.id.Profile_user_age);
        editPassword=view.findViewById(R.id.Profile_user_password);
        editGender=view.findViewById(R.id.Profile_user_gender);
        editHint=view.findViewById(R.id.Profile_user_hint);

        editPassword.setHint(PASSWORD);
        editEmail.setHint(EMAIL);

    }
    public void edit_profile(View view){
        Button edit; Button update;

        edit = view.findViewById(R.id.Edit_btn);
        update = view.findViewById(R.id.update_btn);

                editName = view.findViewById(R.id.Profile_user_name);
                editEmail = view.findViewById(R.id.Profile_user_Email);
                editAge=view.findViewById(R.id.Profile_user_age);
                editPassword=view.findViewById(R.id.Profile_user_password);
                editGender=view.findViewById(R.id.Profile_user_gender);
                editHint=view.findViewById(R.id.Profile_user_hint);

                editName.setEnabled(true);
                editAge.setEnabled(true);
                editGender.setEnabled(true);
                editPassword.setEnabled(true);
                editEmail.setEnabled(true);
                editHint.setEnabled(true);

                edit.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);

    }
    public void update_profile(View view){
        Button edit; Button update;
        EditText editName,editEmail,editAge,editPassword,editGender,editHint;
        edit = view.findViewById(R.id.Edit_btn);
        update = view.findViewById(R.id.update_btn);

        editName = view.findViewById(R.id.Profile_user_name);
        editEmail = view.findViewById(R.id.Profile_user_Email);
        editAge=view.findViewById(R.id.Profile_user_age);
        editPassword=view.findViewById(R.id.Profile_user_password);
        editGender=view.findViewById(R.id.Profile_user_gender);
        editHint=view.findViewById(R.id.Profile_user_hint);



                editName.setEnabled(false);
                editAge.setEnabled(false);
                editGender.setEnabled(false);
                editPassword.setEnabled(false);
                editEmail.setEnabled(false);
                editHint.setEnabled(false);


                edit.setVisibility(View.VISIBLE);
                update.setVisibility(View.GONE);

    }
    public void getUser_Info(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        DocumentReference dr =fb.collection("USERS").document(EMAIL).collection(EMAIL).document("PROFILE");
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                NAME = documentSnapshot.get("name").toString();
                AGE = documentSnapshot.get("age").toString();
                PASSWORD = documentSnapshot.get("password").toString();
                PASSWORD_HINT = documentSnapshot.get("hint").toString();
                GENDER = documentSnapshot.get("gender").toString();
                editAge.setHint(AGE);
                editName.setHint(NAME);
                editGender.setHint(GENDER);
                editPassword.setHint(PASSWORD);
                editHint.setHint(PASSWORD_HINT);
            }
        });

    }

    public void delay(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setProfile(view);
            }
        };
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(runnable,2000);
    }
}