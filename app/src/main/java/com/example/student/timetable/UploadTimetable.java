package com.example.student.timetable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.student.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UploadTimetable extends AppCompatActivity {
    Spinner imageCategory;
    Button uploadImage;
    ImageView galleryImageView;
   CardView selectImage;

String title;
    DatabaseReference reference,dbRef;
    StorageReference storageReference;
    String downloadUrl;
private ProgressDialog pd;
    /* CardView selectImage*/;
String TAG="tag";
    static int REQ=1;
    Bitmap bitmap;

    public  String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_timetable);


        selectImage=findViewById(R.id.selectImage);
        galleryImageView=findViewById(R.id.galleryImageView);
        uploadImage=findViewById(R.id.uploadImage);
        imageCategory=findViewById(R.id.imageCategory);

        pd=new ProgressDialog(this);

 reference= FirebaseDatabase.getInstance().getReference().child("Timetable");

        storageReference= FirebaseStorage.getInstance().getReference().child("Timetable");



        String []item=new String[]{"Select Category","Computer Science","Electronics & Communication"};
        ArrayAdapter<String> adapter=(new ArrayAdapter<String>(UploadTimetable.this, android.R.layout.simple_spinner_dropdown_item,item));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageCategory.setAdapter(adapter);

        imageCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             category=imageCategory.getSelectedItem().toString();
                /*Toast.makeText(UploadImagee.this,item[position],Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap==null){
                    Toast.makeText(UploadTimetable.this,"Please select image",Toast.LENGTH_SHORT).show();
                }
                else if(category.equals("Select Category")){
                    Toast.makeText(UploadTimetable.this,"Please select image category",Toast.LENGTH_LONG).show();
                }
                else{
                    uploadImage();
                }
            }
        });


        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }
    private void uploadImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg=baos.toByteArray();

        Log.d(TAG,"message is"+finalimg);


        final StorageReference filepath;
        filepath=storageReference.child(finalimg+"jpg");
        final UploadTask uploadTask=filepath.putBytes(finalimg);

        Log.d(TAG,"message:"+uploadTask);
        uploadTask.addOnCompleteListener(UploadTimetable.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    uploadData();

                                    /* Log.d(TAG,"image is"+downloadUrl);*/
                                }
                            });
                        }
                    });
                }
                else
                { pd.dismiss();
                    Toast.makeText(UploadTimetable.this,"went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadData() {
        dbRef=reference.child(category);
        final  String uniquekey=dbRef.push().getKey();
        HashMap data=new HashMap();
        data.put("Image",downloadUrl);
        dbRef.child(uniquekey).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(UploadTimetable.this,"Uploaded successfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadTimetable.this,"Not uploaded successfully",Toast.LENGTH_SHORT).show();
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
            galleryImageView.setImageBitmap(bitmap);
        }
    }
}
