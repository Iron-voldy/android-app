package com.example.shopease;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.shopease.adapters.BannerAdapter;
import com.example.shopease.adapters.ProductAdapter;
import com.example.shopease.models.Product;
import com.example.shopease.repository.FirebaseRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ProductAdapter productAdapter;
    private final FirebaseRepository firebaseRepository = new FirebaseRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ✅ Setup Banner Slider
        List<Integer> banners = new ArrayList<>();
        banners.add(R.drawable.banner_eco_products);
        banners.add(R.drawable.banner_smart_devices);
        ViewPager2 bannerViewPager = findViewById(R.id.bannerViewPager);
        bannerViewPager.setAdapter(new BannerAdapter(this, banners));

        // ✅ Setup Featured Products RecyclerView
        RecyclerView featuredList = findViewById(R.id.featuredList);
        productAdapter = new ProductAdapter(this, new ArrayList<>());
        featuredList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        featuredList.setAdapter(productAdapter);
        fetchProducts();

        // ✅ Bottom Navigation Handling
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleNavigation(item);
            }
        });
    }

    // ✅ Inflate menu (Required to show the toolbar icons)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_menu, menu);
        return true;
    }

    // ✅ Handle Toolbar Menu Clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        } else if (item.getItemId() == R.id.action_profile) {
            startActivity(new Intent(this, UserProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ✅ Fetch products from Firebase
    private void fetchProducts() {
        firebaseRepository.fetchProducts(products -> {
            if (products != null) {
                runOnUiThread(() -> productAdapter.updateProducts(products));
            }
        });
    }

    // ✅ Bottom Navigation Handling (Replaced switch-case with if-else)
    private boolean handleNavigation(@NonNull MenuItem item) {
        Class<?> activity = null;
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            return true; // Already in Home
        } else if (itemId == R.id.nav_cart) {
            activity = CartActivity.class;
        } else if (itemId == R.id.nav_wishlist) {
            activity = WishlistActivity.class;
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
