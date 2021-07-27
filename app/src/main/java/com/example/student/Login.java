package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.student.Main.MainScreen;

public class Login extends AppCompatActivity {
    EditText etEmail,etPassword;
    Button btnLogin;
    SharedPreferences sharedPreferences;

    public static final String fileName="login";
    public static final String Username="username";
    public static final String Password="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        sharedPreferences=getSharedPreferences(fileName,MODE_PRIVATE);
        getSupportActionBar().setTitle("Admin");
        if(sharedPreferences.contains(Username)){
            Intent intent = new Intent(Login.this, MainScreen.class);
            startActivity(intent);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=etEmail.getText().toString();
                String password=etPassword.getText().toString();
                if(username.equals("user@gmail.com")&&password.equals("user")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Username, username);
                    editor.putString(Password, password);
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Login.this, MainScreen.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid Details", Toast.LENGTH_SHORT).show();
                    etEmail.setText("");
                    etEmail.requestFocus();
                    etPassword.setText("");
;                }

            }
        });
    }
}