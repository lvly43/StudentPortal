package com.example.studentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Timetable extends AppCompatActivity {
ImageView cseTimeTable;
    FirebaseUser currentUser;
    String address;
    DatabaseReference reference,dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        cseTimeTable=findViewById(R.id.cseTimeTable);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("Timetable");
        getTimeTable();
    }

    private void getTimeTable() {
        dbref=reference.child("Computer Science");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    address = ds.child("Image").getValue(String.class);
                    Picasso.get().load(address).into(cseTimeTable);

                    cseTimeTable.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Timetable.this,FullImageView.class);
                            intent.putExtra("image",address);
                            startActivity(intent);
                        }
                    });



                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(Timetable.this,"Data is not fetched",Toast.LENGTH_LONG).show();
            }
        });
    }
}