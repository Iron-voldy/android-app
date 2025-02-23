package com.example.shopease.repository;

import com.example.shopease.models.CartItem;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CartRepository {
    private final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("cart");

    public void getCartItems(Consumer<List<CartItem>> callback) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<CartItem> cartList = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    CartItem item = itemSnapshot.getValue(CartItem.class);
                    if (item != null) {
                        cartList.add(item);
                    }
                }
                callback.accept(cartList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.accept(null);
            }
        });
    }
}
