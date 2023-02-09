package com.clothing.UI.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductsApi {
    @GET("/products")
    suspend fun getProducts1() : Response<List<ProductsDataItem>>
    @GET("/products")
    suspend fun getProducts() : Response<ProductsData>
    @POST("login")
    fun postData(@Body dataModal: DataModal?): Call<DataModal?>?
    @GET("/products/{id}")
    suspend fun eachProduct(@Path("id") id : String) : Response<EachDataItem>
    @GET("/products/categories")
    suspend fun getCategoriesData():Response<ResposeCategoryData>
    @GET("users")
    fun getDate(): Call<List<AllUsersItem>>
    @POST("/users")
    fun registerPost(@Body data : RegisterResponse) : Call<RegisterResponse>
}