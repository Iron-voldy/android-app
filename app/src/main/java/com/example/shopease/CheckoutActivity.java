package com.example.shopease;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ImageView backButton = findViewById(R.id.backButton);
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        TextView totalAmountTextView = findViewById(R.id.totalAmount);

        // Retrieve total price from intent (if passed)
        String totalPrice = getIntent().getStringExtra("TOTAL_PRICE");
        if (totalPrice == null) {
            totalPrice = "$0.00";
        }
        totalAmountTextView.setText("Total: " + totalPrice);

        // Navigate back to Cart
        backButton.setOnClickListener(v -> finish());

        // Handle order placement
        placeOrderButton.setOnClickListener(v -> {
            Toast.makeText(CheckoutActivity.this, "Order Placed Successfully!", Toast.LENGTH_LONG).show();
            finish(); // Close CheckoutActivity after order placement
        });
    }
}
