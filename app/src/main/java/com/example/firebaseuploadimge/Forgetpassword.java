package com.example.firebaseuploadimge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Forgetpassword extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
    Button buttonforget;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        editText = findViewById(R.id.EmailForgetId);
        buttonforget = findViewById(R.id.resetbtn);
        buttonforget.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        String email = editText.getText().toString().trim();

//        firebaseAuth.sendPasswordResetEmail(email).
//                addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            FancyToast.makeText(Forgetpassword.this, "Success", FancyToast.
//                                    LENGTH_LONG, FancyToast.SUCCESS, true).show();
//
//                        } else {
//                            FancyToast.makeText(Forgetpassword.this, "Already have a account " + task, FancyToast.
//                                    LENGTH_LONG, FancyToast.ERROR, true).show();
//
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                FancyToast.makeText(Forgetpassword.this, "Exception.." + e, FancyToast.
//                        LENGTH_LONG, FancyToast.ERROR, true).show();
//
//            }
//        });


        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            FancyToast.makeText(Forgetpassword.this, "Success", FancyToast.
                                    LENGTH_LONG, FancyToast.SUCCESS, true).show();

                        }
                    }
                });
    }
}


