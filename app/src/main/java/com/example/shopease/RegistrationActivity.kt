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

class RegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_screen)

        // Initialize FirebaseAuth and FirebaseDatabase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // UI Elements
        val nameField = findViewById<EditText>(R.id.nameField)
        val emailField = findViewById<EditText>(R.id.emailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val signInText = findViewById<TextView>(R.id.signInText)

        // Handle Register button click
        registerButton.setOnClickListener {
            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            // Validate input fields
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(name, email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle SignIn Text click to navigate to LoginActivity
        signInText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java)) // Navigate to LoginActivity
            finish() // Close RegistrationActivity
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        // Create user with Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    val userRef = database.getReference("Users").child(userId)

                    // Save user details to Firebase Realtime Database
                    val user = mapOf(
                        "name" to name,
                        "email" to email,
                        "role" to "user",  // Default role
                        "isBlocked" to false
                    )

                    // Save user to the database
                    userRef.setValue(user).addOnSuccessListener {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java)) // Navigate to LoginActivity
                        finish() // Close RegistrationActivity
                    }
                } else {
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
