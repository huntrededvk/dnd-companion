package com.khve.feature_auth.domain.usercase

import com.khve.feature_auth.domain.entity.AuthState
import com.khve.feature_auth.domain.repository.SingUpRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: SingUpRepository
) {

    operator fun invoke(userSignUpDto: com.khve.feature_auth.data.model.UserSignUpDto): StateFlow<AuthState> {
        return repository.createUser(userSignUpDto)
    }

}
