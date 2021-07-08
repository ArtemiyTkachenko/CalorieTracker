package com.artkachenko.core_impl.network

import com.artkachenko.core_api.network.api.RecipeApi
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.core_impl.repositories.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {

    @Binds
    abstract fun bindRecipeRepository(recipeRepositoryImpl: RecipeRepositoryImpl) : RecipeRepository

    @Binds
    abstract fun bindRecipeApi(recipeApiImpl: RecipeApiImpl) : RecipeApi

    companion object {

        @Provides
        @Reusable
        fun provideHttpClient() : HttpClient {
            return HttpClient(Android) {

                val timeOut = 60_000

                install(JsonFeature) {
                    serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })

                    engine {
                        acceptContentTypes = listOf(ContentType.Application.FormUrlEncoded)
                        connectTimeout = timeOut
                        socketTimeout = timeOut
                    }
                }

                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            debugLog(message, tag = "HTTPClient")
                        }
                    }
                    level = LogLevel.NONE
                }

                install(ResponseObserver) {
                    onResponse { response ->
                        debugLog("${response.status.value}")
                    }
                }

                install(DefaultRequest) {
                    parameter("apiKey", "c87a3abc6947480ba37093ddcdc6855d")
                }
            }
        }
    }
}