package com.example.studentapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateProfile extends AppCompatActivity {
    private static final String TAG = "tag";
    EditText edit1,edit3,edit4;
    ImageView imgProfile;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser currentUser;
    StorageReference storageReference;
    Uri pickedImgUri ;
    final static int REQ=1;
    Bitmap bitmap;
    String name,email,number,address,image;
    Button savebtn;
    DatabaseReference reference,dbRef;
    String myProfileImageLink;
    String uid;
    String downloadUrl="";
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
pd=new ProgressDialog(this);
        edit1=findViewById(R.id.et1);
        edit3=findViewById(R.id.et3);
        edit4=findViewById(R.id.et4);
        savebtn=findViewById(R.id.btn);
        imgProfile=findViewById(R.id.imgProfile);
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mFirebaseAuth.getCurrentUser();
        uid=currentUser.getUid();
        reference =FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();


        Intent data=getIntent();
        name=data.getStringExtra("name");
        number=data.getStringExtra("phone");
        address=data.getStringExtra("address");
        edit1.setText(name);
        edit3.setText(number);
        edit4.setText(address);
        image=getIntent().getStringExtra("image");
        try {
            Glide.with(this).load(image).into(imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=edit1.getText().toString();
                number=edit3.getText().toString();
                address=edit4.getText().toString();

                checkValidation();
            }
        });

    }
    private void checkValidation() {
        if(name.isEmpty()){
            edit1.setError("empty");
            edit1.requestFocus();
        }

        else if(number.isEmpty()){
           edit3.setError("empty");
            edit3.requestFocus();
        }
        else if(number.isEmpty()){
            edit4.setError("empty");
            edit4.requestFocus();
        }

        else if(bitmap==null){
            updateData(myProfileImageLink);
        }
        else{
            uploadImage();
        }
    }

    private void uploadImage() {
        pd.setMessage("Update details...");
        pd.show();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg=baos.toByteArray();

        /*    Log.d(TAG,"message is"+finalimg);*/


        final StorageReference filepath;
        filepath=storageReference.child("Students/").child(uid).child(finalimg+"jpg");
        final UploadTask uploadTask=filepath.putBytes(finalimg);

        /*Log.d(TAG,"message:"+uploadTask);*/
        uploadTask.addOnCompleteListener(UpdateProfile.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    updateData(downloadUrl);

                                    /* Log.d(TAG,"image is"+downloadUrl);*/
                                }
                            });
                        }
                    });
                }
                else
                {
                    pd.dismiss();
                    Toast.makeText(UpdateProfile.this,"went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private  void updateData(String s){
        dbRef=reference.child("Users");
        HashMap hp=new HashMap();
        hp.put("name",name);
        hp.put("address",address);
        hp.put("mobileNumber",number);
        hp.put("image",s);



        dbRef.child(uid).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                pd.dismiss();
                Toast.makeText(UpdateProfile.this,"Profile Updated succesfully",Toast.LENGTH_LONG).show();

                Intent intent=new Intent(UpdateProfile.this,MyProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateProfile.this,"Something wrong",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openGallery() {
        Intent pickImage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
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
            imgProfile.setImageBitmap(bitmap);
        }
    }
}