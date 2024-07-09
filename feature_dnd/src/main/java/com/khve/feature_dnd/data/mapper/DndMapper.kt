package com.khve.feature_dnd.data.mapper

import com.khve.feature_dnd.data.model.DndArmorDto
import com.khve.feature_dnd.data.model.DndClassDto
import com.khve.feature_dnd.data.model.DndContentDto
import com.khve.feature_dnd.data.model.DndItemDto
import com.khve.feature_dnd.domain.entity.DndArmor
import com.khve.feature_dnd.domain.entity.DndClass
import com.khve.feature_dnd.domain.entity.DndContent
import com.khve.feature_dnd.domain.entity.DndItem
import javax.inject.Inject

class DndMapper @Inject constructor() {

    fun dndContentDtoToDndContent(dndContentDto: DndContentDto): DndContent {
        return DndContent(
            classes = dndClassesDtoToDndClasses(dndContentDto.classes),
            weapon = dndItemDtoToDndItem(dndContentDto.weapon),
            pendants = dndItemDtoToDndItem(dndContentDto.pendants),
            rings = dndItemDtoToDndItem(dndContentDto.rings),
            armor = dndArmorDtoToDndArmor(dndContentDto.armor)
        )
    }

    private fun dndClassesDtoToDndClasses(dndClassDtoList: List<DndClassDto>): List<DndClass> {
        val dndClassList = mutableListOf<DndClass>()

        for (dndClassDto: DndClassDto in dndClassDtoList) {
            dndClassList.add(
                DndClass(
                    name = dndClassDto.name,
                    previewImage = dndClassDto.previewImage,
                    perks = dndItemDtoToDndItem(dndClassDto.perks),
                    skills = dndItemDtoToDndItem(dndClassDto.skills)
                )
            )
        }

        return dndClassList
    }

    private fun dndArmorDtoToDndArmor(dndArmorDto: DndArmorDto): DndArmor {
        return DndArmor(
            head = dndItemDtoToDndItem(dndArmorDto.head),
            chest = dndItemDtoToDndItem(dndArmorDto.chest),
            legs = dndItemDtoToDndItem(dndArmorDto.legs),
            foot = dndItemDtoToDndItem(dndArmorDto.foot),
            gloves = dndItemDtoToDndItem(dndArmorDto.gloves),
            back = dndItemDtoToDndItem(dndArmorDto.back)
        )
    }

    private fun dndItemDtoToDndItem(dndItemDtoList: List<DndItemDto>): List<DndItem> {
        val dndItemList = mutableListOf<DndItem>()

        for (dndItemDto: DndItemDto in dndItemDtoList) {
            dndItemList.add(
                DndItem(
                    name = dndItemDto.name,
                    previewImage = dndItemDto.previewImage
                )
            )
        }

        return dndItemList
    }

}