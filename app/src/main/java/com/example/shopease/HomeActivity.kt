package com.example.shopease

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.shopease.adapters.BannerAdapter
import com.example.shopease.adapters.ProductAdapter
import com.example.shopease.models.Product

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Set up the banner ViewPager
        val banners = listOf(R.drawable.banner_eco_products, R.drawable.banner_smart_devices)
        val bannerViewPager = findViewById<ViewPager2>(R.id.bannerViewPager)
        bannerViewPager.adapter = BannerAdapter(banners)

        // Set up the featured product list
        val featuredList: RecyclerView = findViewById(R.id.featuredList)
        val productList = getSampleProducts()

        val productAdapter = ProductAdapter(this, productList) // Pass the context for navigation
        featuredList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        featuredList.adapter = productAdapter

        // Navigate to Profile Activity
        val profileButton: ImageView = findViewById(R.id.profileButton)
        profileButton.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        // 🚀 Navigate to Cart Activity when clicking Cart Icon
        val cartButton: ImageView = findViewById(R.id.cartButton)
        cartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getSampleProducts(): List<Product> {
        return listOf(
            Product("Eco-Friendly Bottle", "$15.00", R.drawable.ic_eco_friendly),
            Product("Smart Lock", "$45.00", R.drawable.ic_smart_devices),
            Product("Subscription Box", "$25.00", R.drawable.ic_subscription_boxes),
            Product("Reusable Bag", "$10.00", R.drawable.ic_eco_friendly),
            Product("Smart Light", "$35.00", R.drawable.ic_smart_devices)
        )
    }
}
