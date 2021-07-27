package com.example.studentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentapp.attendence.AttendanceActivity;
import com.example.studentapp.eSyllabus.SyllabusActivity;
import com.example.studentapp.ebook.EbookActivity;
import com.example.studentapp.eceattendance.EceAttendance;
import com.example.studentapp.eceebook.EceBook;
import com.example.studentapp.eceexam.EceExam;
import com.example.studentapp.eceresult.EceResult;
import com.example.studentapp.ecesyllabus.EceSyllabus;
import com.example.studentapp.eresult.Result;
import com.example.studentapp.questionpapers.QuestionPapersActivity;

public class StudentZoneEce extends AppCompatActivity {
    CardView books;
    CardView timetable;
    CardView syllabus;
    CardView questionPapers;
    CardView attendance;
    CardView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_zone_ece);
        books = findViewById(R.id.booksEce);
        timetable = findViewById(R.id.timetableEce);
        syllabus = findViewById(R.id.syllabusEce);
        questionPapers=findViewById(R.id.questionPapersEce);
        attendance=findViewById(R.id.attendanceEce);
        result=findViewById(R.id.resultEce);


        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentZoneEce.this, EceResult.class);
                startActivity(i);
                /*  Toast.makeText(StudentZone.this,"clicked",Toast.LENGTH_SHORT).show();*/
            }
        });



        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentZoneEce.this, EceBook.class);
                startActivity(i);
                /*  Toast.makeText(StudentZone.this,"clicked",Toast.LENGTH_SHORT).show();*/
            }
        });
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentZoneEce.this, EceTimetable.class);
                startActivity(i);
            }
        });
        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(StudentZoneEce.this, EceSyllabus.class);
                startActivity(i);
            }
        });
        questionPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(StudentZoneEce.this, EceExam.class);
                startActivity(i);
            }
        });
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StudentZoneEce.this, EceAttendance.class);
                startActivity(i);
            }
        });
    }

}

