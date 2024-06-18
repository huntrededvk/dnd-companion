package com.khve.dndcompanion.data.dnd.repository

import android.util.Log
import com.khve.dndcompanion.data.network.retrofit.ApiFactory
import com.khve.dndcompanion.di.scope.ApplicationScope
import com.khve.dndcompanion.domain.dnd.entity.DndContentState
import com.khve.dndcompanion.domain.dnd.repository.DndRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@ApplicationScope
class DndRepositoryImpl @Inject constructor(

) : DndRepository {

    private val service = ApiFactory.apiService
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    override suspend fun getDndContent(): StateFlow<DndContentState> = contentData

    private val contentData = flow {
        val dndContent = service.getDndContent()
        emit(DndContentState.Content(dndContent))
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = DndContentState.Initial
    )
}