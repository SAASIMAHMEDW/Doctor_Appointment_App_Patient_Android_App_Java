package com.example.appmai;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {


//    String EMAIL,PASSWORD,NAME,AGE,PASSWORD_HINT,GENDER,PHONE_NO,PROFILE_PIC_URL;
    String NAME,AGE,GENDER,PHONE_NO,EMAIL,PASSWORD,PASSWORD_HINT,PROFILE_PIC_URL;
//    String UPDATED_NAME,UPDATED_AGE,UPDATED_EMAIL,UPDATED_PASSWORD,UPDATED_PASSWORD_HINT,UPDATED_GENDER;
    String UPDATED_NAME,UPDATED_AGE,UPDATED_GENDER,UPDATED_PHONE_NO,UPDATED_EMAIL,UPDATED_PASSWORD,UPDATED_PASSWORD_HINT,UPDATED_PROFILE_PIC_URL;

    EditText editName,editEmail,editAge,editPassword,editGender,editHint,profile_user_phoneno;
    CircleImageView user_profile_image;
    EditText emailET,passwordET;
    Button edit,update,user_logout_button;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        find_views_by_ids(view);
        get_bundle_data();
        setProfile();
        getUser_Info();



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

        user_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log_out_the_user(v);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void find_views_by_ids(View view){
        edit = view.findViewById(R.id.Edit_btn);
        update = view.findViewById(R.id.update_btn);
        user_logout_button = view.findViewById(R.id.user_logout_button);
//        emailET = view.findViewById(R.id.Profile_user_Email);
//        passwordET = view.findViewById(R.id.Profile_user_password);
        editName = view.findViewById(R.id.Profile_user_name);
        editEmail = view.findViewById(R.id.Profile_user_Email);
        editAge=view.findViewById(R.id.Profile_user_age);
        editPassword=view.findViewById(R.id.Profile_user_password);
        editGender=view.findViewById(R.id.Profile_user_gender);
        editHint=view.findViewById(R.id.Profile_user_hint);
        profile_user_phoneno=view.findViewById(R.id.profile_user_phoneno);
        user_profile_image=view.findViewById(R.id.user_profile_image);
    }

    public void Log_out_the_user(View view){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(PatientsSplashScreenActivity.SHARED_PREFERENCES_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(SplashScr.SHARED_PREFERENCES_KAY_FLAG,false);
//        editor.putString("EMAIL",null);
//        editor.putString("PASSWORD",null);
        editor.clear();
        editor.apply();
        Intent intent = new Intent(requireContext().getApplicationContext(), PatientsLoginActivity.class);
//        clearBackStack();
        startActivity(intent);
        requireActivity().finishAffinity();
    }
    private void clearBackStack() {
        FragmentManager manager = requireActivity().getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void get_bundle_data(){
        Bundle bundle = getArguments();
        assert bundle != null;
        String emailx = bundle.getString("EMAILX");
        if (emailx != null){
            EMAIL = emailx;
        }else {
            EMAIL = bundle.getString("XX_EMAIL");
        }
//        EMAIL = b.getString("EMAILX");
        PASSWORD=  bundle.getString("PASSWORDX");
    }

    public void setProfile(){
//        emailET.setHint(EMAIL);
//        passwordET.setHint(PASSWORD);
        editPassword.setHint(PASSWORD);
        editEmail.setHint(EMAIL);

    }
    public void edit_profile(View view){
                editName.setEnabled(true);
                editAge.setEnabled(true);
                editGender.setEnabled(true);
                profile_user_phoneno.setEnabled(true);
                editPassword.setEnabled(false);
                editEmail.setEnabled(false);
                editHint.setEnabled(true);
                edit.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);

    }
    public void update_profile(View view){
        editName.setEnabled(false);
        editAge.setEnabled(false);
        editGender.setEnabled(false);
        profile_user_phoneno.setEnabled(false);
        editPassword.setEnabled(false);
        editEmail.setEnabled(false);
        editHint.setEnabled(false);
        edit.setVisibility(View.VISIBLE);
        update.setVisibility(View.GONE);
        get_Updated_Data();
        editName.setHint(UPDATED_NAME);
        editAge.setHint(UPDATED_AGE);
        editGender.setHint(UPDATED_GENDER);
        profile_user_phoneno.setHint(UPDATED_PHONE_NO);
        editHint.setHint(UPDATED_PASSWORD_HINT);
        delayed();

    }
    public void get_Updated_Data(){
        //NAME
        if ((editName.getText().toString()).length()==0){
            UPDATED_NAME = editName.getHint().toString();
        }else {
            UPDATED_NAME = editName.getText().toString();
        }

//        AGE
        if ((editAge.getText().toString()).length()==0){
            UPDATED_AGE = editAge.getHint().toString();
        }else {
            UPDATED_AGE = editAge.getText().toString();
        }

//        PASSWORD_HINT
        if ((editHint.getText().toString()).length()==0){
            UPDATED_PASSWORD_HINT = editHint.getHint().toString();
        }else {
            UPDATED_PASSWORD_HINT = editHint.getText().toString();
        }

//        GENDER
        if ((editGender.getText().toString()).length()==0){
            UPDATED_GENDER = editGender.getHint().toString();
        }else {
            UPDATED_GENDER = editGender.getText().toString();
        }

        // phone no.
        if ((profile_user_phoneno.getText().toString()).length()==0){
            UPDATED_PHONE_NO = profile_user_phoneno.getHint().toString();
        }else {
            UPDATED_PHONE_NO = profile_user_phoneno.getText().toString();
        }

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
                PHONE_NO = documentSnapshot.get("phone_no").toString();
                PROFILE_PIC_URL = documentSnapshot.get("profile_pic_URL").toString();
                Picasso.get().load(PROFILE_PIC_URL).into(user_profile_image);
                editName.setHint(NAME);
                editAge.setHint(AGE);
                editGender.setHint(GENDER);
                profile_user_phoneno.setHint(PHONE_NO);
                editPassword.setHint(PASSWORD);
                editHint.setHint(PASSWORD_HINT);
            }
        });

    }

    public void set_Updated_Data_in_firestore(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("USERS").document(EMAIL).collection(EMAIL).document("PROFILE");

        DR.update("name",UPDATED_NAME, "age",UPDATED_AGE,"hint",UPDATED_PASSWORD_HINT,"gender",UPDATED_GENDER,"phone_no",UPDATED_PHONE_NO).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(requireContext().getApplicationContext(), "Updated Successful", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(requireContext().getApplicationContext(), "Failed To Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                set_Updated_Data_in_firestore();
//                Toast.makeText(getContext().getApplicationContext(), LOGIN_UID, Toast.LENGTH_SHORT).show();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,1000);
    }


//    public void delay(View view){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                setProfile(view);
//            }
//        };
//        Handler handler = new Handler(Looper.myLooper());
//        handler.postDelayed(runnable,2000);
//    }
}