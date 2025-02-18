package com.example.shopease.repository

import com.example.shopease.models.Product
import com.google.firebase.database.*

class FirebaseRepository {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Products")

    fun fetchProducts(callback: (List<Product>) -> Unit) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<Product>()
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.key ?: ""
                    for (productSnapshot in categorySnapshot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        product?.let {
                            it.id = productSnapshot.key ?: ""
                            it.category = category
                            products.add(it)
                        }
                    }
                }
                callback(products)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors.
            }
        })
    }
}
