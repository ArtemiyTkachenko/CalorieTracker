package com.artkachenko.core_impl.network

import com.artkachenko.core_api.network.api.RecipeApi
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.models.RecipeResultsWrapper
import com.artkachenko.core_api.utils.debugLog
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.serializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeApiImpl @Inject constructor(private val client: HttpClient) : RecipeApi {

    override suspend fun getRecipeList(page: Int): List<RecipeEntity> {
//        val wrapper = client.get<RecipeResultsWrapper>(NetworkEndpoints.RecepeSearch) {
//            url.path("/recipes/search")
//            parameter("query", "chicken")
//        }
//        debugLog("results are ${wrapper.results}")
//        return wrapper.results
//        return emptyList()
        val results = Json {ignoreUnknownKeys = true}.decodeFromString<RecipeResultsWrapper>(Json.serializersModule.serializer(), mockResults)
        return results.results
    }

    override suspend fun getRecipeDetail(id: Int): RecipeEntity {
        return client.get("")
    }
}

val mockResults = """{
    "results": [
        {
            "id": 637876,
            "title": "Chicken 65",
            "readyInMinutes": 45,
            "servings": 6,
            "sourceUrl": "http://www.foodista.com/recipe/G4XPLKBW/chicken-65-chicken-marinaded-in-traditional-indian-spices-and-deep-fried",
            "openLicense": 2,
            "image": "Chicken-65-(-Chicken-Marinaded-In-Traditional-Indian-Spices-and-Deep-Fried)-637876.jpg"
        },
        {
            "id": 629963,
            "title": "chilli chicken",
            "author": "swasthi",
            "readyInMinutes": 160,
            "servings": 2,
            "sourceUrl": "https://spoonacular.com/-1424528841847",
            "openLicense": 2,
            "image": "chilli-chicken-629963.jpg"
        },
        {
            "id": 632810,
            "title": "Asian Chicken",
            "readyInMinutes": 45,
            "servings": 4,
            "sourceUrl": "https://www.foodista.com/recipe/PP54PKNQ/asian-chicken",
            "openLicense": 2,
            "image": "Asian-Chicken-632810.jpg"
        },
        {
            "id": 633959,
            "title": "Balti Chicken",
            "readyInMinutes": 45,
            "servings": 5,
            "sourceUrl": "http://www.foodista.com/recipe/2JXFWGVT/balti-chicken",
            "openLicense": 2,
            "image": "Balti-Chicken-633959.jpg"
        },
        {
            "id": 634476,
            "title": "Bbq Chicken",
            "readyInMinutes": 45,
            "servings": 4,
            "sourceUrl": "http://www.foodista.com/recipe/4BXYSZ32/bbq-chicken",
            "openLicense": 2,
            "image": "Bbq-Chicken-634476.jpg"
        },
        {
            "id": 637942,
            "title": "Chicken Arrozcaldo",
            "readyInMinutes": 45,
            "servings": 9,
            "sourceUrl": "https://www.foodista.com/recipe/BJ4SQ56F/chicken-arrozcaldo",
            "openLicense": 2,
            "image": "Chicken-Arrozcaldo-637942.jpg"
        },
        {
            "id": 637999,
            "title": "Chicken Burritos",
            "readyInMinutes": 45,
            "servings": 4,
            "sourceUrl": "http://www.foodista.com/recipe/YCKK77YK/chicken-burrito-by-bing",
            "openLicense": 2,
            "image": "Chicken-Burrito-By-Bing-637999.jpg"
        },
        {
            "id": 638002,
            "title": "Chicken Cacciatore",
            "readyInMinutes": 45,
            "servings": 6,
            "sourceUrl": "https://www.foodista.com/recipe/7M55MLHY/chicken-cacciatore",
            "openLicense": 2,
            "image": "Chicken-Cacciatore-638002.jpg"
        },
        {
            "id": 638086,
            "title": "Chicken Fingers",
            "readyInMinutes": 30,
            "servings": 2,
            "sourceUrl": "http://www.foodista.com/recipe/6GVTWWJ6/chicken-fingers",
            "openLicense": 2,
            "image": "Chicken-Fingers-638086.jpg"
        },
        {
            "id": 638125,
            "title": "Chicken In A Pot",
            "readyInMinutes": 45,
            "servings": 4,
            "sourceUrl": "https://www.foodista.com/recipe/4FNL5JJ8/chicken-in-a-pot",
            "openLicense": 2,
            "image": "Chicken-In-A-Pot-638125.jpg"
        }
    ],
    "baseUri": "https://spoonacular.com/recipeImages/",
    "offset": 0,
    "number": 10,
    "totalResults": 457,
    "processingTimeMs": 291,
    "expires": 1620249213228,
    "isStale": false
}"""