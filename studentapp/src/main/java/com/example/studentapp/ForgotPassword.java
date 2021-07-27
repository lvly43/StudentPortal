package com.example.studentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    public EditText etEmai;
    public Button btnNex;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        etEmai=findViewById(R.id.etEmail);
        btnNex=findViewById(R.id.btnNext);


        mFirebaseAuth = FirebaseAuth.getInstance();


        btnNex.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                resetPassword();
            }
        });
    }
    private void resetPassword(){
        final String email=etEmai.getText().toString().trim();
        if(email.isEmpty()){
            Toast.makeText(ForgotPassword.this,"Please enter your email",Toast.LENGTH_SHORT).show();
            return;
        }

        mFirebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ForgotPassword.this,"reset link send to your email",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(ForgotPassword.this,MainActivity.class);
                startActivity(i);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(ForgotPassword.this,"reset link not send to your email",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
