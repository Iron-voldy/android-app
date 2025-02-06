package com.example.shopease

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var skipButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Onboarding Data
        val onboardingItems = listOf(
            OnboardingItem(
                R.drawable.image1,
                "Welcome to ShopEase",
                "Discover amazing products and great deals."
            ),
            OnboardingItem(
                R.drawable.image2,
                "Easy Shopping",
                "Shop with ease and convenience from your home."
            ),
            OnboardingItem(
                R.drawable.image3,
                "Fast Delivery",
                "Get your orders delivered quickly and reliably."
            )
        )

        // Set up ViewPager2
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = OnboardingAdapter(onboardingItems)

        skipButton = findViewById(R.id.skipButton)
        nextButton = findViewById(R.id.nextButton)

        // Navigate to Registration when "Skip" is clicked
        skipButton.setOnClickListener {
            navigateToRegistration()
        }

        // Handle "Next" button clicks
        nextButton.setOnClickListener {
            if (viewPager.currentItem + 1 < onboardingItems.size) {
                viewPager.currentItem += 1 // Move to next onboarding screen
            } else {
                navigateToRegistration() // Navigate to Registration on final screen
            }
        }
    }

    private fun navigateToRegistration() {
        startActivity(Intent(this, RegistrationActivity::class.java)) // Navigate to Registration UI
        finish()
    }
}
