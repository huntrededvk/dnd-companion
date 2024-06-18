package com.khve.dndcompanion.data.meta.mapper

import com.khve.dndcompanion.data.meta.model.MetaItemDto
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import javax.inject.Inject

class MetaMapper @Inject constructor() {

    fun mapMetaItemDtoToMetaItem(metaItemDto: MetaItemDto): MetaItem {
        return MetaItem(
            uid = "",
            authorUid = metaItemDto.authorUid,
            title = metaItemDto.title,
            description = metaItemDto.description,
            tier = metaItemDto.tier,
            authorUsername = metaItemDto.authorUsername,
            previewImg = metaItemDto.previewImg
        )
    }

}