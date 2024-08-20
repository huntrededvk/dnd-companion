package com.khve.feature_main.domain.usecase

import com.khve.feature_auth.domain.entity.UserState
import com.khve.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: MainRepository
) {

    operator fun invoke(): StateFlow<UserState> {
        return repository.getCurrentUserFromDb()
    }

}
