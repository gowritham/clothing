package com.clothing.UI.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    val baseurl = "https://fakestoreapi.com"
    var okHttpClient: OkHttpClient? = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()
    fun getInstance() : Retrofit? {
        return okHttpClient?.let { Retrofit.Builder().baseUrl(baseurl).client(it).addConverterFactory(GsonConverterFactory.create()).build() }
    }
}