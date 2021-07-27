package com.example.studentapp.eSyllabus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.studentapp.R;
import com.example.studentapp.ebook.EbookActivity;
import com.example.studentapp.ebook.EbookAdapter;
import com.example.studentapp.ebook.EbookData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SyllabusActivity extends AppCompatActivity {
    private RecyclerView syllabusRecycler;
    private DatabaseReference reference,dbRef;
    private List<EbookData> list;
    private SyllabusAdapter adapter;
    private LinearLayout csNoSyllabus;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        syllabusRecycler=findViewById(R.id.syllabusRecycler);
        csNoSyllabus=findViewById(R.id.csNoSyllabus);
        reference= FirebaseDatabase.getInstance().getReference().child("Syllabus");

        getData();
    }

    private void getData() {
        dbRef=reference.child("Computer Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                if(!snapshot.exists()){
                    csNoSyllabus.setVisibility(View.VISIBLE);
                    syllabusRecycler.setVisibility(View.GONE);
                }
                else{
                    csNoSyllabus.setVisibility(View.GONE);
                    syllabusRecycler.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        EbookData data=snapshot1.getValue(EbookData.class);
                        list.add(data);
                    }
                    syllabusRecycler.setHasFixedSize(true);
                    adapter=new SyllabusAdapter(SyllabusActivity.this,list);
                    syllabusRecycler.setLayoutManager(new LinearLayoutManager(SyllabusActivity.this));
                    syllabusRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SyllabusActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

}