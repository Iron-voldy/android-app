package com.example.shopease.repository;

import androidx.annotation.NonNull;
import com.example.shopease.models.CartItem;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FirebaseCartRepository {
    private final DatabaseReference cartReference;

    public FirebaseCartRepository(String userId) {
        this.cartReference = FirebaseDatabase.getInstance().getReference("Carts").child(userId);
    }

    public void fetchCartItems(final CartCallback callback) {
        cartReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CartItem> cartItems = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    CartItem cartItem = itemSnapshot.getValue(CartItem.class);
                    if (cartItem != null) {
                        cartItem.setId(itemSnapshot.getKey());
                        cartItems.add(cartItem);
                    }
                }
                callback.onCallback(cartItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void addCartItem(CartItem cartItem) {
        cartReference.child(cartItem.getId()).setValue(cartItem);
    }

    public void updateCartItemQuantity(String itemId, int newQuantity) {
        cartReference.child(itemId).child("quantity").setValue(newQuantity);
    }

    public void removeCartItem(String itemId) {
        cartReference.child(itemId).removeValue();
    }

    public void clearCart() {
        cartReference.removeValue();
    }

    public interface CartCallback {
        void onCallback(List<CartItem> cartItems);
    }
}
