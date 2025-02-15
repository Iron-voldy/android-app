package com.example.shopease

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.shopease.seller.SellerDashboardActivity
import com.example.shopease.admin.AdminDashboardActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        // Initialize FirebaseAuth and FirebaseDatabase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val emailField = findViewById<EditText>(R.id.emailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpText = findViewById<TextView>(R.id.signUpText)

        // Handle login button click
        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            // Validate input fields
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle SignUp Text click to navigate to RegistrationActivity
        signUpText.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java)) // Navigate to RegistrationActivity
        }
    }

    private fun loginUser(email: String, password: String) {
        // Sign in with Firebase Authentication
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    navigateBasedOnUserRole(userId)
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateBasedOnUserRole(userId: String?) {
        if (userId == null) return

        database.getReference("Users").child(userId).child("role").get()
            .addOnSuccessListener { snapshot ->
                when (snapshot.value.toString()) {
                    "user" -> startActivity(Intent(this, HomeActivity::class.java)) // Navigate to HomeActivity
                    "seller" -> startActivity(Intent(this, SellerDashboardActivity::class.java)) // Navigate to Seller Dashboard
                    "admin" -> startActivity(Intent(this, AdminDashboardActivity::class.java)) // Navigate to Admin Dashboard
                    else -> Toast.makeText(this, "Unknown role", Toast.LENGTH_SHORT).show()
                }
                finish() // Close LoginActivity after navigating
            }
    }
}
