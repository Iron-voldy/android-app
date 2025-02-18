// HomeActivity.kt
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
import com.example.shopease.repository.FirebaseRepository
import com.example.shopease.models.Product

class HomeActivity : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private val firebaseRepository = FirebaseRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Set up the banner ViewPager
        val banners = listOf(R.drawable.banner_eco_products, R.drawable.banner_smart_devices)
        val bannerViewPager = findViewById<ViewPager2>(R.id.bannerViewPager)
        bannerViewPager.adapter = BannerAdapter(banners)

        // Set up the featured product list
        val featuredList: RecyclerView = findViewById(R.id.featuredList)
        productAdapter = ProductAdapter(this, mutableListOf())
        featuredList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        featuredList.adapter = productAdapter

        // Fetch products from Firebase
        fetchProducts()

        // Navigate to Profile Activity
        val profileButton: ImageView = findViewById(R.id.profileButton)
        profileButton.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        // Navigate to Cart Activity
        val cartButton: ImageView = findViewById(R.id.cartButton)
        cartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchProducts() {
        firebaseRepository.fetchProducts { products ->
            runOnUiThread {
                productAdapter.updateProducts(products)
            }
        }
    }
}
