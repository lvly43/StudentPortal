package com.example.studentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.number.NumberFormatter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public CardView cardViewCse, cardViewEce, cardViewElectrical, cardViewMechanical;
    TextView navUserMail;
    ImageView navUserPhoto;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    GridLayout gridLayout;
    NavigationView navigationView;
    DrawerLayout drawer_layout;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser currentUser;
    DatabaseReference reference,dbRef;
    StorageReference storageReference;

    String navigationImage,navigationName,navigationEmail,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        gridLayout=findViewById(R.id.mainGrid);
        cardViewCse = findViewById(R.id.cardViewCse);
        cardViewEce = findViewById(R.id.cardViewEce);
        toolbar = findViewById(R.id.toolbar);



        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mFirebaseAuth.getCurrentUser();


        reference = FirebaseDatabase.getInstance().getReference().child("Users");


        storageReference= FirebaseStorage.getInstance().getReference();


        drawer_layout = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close);
        drawer_layout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.navigation_drawer);
        View view=navigationView.getHeaderView(0);
        navUserMail=view.findViewById(R.id.nav_header_user_email);
        navUserPhoto=view.findViewById(R.id.nav_header_user_image);


        navigationView.setNavigationItemSelectedListener(this);


        cardViewCse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showToast("home clicked");
                Intent i = new Intent(DashBoard.this, StudentZone.class);
                startActivity(i);
            }
        });
        cardViewEce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showToast("home clicked");
                Intent i = new Intent(DashBoard.this, StudentZoneEce.class);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser==null){
            sendUserToLoginActivity();
        }
        else{
            dbRef=reference.child(currentUser.getUid());
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//        List<UserProfile>list=new ArrayList<>();

                    ProfileData profileData=snapshot.getValue(ProfileData.class);

                    navUserMail.setText(profileData.getEmail());
                    image=profileData.getImage();


                    try {
                        if(image!=null) {

                            Picasso.get().load(profileData.getImage()).into(navUserPhoto);
                        }else
                        {
                            Picasso.get().load(R.drawable.male).into(navUserPhoto);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DashBoard.this,error.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sendUserToLoginActivity() {
        Intent intent=new Intent(DashBoard.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutApp: {
                Intent intent = new Intent(DashBoard.this, AboutApp.class);
                startActivity(intent);
              /*  Toast.makeText(DashBoard.this, "aboutt", Toast.LENGTH_LONG).show();*/
                break;
            }
            case R.id.myProfile: {
                Intent intent = new Intent(DashBoard.this, MyProfile.class);
                startActivity(intent);
                break;

            } case R.id.developedBy:{
                Intent intent = new Intent(DashBoard.this, Developer.class);
                startActivity(intent);
                 break;
            }
             case R.id.share: {
            ShowShareActivity();
            break;

        } case R.id.logout: {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
                builder.setCancelable(false)
                        .setMessage("Are you sure you want to  log out")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intToMain = new Intent(DashBoard.this, MainActivity.class);
                                startActivity(intToMain);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }


        }

        return true;
}

    private void ShowShareActivity() {try {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Student Portal");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share with"));
    }
    catch (Exception e)
    {
        Toast.makeText(this,"error in sharing",Toast.LENGTH_LONG).show();
    }

    }
}