package com.example.shopease

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.shopease.adapters.ProductDetailsPagerAdapter

class ProductDetailActivity : AppCompatActivity() {
    private var isWishlisted = false // Tracks the wishlist state (heart button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val backButton: ImageView = findViewById(R.id.backButton)
        val wishlistHeart: ImageView = findViewById(R.id.wishlistHeart)
        val productTabs: TabLayout = findViewById(R.id.productTabs)
        val detailsViewPager: ViewPager2 = findViewById(R.id.productDetailsViewPager)

        // Back Button Functionality
        backButton.setOnClickListener {
            // Navigate to the home page
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish() // Close the current activity
        }

        // Wishlist Heart Button Toggle
        wishlistHeart.setOnClickListener {
            isWishlisted = !isWishlisted // Toggle the wishlist state
            wishlistHeart.setImageResource(
                if (isWishlisted) R.drawable.ic_heart2 else R.drawable.ic_heart
            )
        }

        // Set up ViewPager2 with FragmentStateAdapter
        val adapter = ProductDetailsPagerAdapter(this)
        detailsViewPager.adapter = adapter

        // Link TabLayout with ViewPager2
        TabLayoutMediator(productTabs, detailsViewPager) { tab, position ->
            tab.text = if (position == 0) "Details" else "Reviews"
        }.attach()
    }
}
