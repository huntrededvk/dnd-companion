package com.khve.dndcompanion.domain.auth.usercase

import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.domain.auth.entity.AuthState
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.repository.SingUpRepository
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: SingUpRepository
) {

    operator fun invoke(userSignUpDto: UserSignUpDto): StateFlow<AuthState> {
        return repository.createUser(userSignUpDto)
    }

}