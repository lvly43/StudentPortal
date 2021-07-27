package com.example.studentapp.eresult;

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

public class Result extends AppCompatActivity {
    private RecyclerView resultRecycler;
    private DatabaseReference reference,dbRef;
    private List<EbookData> list;
    private ResultAdapter adapter;
    private LinearLayout csNoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultRecycler=findViewById(R.id.resultRecycler);
        csNoResult=findViewById(R.id.csNoResult);
        reference= FirebaseDatabase.getInstance().getReference().child("Result");

        getData();
    }

    private void getData() {
        dbRef=reference.child("Computer Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                if(!snapshot.exists()){
                    csNoResult.setVisibility(View.VISIBLE);
                    resultRecycler.setVisibility(View.GONE);
                }
                else{
                    csNoResult.setVisibility(View.GONE);
                    resultRecycler.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        EbookData data=snapshot1.getValue(EbookData.class);
                        list.add(data);
                    }
                    resultRecycler.setHasFixedSize(true);
                    adapter=new ResultAdapter(Result.this,list);
                    resultRecycler.setLayoutManager(new LinearLayoutManager(Result.this));
                    resultRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Result.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

}