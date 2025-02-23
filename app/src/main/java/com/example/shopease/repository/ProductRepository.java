package com.example.shopease.repository;

import com.example.shopease.models.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import java.util.function.Consumer;

public class ProductRepository {
    private final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("products");

    public void getProductById(String productId, Consumer<Product> callback) {
        dbRef.child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Product product = snapshot.getValue(Product.class);
                    callback.accept(product);
                } else {
                    callback.accept(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.accept(null);
            }
        });
    }
}
