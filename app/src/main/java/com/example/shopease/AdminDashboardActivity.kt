package com.example.shopease.admin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.shopease.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var adminWelcomeText: TextView
    private lateinit var manageUsersButton: Button
    private lateinit var manageSellersButton: Button
    private lateinit var manageProductsButton: Button
    private lateinit var viewOrdersButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        auth = FirebaseAuth.getInstance()

        // Initialize UI Elements
        adminWelcomeText = findViewById(R.id.adminWelcomeText)
        manageUsersButton = findViewById(R.id.manageUsersButton)
        manageSellersButton = findViewById(R.id.manageSellersButton)
        manageProductsButton = findViewById(R.id.manageProductsButton)
        viewOrdersButton = findViewById(R.id.viewOrdersButton)
       // logoutButton = findViewById(R.id.logoutButton)

        // Show Admin's Name (if signed in)
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            adminWelcomeText.text = getString(R.string.welcome_admin) + " ${currentUser.displayName}!"
        } else {
            adminWelcomeText.text = getString(R.string.welcome_admin)
        }

        // Navigate to Manage Users
        manageUsersButton.setOnClickListener {
            //val intent = Intent(this, ManageUsersActivity::class.java)
            //startActivity(intent)
        }

        // Navigate to Manage Sellers
        manageSellersButton.setOnClickListener {
//            val intent = Intent(this, ManageSellersActivity::class.java)
//            startActivity(intent)
        }

        // Navigate to Manage Products
        manageProductsButton.setOnClickListener {
//            val intent = Intent(this, ManageProductsActivity::class.java)
//            startActivity(intent)
        }

        // Navigate to View Orders
        viewOrdersButton.setOnClickListener {
//            val intent = Intent(this, ViewOrdersActivity::class.java)
//            startActivity(intent)
        }

        // Logout
        logoutButton.setOnClickListener {
//            auth.signOut()
//            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
        }
    }
}
