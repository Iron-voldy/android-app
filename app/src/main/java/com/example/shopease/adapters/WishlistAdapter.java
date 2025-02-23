package com.example.shopease.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopease.R;
import com.example.shopease.models.Product;
import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    private final Context context;
    private final List<Product> wishlist;
    private final OnWishlistRemoveListener removeListener;

    public interface OnWishlistRemoveListener {
        void onRemove(Product product);
    }

    public WishlistAdapter(Context context, List<Product> wishlist, OnWishlistRemoveListener removeListener) {
        this.context = context;
        this.wishlist = wishlist;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_item, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        Product product = wishlist.get(position);
        holder.productName.setText(product.getTitle());  // 🔹 Fix: Replaced getName() with getTitle()
        holder.productPrice.setText("$" + product.getPrice());

        // Convert Base64 image to Bitmap
        byte[] decodedString = Base64.decode(product.getImageBase64(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.productImage.setImageBitmap(decodedByte);

        holder.removeButton.setOnClickListener(v -> removeListener.onRemove(product));
    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    static class WishlistViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage, removeButton;

        WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}
