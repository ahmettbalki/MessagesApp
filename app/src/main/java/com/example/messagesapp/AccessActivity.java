package com.example.messagesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class AccessActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        findViewById(R.id.btn_signup).setOnClickListener(view -> {
            startActivity(new Intent(AccessActivity.this,SignupActivity.class));
        });
        findViewById(R.id.btn_login).setOnClickListener(view -> {
            startActivity(new Intent(AccessActivity.this,LoginActivity.class));
        });

    }
}