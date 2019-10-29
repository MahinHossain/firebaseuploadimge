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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button SignUpbtn, SignInbtn;
    EditText UsernameTv, EmailTv, PaswordTv;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignInbtn = findViewById(R.id.signupId);
        SignUpbtn = findViewById(R.id.siginId);
        setTitle("Sign up");

        mAuth = FirebaseAuth.getInstance();
        EmailTv = findViewById(R.id.edtEmailSignupid);
        PaswordTv = findViewById(R.id.edtSigninPasswordId);

        SignInbtn.setOnClickListener(this);
        SignUpbtn.setOnClickListener(this);


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

                progressDialog.setMessage("Signup.. ....");
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(em, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FancyToast.makeText(MainActivity.this, "Success", FancyToast.
                                                    LENGTH_LONG, FancyToast.SUCCESS, true).show();

                                        } else {
                                            // If sign in fails, display a message to the user.


                                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                                FancyToast.makeText(getApplicationContext(), "User Already Exits", FancyToast.
                                                        LENGTH_LONG, FancyToast.ERROR, true).show();
                                            }
                                            else
                                            {
                                                FancyToast.makeText(getApplicationContext(), task.getException()+" Faild", FancyToast.
                                                        LENGTH_LONG, FancyToast.ERROR, true).show();
                                            }

                                        }
                                        progressDialog.dismiss();
                                    }
                                }
                        );
                break;
            case R.id.signupId:
                LoginMethod();
                break;
        }
    }

    private void LoginMethod() {
        Intent intent=new Intent(MainActivity.this,Login.class);
        startActivity(intent);
    }


}
