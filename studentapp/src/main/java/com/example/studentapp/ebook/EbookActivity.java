package com.example.studentapp.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.studentapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EbookActivity extends AppCompatActivity {
    private RecyclerView ebookRecycler;
    private DatabaseReference reference,dbRef;
    private List<EbookData> list;
    private EbookAdapter adapter;
    private LinearLayout csNoPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
        ebookRecycler=findViewById(R.id.ebookRecycler);
        csNoPdf=findViewById(R.id.csNoPdf);
        reference= FirebaseDatabase.getInstance().getReference().child("Books");

        getData();
    }

    private void getData() {
        dbRef=reference.child("Computer Science");
        /*reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                list=new ArrayList<>();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    EbookData data=snapshot1.getValue(EbookData.class);
                    list.add(data);
                }

                adapter=new EbookAdapter(EbookActivity.this,list);
                ebookRecycler.setLayoutManager(new LinearLayoutManager(EbookActivity.this));
                ebookRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EbookActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });*/
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
             list=new ArrayList<>();
             if(!snapshot.exists()){
                 csNoPdf.setVisibility(View.VISIBLE);
                 ebookRecycler.setVisibility(View.GONE);
             }
             else{
                 csNoPdf.setVisibility(View.GONE);
                 ebookRecycler.setVisibility(View.VISIBLE);
                 for(DataSnapshot snapshot1:snapshot.getChildren()){
                     EbookData data=snapshot1.getValue(EbookData.class);
                     list.add(data);
                 }
                 ebookRecycler.setHasFixedSize(true);
                 adapter=new EbookAdapter(EbookActivity.this,list);
                 ebookRecycler.setLayoutManager(new LinearLayoutManager(EbookActivity.this));
                 ebookRecycler.setAdapter(adapter);
             }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(EbookActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}