package com.example.studentapp.eceebook;

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

public class EceBook extends AppCompatActivity {
    private RecyclerView ebookRecyclerEce;
    private DatabaseReference reference,dbRef;
    private List<EbookData> list;
    private EceBookAdapter adapter;
    private LinearLayout eceNoPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ece_book);
        ebookRecyclerEce=findViewById(R.id.ebookRecyclerEce);
        eceNoPdf=findViewById(R.id.eceNoPdf);
        reference= FirebaseDatabase.getInstance().getReference().child("Books");

        getData();
    }

    private void getData() {
        dbRef=reference.child("Electronics & Communication");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                if(!snapshot.exists()){
                    eceNoPdf.setVisibility(View.VISIBLE);
                    ebookRecyclerEce.setVisibility(View.GONE);
                }
                else{
                    eceNoPdf.setVisibility(View.GONE);
                    ebookRecyclerEce.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        EbookData data=snapshot1.getValue(EbookData.class);
                        list.add(data);
                    }
                    ebookRecyclerEce.setHasFixedSize(true);
                    adapter=new EceBookAdapter(EceBook.this,list);
                    ebookRecyclerEce.setLayoutManager(new LinearLayoutManager(EceBook.this));
                    ebookRecyclerEce.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EceBook.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}