package com.artkachenko.core_api.network.client

//class Either<Error: Throwable, T : Any> (
//    Left: Error,
//    Right: T
//)

sealed class Either
data class Left(val error: Throwable) : Either()
data class Right<T>(val data: T) : Either()
