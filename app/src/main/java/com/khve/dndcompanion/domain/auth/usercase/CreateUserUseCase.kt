package com.khve.dndcompanion.domain.auth.usercase

import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.repository.UserRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(userSignUpDto: UserSignUpDto): StateFlow<UserState> {
        return repository.createUser(userSignUpDto)
    }

}