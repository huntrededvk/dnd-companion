package com.khve.dndcompanion.domain.auth.usercase

import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.repository.UserRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DeleteCurrentUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): StateFlow<UserState> {
        return repository.deleteCurrentUser()
    }
}