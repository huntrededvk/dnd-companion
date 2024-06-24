package com.khve.dndcompanion.data.network.retrofit.dnd

import com.khve.dndcompanion.data.dnd.model.DndContentDto
import retrofit2.http.GET

interface ApiService {
    @GET("dnd-values-json")
    suspend fun getDndContent(): DndContentDto
}
