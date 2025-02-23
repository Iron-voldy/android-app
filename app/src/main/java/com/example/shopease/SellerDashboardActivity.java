package com.example.shopease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopease.adapters.QuickActionsAdapter;
import com.example.shopease.models.QuickAction;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;

public class SellerDashboardActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private RecyclerView quickActionsGrid;
    private ExtendedFloatingActionButton fabAddProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        auth = FirebaseAuth.getInstance();

        // Initialize UI Elements
        quickActionsGrid = findViewById(R.id.quickActionsGrid);
        fabAddProduct = findViewById(R.id.fabAddProduct);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        // Set up Toolbar menu actions
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_settings) {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // Floating Action Button for Adding Products
        fabAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(SellerDashboardActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        // Set up Quick Actions Grid
        setupQuickActions();
    }

    private void setupQuickActions() {
        List<QuickAction> quickActions = new ArrayList<>();
        quickActions.add(new QuickAction("Manage Products", R.drawable.ic_inventory));
        quickActions.add(new QuickAction("Order Management", R.drawable.ic_orders));
        quickActions.add(new QuickAction("Sales Analytics", R.drawable.ic_analytics));
        quickActions.add(new QuickAction("Customer Chats", R.drawable.ic_chat));

        QuickActionsAdapter adapter = new QuickActionsAdapter(quickActions, this::onQuickActionClicked);
        quickActionsGrid.setLayoutManager(new GridLayoutManager(this, 3));
        quickActionsGrid.setAdapter(adapter);
    }

    private void onQuickActionClicked(QuickAction action) {
        switch (action.getTitle()) {
            case "Manage Products":
                // startActivity(new Intent(this, ManageProductsActivity.class));
                break;
            case "Order Management":
                //startActivity(new Intent(this, ManageOrdersActivity.class));
                break;
            case "Sales Analytics":
                // startActivity(new Intent(this, SalesAnalyticsActivity.class));
                break;
            case "Customer Chats":
                // startActivity(new Intent(this, CustomerChatsActivity.class));
                break;
        }
    }
}
