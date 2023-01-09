package com.example.comp2100groupproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnLogin, btnsignup;
    private EditText input_useremail, input_password;
    private ProgressBar progressbar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.user_button);
        btnLogin.setOnClickListener(this);
        btnsignup = (Button) findViewById(R.id.user_signup);
        btnsignup.setOnClickListener(this);

        input_useremail = (EditText) findViewById(R.id.user_useremail);
        input_password = (EditText) findViewById(R.id.user_password);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_button:
                signin();
                break;
            case R.id.user_signup:
                signup();
                break;
        }
    }

    private void signup() {
        final String email = input_useremail.getText().toString();
        final String password = input_password.getText().toString();

        if (email.isEmpty()) {
            Context c = getApplicationContext();
            CharSequence text = "email is empty";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(c, text, duration);
            toast.show();
            return;
        }
        if (password.isEmpty()) {
            Context c = getApplicationContext();
            CharSequence text = "password is empty";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(c, text, duration);
            toast.show();
            return;
        }
        if (password.length()<6) {
            Context c = getApplicationContext();
            CharSequence text = "password must longer than 6 digit ";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(c, text, duration);
            toast.show();
            return;
        }

            Context c = getApplicationContext();
            CharSequence text = "success to sign up";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(c, text, duration);
            toast.show();
    }

    private void signin() {
        final String email = input_useremail.getText().toString();
        final String password = input_password.getText().toString();

        Context c = getApplicationContext();
        CharSequence text = "success to sign up";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(c, text, duration);
        toast.show();

    }

    public void updateUI(FirebaseUser account){
        startActivity(new Intent(this,MainActivity.class));


    }


}