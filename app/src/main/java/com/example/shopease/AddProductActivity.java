package com.example.shopease;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shopease.models.Product;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class AddProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView mainImage;
    private TextInputEditText productTitle, productPrice, productDescription, productQuantity, deliveryFee;
    private AutoCompleteTextView productCategory;
    private MaterialButton addProductButton;
    private Bitmap selectedImageBitmap;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private String sellerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        // Initialize UI elements
        mainImage = findViewById(R.id.mainProductImage);
        productTitle = findViewById(R.id.productTitle);
        productCategory = findViewById(R.id.productCategory);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);
        productQuantity = findViewById(R.id.productQuantity);
        deliveryFee = findViewById(R.id.deliveryFee);
        addProductButton = findViewById(R.id.addProductButton);

        setupCategoryDropdown();
        fetchSellerName();

        // Select Image
        mainImage.setOnClickListener(v -> openImageChooser());

        // Upload Product to Firebase
        addProductButton.setOnClickListener(v -> uploadProductToFirebase());
    }

    private void setupCategoryDropdown() {
        String[] categories = {"Eco-Friendly", "Smart Devices", "Subscription Boxes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);

        productCategory.setAdapter(adapter);
        productCategory.setThreshold(1);  // Show suggestions after 1 character input

        // Ensure dropdown opens on focus
        productCategory.setOnClickListener(v -> productCategory.showDropDown());
    }

    private void fetchSellerName() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            sellerName = user.getDisplayName();
        } else {
            sellerName = "Unknown Seller";
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Product Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                mainImage.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void uploadProductToFirebase() {
        String id = UUID.randomUUID().toString();
        String title = productTitle.getText().toString();
        String category = productCategory.getText().toString();
        String description = productDescription.getText().toString();

        if (title.isEmpty() || category.isEmpty() || description.isEmpty() || selectedImageBitmap == null) {
            Toast.makeText(this, "Please fill all fields and select an image!", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(productPrice.getText().toString());
        int quantity = Integer.parseInt(productQuantity.getText().toString());
        double deliveryFeeAmount = Double.parseDouble(deliveryFee.getText().toString());

        // Convert image to Base64
        String encodedImage = encodeImageToBase64(selectedImageBitmap);

        // Create product object
        Product product = new Product(id, title, price, encodedImage, description, category, sellerName, quantity, deliveryFeeAmount);

        // Save product under the selected category
        databaseReference.child(category).child(id).setValue(product)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Product Added!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, SellerDashboardActivity.class)); // Navigate to Seller Dashboard
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show());
    }
}
