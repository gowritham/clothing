package com.clothing.UI.retrofit

import com.clothing.UI.retrofit.Rating

data class ProductsDataItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)