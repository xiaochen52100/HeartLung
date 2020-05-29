package com.example.qi378.heartlung;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class Signin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        MyApplication.getInstance().addActivity(this);
    }
}
