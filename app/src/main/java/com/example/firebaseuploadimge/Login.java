package com.example.firebaseuploadimge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button SignUpbtn, login,reset;

    EditText UsernameTv, EmailTv, PaswordTv;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        login = findViewById(R.id.siginId);
        SignUpbtn = findViewById(R.id.signupId);
        reset = findViewById(R.id.forgetpasswordIinLoginId);


        mAuth = FirebaseAuth.getInstance();
        EmailTv = findViewById(R.id.edtEmailSigninid);
        PaswordTv = findViewById(R.id.edtSigninPasswordId);



        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(this);
        SignUpbtn.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.siginId:

                String em = EmailTv.getText().toString();
                String pass = PaswordTv.getText().toString();

                if (em.isEmpty()) {
                    EmailTv.setError("enter valid email");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
                    EmailTv.setError("enter valid email");
                    return;
                }
                if (pass.isEmpty()) {
                    PaswordTv.setError("enterpassword");
                    return;
                }
                if (pass.length() < 6) {
                    PaswordTv.setError("enter 6 digta valid pass");
                    return;
                }
                progressDialog = new ProgressDialog(this);

                progressDialog.setMessage("Logn.. ....");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(em, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FancyToast.makeText(Login.this, "Success", FancyToast.
                                                    LENGTH_LONG, FancyToast.SUCCESS, true).show();


                                            Intent intent = new Intent(Login.this, profile.class);
                                            startActivity(intent);

                                        } else {

                                            FancyToast.makeText(getApplicationContext(), task.getException() + " Faild to login", FancyToast.
                                                    LENGTH_LONG, FancyToast.ERROR, true).show();
                                        }


                                        progressDialog.dismiss();
                                    }
                                }
                        );
                break;
            case R.id.signupId:
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.forgetpasswordIinLoginId:
                Intent intent1 = new Intent(Login.this, Forgetpassword.class);
                startActivity(intent1);
        }
    }
}

