package com.example.shopease.repository;

import androidx.annotation.NonNull;
import com.example.shopease.models.Product;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FirebaseWishlistRepository {
    private final DatabaseReference wishlistReference;

    public FirebaseWishlistRepository(String userId) {
        this.wishlistReference = FirebaseDatabase.getInstance().getReference("Wishlist").child(userId);
    }

    public void addProductToWishlist(Product product) {
        wishlistReference.child(product.getId()).setValue(product);
    }

    public void removeProductFromWishlist(String productId) {
        wishlistReference.child(productId).removeValue();
    }

    public void checkIfProductInWishlist(String productId, final WishlistCallback callback) {
        wishlistReference.child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                callback.onResult(snapshot.exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void fetchWishlist(final WishlistFetchCallback callback) {
        wishlistReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> wishlist = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Product product = itemSnapshot.getValue(Product.class);
                    if (product != null) {
                        wishlist.add(product);
                    }
                }
                callback.onFetch(wishlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public interface WishlistCallback {
        void onResult(boolean isInWishlist);
    }

    public interface WishlistFetchCallback {
        void onFetch(List<Product> wishlist);
    }
}
