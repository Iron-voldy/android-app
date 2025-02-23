package com.example.shopease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.shopease.adapters.OnboardingAdapter;
import com.example.shopease.models.OnboardingItem;
import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button skipButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        List<OnboardingItem> onboardingItems = new ArrayList<>();
        onboardingItems.add(new OnboardingItem(R.drawable.image1, "Welcome to ShopEase", "Discover amazing products and great deals."));
        onboardingItems.add(new OnboardingItem(R.drawable.image2, "Easy Shopping", "Shop with ease and convenience from your home."));
        onboardingItems.add(new OnboardingItem(R.drawable.image3, "Fast Delivery", "Get your orders delivered quickly and reliably."));

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new OnboardingAdapter(onboardingItems));

        skipButton = findViewById(R.id.skipButton);
        nextButton = findViewById(R.id.nextButton);

        skipButton.setOnClickListener(v -> navigateToRegistration());

        nextButton.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() + 1 < onboardingItems.size()) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                navigateToRegistration();
            }
        });
    }

    private void navigateToRegistration() {
        startActivity(new Intent(this, RegistrationActivity.class));
        finish();
    }
}
