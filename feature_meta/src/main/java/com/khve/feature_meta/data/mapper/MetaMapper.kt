package com.khve.feature_meta.data.mapper

import com.khve.feature_meta.data.model.BuildKarmaDto
import com.khve.feature_meta.data.model.MetaCardItemDto
import com.khve.feature_meta.data.model.MetaItemDto
import com.khve.feature_meta.domain.entity.BuildKarma
import com.khve.feature_meta.domain.entity.MetaCardItem
import com.khve.feature_meta.domain.entity.MetaItem
import javax.inject.Inject

class MetaMapper @Inject constructor() {
    fun metaItemDtoToMetaItem(metaItemDto: MetaItemDto): MetaItem {
        return MetaItem(
            uid = metaItemDto.uid,
            title = metaItemDto.title,
            likes = buildKarmaDtoListToBuildKarmaList(metaItemDto.likes),
            dislikes = buildKarmaDtoListToBuildKarmaList(metaItemDto.dislikes),
            activated = metaItemDto.activated,
            partySize = metaItemDto.partySize,
            description = metaItemDto.description,
            tier = metaItemDto.tier,
            youtubeVideoId = metaItemDto.youtubeVideoId,
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
            likes = buildKarmaDtoListToBuildKarmaList(metaCardItemDto.likes),
            dislikes = buildKarmaDtoListToBuildKarmaList(metaCardItemDto.dislikes),
            activated = metaCardItemDto.activated,
            partySize = metaCardItemDto.partySize,
            tier = metaCardItemDto.tier,
            author = metaCardItemDto.author,
            dndClass = metaCardItemDto.dndClass
        )
    }

    fun metaItemToMetaItemDto(metaItem: MetaItem): MetaItemDto {
        return MetaItemDto(
            uid = metaItem.uid,
            title = metaItem.title,
            likes = buildKarmaListToBuildKarmaDtoList(metaItem.likes),
            dislikes = buildKarmaListToBuildKarmaDtoList(metaItem.dislikes),
            activated = metaItem.activated,
            partySize = metaItem.partySize,
            description = metaItem.description,
            tier = metaItem.tier,
            youtubeVideoId = metaItem.youtubeVideoId,
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

    private fun buildKarmaDtoListToBuildKarmaList(buildKarmaDto: List<BuildKarmaDto>) =
        buildKarmaDto.map { BuildKarma(it.userId) }

    private fun buildKarmaListToBuildKarmaDtoList(buildKarma: List<BuildKarma>) =
        buildKarma.map { BuildKarmaDto(it.userId) }
}