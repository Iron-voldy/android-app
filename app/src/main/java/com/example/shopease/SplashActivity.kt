package com.example.shopease

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase before using any Firebase services
        FirebaseApp.initializeApp(this)
        if (FirebaseApp.getApps(this).isNotEmpty()) {
            Log.d("FirebaseTest", "Firebase is initialized successfully.")
        } else {
            Log.e("FirebaseTest", "Firebase initialization failed.")
        }

        setContentView(R.layout.splash_screen) // Set your splash screen layout

        // Navigate to OnboardingActivity after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent) // Navigate to Onboarding
            finish() // Close SplashActivity
        }, 3000)
    }
}
