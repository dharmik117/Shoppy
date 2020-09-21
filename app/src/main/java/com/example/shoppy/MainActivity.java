package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Model.Sessions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private Button buttonfp;
    private TextView contactus;

    private EditText edittextemail;
    private EditText edittextpassword;
    private Button buttonsignin;
    private Button buttonsignup;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Homepage.class));
            finish();
        }

        edittextemail = (EditText)findViewById(R.id.emaillogin);
        edittextpassword =(EditText)findViewById(R.id.password);
        buttonsignin = (Button)findViewById(R.id.buttonsignin);
        buttonsignup = (Button)findViewById(R.id.btnSignUp);

        progressDialog = new ProgressDialog(this);

        buttonsignin.setOnClickListener(this);
        buttonsignup.setOnClickListener(this);

        buttonfp = findViewById(R.id.btnforgot);
        buttonfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openactivity_fppage();
            }
        });

        contactus = findViewById(R.id.btncontactus);
        contactus.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                openactivity_contactus();
            }
        });
    }


    public void openactivity_fppage() {
        Intent intent = new Intent(MainActivity.this, fppage.class);
        startActivity(intent);
    }



    public void openactivity_contactus() {
        Intent intent = new Intent(MainActivity.this, contactus.class);
        startActivity(intent);
    }

    private void userLogin(){

        String email = edittextemail.getText().toString().trim();
        String password = edittextpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){

            Toast.makeText(this,"Please Enter Email-Id",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      progressDialog.dismiss();
                      if(task.isSuccessful()){
                          startActivity(new Intent(getApplicationContext(),Homepage.class));
                          finish();
                      }
                      else
                      {
                          String message = task.getException().getMessage();
                          Toast.makeText(MainActivity.this, "Error" + message, Toast.LENGTH_LONG).show();
                      }
                    }
                });


    }

    @Override
    public void onClick(View view)
    {
        if (view == buttonsignin)
        {
            userLogin();
        }
        if (view == buttonsignup){
            startActivity(new Intent(this,registration.class));
            //finish();
        }
    }
}
