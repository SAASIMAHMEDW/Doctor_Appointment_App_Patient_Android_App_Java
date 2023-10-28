package com.example.appmai;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUpapp extends AppCompatActivity {

    Button user_register_signupBTN;


    String NAME,AGE,EMAIL,PASSWORD,PASSWORD_HINT,GENDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signmain);

        user_register_signupBTN = findViewById(R.id.user_register_signupBTN);
        user_register_signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    get_user_register_filled_data();
                    // Toast.makeText(SignUpapp.this, NAME, Toast.LENGTH_SHORT).show();

                    send_email_password(EMAIL,PASSWORD);


            }
        });

    }
    public  void get_user_register_filled_data(){
        EditText user_register_name,user_register_age,user_register_email,user_register_password,user_register_password_hint,user_register_gender;
        user_register_name = findViewById(R.id.user_register_name);
        user_register_age = findViewById(R.id.user_register_age);
        user_register_email = findViewById(R.id.user_register_email);
        user_register_password = findViewById(R.id.user_register_password);
        user_register_password_hint = findViewById(R.id.user_register_password_hint);
        user_register_gender = findViewById(R.id.user_register_gender);



        NAME = user_register_name.getText().toString();
        AGE = user_register_age.getText().toString();
        EMAIL = user_register_email.getText().toString();
        PASSWORD = user_register_password.getText().toString();
        PASSWORD_HINT = user_register_password_hint.getText().toString();
        GENDER = user_register_gender.getText().toString();
    }

    public void create_user_and_store_firebase(String name,String age,String email,String password, String password_hint,String gender){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        String uid = fb.collection("USERS").document().collection(name).document().getId();
        Users user = new Users(name,age,email,password,password_hint,gender,uid);
        DocumentReference DR = fb.collection("USERS").document(email).collection(email).document("APPOINTMENT");
        UserAppointmentModel data = new UserAppointmentModel("NULL","NULL","NULL","NULL");
        DR.set(data);
        fb.collection("USERS").document(email).collection(email).document("PROFILE").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpapp.this, "User Successfully Created", Toast.LENGTH_SHORT).show();
                    get_user_register_clear_data();
                    register_to_login();
                }
                else{
                    Toast.makeText(SignUpapp.this, "Failed To Create User", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public  void get_user_register_clear_data(){
        EditText user_register_name,user_register_age,user_register_email,user_register_password,user_register_password_hint,user_register_gender;
        user_register_name = findViewById(R.id.user_register_name);
        user_register_age = findViewById(R.id.user_register_age);
        user_register_email = findViewById(R.id.user_register_email);
        user_register_password = findViewById(R.id.user_register_password);
        user_register_password_hint = findViewById(R.id.user_register_password_hint);
        user_register_gender = findViewById(R.id.user_register_gender);

        user_register_name.setText("");
        user_register_age.setText("");
        user_register_email.setText("");
        user_register_password.setText("");
        user_register_password_hint.setText("");
        user_register_gender.setText("");
        
    }
    public void register_to_login(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
                            Toast.makeText(SignUpapp.this, "Successfully Created User", Toast.LENGTH_SHORT).show();
                            create_user_and_store_firebase(NAME, AGE, EMAIL, PASSWORD, PASSWORD_HINT,GENDER);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpapp.this, "Failed To Create User", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    
}
