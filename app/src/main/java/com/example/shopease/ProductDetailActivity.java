package com.example.shopease;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shopease.models.Product;
import com.example.shopease.repository.FirebaseWishlistRepository;
import com.google.firebase.database.*;

public class ProductDetailActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseWishlistRepository wishlistRepository;
    private String productId, category, userId = "User123"; // Replace with actual user ID
    private TextView productTitle, productPrice, productDescription, sellerName, productCategory, productQuantity, deliveryFee, bottomProductPrice;
    private ImageView productImage, wishlistHeart;
    private Button addToCartButton;
    private boolean isInWishlist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Initialize UI components
        productTitle = findViewById(R.id.productTitle);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);
        sellerName = findViewById(R.id.sellerName);
        productCategory = findViewById(R.id.productCategory);
        productQuantity = findViewById(R.id.productQuantity);
        deliveryFee = findViewById(R.id.deliveryFee);
        bottomProductPrice = findViewById(R.id.bottomProductPrice);
        productImage = findViewById(R.id.productImage);
        wishlistHeart = findViewById(R.id.wishlistHeart);
        addToCartButton = findViewById(R.id.addToCartButton);

        // Retrieve Product ID and Category from Intent
        productId = getIntent().getStringExtra("PRODUCT_ID");
        category = getIntent().getStringExtra("CATEGORY");

        if (productId == null || category == null) {
            Toast.makeText(this, "Invalid Product Data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Firebase references
        databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(category).child(productId);
        wishlistRepository = new FirebaseWishlistRepository(userId);

        fetchProductDetails();
        checkWishlistStatus();

        // Toggle Wishlist when clicked
        wishlistHeart.setOnClickListener(v -> toggleWishlist());
    }

    private void fetchProductDetails() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Product product = snapshot.getValue(Product.class);
                    if (product != null) {
                        productTitle.setText(product.getTitle());
                        productPrice.setText("Price: $" + product.getPrice());
                        productDescription.setText("Description: " + product.getDescription());
                        sellerName.setText("Seller: " + product.getSellerName());
                        productCategory.setText("Category: " + product.getCategory());
                        productQuantity.setText("Available Quantity: " + product.getQuantity());
                        deliveryFee.setText("Delivery Fee: $" + product.getDeliveryFee());
                        bottomProductPrice.setText("$" + product.getPrice());

                        // Decode Base64 Image
                        if (product.getImageBase64() != null) {
                            byte[] decodedString = Base64.decode(product.getImageBase64(), Base64.DEFAULT);
                            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            productImage.setImageBitmap(decodedBitmap);
                        }
                    }
                } else {
                    Log.d("ProductDetailActivity", "Product not found in Firebase");
                    Toast.makeText(ProductDetailActivity.this, "Product not found!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProductDetailActivity", "Error fetching product: " + error.getMessage());
                Toast.makeText(ProductDetailActivity.this, "Failed to load product", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkWishlistStatus() {
        wishlistRepository.checkIfProductInWishlist(productId, isInWishlist -> {
            this.isInWishlist = isInWishlist;
            updateWishlistIcon();
        });
    }

    private void toggleWishlist() {
        if (isInWishlist) {
            wishlistRepository.removeProductFromWishlist(productId);
            isInWishlist = false;
            Toast.makeText(this, "Removed from Wishlist", Toast.LENGTH_SHORT).show();
        } else {
            Product product = new Product(productId, productTitle.getText().toString(),
                    Double.parseDouble(productPrice.getText().toString().replace("Price: $", "")),
                    null, productDescription.getText().toString(), category, sellerName.getText().toString(),
                    Integer.parseInt(productQuantity.getText().toString().replace("Available Quantity: ", "")),
                    Double.parseDouble(deliveryFee.getText().toString().replace("Delivery Fee: $", "")));

            wishlistRepository.addProductToWishlist(product);
            isInWishlist = true;
            Toast.makeText(this, "Added to Wishlist", Toast.LENGTH_SHORT).show();
        }
        updateWishlistIcon();
    }

    private void updateWishlistIcon() {
        if (isInWishlist) {
            wishlistHeart.setImageResource(R.drawable.ic_heart2); // Use filled heart icon
        } else {
            wishlistHeart.setImageResource(R.drawable.ic_heart); // Use empty heart icon
        }
    }
}
