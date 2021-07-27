package com.example.student.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.student.Login;
import com.example.student.UploadBook;
import com.example.student.timetable.UploadTimetable;
import com.example.student.R;

import com.example.student.UploadAttendance;
import com.example.student.UploadQuestionPapers;
import com.example.student.UploadResult;
import com.example.student.UploadSyllabus;

public class MainScreen extends AppCompatActivity {
    CardView uploadResult, cardviewUploadImage, cardviewUploadPdf,  cardViewQuestionPapers, cardViewAttendence, cardViewSyllabus;
SharedPreferences sharedPreferences;
    public static final String fileName="login";
    public static final String Username="username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        cardviewUploadImage = findViewById(R.id.cardviewUploadImage);
        cardviewUploadPdf = findViewById(R.id.cardviewUploadPdf);

        cardViewSyllabus = findViewById(R.id.uploadSyllabus);
        cardViewQuestionPapers=findViewById(R.id.uploadQuestionPapers);
        cardViewAttendence=findViewById(R.id.uploadAttendance);
        uploadResult=findViewById(R.id.uploadResult);
        sharedPreferences=getSharedPreferences(fileName,MODE_PRIVATE);

        uploadResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainScreen.this, UploadResult.class);
                startActivity(i);
            }
        });

        cardviewUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainScreen.this, UploadTimetable.class);
                startActivity(i);
            }
        });
        cardviewUploadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainScreen.this, UploadBook.class);
                startActivity(i);
            }
        });
        cardViewSyllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainScreen.this, UploadSyllabus.class);
                startActivity(i);
            }
        });
        cardViewQuestionPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainScreen.this, UploadQuestionPapers.class);
                startActivity(i);
            }
        });
        cardViewAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainScreen.this, UploadAttendance.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ic_Logout:
                logoutUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(MainScreen.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast.makeText(this,"You have been logged out ",Toast.LENGTH_SHORT).show();
    }
}