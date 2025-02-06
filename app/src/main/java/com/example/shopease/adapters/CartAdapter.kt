package com.example.shopease.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopease.R
import com.example.shopease.models.CartItem

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val updateTotalPrice: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.cartProductImage)
        val productName: TextView = itemView.findViewById(R.id.cartProductName)
        val productPrice: TextView = itemView.findViewById(R.id.cartProductPrice)
        val quantityText: TextView = itemView.findViewById(R.id.cartProductQuantity)
        val increaseButton: Button = itemView.findViewById(R.id.increaseQuantity)
        val decreaseButton: Button = itemView.findViewById(R.id.decreaseQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.productImage.setImageResource(cartItem.imageResId)
        holder.productName.text = cartItem.name
        holder.productPrice.text = cartItem.price
        holder.quantityText.text = cartItem.quantity.toString()

        // Increase quantity
        holder.increaseButton.setOnClickListener {
            cartItem.quantity++
            holder.quantityText.text = cartItem.quantity.toString()
            updateTotalPrice()
        }

        // Decrease quantity
        holder.decreaseButton.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity--
                holder.quantityText.text = cartItem.quantity.toString()
                updateTotalPrice()
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
