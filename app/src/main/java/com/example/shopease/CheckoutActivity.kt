package com.example.shopease

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val backButton: ImageView = findViewById(R.id.backButton)
        val placeOrderButton: Button = findViewById(R.id.placeOrderButton)
        val totalAmountTextView: TextView = findViewById(R.id.totalAmount)

        // Retrieve total price from intent (if passed)
        val totalPrice = intent.getStringExtra("TOTAL_PRICE") ?: "$0.00"
        totalAmountTextView.text = "Total: $totalPrice"

        // Navigate back to Cart
        backButton.setOnClickListener {
            finish()
        }

        // Handle order placement
        placeOrderButton.setOnClickListener {
            Toast.makeText(this, "Order Placed Successfully!", Toast.LENGTH_LONG).show()
            finish() // Close CheckoutActivity after order placement
        }
    }
}
