package com.example.shopease.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopease.R;
import com.example.shopease.models.CartItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;
    private Context context;
    private DatabaseReference cartRef;

    public CartAdapter(List<CartItem> cartItemList, Context context) {
        this.cartItemList = cartItemList;
        this.context = context;
        this.cartRef = FirebaseDatabase.getInstance().getReference("Cart").child("User123"); // Replace with actual user ID
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.cartProductName.setText(cartItem.getTitle());
        holder.cartProductPrice.setText("$" + cartItem.getPrice());
        holder.cartProductQuantity.setText(String.valueOf(cartItem.getQuantity()));

        // Decode Base64 Image
        if (cartItem.getImageBase64() != null) {
            byte[] decodedString = Base64.decode(cartItem.getImageBase64(), Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.cartProductImage.setImageBitmap(decodedBitmap);
        }

        // ✅ Remove Item from Cart
        holder.removeItemButton.setOnClickListener(v -> cartRef.child(cartItem.getId()).removeValue());

        // ✅ Increase Quantity
        holder.increaseQuantity.setOnClickListener(v -> {
            int currentQuantity = cartItem.getQuantity();
            cartItem.setQuantity(currentQuantity + 1);
            holder.cartProductQuantity.setText(String.valueOf(cartItem.getQuantity()));
            cartRef.child(cartItem.getId()).child("quantity").setValue(cartItem.getQuantity());
        });

        // ✅ Decrease Quantity (Cannot go below 1)
        holder.decreaseQuantity.setOnClickListener(v -> {
            int currentQuantity = cartItem.getQuantity();
            if (currentQuantity > 1) {
                cartItem.setQuantity(currentQuantity - 1);
                holder.cartProductQuantity.setText(String.valueOf(cartItem.getQuantity()));
                cartRef.child(cartItem.getId()).child("quantity").setValue(cartItem.getQuantity());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView cartProductName, cartProductPrice, cartProductQuantity;
        ImageView cartProductImage, removeItemButton;
        Button increaseQuantity, decreaseQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartProductName = itemView.findViewById(R.id.cartProductName);
            cartProductPrice = itemView.findViewById(R.id.cartProductPrice);
            cartProductQuantity = itemView.findViewById(R.id.cartProductQuantity);
            cartProductImage = itemView.findViewById(R.id.cartProductImage);
            removeItemButton = itemView.findViewById(R.id.removeItemButton);
            increaseQuantity = itemView.findViewById(R.id.increaseQuantity);
            decreaseQuantity = itemView.findViewById(R.id.decreaseQuantity);
        }
    }
}
