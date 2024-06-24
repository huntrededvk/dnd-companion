package com.khve.dndcompanion.domain.main.usecase

import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.main.repository.MainRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: MainRepository
) {

    operator fun invoke(): StateFlow<UserState> {
        return repository.getCurrentUserFromDb()
    }

}
