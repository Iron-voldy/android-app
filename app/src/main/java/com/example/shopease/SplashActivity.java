package com.example.shopease;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase before using any Firebase services
        FirebaseApp.initializeApp(this);
        if (!FirebaseApp.getApps(this).isEmpty()) {
            Log.d("FirebaseTest", "Firebase is initialized successfully.");
        } else {
            Log.e("FirebaseTest", "Firebase initialization failed.");
        }

        setContentView(R.layout.splash_screen); // Set your splash screen layout

        // Navigate to OnboardingActivity after 3 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class);
            startActivity(intent); // Navigate to Onboarding
            finish(); // Close SplashActivity
        }, 3000);
    }
}
