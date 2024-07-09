package com.khve.feature_dnd.data.network.retrofit.dnd

import retrofit2.http.GET

interface ApiService {
    @GET("dnd-values-json")
    suspend fun getDndContent(): com.khve.feature_dnd.data.model.DndContentDto
}
