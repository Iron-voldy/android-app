package com.example.shopease.repository;

import com.example.shopease.models.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;
import com.google.firebase.database.GenericTypeIndicator;
import java.util.function.Consumer;


public class WishlistRepository {
    private final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("wishlist");

    public void getWishlist(Consumer<List<Product>> callback) {
        dbRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                List<Product> products = task.getResult().getValue(new GenericTypeIndicator<List<Product>>() {});
                callback.accept(products);
            }
        });
    }

    public void removeFromWishlist(Product product, Consumer<Boolean> callback) {
        dbRef.child(product.getTitle()).removeValue()
                .addOnSuccessListener(unused -> callback.accept(true))
                .addOnFailureListener(e -> callback.accept(false));
    }
}
