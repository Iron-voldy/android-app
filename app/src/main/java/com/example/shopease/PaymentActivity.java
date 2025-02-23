package com.example.shopease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {
    private TextView paymentAmount;
    private Button payNowButton, cancelPaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentAmount = findViewById(R.id.paymentAmount);
        payNowButton = findViewById(R.id.payNowButton);
        cancelPaymentButton = findViewById(R.id.cancelPaymentButton);

        // Retrieve total price from intent
        String totalPrice = getIntent().getStringExtra("TOTAL_PRICE");
        if (totalPrice == null) {
            totalPrice = "$0.00";
        }
        paymentAmount.setText("Total to Pay: " + totalPrice);

        // Handle payment process (Dummy for now)
        payNowButton.setOnClickListener(v -> {
            Toast.makeText(PaymentActivity.this, "Payment Successful!", Toast.LENGTH_LONG).show();
            finish(); // Close PaymentActivity after success
        });

        // Cancel payment and go back
        cancelPaymentButton.setOnClickListener(v -> finish());
    }
}
