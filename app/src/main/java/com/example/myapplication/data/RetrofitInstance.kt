package com.example.myapplication.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val QUOTE_BASE_URL = "https://zenquotes.io/api/"
    private const val NASA_BASE_URL = "https://api.nasa.gov/"

    val api: QuoteApi by lazy {
        Retrofit.Builder()
            .baseUrl(QUOTE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteApi::class.java)
    }

    val nasaApi: NasaApi by lazy {
        Retrofit.Builder()
            .baseUrl(NASA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)
    }
}