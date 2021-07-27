package com.example.studentapp.ecesyllabus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.studentapp.R;
import com.example.studentapp.eSyllabus.SyllabusActivity;
import com.example.studentapp.eSyllabus.SyllabusAdapter;
import com.example.studentapp.ebook.EbookData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EceSyllabus extends AppCompatActivity {
    private RecyclerView syllabusRecyclerEce;
    private DatabaseReference reference,dbRef;
    private List<EbookData> list;
    private SyllabusAdapter adapter;
    private LinearLayout eceNoSyllabus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ece_syllabus);
        syllabusRecyclerEce=findViewById(R.id.syllabusRecyclerEce);
        eceNoSyllabus=findViewById(R.id.eceNoSyllabus);
        reference= FirebaseDatabase.getInstance().getReference().child("Syllabus");

        getData();
    }

    private void getData() {
        dbRef=reference.child("Electronics & Communication");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                if(!snapshot.exists()){
                    eceNoSyllabus.setVisibility(View.VISIBLE);
                    syllabusRecyclerEce.setVisibility(View.GONE);
                }
                else{
                    eceNoSyllabus.setVisibility(View.GONE);
                    syllabusRecyclerEce.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        EbookData data=snapshot1.getValue(EbookData.class);
                        list.add(data);
                    }
                    syllabusRecyclerEce.setHasFixedSize(true);
                    adapter=new SyllabusAdapter(EceSyllabus.this,list);
                    syllabusRecyclerEce.setLayoutManager(new LinearLayoutManager(EceSyllabus.this));
                    syllabusRecyclerEce.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EceSyllabus.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

}