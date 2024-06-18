package com.khve.dndcompanion.domain.meta.repository

import com.khve.dndcompanion.data.meta.model.MetaItemDto
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.entity.MetaListState
import kotlinx.coroutines.flow.StateFlow

interface MetaListRepository {
    fun getMetaList(): StateFlow<MetaListState>

    fun addMetaItem(metaItemDto: MetaItemDto): StateFlow<MetaItemState>

    fun getMetaItem(metaItemUid: String): StateFlow<MetaItemState>
}