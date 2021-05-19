package com.artkachenko.core_impl.network

import com.artkachenko.core_api.network.api.RecipeApi
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.core_api.utils.debugVerbose
import com.artkachenko.core_impl.DispatchersModule
import com.artkachenko.core_impl.IoDispatcher
import com.artkachenko.core_impl.repositories.RecipeRepositoryImpl
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
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

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
                        debugLog(message)
                    }
                }
                level = LogLevel.ALL
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

    @Provides
    @Singleton
    @JvmStatic
    fun provideRecipeRepository(recipeApi: RecipeApi, @IoDispatcher dispatcher: CoroutineDispatcher) : RecipeRepository {
        return RecipeRepositoryImpl(recipeApi, dispatcher)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideRecipeApi() : RecipeApi {
        return RecipeApiImpl(provideHttpClient())
    }
}