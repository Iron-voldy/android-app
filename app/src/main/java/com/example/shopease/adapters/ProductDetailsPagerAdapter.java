package com.example.shopease.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.shopease.ProductDetailActivity;
import com.example.shopease.ProductDetailsFragment;
import com.example.shopease.ReviewsFragment;

public class ProductDetailsPagerAdapter extends FragmentStateAdapter {
    public ProductDetailsPagerAdapter(@NonNull ProductDetailActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return (position == 0) ? new ProductDetailsFragment() : new ReviewsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
