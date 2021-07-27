package com.example.studentapp.eceattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.studentapp.R;
import com.example.studentapp.attendence.AttendanceActivity;
import com.example.studentapp.attendence.AttendanceAdapter;
import com.example.studentapp.ebook.EbookData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EceAttendance extends AppCompatActivity {
    private RecyclerView attendanceRecyclerEce;
    private DatabaseReference reference,dbRef;
    private List<EbookData> list;
    private AttendanceAdapter adapter;
    private LinearLayout EceNoAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ece_attendance);
        attendanceRecyclerEce=findViewById(R.id.attendanceRecyclerEce);
        EceNoAttendance=findViewById(R.id.eceNoPdf);
        reference= FirebaseDatabase.getInstance().getReference().child("Attendance");

        getData();
    }

    private void getData() {
        dbRef=reference.child("Electronics & Communication");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                if(!snapshot.exists()){
                    EceNoAttendance.setVisibility(View.VISIBLE);
                    attendanceRecyclerEce.setVisibility(View.GONE);
                }
                else{
                    EceNoAttendance.setVisibility(View.GONE);
                    attendanceRecyclerEce.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        EbookData data=snapshot1.getValue(EbookData.class);
                        list.add(data);
                    }
                    attendanceRecyclerEce.setHasFixedSize(true);
                    adapter=new AttendanceAdapter(EceAttendance.this,list);
                    attendanceRecyclerEce.setLayoutManager(new LinearLayoutManager(EceAttendance.this));
                    attendanceRecyclerEce.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EceAttendance.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

}