package com.example.shopease.seller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shopease.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SellerDashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sellerWelcomeText: TextView
    private lateinit var manageProductsButton: Button
    private lateinit var manageOrdersButton: Button
    private lateinit var checkMessagesButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_dashboard)

        auth = FirebaseAuth.getInstance()

        // Initialize UI Elements
        sellerWelcomeText = findViewById(R.id.sellerWelcomeText)
        manageProductsButton = findViewById(R.id.manageProductsButton)
        manageOrdersButton = findViewById(R.id.manageOrdersButton)
        checkMessagesButton = findViewById(R.id.checkMessagesButton)
        logoutButton = findViewById(R.id.logoutButton)

        // Show Seller's Name
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            sellerWelcomeText.text = "Welcome, ${currentUser.displayName}!"
        } else {
            sellerWelcomeText.text = "Welcome, Seller!"
        }

        // Navigate to Product Management
        manageProductsButton.setOnClickListener {
//            val intent = Intent(this, ManageProductsActivity::class.java)
//            startActivity(intent)
        }

        // Navigate to Order Management
        manageOrdersButton.setOnClickListener {
//            val intent = Intent(this, ManageOrdersActivity::class.java)
//            startActivity(intent)
        }

        // Navigate to Messages
        checkMessagesButton.setOnClickListener {
//            val intent = Intent(this, SellerMessagesActivity::class.java)
//            startActivity(intent)
        }

        // Logout
        logoutButton.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
