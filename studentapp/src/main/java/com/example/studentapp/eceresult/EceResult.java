package com.example.studentapp.eceresult;

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
import com.example.studentapp.eresult.Result;
import com.example.studentapp.eresult.ResultAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EceResult extends AppCompatActivity {
    private RecyclerView resultRecyclerEce;
    private DatabaseReference reference,dbRef;
    private List<EbookData> list;
    private ResultAdapter adapter;
    private LinearLayout eceNoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ece_result);
        resultRecyclerEce=findViewById(R.id. resultRecyclerEce);
        eceNoResult=findViewById(R.id.eceNoResult);
        reference= FirebaseDatabase.getInstance().getReference().child("Result");
        getData();
    }

    private void getData() {
        dbRef=reference.child("Electronics & Communication");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                if(!snapshot.exists()){
                    eceNoResult.setVisibility(View.VISIBLE);
                    resultRecyclerEce.setVisibility(View.GONE);
                }
                else{
                    eceNoResult.setVisibility(View.GONE);
                    resultRecyclerEce.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        EbookData data=snapshot1.getValue(EbookData.class);
                        list.add(data);
                    }
                    resultRecyclerEce.setHasFixedSize(true);
                    adapter=new ResultAdapter(EceResult.this,list);
                    resultRecyclerEce.setLayoutManager(new LinearLayoutManager(EceResult.this));
                    resultRecyclerEce.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EceResult.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

}