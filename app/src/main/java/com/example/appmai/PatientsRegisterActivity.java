package com.example.appmai;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class PatientsRegisterActivity extends AppCompatActivity {

    Button user_register_signupBTN;
    EditText user_register_name,user_register_age,user_register_email,user_register_password,user_register_password_hint,user_register_gender,user_register_phoneNo;
    CircleImageView register_user_profile_pic;
    ProgressBar signup_button_progress_bar;
    Uri PROFILE_PIC_URL_URI;
    ActivityResultLauncher<String> launcher;

    String NAME,AGE,EMAIL,PASSWORD,PASSWORD_HINT,GENDER,PHONE_NO,PROFILE_PIC_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializer();
        image_activity_result_launcher();
        user_register_signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    signup_button_progress_bar.setVisibility(View.VISIBLE);
                    handleFieldsVisibility(false);
                    get_user_register_filled_data();
                    // Toast.makeText(SignUpapp.this, NAME, Toast.LENGTH_SHORT).show();
                    send_email_password(EMAIL,PASSWORD);


            }
        });
        register_user_profile_pic.setOnClickListener(v -> {
            try {
                handle_upload_button();
            }catch (Exception e){
                Toast.makeText(PatientsRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        user_register_phoneNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (user_register_phoneNo.getText().toString().length()<10)
                        user_register_phoneNo.setError("Phone no. must have 10 digits");
                }

            }
        });
        user_register_phoneNo.addTextChangedListener(new TextWatcher() {
            String phone_no;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (user_register_phoneNo.getText().toString().length()==10){
                    phone_no = user_register_phoneNo.getText().toString();
                    user_register_phoneNo.setError(null);
                }
                if (user_register_phoneNo.getText().toString().length()>10){
                    user_register_phoneNo.setError("Phone no. must be 10 digit only");
                    user_register_phoneNo.setText(phone_no);
                    user_register_phoneNo.setSelection(phone_no.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public void initializer(){
        user_register_name = findViewById(R.id.user_register_name);
        user_register_age = findViewById(R.id.user_register_age);
        user_register_gender = findViewById(R.id.user_register_gender);
        user_register_phoneNo = findViewById(R.id.user_register_phoneNo);
        user_register_email = findViewById(R.id.user_register_email);
        user_register_password = findViewById(R.id.user_register_password);
        user_register_password_hint = findViewById(R.id.user_register_password_hint);
        register_user_profile_pic = findViewById(R.id.register_user_profile_pic);
        user_register_signupBTN = findViewById(R.id.user_register_signupBTN);
        signup_button_progress_bar = findViewById(R.id.signup_button_progress_bar);

    }
    public void handleFieldsVisibility(boolean flag){
        user_register_name.setEnabled(flag);
        user_register_age.setEnabled(flag);
        user_register_gender.setEnabled(flag);
        user_register_phoneNo.setEnabled(flag);
        user_register_email.setEnabled(flag);
        user_register_password.setEnabled(flag);
        user_register_password_hint.setEnabled(flag);
        register_user_profile_pic.setClickable(flag);
        user_register_signupBTN.setEnabled(flag);
    }
    public void handle_upload_button() {
        launcher.launch("image/*");
    }
    public void image_activity_result_launcher() {
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            PROFILE_PIC_URL_URI = result;
            register_user_profile_pic.setImageURI(result);
            PROFILE_PIC_URL = result.toString();
//                Toast.makeText(this, PROFILE_PIC_URL_URI.toString(), Toast.LENGTH_LONG).show();
        });
    }
    public  void get_user_register_filled_data(){
        NAME = user_register_name.getText().toString();
        AGE = user_register_age.getText().toString();
        PHONE_NO = user_register_phoneNo.getText().toString();
        EMAIL = user_register_email.getText().toString();
        PASSWORD = user_register_password.getText().toString();
        PASSWORD_HINT = user_register_password_hint.getText().toString();
        GENDER = user_register_gender.getText().toString();
        PHONE_NO = user_register_phoneNo.getText().toString();
    }

    public void create_user_and_store_firebase(String name,String age,String email,String password, String password_hint,String gender,String phoneNo,String profile_pic_url){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        String uid = fb.collection("USERS").document().collection(name).document().getId();
        Users user = new Users(name,age,email,password,password_hint,gender,uid,phoneNo,profile_pic_url);
        DocumentReference DR = fb.collection("USERS").document(email).collection(email).document("APPOINTMENT");
        UserAppointmentModel data = new UserAppointmentModel("NULL","NULL","NULL","NULL","NULL");

        DocumentReference DR_NOTI = fb.collection("USERS").document(email).collection(email).document(email).collection("NOTIFICATION").document("APPOINTMENT STATUS NOTIFICATION");
        HashMap<String,String> notification_map = new HashMap<>();
        notification_map.put("status","NULL");
        DR_NOTI.set(notification_map);

        DR.set(data);
        fb.collection("USERS").document(email).collection(email).document("PROFILE").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PatientsRegisterActivity.this, "User Successfully Created", Toast.LENGTH_SHORT).show();
                    get_user_register_clear_data();
                    register_to_login();
                }
                else{
                    signup_button_progress_bar.setVisibility(View.GONE);
                    handleFieldsVisibility(true);
                    Toast.makeText(PatientsRegisterActivity.this, "Failed To Create User", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public  void get_user_register_clear_data(){
        user_register_name.setText("");
        user_register_age.setText("");
        user_register_email.setText("");
        user_register_password.setText("");
        user_register_password_hint.setText("");
        user_register_gender.setText("");
        user_register_phoneNo.setText("");
        register_user_profile_pic.setImageResource(R.drawable.nav_profile);

    }
    public void register_to_login(){
        Intent intent = new Intent(getApplicationContext(), PatientsLoginActivity.class);
        startActivity(intent);
        finish();
        
    }
    public void send_email_password(String email, String password){
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(SignUpapp.this, "Successfully Created User", Toast.LENGTH_SHORT).show();
                            upload_profile_pic_firestore_storage();
//                            create_user_and_store_firebase(NAME, AGE, EMAIL, PASSWORD, PASSWORD_HINT,GENDER,PHONE_NO,PROFILE_PIC_URL);
                        } else {
                            signup_button_progress_bar.setVisibility(View.GONE);
                            handleFieldsVisibility(true);
                            // If sign in fails, display a message to the user.
                            Toast.makeText(PatientsRegisterActivity.this, "Failed To Create User AUTH REGISTER", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void upload_profile_pic_firestore_storage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("IMAGES").child("(USERS) PATIENT").child("USERS PROFILE PICTURES").child(EMAIL);
        storageReference.putFile(PROFILE_PIC_URL_URI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        PROFILE_PIC_URL = uri.toString();
                        firestore_delayed();
                    }
                });
            }
        });

    }

  public void firestore_delayed(){
      Runnable runnable = new Runnable() {
          @Override
          public void run() {
              create_user_and_store_firebase(NAME, AGE, EMAIL, PASSWORD, PASSWORD_HINT,GENDER,PHONE_NO,PROFILE_PIC_URL);
          }
      };
      Handler handler = new Handler(Looper.getMainLooper());
      handler.postDelayed(runnable,3000);
  }
    
}
