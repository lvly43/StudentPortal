package com.example.studentapp.eceexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.studentapp.R;
import com.example.studentapp.attendence.AttendanceAdapter;
import com.example.studentapp.ebook.EbookData;
import com.example.studentapp.questionpapers.QuestionPapersActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EceExam extends AppCompatActivity {
    private RecyclerView questionPapersRecyclerEce;
    private DatabaseReference reference,dbRef;
    private List<EbookData> list;
    private AttendanceAdapter adapter;
    private LinearLayout eceNoQuestionPapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ece_exam);
        questionPapersRecyclerEce=findViewById(R.id.questionPapersRecyclerEce);
        eceNoQuestionPapers=findViewById(R.id.eceNoPdf);
        reference= FirebaseDatabase.getInstance().getReference().child("Question Papers");

        getData();
    }

    private void getData() {
        dbRef=reference.child("Electronics & Communication");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                if(!snapshot.exists()){
                    eceNoQuestionPapers.setVisibility(View.VISIBLE);
                    questionPapersRecyclerEce.setVisibility(View.GONE);
                }
                else{
                    eceNoQuestionPapers.setVisibility(View.GONE);
                    questionPapersRecyclerEce.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        EbookData data=snapshot1.getValue(EbookData.class);
                        list.add(data);
                    }
                    questionPapersRecyclerEce.setHasFixedSize(true);
                    adapter=new AttendanceAdapter(EceExam.this,list);
                    questionPapersRecyclerEce.setLayoutManager(new LinearLayoutManager(EceExam.this));
                    questionPapersRecyclerEce.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EceExam.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

}