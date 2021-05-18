package com.artkachenko.core_impl.network

import android.util.Log
import com.artkachenko.core_api.network.api.RecipeApi
import com.artkachenko.core_api.network.models.Ingredient
import com.artkachenko.core_api.network.models.RecipeDetailModel
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
//        val wrapper = client.get<RecipeResultsWrapper>(NetworkEndpoints.RecipeSearch) {
//            url.path("/recipes/search")
//            parameter("query", "chicken")
//        }
//        debugLog("results are ${wrapper.results}")
//        return wrapper.results
//        return emptyList()
        val results = Json {
            ignoreUnknownKeys = true
        }.decodeFromString<RecipeResultsWrapper>(Json.serializersModule.serializer(), mockResults)
        return results.results
    }

    override suspend fun getRecipeDetail(id: Long): RecipeDetailModel {
        return client.get<RecipeDetailModel>(NetworkEndpoints.getRecipeDetailEndPoint(id)) {
            parameter("includeNutrition", true)
            debugLog("API, url is ${url.build()}")
        }
//        return Json {ignoreUnknownKeys = true}.decodeFromString(Json.serializersModule.serializer(), mockDetail)
    }

    override suspend fun parseIngredients(ingredients: List<String>): List<Ingredient> {
        return emptyList()
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

//val mockDetail = """{
//    "vegetarian": false,
//    "vegan": false,
//    "glutenFree": false,
//    "dairyFree": false,
//    "veryHealthy": false,
//    "cheap": false,
//    "veryPopular": false,
//    "sustainable": false,
//    "weightWatcherSmartPoints": 17,
//    "gaps": "no",
//    "lowFodmap": false,
//    "aggregateLikes": 209,
//    "spoonacularScore": 82.0,
//    "healthScore": 19.0,
//    "creditsText": "Full Belly Sisters",
//    "license": "CC BY-SA 3.0",
//    "sourceName": "Full Belly Sisters",
//    "pricePerServing": 163.15,
//    "extendedIngredients": [
//        {
//            "id": 1001,
//            "aisle": "Milk, Eggs, Other Dairy",
//            "image": "butter-sliced.jpg",
//            "consistency": "solid",
//            "name": "butter",
//            "nameClean": "butter",
//            "original": "1 tbsp butter",
//            "originalString": "1 tbsp butter",
//            "originalName": "butter",
//            "amount": 1.0,
//            "unit": "tbsp",
//            "meta": [],
//            "metaInformation": [],
//            "measures": {
//                "us": {
//                    "amount": 1.0,
//                    "unitShort": "Tbsp",
//                    "unitLong": "Tbsp"
//                },
//                "metric": {
//                    "amount": 1.0,
//                    "unitShort": "Tbsp",
//                    "unitLong": "Tbsp"
//                }
//            }
//        },
//        {
//            "id": 10011135,
//            "aisle": "Produce",
//            "image": "cauliflower.jpg",
//            "consistency": "solid",
//            "name": "cauliflower florets",
//            "nameClean": "cauliflower florets",
//            "original": "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
//            "originalString": "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
//            "originalName": "about frozen cauliflower florets, thawed, cut into bite-sized pieces",
//            "amount": 2.0,
//            "unit": "cups",
//            "meta": [
//                "frozen",
//                "thawed",
//                "cut into bite-sized pieces"
//            ],
//            "metaInformation": [
//                "frozen",
//                "thawed",
//                "cut into bite-sized pieces"
//            ],
//            "measures": {
//                "us": {
//                    "amount": 2.0,
//                    "unitShort": "cups",
//                    "unitLong": "cups"
//                },
//                "metric": {
//                    "amount": 473.176,
//                    "unitShort": "ml",
//                    "unitLong": "milliliters"
//                }
//            }
//        },
//        {
//            "id": 1041009,
//            "aisle": "Cheese",
//            "image": "cheddar-cheese.png",
//            "consistency": "solid",
//            "name": "cheese",
//            "nameClean": "cheese",
//            "original": "2 tbsp grated cheese (I used romano)",
//            "originalString": "2 tbsp grated cheese (I used romano)",
//            "originalName": "grated cheese (I used romano)",
//            "amount": 2.0,
//            "unit": "tbsp",
//            "meta": [
//                "grated",
//                "(I used romano)"
//            ],
//            "metaInformation": [
//                "grated",
//                "(I used romano)"
//            ],
//            "measures": {
//                "us": {
//                    "amount": 2.0,
//                    "unitShort": "Tbsps",
//                    "unitLong": "Tbsps"
//                },
//                "metric": {
//                    "amount": 2.0,
//                    "unitShort": "Tbsps",
//                    "unitLong": "Tbsps"
//                }
//            }
//        },
//        {
//            "id": 1034053,
//            "aisle": "Oil, Vinegar, Salad Dressing",
//            "image": "olive-oil.jpg",
//            "consistency": "liquid",
//            "name": "extra virgin olive oil",
//            "nameClean": "extra virgin olive oil",
//            "original": "1-2 tbsp extra virgin olive oil",
//            "originalString": "1-2 tbsp extra virgin olive oil",
//            "originalName": "extra virgin olive oil",
//            "amount": 1.0,
//            "unit": "tbsp",
//            "meta": [],
//            "metaInformation": [],
//            "measures": {
//                "us": {
//                    "amount": 1.0,
//                    "unitShort": "Tbsp",
//                    "unitLong": "Tbsp"
//                },
//                "metric": {
//                    "amount": 1.0,
//                    "unitShort": "Tbsp",
//                    "unitLong": "Tbsp"
//                }
//            }
//        },
//        {
//            "id": 11215,
//            "aisle": "Produce",
//            "image": "garlic.png",
//            "consistency": "solid",
//            "name": "garlic",
//            "nameClean": "garlic",
//            "original": "5-6 cloves garlic",
//            "originalString": "5-6 cloves garlic",
//            "originalName": "garlic",
//            "amount": 5.0,
//            "unit": "cloves",
//            "meta": [],
//            "metaInformation": [],
//            "measures": {
//                "us": {
//                    "amount": 5.0,
//                    "unitShort": "cloves",
//                    "unitLong": "cloves"
//                },
//                "metric": {
//                    "amount": 5.0,
//                    "unitShort": "cloves",
//                    "unitLong": "cloves"
//                }
//            }
//        },
//        {
//            "id": 20420,
//            "aisle": "Pasta and Rice",
//            "image": "fusilli.jpg",
//            "consistency": "solid",
//            "name": "pasta",
//            "nameClean": "pasta",
//            "original": "6-8 ounces pasta (I used linguine)",
//            "originalString": "6-8 ounces pasta (I used linguine)",
//            "originalName": "pasta (I used linguine)",
//            "amount": 6.0,
//            "unit": "ounces",
//            "meta": [
//                "(I used linguine)"
//            ],
//            "metaInformation": [
//                "(I used linguine)"
//            ],
//            "measures": {
//                "us": {
//                    "amount": 6.0,
//                    "unitShort": "oz",
//                    "unitLong": "ounces"
//                },
//                "metric": {
//                    "amount": 170.097,
//                    "unitShort": "g",
//                    "unitLong": "grams"
//                }
//            }
//        },
//        {
//            "id": 1032009,
//            "aisle": "Spices and Seasonings",
//            "image": "red-pepper-flakes.jpg",
//            "consistency": "solid",
//            "name": "red pepper flakes",
//            "nameClean": "red pepper flakes",
//            "original": "couple of pinches red pepper flakes, optional",
//            "originalString": "couple of pinches red pepper flakes, optional",
//            "originalName": "couple of red pepper flakes, optional",
//            "amount": 2.0,
//            "unit": "pinches",
//            "meta": [
//                "red"
//            ],
//            "metaInformation": [
//                "red"
//            ],
//            "measures": {
//                "us": {
//                    "amount": 2.0,
//                    "unitShort": "pinches",
//                    "unitLong": "pinches"
//                },
//                "metric": {
//                    "amount": 2.0,
//                    "unitShort": "pinches",
//                    "unitLong": "pinches"
//                }
//            }
//        },
//        {
//            "id": 1102047,
//            "aisle": "Spices and Seasonings",
//            "image": "salt-and-pepper.jpg",
//            "consistency": "solid",
//            "name": "salt and pepper",
//            "nameClean": "salt and pepper",
//            "original": "salt and pepper, to taste",
//            "originalString": "salt and pepper, to taste",
//            "originalName": "salt and pepper, to taste",
//            "amount": 2.0,
//            "unit": "servings",
//            "meta": [
//                "to taste"
//            ],
//            "metaInformation": [
//                "to taste"
//            ],
//            "measures": {
//                "us": {
//                    "amount": 2.0,
//                    "unitShort": "servings",
//                    "unitLong": "servings"
//                },
//                "metric": {
//                    "amount": 2.0,
//                    "unitShort": "servings",
//                    "unitLong": "servings"
//                }
//            }
//        },
//        {
//            "id": 11291,
//            "aisle": "Produce",
//            "image": "spring-onions.jpg",
//            "consistency": "solid",
//            "name": "scallions",
//            "nameClean": "spring onions",
//            "original": "3 scallions, chopped, white and green parts separated",
//            "originalString": "3 scallions, chopped, white and green parts separated",
//            "originalName": "scallions, chopped, white and green parts separated",
//            "amount": 3.0,
//            "unit": "",
//            "meta": [
//                "white",
//                "green",
//                "separated",
//                "chopped"
//            ],
//            "metaInformation": [
//                "white",
//                "green",
//                "separated",
//                "chopped"
//            ],
//            "measures": {
//                "us": {
//                    "amount": 3.0,
//                    "unitShort": "",
//                    "unitLong": ""
//                },
//                "metric": {
//                    "amount": 3.0,
//                    "unitShort": "",
//                    "unitLong": ""
//                }
//            }
//        },
//        {
//            "id": 14106,
//            "aisle": "Alcoholic Beverages",
//            "image": "white-wine.jpg",
//            "consistency": "liquid",
//            "name": "white wine",
//            "nameClean": "white wine",
//            "original": "2-3 tbsp white wine",
//            "originalString": "2-3 tbsp white wine",
//            "originalName": "white wine",
//            "amount": 2.0,
//            "unit": "tbsp",
//            "meta": [
//                "white"
//            ],
//            "metaInformation": [
//                "white"
//            ],
//            "measures": {
//                "us": {
//                    "amount": 2.0,
//                    "unitShort": "Tbsps",
//                    "unitLong": "Tbsps"
//                },
//                "metric": {
//                    "amount": 2.0,
//                    "unitShort": "Tbsps",
//                    "unitLong": "Tbsps"
//                }
//            }
//        },
//        {
//            "id": 99025,
//            "aisle": "Pasta and Rice",
//            "image": "breadcrumbs.jpg",
//            "consistency": "solid",
//            "name": "whole wheat bread crumbs",
//            "nameClean": "whole wheat breadcrumbs",
//            "original": "1/4 cup whole wheat bread crumbs (I used panko)",
//            "originalString": "1/4 cup whole wheat bread crumbs (I used panko)",
//            "originalName": "whole wheat bread crumbs (I used panko)",
//            "amount": 0.25,
//            "unit": "cup",
//            "meta": [
//                "whole wheat",
//                "(I used panko)"
//            ],
//            "metaInformation": [
//                "whole wheat",
//                "(I used panko)"
//            ],
//            "measures": {
//                "us": {
//                    "amount": 0.25,
//                    "unitShort": "cups",
//                    "unitLong": "cups"
//                },
//                "metric": {
//                    "amount": 59.147,
//                    "unitShort": "ml",
//                    "unitLong": "milliliters"
//                }
//            }
//        }
//    ],
//    "id": 716429,
//    "title": "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
//    "readyInMinutes": 45,
//    "servings": 2,
//    "sourceUrl": "http://fullbellysisters.blogspot.com/2012/06/pasta-with-garlic-scallions-cauliflower.html",
//    "image": "https://spoonacular.com/recipeImages/716429-556x370.jpg",
//    "imageType": "jpg",
//    "nutrition": {
//        "nutrients": [
//            {
//                "name": "Calories",
//                "title": "Calories",
//                "amount": 584.46,
//                "unit": "kcal",
//                "percentOfDailyNeeds": 29.22
//            },
//            {
//                "name": "Fat",
//                "title": "Fat",
//                "amount": 19.83,
//                "unit": "g",
//                "percentOfDailyNeeds": 30.51
//            },
//            {
//                "name": "Saturated Fat",
//                "title": "Saturated Fat",
//                "amount": 8.09,
//                "unit": "g",
//                "percentOfDailyNeeds": 50.57
//            },
//            {
//                "name": "Carbohydrates",
//                "title": "Carbohydrates",
//                "amount": 83.71,
//                "unit": "g",
//                "percentOfDailyNeeds": 27.9
//            },
//            {
//                "name": "Net Carbohydrates",
//                "title": "Net Carbohydrates",
//                "amount": 76.98,
//                "unit": "g",
//                "percentOfDailyNeeds": 27.99
//            },
//            {
//                "name": "Sugar",
//                "title": "Sugar",
//                "amount": 5.36,
//                "unit": "g",
//                "percentOfDailyNeeds": 5.95
//            },
//            {
//                "name": "Cholesterol",
//                "title": "Cholesterol",
//                "amount": 31.02,
//                "unit": "mg",
//                "percentOfDailyNeeds": 10.34
//            },
//            {
//                "name": "Sodium",
//                "title": "Sodium",
//                "amount": 451.42,
//                "unit": "mg",
//                "percentOfDailyNeeds": 19.63
//            },
//            {
//                "name": "Alcohol",
//                "title": "Alcohol",
//                "amount": 1.55,
//                "unit": "g",
//                "percentOfDailyNeeds": 8.58
//            },
//            {
//                "name": "Protein",
//                "title": "Protein",
//                "amount": 18.98,
//                "unit": "g",
//                "percentOfDailyNeeds": 37.97
//            },
//            {
//                "name": "Selenium",
//                "title": "Selenium",
//                "amount": 57.72,
//                "unit": "µg",
//                "percentOfDailyNeeds": 82.45
//            },
//            {
//                "name": "Vitamin C",
//                "title": "Vitamin C",
//                "amount": 56.62,
//                "unit": "mg",
//                "percentOfDailyNeeds": 68.64
//            },
//            {
//                "name": "Manganese",
//                "title": "Manganese",
//                "amount": 1.11,
//                "unit": "mg",
//                "percentOfDailyNeeds": 55.52
//            },
//            {
//                "name": "Vitamin K",
//                "title": "Vitamin K",
//                "amount": 58.27,
//                "unit": "µg",
//                "percentOfDailyNeeds": 55.49
//            },
//            {
//                "name": "Phosphorus",
//                "title": "Phosphorus",
//                "amount": 304.38,
//                "unit": "mg",
//                "percentOfDailyNeeds": 30.44
//            },
//            {
//                "name": "Fiber",
//                "title": "Fiber",
//                "amount": 6.73,
//                "unit": "g",
//                "percentOfDailyNeeds": 26.92
//            },
//            {
//                "name": "Folate",
//                "title": "Folate",
//                "amount": 87.14,
//                "unit": "µg",
//                "percentOfDailyNeeds": 21.79
//            },
//            {
//                "name": "Vitamin B6",
//                "title": "Vitamin B6",
//                "amount": 0.43,
//                "unit": "mg",
//                "percentOfDailyNeeds": 21.46
//            },
//            {
//                "name": "Magnesium",
//                "title": "Magnesium",
//                "amount": 71.55,
//                "unit": "mg",
//                "percentOfDailyNeeds": 17.89
//            },
//            {
//                "name": "Calcium",
//                "title": "Calcium",
//                "amount": 178.12,
//                "unit": "mg",
//                "percentOfDailyNeeds": 17.81
//            },
//            {
//                "name": "Potassium",
//                "title": "Potassium",
//                "amount": 597.53,
//                "unit": "mg",
//                "percentOfDailyNeeds": 17.07
//            },
//            {
//                "name": "Iron",
//                "title": "Iron",
//                "amount": 3.07,
//                "unit": "mg",
//                "percentOfDailyNeeds": 17.04
//            },
//            {
//                "name": "Copper",
//                "title": "Copper",
//                "amount": 0.33,
//                "unit": "mg",
//                "percentOfDailyNeeds": 16.43
//            },
//            {
//                "name": "Zinc",
//                "title": "Zinc",
//                "amount": 2.12,
//                "unit": "mg",
//                "percentOfDailyNeeds": 14.15
//            },
//            {
//                "name": "Vitamin B5",
//                "title": "Vitamin B5",
//                "amount": 1.17,
//                "unit": "mg",
//                "percentOfDailyNeeds": 11.69
//            },
//            {
//                "name": "Vitamin B2",
//                "title": "Vitamin B2",
//                "amount": 0.2,
//                "unit": "mg",
//                "percentOfDailyNeeds": 11.5
//            },
//            {
//                "name": "Vitamin A",
//                "title": "Vitamin A",
//                "amount": 537.51,
//                "unit": "IU",
//                "percentOfDailyNeeds": 10.75
//            },
//            {
//                "name": "Vitamin B3",
//                "title": "Vitamin B3",
//                "amount": 2.14,
//                "unit": "mg",
//                "percentOfDailyNeeds": 10.71
//            },
//            {
//                "name": "Vitamin B1",
//                "title": "Vitamin B1",
//                "amount": 0.16,
//                "unit": "mg",
//                "percentOfDailyNeeds": 10.46
//            },
//            {
//                "name": "Vitamin E",
//                "title": "Vitamin E",
//                "amount": 1.53,
//                "unit": "mg",
//                "percentOfDailyNeeds": 10.2
//            },
//            {
//                "name": "Vitamin B12",
//                "title": "Vitamin B12",
//                "amount": 0.14,
//                "unit": "µg",
//                "percentOfDailyNeeds": 2.28
//            },
//            {
//                "name": "Vitamin D",
//                "title": "Vitamin D",
//                "amount": 0.2,
//                "unit": "µg",
//                "percentOfDailyNeeds": 1.31
//            }
//        ],
//        "properties": [
//            {
//                "name": "Glycemic Index",
//                "title": "Glycemic Index",
//                "amount": 114.0,
//                "unit": ""
//            },
//            {
//                "name": "Glycemic Load",
//                "title": "Glycemic Load",
//                "amount": 27.56,
//                "unit": ""
//            }
//        ],
//        "flavanoids": [
//            {
//                "name": "Cyanidin",
//                "title": "Cyanidin",
//                "amount": 0.0,
//                "unit": "mg"
//            },
//            {
//                "name": "Petunidin",
//                "title": "Petunidin",
//                "amount": 0.0,
//                "unit": ""
//            },
//            {
//                "name": "Delphinidin",
//                "title": "Delphinidin",
//                "amount": 0.0,
//                "unit": ""
//            },
//            {
//                "name": "Malvidin",
//                "title": "Malvidin",
//                "amount": 0.01,
//                "unit": "mg"
//            },
//            {
//                "name": "Pelargonidin",
//                "title": "Pelargonidin",
//                "amount": 0.0,
//                "unit": ""
//            },
//            {
//                "name": "Peonidin",
//                "title": "Peonidin",
//                "amount": 0.0,
//                "unit": ""
//            },
//            {
//                "name": "Catechin",
//                "title": "Catechin",
//                "amount": 0.12,
//                "unit": "mg"
//            },
//            {
//                "name": "Epigallocatechin",
//                "title": "Epigallocatechin",
//                "amount": 0.0,
//                "unit": "mg"
//            },
//            {
//                "name": "Epicatechin",
//                "title": "Epicatechin",
//                "amount": 0.08,
//                "unit": "mg"
//            },
//            {
//                "name": "Epicatechin 3-gallate",
//                "title": "Epicatechin 3-gallate",
//                "amount": 0.0,
//                "unit": "mg"
//            },
//            {
//                "name": "Epigallocatechin 3-gallate",
//                "title": "Epigallocatechin 3-gallate",
//                "amount": 0.0,
//                "unit": "mg"
//            },
//            {
//                "name": "Theaflavin",
//                "title": "Theaflavin",
//                "amount": 0.0,
//                "unit": ""
//            },
//            {
//                "name": "Thearubigins",
//                "title": "Thearubigins",
//                "amount": 0.0,
//                "unit": ""
//            },
//            {
//                "name": "Eriodictyol",
//                "title": "Eriodictyol",
//                "amount": 0.0,
//                "unit": ""
//            },
//            {
//                "name": "Hesperetin",
//                "title": "Hesperetin",
//                "amount": 0.06,
//                "unit": "mg"
//            },
//            {
//                "name": "Naringenin",
//                "title": "Naringenin",
//                "amount": 0.06,
//                "unit": "mg"
//            },
//            {
//                "name": "Apigenin",
//                "title": "Apigenin",
//                "amount": 0.04,
//                "unit": "mg"
//            },
//            {
//                "name": "Luteolin",
//                "title": "Luteolin",
//                "amount": 0.1,
//                "unit": "mg"
//            },
//            {
//                "name": "Isorhamnetin",
//                "title": "Isorhamnetin",
//                "amount": 0.0,
//                "unit": "mg"
//            },
//            {
//                "name": "Kaempferol",
//                "title": "Kaempferol",
//                "amount": 0.63,
//                "unit": "mg"
//            },
//            {
//                "name": "Myricetin",
//                "title": "Myricetin",
//                "amount": 0.12,
//                "unit": "mg"
//            },
//            {
//                "name": "Quercetin",
//                "title": "Quercetin",
//                "amount": 2.6,
//                "unit": "mg"
//            },
//            {
//                "name": "Theaflavin-3,3'-digallate",
//                "title": "Theaflavin-3,3'-digallate",
//                "amount": 0.0,
//                "unit": ""
//            },
//            {
//                "name": "Theaflavin-3'-gallate",
//                "title": "Theaflavin-3'-gallate",
//                "amount": 0.0,
//                "unit": ""
//            },
//            {
//                "name": "Theaflavin-3-gallate",
//                "title": "Theaflavin-3-gallate",
//                "amount": 0.0,
//                "unit": ""
//            },
//            {
//                "name": "Gallocatechin",
//                "title": "Gallocatechin",
//                "amount": 0.0,
//                "unit": "mg"
//            }
//        ],
//        "ingredients": [
//            {
//                "id": 1001,
//                "name": "butter",
//                "amount": 0.5,
//                "unit": "tbsp",
//                "nutrients": [
//                    {
//                        "title": "Selenium",
//                        "name": "Selenium",
//                        "amount": 0.07,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Folate",
//                        "name": "Folate",
//                        "amount": 0.21,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Potassium",
//                        "name": "Potassium",
//                        "amount": 1.7,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B6",
//                        "name": "Vitamin B6",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Saturated Fat",
//                        "name": "Saturated Fat",
//                        "amount": 3.65,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Trans Fat",
//                        "name": "Trans Fat",
//                        "amount": 0.23,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Mono Unsaturated Fat",
//                        "name": "Mono Unsaturated Fat",
//                        "amount": 1.49,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Magnesium",
//                        "name": "Magnesium",
//                        "amount": 0.14,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B1",
//                        "name": "Vitamin B1",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Sugar",
//                        "name": "Sugar",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Phosphorus",
//                        "name": "Phosphorus",
//                        "amount": 1.7,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin D",
//                        "name": "Vitamin D",
//                        "amount": 0.11,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Fluoride",
//                        "name": "Fluoride",
//                        "amount": 0.2,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin C",
//                        "name": "Vitamin C",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Copper",
//                        "name": "Copper",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Protein",
//                        "name": "Protein",
//                        "amount": 0.06,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Folic Acid",
//                        "name": "Folic Acid",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Calories",
//                        "name": "Calories",
//                        "amount": 50.91,
//                        "unit": "kcal"
//                    },
//                    {
//                        "title": "Iron",
//                        "name": "Iron",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fat",
//                        "name": "Fat",
//                        "amount": 5.76,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin B3",
//                        "name": "Vitamin B3",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Zinc",
//                        "name": "Zinc",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fiber",
//                        "name": "Fiber",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Sodium",
//                        "name": "Sodium",
//                        "amount": 50.69,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Manganese",
//                        "name": "Manganese",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B5",
//                        "name": "Vitamin B5",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Net Carbohydrates",
//                        "name": "Net Carbohydrates",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin A",
//                        "name": "Vitamin A",
//                        "amount": 177.43,
//                        "unit": "IU"
//                    },
//                    {
//                        "title": "Vitamin E",
//                        "name": "Vitamin E",
//                        "amount": 0.16,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Calcium",
//                        "name": "Calcium",
//                        "amount": 1.7,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Carbohydrates",
//                        "name": "Carbohydrates",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Caffeine",
//                        "name": "Caffeine",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Cholesterol",
//                        "name": "Cholesterol",
//                        "amount": 15.27,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Alcohol",
//                        "name": "Alcohol",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Choline",
//                        "name": "Choline",
//                        "amount": 1.33,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin K",
//                        "name": "Vitamin K",
//                        "amount": 0.5,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin B2",
//                        "name": "Vitamin B2",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B12",
//                        "name": "Vitamin B12",
//                        "amount": 0.01,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Poly Unsaturated Fat",
//                        "name": "Poly Unsaturated Fat",
//                        "amount": 0.22,
//                        "unit": "g"
//                    }
//                ]
//            },
//            {
//                "id": 10011135,
//                "name": "cauliflower florets",
//                "amount": 1.0,
//                "unit": "cups",
//                "nutrients": [
//                    {
//                        "title": "Selenium",
//                        "name": "Selenium",
//                        "amount": 0.6,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Folate",
//                        "name": "Folate",
//                        "amount": 57.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Potassium",
//                        "name": "Potassium",
//                        "amount": 299.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B6",
//                        "name": "Vitamin B6",
//                        "amount": 0.18,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Saturated Fat",
//                        "name": "Saturated Fat",
//                        "amount": 0.06,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Trans Fat",
//                        "name": "Trans Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Mono Unsaturated Fat",
//                        "name": "Mono Unsaturated Fat",
//                        "amount": 0.02,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Magnesium",
//                        "name": "Magnesium",
//                        "amount": 15.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B1",
//                        "name": "Vitamin B1",
//                        "amount": 0.05,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Sugar",
//                        "name": "Sugar",
//                        "amount": 1.91,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Phosphorus",
//                        "name": "Phosphorus",
//                        "amount": 44.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin D",
//                        "name": "Vitamin D",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Fluoride",
//                        "name": "Fluoride",
//                        "amount": 1.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin C",
//                        "name": "Vitamin C",
//                        "amount": 48.2,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Copper",
//                        "name": "Copper",
//                        "amount": 0.04,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Protein",
//                        "name": "Protein",
//                        "amount": 1.92,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Folic Acid",
//                        "name": "Folic Acid",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Calories",
//                        "name": "Calories",
//                        "amount": 25.0,
//                        "unit": "kcal"
//                    },
//                    {
//                        "title": "Iron",
//                        "name": "Iron",
//                        "amount": 0.42,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fat",
//                        "name": "Fat",
//                        "amount": 0.28,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin B3",
//                        "name": "Vitamin B3",
//                        "amount": 0.51,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Zinc",
//                        "name": "Zinc",
//                        "amount": 0.27,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fiber",
//                        "name": "Fiber",
//                        "amount": 2.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Sodium",
//                        "name": "Sodium",
//                        "amount": 30.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Manganese",
//                        "name": "Manganese",
//                        "amount": 0.16,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B5",
//                        "name": "Vitamin B5",
//                        "amount": 0.67,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Net Carbohydrates",
//                        "name": "Net Carbohydrates",
//                        "amount": 2.97,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin A",
//                        "name": "Vitamin A",
//                        "amount": 0.0,
//                        "unit": "IU"
//                    },
//                    {
//                        "title": "Vitamin E",
//                        "name": "Vitamin E",
//                        "amount": 0.08,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Calcium",
//                        "name": "Calcium",
//                        "amount": 22.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Carbohydrates",
//                        "name": "Carbohydrates",
//                        "amount": 4.97,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Caffeine",
//                        "name": "Caffeine",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Cholesterol",
//                        "name": "Cholesterol",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Alcohol",
//                        "name": "Alcohol",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Choline",
//                        "name": "Choline",
//                        "amount": 44.3,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin K",
//                        "name": "Vitamin K",
//                        "amount": 15.5,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin B2",
//                        "name": "Vitamin B2",
//                        "amount": 0.06,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B12",
//                        "name": "Vitamin B12",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Poly Unsaturated Fat",
//                        "name": "Poly Unsaturated Fat",
//                        "amount": 0.02,
//                        "unit": "g"
//                    }
//                ]
//            },
//            {
//                "id": 1041009,
//                "name": "cheese",
//                "amount": 1.0,
//                "unit": "tbsp",
//                "nutrients": [
//                    {
//                        "title": "Selenium",
//                        "name": "Selenium",
//                        "amount": 2.09,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Folate",
//                        "name": "Folate",
//                        "amount": 2.7,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Potassium",
//                        "name": "Potassium",
//                        "amount": 14.7,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B6",
//                        "name": "Vitamin B6",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Saturated Fat",
//                        "name": "Saturated Fat",
//                        "amount": 3.16,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Mono Unsaturated Fat",
//                        "name": "Mono Unsaturated Fat",
//                        "amount": 1.41,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Magnesium",
//                        "name": "Magnesium",
//                        "amount": 4.2,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B1",
//                        "name": "Vitamin B1",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Sugar",
//                        "name": "Sugar",
//                        "amount": 0.08,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Phosphorus",
//                        "name": "Phosphorus",
//                        "amount": 76.8,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin D",
//                        "name": "Vitamin D",
//                        "amount": 0.09,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Fluoride",
//                        "name": "Fluoride",
//                        "amount": 5.24,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin C",
//                        "name": "Vitamin C",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Copper",
//                        "name": "Copper",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Protein",
//                        "name": "Protein",
//                        "amount": 3.74,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Folic Acid",
//                        "name": "Folic Acid",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Calories",
//                        "name": "Calories",
//                        "amount": 60.45,
//                        "unit": "kcal"
//                    },
//                    {
//                        "title": "Iron",
//                        "name": "Iron",
//                        "amount": 0.1,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fat",
//                        "name": "Fat",
//                        "amount": 4.97,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin B3",
//                        "name": "Vitamin B3",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Zinc",
//                        "name": "Zinc",
//                        "amount": 0.47,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fiber",
//                        "name": "Fiber",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Sodium",
//                        "name": "Sodium",
//                        "amount": 93.15,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Manganese",
//                        "name": "Manganese",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B5",
//                        "name": "Vitamin B5",
//                        "amount": 0.06,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Net Carbohydrates",
//                        "name": "Net Carbohydrates",
//                        "amount": 0.19,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin A",
//                        "name": "Vitamin A",
//                        "amount": 150.3,
//                        "unit": "IU"
//                    },
//                    {
//                        "title": "Vitamin E",
//                        "name": "Vitamin E",
//                        "amount": 0.04,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Calcium",
//                        "name": "Calcium",
//                        "amount": 108.15,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Carbohydrates",
//                        "name": "Carbohydrates",
//                        "amount": 0.19,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Caffeine",
//                        "name": "Caffeine",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Cholesterol",
//                        "name": "Cholesterol",
//                        "amount": 15.75,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Alcohol",
//                        "name": "Alcohol",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Choline",
//                        "name": "Choline",
//                        "amount": 2.48,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin K",
//                        "name": "Vitamin K",
//                        "amount": 0.42,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin B2",
//                        "name": "Vitamin B2",
//                        "amount": 0.06,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B12",
//                        "name": "Vitamin B12",
//                        "amount": 0.12,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Poly Unsaturated Fat",
//                        "name": "Poly Unsaturated Fat",
//                        "amount": 0.14,
//                        "unit": "g"
//                    }
//                ]
//            },
//            {
//                "id": 1034053,
//                "name": "extra virgin olive oil",
//                "amount": 0.5,
//                "unit": "tbsp",
//                "nutrients": [
//                    {
//                        "title": "Selenium",
//                        "name": "Selenium",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Folate",
//                        "name": "Folate",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Potassium",
//                        "name": "Potassium",
//                        "amount": 0.07,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B6",
//                        "name": "Vitamin B6",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Saturated Fat",
//                        "name": "Saturated Fat",
//                        "amount": 0.97,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Mono Unsaturated Fat",
//                        "name": "Mono Unsaturated Fat",
//                        "amount": 5.11,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Magnesium",
//                        "name": "Magnesium",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B1",
//                        "name": "Vitamin B1",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Sugar",
//                        "name": "Sugar",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Phosphorus",
//                        "name": "Phosphorus",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin D",
//                        "name": "Vitamin D",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin C",
//                        "name": "Vitamin C",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Copper",
//                        "name": "Copper",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Protein",
//                        "name": "Protein",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Folic Acid",
//                        "name": "Folic Acid",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Calories",
//                        "name": "Calories",
//                        "amount": 61.88,
//                        "unit": "kcal"
//                    },
//                    {
//                        "title": "Iron",
//                        "name": "Iron",
//                        "amount": 0.04,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fat",
//                        "name": "Fat",
//                        "amount": 7.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin B3",
//                        "name": "Vitamin B3",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Zinc",
//                        "name": "Zinc",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fiber",
//                        "name": "Fiber",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Sodium",
//                        "name": "Sodium",
//                        "amount": 0.14,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Manganese",
//                        "name": "Manganese",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B5",
//                        "name": "Vitamin B5",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Net Carbohydrates",
//                        "name": "Net Carbohydrates",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin A",
//                        "name": "Vitamin A",
//                        "amount": 0.0,
//                        "unit": "IU"
//                    },
//                    {
//                        "title": "Vitamin E",
//                        "name": "Vitamin E",
//                        "amount": 1.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Calcium",
//                        "name": "Calcium",
//                        "amount": 0.07,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Carbohydrates",
//                        "name": "Carbohydrates",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Caffeine",
//                        "name": "Caffeine",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Cholesterol",
//                        "name": "Cholesterol",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Alcohol",
//                        "name": "Alcohol",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Choline",
//                        "name": "Choline",
//                        "amount": 0.02,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin K",
//                        "name": "Vitamin K",
//                        "amount": 4.21,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin B2",
//                        "name": "Vitamin B2",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B12",
//                        "name": "Vitamin B12",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Poly Unsaturated Fat",
//                        "name": "Poly Unsaturated Fat",
//                        "amount": 0.74,
//                        "unit": "g"
//                    }
//                ]
//            },
//            {
//                "id": 11215,
//                "name": "garlic",
//                "amount": 2.5,
//                "unit": "cloves",
//                "nutrients": [
//                    {
//                        "title": "Selenium",
//                        "name": "Selenium",
//                        "amount": 1.07,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Folate",
//                        "name": "Folate",
//                        "amount": 0.23,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Potassium",
//                        "name": "Potassium",
//                        "amount": 30.08,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B6",
//                        "name": "Vitamin B6",
//                        "amount": 0.09,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Saturated Fat",
//                        "name": "Saturated Fat",
//                        "amount": 0.01,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Mono Unsaturated Fat",
//                        "name": "Mono Unsaturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Magnesium",
//                        "name": "Magnesium",
//                        "amount": 1.88,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B1",
//                        "name": "Vitamin B1",
//                        "amount": 0.02,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Sugar",
//                        "name": "Sugar",
//                        "amount": 0.08,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Phosphorus",
//                        "name": "Phosphorus",
//                        "amount": 11.48,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin D",
//                        "name": "Vitamin D",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin C",
//                        "name": "Vitamin C",
//                        "amount": 2.34,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Copper",
//                        "name": "Copper",
//                        "amount": 0.02,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Protein",
//                        "name": "Protein",
//                        "amount": 0.48,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Folic Acid",
//                        "name": "Folic Acid",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Calories",
//                        "name": "Calories",
//                        "amount": 11.18,
//                        "unit": "kcal"
//                    },
//                    {
//                        "title": "Iron",
//                        "name": "Iron",
//                        "amount": 0.13,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fat",
//                        "name": "Fat",
//                        "amount": 0.04,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin B3",
//                        "name": "Vitamin B3",
//                        "amount": 0.05,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Zinc",
//                        "name": "Zinc",
//                        "amount": 0.09,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fiber",
//                        "name": "Fiber",
//                        "amount": 0.16,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Sodium",
//                        "name": "Sodium",
//                        "amount": 1.27,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Manganese",
//                        "name": "Manganese",
//                        "amount": 0.13,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B5",
//                        "name": "Vitamin B5",
//                        "amount": 0.04,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Net Carbohydrates",
//                        "name": "Net Carbohydrates",
//                        "amount": 2.32,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin A",
//                        "name": "Vitamin A",
//                        "amount": 0.68,
//                        "unit": "IU"
//                    },
//                    {
//                        "title": "Vitamin E",
//                        "name": "Vitamin E",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Calcium",
//                        "name": "Calcium",
//                        "amount": 13.58,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Carbohydrates",
//                        "name": "Carbohydrates",
//                        "amount": 2.48,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Caffeine",
//                        "name": "Caffeine",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Cholesterol",
//                        "name": "Cholesterol",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Alcohol",
//                        "name": "Alcohol",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Choline",
//                        "name": "Choline",
//                        "amount": 1.74,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin K",
//                        "name": "Vitamin K",
//                        "amount": 0.13,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin B2",
//                        "name": "Vitamin B2",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B12",
//                        "name": "Vitamin B12",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Poly Unsaturated Fat",
//                        "name": "Poly Unsaturated Fat",
//                        "amount": 0.02,
//                        "unit": "g"
//                    }
//                ]
//            },
//            {
//                "id": 20420,
//                "name": "pasta",
//                "amount": 3.0,
//                "unit": "ounces",
//                "nutrients": [
//                    {
//                        "title": "Selenium",
//                        "name": "Selenium",
//                        "amount": 53.75,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Folate",
//                        "name": "Folate",
//                        "amount": 15.31,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Potassium",
//                        "name": "Potassium",
//                        "amount": 189.66,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B6",
//                        "name": "Vitamin B6",
//                        "amount": 0.12,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Saturated Fat",
//                        "name": "Saturated Fat",
//                        "amount": 0.24,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Trans Fat",
//                        "name": "Trans Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Mono Unsaturated Fat",
//                        "name": "Mono Unsaturated Fat",
//                        "amount": 0.15,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Magnesium",
//                        "name": "Magnesium",
//                        "amount": 45.08,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B1",
//                        "name": "Vitamin B1",
//                        "amount": 0.08,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Sugar",
//                        "name": "Sugar",
//                        "amount": 2.27,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Phosphorus",
//                        "name": "Phosphorus",
//                        "amount": 160.74,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin D",
//                        "name": "Vitamin D",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin C",
//                        "name": "Vitamin C",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Copper",
//                        "name": "Copper",
//                        "amount": 0.25,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Protein",
//                        "name": "Protein",
//                        "amount": 11.09,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Folic Acid",
//                        "name": "Folic Acid",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Calories",
//                        "name": "Calories",
//                        "amount": 315.53,
//                        "unit": "kcal"
//                    },
//                    {
//                        "title": "Iron",
//                        "name": "Iron",
//                        "amount": 1.11,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fat",
//                        "name": "Fat",
//                        "amount": 1.28,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin B3",
//                        "name": "Vitamin B3",
//                        "amount": 1.45,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Zinc",
//                        "name": "Zinc",
//                        "amount": 1.2,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fiber",
//                        "name": "Fiber",
//                        "amount": 2.72,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Sodium",
//                        "name": "Sodium",
//                        "amount": 5.1,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Manganese",
//                        "name": "Manganese",
//                        "amount": 0.78,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B5",
//                        "name": "Vitamin B5",
//                        "amount": 0.37,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Net Carbohydrates",
//                        "name": "Net Carbohydrates",
//                        "amount": 60.78,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin A",
//                        "name": "Vitamin A",
//                        "amount": 0.0,
//                        "unit": "IU"
//                    },
//                    {
//                        "title": "Vitamin E",
//                        "name": "Vitamin E",
//                        "amount": 0.09,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Calcium",
//                        "name": "Calcium",
//                        "amount": 17.86,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Carbohydrates",
//                        "name": "Carbohydrates",
//                        "amount": 63.51,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Cholesterol",
//                        "name": "Cholesterol",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Alcohol",
//                        "name": "Alcohol",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin K",
//                        "name": "Vitamin K",
//                        "amount": 0.09,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin B2",
//                        "name": "Vitamin B2",
//                        "amount": 0.05,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B12",
//                        "name": "Vitamin B12",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Poly Unsaturated Fat",
//                        "name": "Poly Unsaturated Fat",
//                        "amount": 0.48,
//                        "unit": "g"
//                    }
//                ]
//            },
//            {
//                "id": 1032009,
//                "name": "red pepper flakes",
//                "amount": 1.0,
//                "unit": "pinches",
//                "nutrients": [
//                    {
//                        "title": "Selenium",
//                        "name": "Selenium",
//                        "amount": 0.02,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Folate",
//                        "name": "Folate",
//                        "amount": 0.03,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Potassium",
//                        "name": "Potassium",
//                        "amount": 1.95,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B6",
//                        "name": "Vitamin B6",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Saturated Fat",
//                        "name": "Saturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Trans Fat",
//                        "name": "Trans Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Mono Unsaturated Fat",
//                        "name": "Mono Unsaturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Magnesium",
//                        "name": "Magnesium",
//                        "amount": 0.15,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B1",
//                        "name": "Vitamin B1",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Sugar",
//                        "name": "Sugar",
//                        "amount": 0.01,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Phosphorus",
//                        "name": "Phosphorus",
//                        "amount": 0.3,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin D",
//                        "name": "Vitamin D",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin C",
//                        "name": "Vitamin C",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Copper",
//                        "name": "Copper",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Protein",
//                        "name": "Protein",
//                        "amount": 0.01,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Folic Acid",
//                        "name": "Folic Acid",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Calories",
//                        "name": "Calories",
//                        "amount": 0.28,
//                        "unit": "kcal"
//                    },
//                    {
//                        "title": "Iron",
//                        "name": "Iron",
//                        "amount": 0.02,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fat",
//                        "name": "Fat",
//                        "amount": 0.01,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin B3",
//                        "name": "Vitamin B3",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Zinc",
//                        "name": "Zinc",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fiber",
//                        "name": "Fiber",
//                        "amount": 0.03,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Sodium",
//                        "name": "Sodium",
//                        "amount": 1.64,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Manganese",
//                        "name": "Manganese",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B5",
//                        "name": "Vitamin B5",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Net Carbohydrates",
//                        "name": "Net Carbohydrates",
//                        "amount": 0.01,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin A",
//                        "name": "Vitamin A",
//                        "amount": 29.65,
//                        "unit": "IU"
//                    },
//                    {
//                        "title": "Vitamin E",
//                        "name": "Vitamin E",
//                        "amount": 0.04,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Calcium",
//                        "name": "Calcium",
//                        "amount": 0.33,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Carbohydrates",
//                        "name": "Carbohydrates",
//                        "amount": 0.05,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Caffeine",
//                        "name": "Caffeine",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Cholesterol",
//                        "name": "Cholesterol",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Alcohol",
//                        "name": "Alcohol",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Choline",
//                        "name": "Choline",
//                        "amount": 0.07,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin K",
//                        "name": "Vitamin K",
//                        "amount": 0.11,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin B2",
//                        "name": "Vitamin B2",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B12",
//                        "name": "Vitamin B12",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Poly Unsaturated Fat",
//                        "name": "Poly Unsaturated Fat",
//                        "amount": 0.01,
//                        "unit": "g"
//                    }
//                ]
//            },
//            {
//                "id": 1102047,
//                "name": "salt and pepper",
//                "amount": 1.0,
//                "unit": "servings",
//                "nutrients": [
//                    {
//                        "title": "Selenium",
//                        "name": "Selenium",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Folate",
//                        "name": "Folate",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Potassium",
//                        "name": "Potassium",
//                        "amount": 0.04,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B6",
//                        "name": "Vitamin B6",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Saturated Fat",
//                        "name": "Saturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Mono Unsaturated Fat",
//                        "name": "Mono Unsaturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Magnesium",
//                        "name": "Magnesium",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B1",
//                        "name": "Vitamin B1",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Sugar",
//                        "name": "Sugar",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Phosphorus",
//                        "name": "Phosphorus",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin D",
//                        "name": "Vitamin D",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Fluoride",
//                        "name": "Fluoride",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin C",
//                        "name": "Vitamin C",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Copper",
//                        "name": "Copper",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Protein",
//                        "name": "Protein",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Folic Acid",
//                        "name": "Folic Acid",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Calories",
//                        "name": "Calories",
//                        "amount": 0.0,
//                        "unit": "kcal"
//                    },
//                    {
//                        "title": "Iron",
//                        "name": "Iron",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fat",
//                        "name": "Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin B3",
//                        "name": "Vitamin B3",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Zinc",
//                        "name": "Zinc",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fiber",
//                        "name": "Fiber",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Sodium",
//                        "name": "Sodium",
//                        "amount": 193.79,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Manganese",
//                        "name": "Manganese",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B5",
//                        "name": "Vitamin B5",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Net Carbohydrates",
//                        "name": "Net Carbohydrates",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin A",
//                        "name": "Vitamin A",
//                        "amount": 0.0,
//                        "unit": "IU"
//                    },
//                    {
//                        "title": "Vitamin E",
//                        "name": "Vitamin E",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Calcium",
//                        "name": "Calcium",
//                        "amount": 0.12,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Carbohydrates",
//                        "name": "Carbohydrates",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Caffeine",
//                        "name": "Caffeine",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Cholesterol",
//                        "name": "Cholesterol",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Alcohol",
//                        "name": "Alcohol",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Choline",
//                        "name": "Choline",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin K",
//                        "name": "Vitamin K",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin B2",
//                        "name": "Vitamin B2",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B12",
//                        "name": "Vitamin B12",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Poly Unsaturated Fat",
//                        "name": "Poly Unsaturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    }
//                ]
//            },
//            {
//                "id": 11291,
//                "name": "scallions",
//                "amount": 1.5,
//                "unit": "",
//                "nutrients": [
//                    {
//                        "title": "Selenium",
//                        "name": "Selenium",
//                        "amount": 0.11,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Folate",
//                        "name": "Folate",
//                        "amount": 11.52,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Potassium",
//                        "name": "Potassium",
//                        "amount": 49.68,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B6",
//                        "name": "Vitamin B6",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Saturated Fat",
//                        "name": "Saturated Fat",
//                        "amount": 0.01,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Mono Unsaturated Fat",
//                        "name": "Mono Unsaturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Magnesium",
//                        "name": "Magnesium",
//                        "amount": 3.6,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B1",
//                        "name": "Vitamin B1",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Sugar",
//                        "name": "Sugar",
//                        "amount": 0.42,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Phosphorus",
//                        "name": "Phosphorus",
//                        "amount": 6.66,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin D",
//                        "name": "Vitamin D",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin C",
//                        "name": "Vitamin C",
//                        "amount": 3.38,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Copper",
//                        "name": "Copper",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Protein",
//                        "name": "Protein",
//                        "amount": 0.33,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Folic Acid",
//                        "name": "Folic Acid",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Calories",
//                        "name": "Calories",
//                        "amount": 5.76,
//                        "unit": "kcal"
//                    },
//                    {
//                        "title": "Iron",
//                        "name": "Iron",
//                        "amount": 0.27,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fat",
//                        "name": "Fat",
//                        "amount": 0.03,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin B3",
//                        "name": "Vitamin B3",
//                        "amount": 0.09,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Zinc",
//                        "name": "Zinc",
//                        "amount": 0.07,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fiber",
//                        "name": "Fiber",
//                        "amount": 0.47,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Sodium",
//                        "name": "Sodium",
//                        "amount": 2.88,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Manganese",
//                        "name": "Manganese",
//                        "amount": 0.03,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B5",
//                        "name": "Vitamin B5",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Net Carbohydrates",
//                        "name": "Net Carbohydrates",
//                        "amount": 0.85,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin A",
//                        "name": "Vitamin A",
//                        "amount": 179.46,
//                        "unit": "IU"
//                    },
//                    {
//                        "title": "Vitamin E",
//                        "name": "Vitamin E",
//                        "amount": 0.1,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Calcium",
//                        "name": "Calcium",
//                        "amount": 12.96,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Carbohydrates",
//                        "name": "Carbohydrates",
//                        "amount": 1.32,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Caffeine",
//                        "name": "Caffeine",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Cholesterol",
//                        "name": "Cholesterol",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Alcohol",
//                        "name": "Alcohol",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Choline",
//                        "name": "Choline",
//                        "amount": 1.03,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin K",
//                        "name": "Vitamin K",
//                        "amount": 37.26,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin B2",
//                        "name": "Vitamin B2",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B12",
//                        "name": "Vitamin B12",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Poly Unsaturated Fat",
//                        "name": "Poly Unsaturated Fat",
//                        "amount": 0.01,
//                        "unit": "g"
//                    }
//                ]
//            },
//            {
//                "id": 14106,
//                "name": "white wine",
//                "amount": 1.0,
//                "unit": "tbsp",
//                "nutrients": [
//                    {
//                        "title": "Selenium",
//                        "name": "Selenium",
//                        "amount": 0.02,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Folate",
//                        "name": "Folate",
//                        "amount": 0.15,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Potassium",
//                        "name": "Potassium",
//                        "amount": 10.65,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B6",
//                        "name": "Vitamin B6",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Saturated Fat",
//                        "name": "Saturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Mono Unsaturated Fat",
//                        "name": "Mono Unsaturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Magnesium",
//                        "name": "Magnesium",
//                        "amount": 1.5,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B1",
//                        "name": "Vitamin B1",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Sugar",
//                        "name": "Sugar",
//                        "amount": 0.14,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Phosphorus",
//                        "name": "Phosphorus",
//                        "amount": 2.7,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin D",
//                        "name": "Vitamin D",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Fluoride",
//                        "name": "Fluoride",
//                        "amount": 30.3,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin C",
//                        "name": "Vitamin C",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Copper",
//                        "name": "Copper",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Protein",
//                        "name": "Protein",
//                        "amount": 0.01,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Folic Acid",
//                        "name": "Folic Acid",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Calories",
//                        "name": "Calories",
//                        "amount": 12.3,
//                        "unit": "kcal"
//                    },
//                    {
//                        "title": "Iron",
//                        "name": "Iron",
//                        "amount": 0.04,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fat",
//                        "name": "Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin B3",
//                        "name": "Vitamin B3",
//                        "amount": 0.02,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Zinc",
//                        "name": "Zinc",
//                        "amount": 0.02,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fiber",
//                        "name": "Fiber",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Sodium",
//                        "name": "Sodium",
//                        "amount": 0.75,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Manganese",
//                        "name": "Manganese",
//                        "amount": 0.02,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B5",
//                        "name": "Vitamin B5",
//                        "amount": 0.01,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Net Carbohydrates",
//                        "name": "Net Carbohydrates",
//                        "amount": 0.39,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin A",
//                        "name": "Vitamin A",
//                        "amount": 0.0,
//                        "unit": "IU"
//                    },
//                    {
//                        "title": "Vitamin E",
//                        "name": "Vitamin E",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Calcium",
//                        "name": "Calcium",
//                        "amount": 1.35,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Carbohydrates",
//                        "name": "Carbohydrates",
//                        "amount": 0.39,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Caffeine",
//                        "name": "Caffeine",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Cholesterol",
//                        "name": "Cholesterol",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Alcohol",
//                        "name": "Alcohol",
//                        "amount": 1.55,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Choline",
//                        "name": "Choline",
//                        "amount": 0.65,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin K",
//                        "name": "Vitamin K",
//                        "amount": 0.06,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin B2",
//                        "name": "Vitamin B2",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B12",
//                        "name": "Vitamin B12",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Poly Unsaturated Fat",
//                        "name": "Poly Unsaturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    }
//                ]
//            },
//            {
//                "id": 99025,
//                "name": "whole wheat bread crumbs",
//                "amount": 0.13,
//                "unit": "cup",
//                "nutrients": [
//                    {
//                        "title": "Selenium",
//                        "name": "Selenium",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Folate",
//                        "name": "Folate",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Potassium",
//                        "name": "Potassium",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B6",
//                        "name": "Vitamin B6",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Saturated Fat",
//                        "name": "Saturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Trans Fat",
//                        "name": "Trans Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Mono Unsaturated Fat",
//                        "name": "Mono Unsaturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Magnesium",
//                        "name": "Magnesium",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B1",
//                        "name": "Vitamin B1",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Sugar",
//                        "name": "Sugar",
//                        "amount": 0.45,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Phosphorus",
//                        "name": "Phosphorus",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin D",
//                        "name": "Vitamin D",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Fluoride",
//                        "name": "Fluoride",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin C",
//                        "name": "Vitamin C",
//                        "amount": 2.7,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Copper",
//                        "name": "Copper",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Protein",
//                        "name": "Protein",
//                        "amount": 1.35,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Calories",
//                        "name": "Calories",
//                        "amount": 41.18,
//                        "unit": "kcal"
//                    },
//                    {
//                        "title": "Iron",
//                        "name": "Iron",
//                        "amount": 0.95,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fat",
//                        "name": "Fat",
//                        "amount": 0.45,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin B3",
//                        "name": "Vitamin B3",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Zinc",
//                        "name": "Zinc",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Fiber",
//                        "name": "Fiber",
//                        "amount": 1.35,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Sodium",
//                        "name": "Sodium",
//                        "amount": 72.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Manganese",
//                        "name": "Manganese",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B5",
//                        "name": "Vitamin B5",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Net Carbohydrates",
//                        "name": "Net Carbohydrates",
//                        "amount": 9.45,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin A",
//                        "name": "Vitamin A",
//                        "amount": 0.0,
//                        "unit": "IU"
//                    },
//                    {
//                        "title": "Vitamin E",
//                        "name": "Vitamin E",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Calcium",
//                        "name": "Calcium",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Carbohydrates",
//                        "name": "Carbohydrates",
//                        "amount": 10.8,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Caffeine",
//                        "name": "Caffeine",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Cholesterol",
//                        "name": "Cholesterol",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Alcohol",
//                        "name": "Alcohol",
//                        "amount": 0.0,
//                        "unit": "g"
//                    },
//                    {
//                        "title": "Vitamin K",
//                        "name": "Vitamin K",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Vitamin B2",
//                        "name": "Vitamin B2",
//                        "amount": 0.0,
//                        "unit": "mg"
//                    },
//                    {
//                        "title": "Vitamin B12",
//                        "name": "Vitamin B12",
//                        "amount": 0.0,
//                        "unit": "µg"
//                    },
//                    {
//                        "title": "Poly Unsaturated Fat",
//                        "name": "Poly Unsaturated Fat",
//                        "amount": 0.0,
//                        "unit": "g"
//                    }
//                ]
//            }
//        ],
//        "caloricBreakdown": {
//            "percentProtein": 12.89,
//            "percentFat": 30.29,
//            "percentCarbs": 56.82
//        },
//        "weightPerServing": {
//            "amount": 269,
//            "unit": "g"
//        }
//    },
//    "summary": "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs might be just the main course you are searching for. This recipe makes 2 servings with <b>636 calories</b>, <b>21g of protein</b>, and <b>20g of fat</b> each. For <b>${'$'}1.83 per serving</b>, this recipe <b>covers 24%</b> of your daily requirements of vitamins and minerals. From preparation to the plate, this recipe takes about <b>45 minutes</b>. This recipe is liked by 209 foodies and cooks. If you have pasta, salt and pepper, cheese, and a few other ingredients on hand, you can make it. To use up the extra virgin olive oil you could follow this main course with the <a href=\"https://spoonacular.com/recipes/peach-crisp-healthy-crisp-for-breakfast-487698\">Peach Crisp: Healthy Crisp for Breakfast</a> as a dessert. All things considered, we decided this recipe <b>deserves a spoonacular score of 86%</b>. This score is tremendous. Try <a href=\"https://spoonacular.com/recipes/cauliflower-gratin-with-garlic-breadcrumbs-318375\">Cauliflower Gratin with Garlic Breadcrumbs</a>, <a href=\"https://spoonacular.com/recipes/pasta-with-cauliflower-sausage-breadcrumbs-30437\">Pasta With Cauliflower, Sausage, & Breadcrumbs</a>, and <a href=\"https://spoonacular.com/recipes/pasta-with-roasted-cauliflower-parsley-and-breadcrumbs-30738\">Pasta With Roasted Cauliflower, Parsley, And Breadcrumbs</a> for similar recipes.",
//    "cuisines": [],
//    "dishTypes": [
//        "lunch",
//        "main course",
//        "main dish",
//        "dinner"
//    ],
//    "diets": [],
//    "occasions": [],
//    "winePairing": {
//        "pairedWines": [],
//        "pairingText": "No one wine will suit every pasta dish. Pasta in a tomato-based sauce will usually work well with a medium-bodied red, such as a montepulciano or chianti. Pasta with seafood or pesto will fare better with a light-bodied white, such as a pinot grigio. Cheese-heavy pasta can pair well with red or white - you might try a sangiovese wine for hard cheeses and a chardonnay for soft cheeses. We may be able to make a better recommendation if you ask again with a specific pasta dish.",
//        "productMatches": []
//    },
//    "instructions": "",
//    "analyzedInstructions": [],
//    "originalId": null,
//    "spoonacularSourceUrl": "https://spoonacular.com/pasta-with-garlic-scallions-cauliflower-breadcrumbs-716429"
//}"""