package com.example.firebaseuploadimge;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class showData extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    List<Student> studentList;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        recyclerView = findViewById(R.id.listviewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Student");


        //database e data change hole ei listerner calll hobe
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//datasnap shot e shob data thake


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Student student = dataSnapshot1.getValue(Student.class);
                    studentList.add(student);
                }
                customAdapter = new CustomAdapter(showData.this, studentList);
                recyclerView.setAdapter(customAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                FancyToast.makeText(showData.this,
                        "Faild show datacancel " + databaseError, FancyToast.
                                LENGTH_LONG, FancyToast.ERROR, true).show();


            }
        });


    }
}
