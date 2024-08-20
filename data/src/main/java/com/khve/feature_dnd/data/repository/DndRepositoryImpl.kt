package com.khve.feature_dnd.data.repository

import com.khve.feature_dnd.data.mapper.DndMapper
import com.khve.feature_dnd.data.network.retrofit.dnd.ApiFactory
import com.khve.feature_dnd.domain.entity.DndArmor
import com.khve.feature_dnd.domain.entity.DndContent
import com.khve.feature_dnd.domain.entity.DndContentState
import com.khve.feature_dnd.domain.repository.DndRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DndRepositoryImpl @Inject constructor(
    private val dndMapper: DndMapper
) : DndRepository {

    private val service = ApiFactory.apiService
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    override suspend fun getDndContent(): StateFlow<DndContentState> = contentData

    private val contentData = flow {
        val dndContentDto = service.getDndContent()
        val mappedDndContent = dndMapper.dndContentDtoToDndContent(dndContentDto)
        val sortedDndContent = sortDndContent(mappedDndContent)

        emit(DndContentState.Content(sortedDndContent))
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = DndContentState.Initial
    )

    private fun sortDndContent(dndContent: DndContent): DndContent {
        return DndContent(
            classes = dndContent.classes.sortedBy { it.name },
            weapon = dndContent.weapon.sortedBy { it.name },
            pendants = dndContent.pendants.sortedBy { it.name },
            rings = dndContent.rings.sortedBy { it.name },
            armor = DndArmor(
                head = dndContent.armor.head.sortedBy { it.name },
                chest = dndContent.armor.chest.sortedBy { it.name },
                legs = dndContent.armor.legs.sortedBy { it.name },
                foot = dndContent.armor.foot.sortedBy { it.name },
                gloves = dndContent.armor.gloves.sortedBy { it.name },
                back = dndContent.armor.back.sortedBy { it.name },
            )
        )
    }
}
