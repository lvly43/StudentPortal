package com.example.studentapp.questionpapers;

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

public class QuestionPapersActivity extends AppCompatActivity {
    private RecyclerView questionPapersRecycler;
    private DatabaseReference reference,dbRef;
    private List<EbookData> list;
    private AttendanceAdapter adapter;
    private LinearLayout csNoQuestionPapers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_papers);
        questionPapersRecycler=findViewById(R.id.questionPapersRecycler);
        csNoQuestionPapers=findViewById(R.id.csNoPdf);
        reference= FirebaseDatabase.getInstance().getReference().child("Question Papers");

        getData();
    }

    private void getData() {
        dbRef=reference.child("Computer Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                if(!snapshot.exists()){
                    csNoQuestionPapers.setVisibility(View.VISIBLE);
                    questionPapersRecycler.setVisibility(View.GONE);
                }
                else{
                    csNoQuestionPapers.setVisibility(View.GONE);
                    questionPapersRecycler.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        EbookData data=snapshot1.getValue(EbookData.class);
                        list.add(data);
                    }
                    questionPapersRecycler.setHasFixedSize(true);
                    adapter=new AttendanceAdapter(QuestionPapersActivity.this,list);
                    questionPapersRecycler.setLayoutManager(new LinearLayoutManager(QuestionPapersActivity.this));
                    questionPapersRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuestionPapersActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

}