package com.example.user.xolostyak20;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Button log,reg;
    EditText mail,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        log = (Button) findViewById(R.id.login);
        reg = (Button) findViewById(R.id.reg);
        mail = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Intent i = new Intent(SignInActivity.this,SearchActivity.class);
                    startActivity(i);
                }else{

                }
            }
        };
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent i = new Intent(SignInActivity.this,SearchActivity.class);
            startActivity(i);
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signing(mail.getText().toString(),pass.getText().toString());
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration(mail.getText().toString(),pass.getText().toString());
            }
        });
    }
    public void signing(String mail,String pass){
        mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignInActivity.this,"Login succesfull!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SignInActivity.this,"Login not succesfull!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void registration(String mail, String pass){
        mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignInActivity.this,"Registration succesfull!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignInActivity.this,"Registration not succesfull!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
