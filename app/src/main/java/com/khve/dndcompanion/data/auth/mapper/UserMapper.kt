package com.khve.dndcompanion.data.auth.mapper

import com.khve.dndcompanion.data.auth.model.UserDbDto
import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.domain.auth.enum.UserRole
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapUserSignUpDtoToUserDto(userSignUpDto: UserSignUpDto): UserDbDto {
        return UserDbDto(
            email = userSignUpDto.email,
            username = userSignUpDto.username,
            discord = userSignUpDto.discord,
            role = listOf(UserRole.AUTHORIZED),
        )
    }

}