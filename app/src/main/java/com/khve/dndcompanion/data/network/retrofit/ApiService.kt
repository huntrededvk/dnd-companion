package com.khve.dndcompanion.data.network.retrofit

import com.khve.dndcompanion.domain.dnd.entity.DndContent
import retrofit2.http.GET

interface ApiService {
    @GET("dnd-values-json")
    suspend fun getDndContent(): DndContent
}