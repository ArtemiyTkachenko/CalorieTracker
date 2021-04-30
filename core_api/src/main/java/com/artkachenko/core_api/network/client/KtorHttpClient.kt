package com.artkachenko.core_api.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
//
//
//suspend fun <T> HttpClient.requestAndCatch(
//    block: suspend HttpClient.() -> T,
//    errorHandler: suspend ResponseException.() -> T
//): T = runCatching { block() }
//    .getOrElse {
//        when (it) {
//            is ResponseException -> it.errorHandler()
//            else -> throw it
//        }
//    }
//}