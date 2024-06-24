package com.khve.dndcompanion.data.meta.mapper

import com.khve.dndcompanion.data.meta.model.MetaCardItemDto
import com.khve.dndcompanion.data.meta.model.MetaItemDto
import com.khve.dndcompanion.domain.meta.entity.MetaCardItem
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import javax.inject.Inject

class MetaMapper @Inject constructor() {
    fun metaItemDtoToMetaItem(metaItemDto: MetaItemDto): MetaItem {
        return MetaItem(
            uid = metaItemDto.uid,
            title = metaItemDto.title,
            description = metaItemDto.description,
            tier = metaItemDto.tier,
            author = metaItemDto.author,
            dndClass = metaItemDto.dndClass,
            primaryWeaponSlotOne = metaItemDto.primaryWeaponSlotOne,
            primaryWeaponSlotTwo = metaItemDto.primaryWeaponSlotTwo,
            secondaryWeaponSlotOne = metaItemDto.secondaryWeaponSlotOne,
            secondaryWeaponSlotTwo = metaItemDto.secondaryWeaponSlotTwo,
            headArmor = metaItemDto.headArmor,
            chestArmor = metaItemDto.chestArmor,
            legsArmor = metaItemDto.legsArmor,
            footArmor = metaItemDto.footArmor,
            glovesArmor = metaItemDto.glovesArmor,
            backArmor = metaItemDto.backArmor,
            pendant = metaItemDto.pendant,
            ringSlotOne = metaItemDto.ringSlotOne,
            ringSlotTwo = metaItemDto.ringSlotTwo
        )
    }

    fun metaCardItemDtoToMetaCardItem(metaCardItemDto: MetaCardItemDto): MetaCardItem {
        return MetaCardItem(
            uid = metaCardItemDto.uid,
            title = metaCardItemDto.title,
            tier = metaCardItemDto.tier,
            author = metaCardItemDto.author,
            dndClass = metaCardItemDto.dndClass
        )
    }

    fun metaItemDtoToMetaCardItem(metaItemDto: MetaItemDto) : MetaCardItem {
        return MetaCardItem(
            uid = metaItemDto.uid,
            title = metaItemDto.title,
            tier = metaItemDto.tier,
            author = metaItemDto.author,
            dndClass = metaItemDto.dndClass
        )
    }

    fun metaItemToMetaItemDto(metaItem: MetaItem): MetaItemDto {
        return MetaItemDto(
            uid = metaItem.uid,
            title = metaItem.title,
            description = metaItem.description,
            tier = metaItem.tier,
            author = metaItem.author,
            dndClass = metaItem.dndClass,
            primaryWeaponSlotOne = metaItem.primaryWeaponSlotOne,
            primaryWeaponSlotTwo = metaItem.primaryWeaponSlotTwo,
            secondaryWeaponSlotOne = metaItem.secondaryWeaponSlotOne,
            secondaryWeaponSlotTwo = metaItem.secondaryWeaponSlotTwo,
            headArmor = metaItem.headArmor,
            chestArmor = metaItem.chestArmor,
            legsArmor = metaItem.legsArmor,
            footArmor = metaItem.footArmor,
            glovesArmor = metaItem.glovesArmor,
            backArmor = metaItem.backArmor,
            pendant = metaItem.pendant,
            ringSlotOne = metaItem.ringSlotOne,
            ringSlotTwo = metaItem.ringSlotTwo
        )
    }
}