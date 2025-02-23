package com.example.shopease;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopease.adapters.WishlistAdapter;
import com.example.shopease.models.Product;
import com.example.shopease.repository.WishlistRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private WishlistAdapter wishlistAdapter;
    private final WishlistRepository wishlistRepository = new WishlistRepository();
    private final List<Product> wishlistItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        // ✅ Setup RecyclerView
        RecyclerView wishlistRecyclerView = findViewById(R.id.wishlistRecyclerView);
        wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        wishlistAdapter = new WishlistAdapter(this, wishlistItems, this::removeFromWishlist);
        wishlistRecyclerView.setAdapter(wishlistAdapter);

        // ✅ Fetch Wishlist from Firebase
        loadWishlist();

        // ✅ Bottom Navigation Handling
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleNavigation(item);
            }
        });

    }

    private void loadWishlist() {
        wishlistRepository.getWishlist(products -> {
            wishlistItems.clear();
            wishlistItems.addAll(products);
            runOnUiThread(() -> wishlistAdapter.notifyDataSetChanged());
        });
    }

    private void removeFromWishlist(Product product) {
        wishlistRepository.removeFromWishlist(product, success -> {
            if (success) {
                wishlistItems.remove(product);
                wishlistAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Removed from wishlist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to remove", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ✅ Handle Bottom Navigation
    private boolean handleNavigation(@NonNull MenuItem item) {
        Class<?> activity = null;
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            activity = HomeActivity.class;
        } else if (itemId == R.id.nav_cart) {
            activity = CartActivity.class;
        } else if (itemId == R.id.nav_wishlist) {
            return true; // Already in Wishlist
        } else if (itemId == R.id.nav_chat) {
            activity = ChatActivity.class;
        } else if (itemId == R.id.nav_profile) {
            activity = UserProfileActivity.class;
        }

        if (activity != null) {
            startActivity(new Intent(this, activity));
        }
        return false;
    }

}
