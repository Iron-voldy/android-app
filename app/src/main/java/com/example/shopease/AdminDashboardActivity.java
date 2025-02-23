package com.example.shopease;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shopease.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminDashboardActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView adminWelcomeText;
    private Button manageUsersButton, manageSellersButton, manageProductsButton, viewOrdersButton, logoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        auth = FirebaseAuth.getInstance();

        // Initialize UI Elements
        adminWelcomeText = findViewById(R.id.adminWelcomeText);
        manageUsersButton = findViewById(R.id.manageUsersButton);
        manageSellersButton = findViewById(R.id.manageSellersButton);
        manageProductsButton = findViewById(R.id.manageProductsButton);
        viewOrdersButton = findViewById(R.id.viewOrdersButton);
        // logoutButton = findViewById(R.id.logoutButton); // Uncomment when logout button is available in the layout

        // Show Admin's Name (if signed in)
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            adminWelcomeText.setText(getString(R.string.welcome_admin) + " " + currentUser.getDisplayName() + "!");
        } else {
            adminWelcomeText.setText(getString(R.string.welcome_admin));
        }

        // Navigate to Manage Users
        manageUsersButton.setOnClickListener(v -> {
            // Intent intent = new Intent(AdminDashboardActivity.this, ManageUsersActivity.class);
            // startActivity(intent);
        });

        // Navigate to Manage Sellers
        manageSellersButton.setOnClickListener(v -> {
            // Intent intent = new Intent(AdminDashboardActivity.this, ManageSellersActivity.class);
            // startActivity(intent);
        });

        // Navigate to Manage Products
        manageProductsButton.setOnClickListener(v -> {
            // Intent intent = new Intent(AdminDashboardActivity.this, ManageProductsActivity.class);
            // startActivity(intent);
        });

        // Navigate to View Orders
        viewOrdersButton.setOnClickListener(v -> {
            // Intent intent = new Intent(AdminDashboardActivity.this, ViewOrdersActivity.class);
            // startActivity(intent);
        });

        // Logout functionality
        // Uncomment when logout button is available in the layout
        /*
        logoutButton.setOnClickListener(v -> {
            auth.signOut();
            Toast.makeText(AdminDashboardActivity.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminDashboardActivity.this, LoginActivity.class));
            finish();
        });
        */
    }
}
