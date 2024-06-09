package com.khve.dndcompanion.data.auth.mapper

import com.khve.dndcompanion.data.auth.model.UserDto
import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.domain.auth.enum.UserRole
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapUserSignUpDtoToUserDto(userSignUpDto: UserSignUpDto): UserDto {
        return UserDto(
            email = userSignUpDto.email,
            username = userSignUpDto.username,
            discord = userSignUpDto.discord,
            role = listOf(UserRole.AUTHORIZED),
        )
    }

}