package com.mak.pcr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class FacultySplashScreen extends AppCompatActivity {

    private final int DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_splash_screen);

        new Handler().postDelayed(
                () -> {
                    startActivity(new Intent(FacultySplashScreen.this, FacultyLogin.class));
                    finish();
                }, DELAY
        );
    }
}