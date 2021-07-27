package com.example.studentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public EditText etEmail,etPassword,username;
    public TextView txtForgotPassword;
    public ImageView imgAppIcon,imageProfile;
    public Button btnLogin;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    FirebaseDatabase database ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        imgAppIcon=findViewById(R.id.imgAppIcon);
        txtForgotPassword=findViewById(R.id.txtForgotPassword);
        btnLogin=findViewById(R.id.btnLogin);
        mFirebaseAuth= FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(MainActivity.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=etEmail.getText().toString();
                String pwd=etPassword.getText().toString();
                if(email.isEmpty()){
                    etEmail.setError("please enter valid email address");
                    etEmail.requestFocus();
                }
                else if(pwd.isEmpty() ||pwd.length() <= 8){
                    etPassword.setError("please enter your password");
                    etPassword.requestFocus();

                }
                else if(email.isEmpty() &&pwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_SHORT);
                }
                else if(!(email.isEmpty() &&pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                String email1 = currentUser.getEmail();
                                String uid = currentUser.getUid();

                                HashMap<Object,String> hashMap = new HashMap<>();
                                hashMap.put("email",email1);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);
                                Intent i = new Intent(MainActivity.this,SetupProfile.class);
                                startActivity(i);


                            } else {
                                Toast.makeText(getApplicationContext(), "SignUp Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
              /*  else{
                    Toast.makeText(getApplicationContext(),"Error occurred",Toast.LENGTH_SHORT);

                }*/
            }

        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent f=new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(f);

            }
        });
    }
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null ){

            Intent intent = new Intent(MainActivity.this, DashBoard.class);
            startActivity(intent);
            finish();
        }
    }
}