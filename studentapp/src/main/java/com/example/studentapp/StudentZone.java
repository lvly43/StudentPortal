package com.example.studentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.studentapp.attendence.AttendanceActivity;
import com.example.studentapp.eSyllabus.SyllabusActivity;
import com.example.studentapp.ebook.EbookActivity;
import com.example.studentapp.eresult.Result;
import com.example.studentapp.questionpapers.QuestionPapersActivity;

public class StudentZone extends AppCompatActivity {
    CardView books;
    CardView timetable;
    CardView syllabus;
    CardView questionPapers;
    CardView attendance;
    CardView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_zone);
        books = findViewById(R.id.books);
        timetable = findViewById(R.id.timetable);
        syllabus = findViewById(R.id.syllabus);
        questionPapers=findViewById(R.id.questionPapers);
        attendance=findViewById(R.id.attendance);
        result=findViewById(R.id.result);


        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentZone.this, Result.class);
                startActivity(i);
                /*  Toast.makeText(StudentZone.this,"clicked",Toast.LENGTH_SHORT).show();*/
            }
        });



        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentZone.this, EbookActivity.class);
                startActivity(i);
                /*  Toast.makeText(StudentZone.this,"clicked",Toast.LENGTH_SHORT).show();*/
            }
        });
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentZone.this, Timetable.class);
                startActivity(i);
            }
        });
        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(StudentZone.this, SyllabusActivity.class);
                startActivity(i);
            }
        });
        questionPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(StudentZone.this, QuestionPapersActivity.class);
                startActivity(i);
            }
        });
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StudentZone.this, AttendanceActivity.class);
                startActivity(i);
            }
        });
    }

}
