package com.clothing.UI.retrofit

data class EachDataItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: RatingX,
    val title: String
)