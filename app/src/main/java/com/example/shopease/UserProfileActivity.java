package com.example.shopease;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private TextInputLayout addressIcon;
    private ActivityResultLauncher<Intent> cameraLauncher, galleryLauncher;
    private TextInputEditText firstNameField, lastNameField, emailField, addressField;
    private MaterialButton saveButton, becomeSellerButton, changePictureButton;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private Bitmap selectedBitmap = null; // Stores the selected profile image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize Views
        profilePicture = findViewById(R.id.profilePicture);
        addressIcon = findViewById(R.id.addressContainer);
        firstNameField = findViewById(R.id.firstNameField);
        lastNameField = findViewById(R.id.lastNameField);
        emailField = findViewById(R.id.emailField);
        addressField = findViewById(R.id.addressField);
        saveButton = findViewById(R.id.saveButton);
        becomeSellerButton = findViewById(R.id.becomeSellerButton);
        changePictureButton = findViewById(R.id.changePictureText);

        // Disable email editing
        emailField.setEnabled(false);

        // Setup activity launchers
        setupActivityResultLaunchers();

        // Load user profile
        loadUserProfile();

        // Change profile picture
        changePictureButton.setOnClickListener(v -> showImageOptions());

        // Save profile updates
        saveButton.setOnClickListener(v -> saveUserProfile());

        // Navigate to Seller Dashboard
        becomeSellerButton.setOnClickListener(v -> navigateToSellerDashboard());
    }

    private void setupActivityResultLaunchers() {
        // Camera launcher
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                if (imageBitmap != null) {
                    profilePicture.setImageBitmap(imageBitmap);
                    selectedBitmap = imageBitmap;
                } else {
                    Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Gallery launcher
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Uri imageUri = result.getData().getData();
                if (imageUri != null) {
                    try {
                        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        profilePicture.setImageBitmap(imageBitmap);
                        selectedBitmap = imageBitmap;
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showImageOptions() {
        String[] options = {"Take Photo", "Choose from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) openCamera();
            else openGallery();
        });
        builder.show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            cameraLauncher.launch(cameraIntent);
        } else {
            Toast.makeText(this, "No Camera App Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap decodeBase64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private void loadUserProfile() {
        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        firstNameField.setText(snapshot.child("firstName").getValue(String.class));
                        lastNameField.setText(snapshot.child("lastName").getValue(String.class));
                        emailField.setText(snapshot.child("email").getValue(String.class));
                        addressField.setText(snapshot.child("address").getValue(String.class));

                        // Load profile picture from Base64
                        String encodedImage = snapshot.child("profileImageBase64").getValue(String.class);
                        if (encodedImage != null) {
                            profilePicture.setImageBitmap(decodeBase64ToBitmap(encodedImage));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(UserProfileActivity.this, "Failed to load profile: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveUserProfile() {
        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String address = addressField.getText().toString();

        if (auth.getCurrentUser() != null) {
            String userId = currentUser.getUid();

            DatabaseReference userRef = databaseReference.child(userId);
            userRef.child("firstName").setValue(firstName);
            userRef.child("lastName").setValue(lastName);
            userRef.child("address").setValue(address);

            if (selectedBitmap != null) {
                String base64Image = encodeImageToBase64(selectedBitmap);
                userRef.child("profileImageBase64").setValue(base64Image)
                        .addOnSuccessListener(aVoid -> Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToSellerDashboard() {
        Intent intent = new Intent(UserProfileActivity.this, SellerDashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
