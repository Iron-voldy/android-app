package com.example.shopease.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shopease.ProductDetailActivity
import com.example.shopease.fragments.ProductDetailsFragment
import com.example.shopease.fragments.ReviewsFragment

class ProductDetailsPagerAdapter(activity: ProductDetailActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) ProductDetailsFragment() else ReviewsFragment()
    }
}
