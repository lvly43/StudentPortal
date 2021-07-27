package com.example.studentapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class  SetupProfile extends AppCompatActivity {
String TAG="tag";
    private ImageView ImgUserPhoto;
    static int REQ = 1;
    Uri pickedImgUri ;

    ProgressDialog progressDialog;

    private EditText userName,etEnterMobile,etEnterAddress;
    private Button regBtn;

    Bitmap bitmap;

    String name,mobileNumber,address,downloadUrl="";

    DatabaseReference reference,dbRef;
    StorageReference storageReference;


    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);

        progressDialog=new ProgressDialog(this);

        //init firebase

        firebaseAuth = FirebaseAuth.getInstance();





         currentUser = FirebaseAuth.getInstance().getCurrentUser();
         uid=currentUser.getUid();


        reference =FirebaseDatabase.getInstance().getReference();


        storageReference= FirebaseStorage.getInstance().getReference();


        //init views

        userName = findViewById(R.id.etEnterName);
        etEnterMobile=findViewById(R.id.etEnterMobile);
        etEnterAddress=findViewById(R.id.etEnterAddress);

        regBtn = findViewById(R.id.btnNext);
        ImgUserPhoto = findViewById(R.id.user123) ;
        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               openGallery();


            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }

        private void checkValidation() {
            name=userName.getText().toString();
            mobileNumber=etEnterMobile.getText().toString();
            address=etEnterAddress.getText().toString();

            if(name.isEmpty()){
                userName.setError("Name Missing!");
                userName.requestFocus();
            }
            else if(mobileNumber.isEmpty()||mobileNumber.length() != 10){
                etEnterMobile.setError("Invalid Mobile Number!");
                etEnterMobile.requestFocus();
            }
            else if(address.isEmpty()){
                etEnterAddress.setError("Address Missing!");
                etEnterAddress.requestFocus();
            }

            else if(bitmap==null){
                insertData();
            }
            else{
                uploadImage();
            }


        }

    private void insertData() {
dbRef=reference.child("Users");

    /* final  String uniquekey=dbRef.push().getKey();*/

        String name=userName.getText().toString();
        String mobileNumber=etEnterMobile.getText().toString();
        String address=etEnterAddress.getText().toString();
        String email=currentUser.getEmail();


        ProfileData profileData=new ProfileData(name,mobileNumber,address,downloadUrl,uid,email);

        dbRef.child(uid).setValue(profileData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(SetupProfile.this,"details added",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SetupProfile.this,DashBoard.class);
                startActivity(intent);

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SetupProfile.this,"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void uploadImage() {
        progressDialog.setMessage("Uploading..");
        progressDialog.show();


        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg=baos.toByteArray();


            Log.d(TAG,"message is"+finalimg);


        final StorageReference filepath;
        filepath=storageReference.child("Students/").child(uid).child(finalimg+"jpg");
        final UploadTask uploadTask=filepath.putBytes(finalimg);

        /*Log.d(TAG,"message:"+uploadTask);*/
        uploadTask.addOnCompleteListener(SetupProfile.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf(uri);
                                    insertData();

                                     Log.d(TAG,"image is"+downloadUrl);
                                }
                            });
                        }
                    });
                }
                else
                {progressDialog.dismiss();
                    Toast.makeText(SetupProfile.this,"went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





    private void openGallery() {
 /*       Intent pickImage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);*/

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQ);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ&&resultCode==RESULT_OK){
            Uri uri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImgUserPhoto.setImageBitmap(bitmap);
        }
    }


}
