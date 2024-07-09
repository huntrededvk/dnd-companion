package com.khve.feature_auth.data.mapper

import com.khve.feature_auth.data.model.UserDbDto
import com.khve.feature_auth.data.model.UserSignUpDto
import com.khve.feature_auth.domain.entity.User
import com.khve.feature_auth.domain.entity.UserRole
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapUserSignUpDtoToUserDbDto(userSignUpDto: UserSignUpDto): UserDbDto {
        return UserDbDto(
            email = userSignUpDto.email,
            username = userSignUpDto.username,
            discord = userSignUpDto.discord,
            role = listOf(UserRole.AUTHORIZED),
        )
    }

    fun mapUserDbDtoToUser(userDbDto: UserDbDto, userUid: String): User {
        return User(
            uid = userUid,
            email = userDbDto.email,
            username = userDbDto.username,
            discord = userDbDto.discord,
            role = userDbDto.role
        )
    }

}
