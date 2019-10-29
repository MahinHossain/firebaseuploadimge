package com.example.firebaseuploadimge;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

public class profile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    Button save, imagechoose, showdata;
    EditText age, discrip, name;
    static final int Image_request = 1;
    StorageReference storageReference;
    ImageView imageView;
    Uri imageUri;
    StorageTask storageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.imagePhotoId);

        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        storageReference = FirebaseStorage.getInstance().getReference("Student");


        save = findViewById(R.id.saveid);
        imagechoose = findViewById(R.id.imagechooseId);

        discrip = findViewById(R.id.desccripId);
        showdata = findViewById(R.id.loadDataid);
        age = findViewById(R.id.ageid);
        name = findViewById(R.id.nameid);

        imagechoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Image_request);

            }

        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String imageName = discrip.getText().toString().trim();
                final String name1 = name.getText().toString();
                final String age1 = age.getText().toString();


                StorageReference reference = storageReference.child(System.currentTimeMillis() +
                        "." + getfileextension(imageUri));
                progressDialog = new ProgressDialog(profile.this);

                progressDialog.setMessage("Uploading.. ....");
                progressDialog.show();
                reference.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content

                                Task<Uri> ulrTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!ulrTask.isSuccessful()) ;
                                Uri downloaduri = ulrTask.getResult();


                                Student student = new Student(name1, age1, imageName, downloaduri.toString());
                                String key = databaseReference.push().getKey();
                                databaseReference.child(key).setValue(student);
                                FancyToast.makeText(getApplicationContext(),
                                        " Data saved successsfully ", FancyToast.
                                                LENGTH_LONG, FancyToast.SUCCESS, true).show();


                                progressDialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                FancyToast.makeText(getApplicationContext(),
                                        " Data not saved ", FancyToast.
                                                LENGTH_LONG, FancyToast.ERROR, true).show();

                                // ...
                            }
                        });


                name.setText("");
                age.setText("");


            }
        });

        showdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(profile.this, showData.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menulogoutid) {
            FirebaseAuth.getInstance().signOut();
            finish();

            Intent intent = new Intent(profile.this, MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_request && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {

            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(imageView);

        }
    }
    //getting extension og image


    String getfileextension(Uri imageUri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }
}
