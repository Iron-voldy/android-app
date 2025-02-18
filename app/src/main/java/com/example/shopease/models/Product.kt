// Product.kt
package com.example.shopease.models

data class Product(
    var id: String = "",
    var name: String = "",
    var price: Double = 0.0,
    var imageUrl: String = "",
    var description: String = "",
    var category: String = ""
)
