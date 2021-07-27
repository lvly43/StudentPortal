package com.example.studentapp.attendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.studentapp.R;
import com.example.studentapp.ebook.EbookData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
    private RecyclerView attendanceRecycler;
    private DatabaseReference reference,dbRef;
    private List<EbookData> list;
    private AttendanceAdapter adapter;
    private LinearLayout csNoAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        attendanceRecycler=findViewById(R.id.attendanceRecycler);
        csNoAttendance=findViewById(R.id.csNoPdf);
        reference= FirebaseDatabase.getInstance().getReference().child("Attendance");

        getData();
    }

    private void getData() {
        dbRef=reference.child("Computer Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                if(!snapshot.exists()){
                    csNoAttendance.setVisibility(View.VISIBLE);
                    attendanceRecycler.setVisibility(View.GONE);
                }
                else{
                    csNoAttendance.setVisibility(View.GONE);
                    attendanceRecycler.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        EbookData data=snapshot1.getValue(EbookData.class);
                        list.add(data);
                    }
                    attendanceRecycler.setHasFixedSize(true);
                    adapter=new AttendanceAdapter(AttendanceActivity.this,list);
                    attendanceRecycler.setLayoutManager(new LinearLayoutManager(AttendanceActivity.this));
                    attendanceRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AttendanceActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

}