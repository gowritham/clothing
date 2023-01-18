package com.clothing.UI.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductsApi {
    @GET("/products")
    suspend fun getProducts() : Response<ProductsData>
    @POST("login")
    fun postData(@Body dataModal: DataModal?): Call<DataModal?>?
    @GET("/products/{id}")
    suspend fun eachProduct(@Path("id") id : String) : Response<EachDataItem>
}