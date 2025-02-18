package com.busschedule.domain.usecase.login

import com.busschedule.domain.repository.LoginRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository){
//    operator fun invoke(loginUser: LoginUser) =
//        loginRepository.login(loginUser)
    suspend operator fun invoke(name: String, password: String) = runCatchingIgnoreCancelled {
        loginRepository.login(name, password)
    }
}

inline fun <T> runCatchingIgnoreCancelled(block: () -> T): Result<T> = runCatching(block)
    .onFailure { error ->
        if (error is CancellationException) throw error
    }