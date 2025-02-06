package com.example.shopease

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopease.adapters.CartAdapter
import com.example.shopease.models.CartItem

class CartActivity : AppCompatActivity() {
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var totalPriceTextView: TextView
    private lateinit var checkoutButton: Button
    private val cartItems = mutableListOf<CartItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val backButton: ImageView = findViewById(R.id.backButton)
        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        totalPriceTextView = findViewById(R.id.totalPrice)
        checkoutButton = findViewById(R.id.checkoutButton)

        // Back button functionality
        backButton.setOnClickListener {
            finish() // Navigate back to previous screen
        }

        // Load sample cart items
        loadCartItems()

        // Set up RecyclerView
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CartAdapter(cartItems) { updateTotalPrice() }
        cartRecyclerView.adapter = adapter

        // Checkout button functionality
        checkoutButton.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("TOTAL_PRICE", totalPriceTextView.text.toString()) // Pass total price
            startActivity(intent)
        }

        updateTotalPrice() // Initial total price calculation
    }

    private fun loadCartItems() {
        cartItems.add(CartItem("Eco-Friendly Bottle", "$15.00", R.drawable.ic_eco_friendly, 1))
        cartItems.add(CartItem("Smart Lock", "$45.00", R.drawable.ic_smart_devices, 1))
        cartItems.add(CartItem("Reusable Bag", "$10.00", R.drawable.ic_eco_friendly, 2))
    }

    private fun updateTotalPrice() {
        var total = 0.0
        for (item in cartItems) {
            total += item.price.replace("$", "").toDouble() * item.quantity
        }
        totalPriceTextView.text = "Total: $${String.format("%.2f", total)}"
    }
}
