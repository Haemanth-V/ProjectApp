package com.example.projectapp.view.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectapp.view.MainActivity;
import com.example.projectapp.R;
import com.example.projectapp.view.auth.PhoneLogin;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        Button button = findViewById(R.id.btnAgreeAndCont);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this, PhoneLogin.class));
                finish();
            }
        });
    }
}