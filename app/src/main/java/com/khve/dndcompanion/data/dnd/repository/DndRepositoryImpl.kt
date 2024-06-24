package com.khve.dndcompanion.data.dnd.repository

import com.khve.dndcompanion.data.dnd.mapper.DndMapper
import com.khve.dndcompanion.data.network.retrofit.ApiFactory
import com.khve.dndcompanion.di.scope.ApplicationScope
import com.khve.dndcompanion.domain.dnd.entity.DndArmor
import com.khve.dndcompanion.domain.dnd.entity.DndContent
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
