package com.khve.dndcompanion.data.dnd.mapper

import com.khve.dndcompanion.data.dnd.model.DndArmorDto
import com.khve.dndcompanion.data.dnd.model.DndClassDto
import com.khve.dndcompanion.data.dnd.model.DndContentDto
import com.khve.dndcompanion.data.dnd.model.DndItemDto
import com.khve.dndcompanion.domain.dnd.entity.DndArmor
import com.khve.dndcompanion.domain.dnd.entity.DndClass
import com.khve.dndcompanion.domain.dnd.entity.DndContent
import com.khve.dndcompanion.domain.dnd.entity.DndItem
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