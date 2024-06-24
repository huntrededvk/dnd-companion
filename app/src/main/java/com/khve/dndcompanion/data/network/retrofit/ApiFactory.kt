package com.khve.dndcompanion.data.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/huntrededvk/dnd-open-values/main/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}
