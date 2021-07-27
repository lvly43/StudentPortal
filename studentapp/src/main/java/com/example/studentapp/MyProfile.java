package com.example.studentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyProfile extends AppCompatActivity {
    TextView profilename,profilenumber,profileaddress,profileemail;
    ImageView profilepic;
    Button profilebutton;
    String image;

    DatabaseReference ref,dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        profilename=findViewById(R.id.profilename);
        profilenumber=findViewById(R.id.profilenumber);
        profileaddress=findViewById(R.id.profileaddress);
        profileemail=findViewById(R.id.profileemail);
        profilepic=findViewById(R.id.profilepic);
        profilebutton=findViewById(R.id.profilebutton);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser currentuser =auth.getCurrentUser();
        String uid=currentuser.getUid();

        ref= FirebaseDatabase.getInstance().getReference("Users").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//        List<UserProfile>list=new ArrayList<>();

                ProfileData profileData=snapshot.getValue(ProfileData.class);
                profilename.setText(profileData.getName());
                profileemail.setText(profileData.getEmail());
                profileaddress.setText(profileData.getAddress());
                profilenumber.setText(profileData.getMobileNumber());
                image=profileData.getImage();


                try {
                    if(image!=null) {

                        Picasso.get().load(profileData.getImage()).into(profilepic);
                    }else
                    {
                        Picasso.get().load(R.drawable.male).into(profilepic);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyProfile.this,error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MyProfile.this,UpdateProfile.class);
                i.putExtra("name",profilename.getText().toString());
                i.putExtra("email",profileemail.getText().toString());
                i.putExtra("phone",profilenumber.getText().toString());
                i.putExtra("address",profileaddress.getText().toString());
                i.putExtra("image",image);

                startActivity(i);
            }
        });
    }
}