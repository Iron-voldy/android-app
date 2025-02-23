package com.example.shopease;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopease.adapters.OrderAdapter;
import com.example.shopease.models.Order;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    private RecyclerView ordersRecyclerView;
    private OrderAdapter orderAdapter;
    private DatabaseReference ordersRef;
    private List<Order> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Initialize UI
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        ImageView backButton = findViewById(R.id.backButton);

        // Set up RecyclerView
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(this, orderList);
        ordersRecyclerView.setAdapter(orderAdapter);

        // Back Button Functionality
        backButton.setOnClickListener(v -> finish());

        // Fetch Orders from Firebase
        String userId = "User123"; // TODO: Replace with the actual logged-in user ID
        ordersRef = FirebaseDatabase.getInstance().getReference("Orders").child(userId);
        fetchOrders();
    }

    private void fetchOrders() {
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    Order order = orderSnapshot.getValue(Order.class);
                    if (order != null) {
                        orderList.add(order);
                    }
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("OrderHistoryActivity", "Failed to load orders: " + error.getMessage());
            }
        });
    }
}
