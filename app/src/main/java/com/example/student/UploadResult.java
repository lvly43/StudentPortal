package com.example.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class UploadResult extends AppCompatActivity {
    CardView addPdf;
    Button uploadPdfButoon;
    TextView pdfTextView;
    Spinner addPdfCategory;
    EditText syllabusTitle;
    public String category;
    String title;      // to store title name in firebase
    public static int REQ = 1;
    private Uri pdfData;
    private ProgressDialog pd;

    DatabaseReference databaseReference, dbRef;
    StorageReference storageReference;

    String pdfName;   //to store the name of the pdf file that is selected from file manager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_result);
        syllabusTitle=findViewById(R.id.syllabusTitle);
        addPdfCategory = findViewById(R.id.addPdfCategory);
        databaseReference = FirebaseDatabase.getInstance().getReference("Result");
        pd=new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        addPdf = findViewById(R.id.addPdf);
        uploadPdfButoon = findViewById(R.id.uploadPdfButton);
        pdfTextView = findViewById(R.id.pdfTextView);


        String[] item = new String[]{"Select Category", "Computer Science","Electronics & Communication"};
        ArrayAdapter<String> adapter = (new ArrayAdapter<String>(UploadResult.this, android.R.layout.simple_spinner_dropdown_item, item));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addPdfCategory.setAdapter(adapter);

        addPdfCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addPdfCategory.getSelectedItem().toString();
                /*Toast.makeText(UploadImagee.this,item[position],Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        uploadPdfButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=syllabusTitle.getText().toString();
                if(title.isEmpty()){
                    syllabusTitle.setError("Enter title");
                    syllabusTitle.requestFocus();
                }
                else if (category.equals("Select Category")) {
                    Toast.makeText(UploadResult.this, "Please select category", Toast.LENGTH_LONG).show();
                } else if (pdfData == null) {
                    Toast.makeText(UploadResult.this, "Please upload pdf file", Toast.LENGTH_LONG).show();
                } else {
                    uploadPdf();
                }
            }
        });


    }

    private void uploadPdf() {
        pd.setMessage("Uploading...");
        pd.show();
        StorageReference reference = storageReference.child("pdf/" + pdfName + "-" + System.currentTimeMillis() + ".pdf");
        reference.putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri uri = uriTask.getResult();
                uploadData(String.valueOf(uri));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadResult.this, "error in upload pdf", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void uploadData(String downloadUrl) {
        dbRef = databaseReference.child(category);
        String uniqueKey = dbRef.push().getKey();
        HashMap data = new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl", downloadUrl);
        dbRef.child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadResult.this, "upload pdf", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadResult.this, "error....", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("pdf/docs/ppt");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select pdf file"), REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQ && resultCode == RESULT_OK) {
            pdfData = data.getData();
            /* Toast.makeText(UploadPdfActivity.this,""+pdfData,Toast.LENGTH_LONG).show(); //it will give file name in content or file;//*/

            if (pdfData.toString().startsWith("content://")) {
                Cursor cursor = null;
                cursor = UploadResult.this.getContentResolver().query(pdfData, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } else if (pdfData.toString().startsWith("file://")) {
                pdfName = new File(pdfData.toString()).getName();
            }
            pdfTextView.setText(pdfName);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}