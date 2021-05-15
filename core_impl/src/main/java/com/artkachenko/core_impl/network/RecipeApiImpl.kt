package com.artkachenko.core_impl.network

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

    override suspend fun getRecipeDetail(id: Long): RecipeDetailModel {
//        return client.get<RecipeDetailModel>(NetworkEndpoints.getRecipeDetailEndPoint(id))
        return Json {ignoreUnknownKeys = true}.decodeFromString(Json.serializersModule.serializer(), mockDetail)
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

val mockDetail = """{
    "vegetarian": false,
    "vegan": false,
    "glutenFree": true,
    "dairyFree": true,
    "veryHealthy": false,
    "cheap": false,
    "veryPopular": false,
    "sustainable": false,
    "weightWatcherSmartPoints": 5,
    "gaps": "no",
    "lowFodmap": false,
    "aggregateLikes": 3,
    "spoonacularScore": 61.0,
    "healthScore": 22.0,
    "creditsText": "swasthi",
    "license": "spoonacular's terms",
    "sourceName": "spoonacular",
    "pricePerServing": 183.79,
    "extendedIngredients": [
        {
            "id": 10211821,
            "aisle": "Produce",
            "image": "bell-pepper-orange.png",
            "consistency": "solid",
            "name": "bell pepper",
            "nameClean": "bell pepper",
            "original": "¼ cup cubed bell pepper / capsicum",
            "originalString": "¼ cup cubed bell pepper / capsicum",
            "originalName": "cubed bell pepper / capsicum",
            "amount": 0.25,
            "unit": "cup",
            "meta": [
                "cubed"
            ],
            "metaInformation": [
                "cubed"
            ],
            "measures": {
                "us": {
                    "amount": 0.25,
                    "unitShort": "cups",
                    "unitLong": "cups"
                },
                "metric": {
                    "amount": 59.147,
                    "unitShort": "ml",
                    "unitLong": "milliliters"
                }
            }
        },
        {
            "id": 5062,
            "aisle": "Meat",
            "image": "chicken-breasts.png",
            "consistency": "solid",
            "name": "boneless chicken breast",
            "nameClean": "boneless chicken breast",
            "original": "250 grams boneless chicken breast",
            "originalString": "250 grams boneless chicken breast",
            "originalName": "boneless chicken breast",
            "amount": 250.0,
            "unit": "grams",
            "meta": [
                "boneless"
            ],
            "metaInformation": [
                "boneless"
            ],
            "measures": {
                "us": {
                    "amount": 8.818,
                    "unitShort": "oz",
                    "unitLong": "ounces"
                },
                "metric": {
                    "amount": 250.0,
                    "unitShort": "g",
                    "unitLong": "grams"
                }
            }
        },
        {
            "id": 11819,
            "aisle": "Produce",
            "image": "red-chili.jpg",
            "consistency": "solid",
            "name": "chilies",
            "nameClean": "chili pepper",
            "original": "1 to 2 green chilies slit and deseeded",
            "originalString": "1 to 2 green chilies slit and deseeded",
            "originalName": "to 2 green chilies slit and deseeded",
            "amount": 1.0,
            "unit": "",
            "meta": [
                "green",
                "deseeded"
            ],
            "metaInformation": [
                "green",
                "deseeded"
            ],
            "measures": {
                "us": {
                    "amount": 1.0,
                    "unitShort": "",
                    "unitLong": ""
                },
                "metric": {
                    "amount": 1.0,
                    "unitShort": "",
                    "unitLong": ""
                }
            }
        },
        {
            "id": 2009,
            "aisle": "Spices and Seasonings",
            "image": "chili-powder.jpg",
            "consistency": "solid",
            "name": "chilli powder",
            "nameClean": "chili powder",
            "original": "½ tsp. red chilli powder",
            "originalString": "½ tsp. red chilli powder",
            "originalName": "red chilli powder",
            "amount": 0.5,
            "unit": "tsp",
            "meta": [
                "red"
            ],
            "metaInformation": [
                "red"
            ],
            "measures": {
                "us": {
                    "amount": 0.5,
                    "unitShort": "tsps",
                    "unitLong": "teaspoons"
                },
                "metric": {
                    "amount": 0.5,
                    "unitShort": "tsps",
                    "unitLong": "teaspoons"
                }
            }
        },
        {
            "id": 20019,
            "aisle": "Ethnic Foods;Baking",
            "image": "corn-flour.jpg",
            "consistency": "solid",
            "name": "corn flour",
            "nameClean": "corn flour",
            "original": "2 tbsps. Corn flour",
            "originalString": "2 tbsps. Corn flour",
            "originalName": "Corn flour",
            "amount": 2.0,
            "unit": "tbsps",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 2.0,
                    "unitShort": "Tbsps",
                    "unitLong": "Tbsps"
                },
                "metric": {
                    "amount": 2.0,
                    "unitShort": "Tbsps",
                    "unitLong": "Tbsps"
                }
            }
        },
        {
            "id": 11215,
            "aisle": "Produce",
            "image": "garlic.png",
            "consistency": "solid",
            "name": "garlic",
            "nameClean": "garlic",
            "original": "¾ tbsp. garlic",
            "originalString": "¾ tbsp. garlic",
            "originalName": "garlic",
            "amount": 0.75,
            "unit": "tbsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 0.75,
                    "unitShort": "Tbsps",
                    "unitLong": "Tbsps"
                },
                "metric": {
                    "amount": 0.75,
                    "unitShort": "Tbsps",
                    "unitLong": "Tbsps"
                }
            }
        },
        {
            "id": 4582,
            "aisle": "Oil, Vinegar, Salad Dressing",
            "image": "vegetable-oil.jpg",
            "consistency": "liquid",
            "name": "oil",
            "nameClean": "cooking oil",
            "original": "Oil for deep frying",
            "originalString": "Oil for deep frying",
            "originalName": "Oil for deep frying",
            "amount": 2.0,
            "unit": "servings",
            "meta": [
                "for deep frying"
            ],
            "metaInformation": [
                "for deep frying"
            ],
            "measures": {
                "us": {
                    "amount": 2.0,
                    "unitShort": "servings",
                    "unitLong": "servings"
                },
                "metric": {
                    "amount": 2.0,
                    "unitShort": "servings",
                    "unitLong": "servings"
                }
            }
        },
        {
            "id": 11282,
            "aisle": "Produce",
            "image": "brown-onion.png",
            "consistency": "solid",
            "name": "onion",
            "nameClean": "onion",
            "original": "1 small onion thinly sliced",
            "originalString": "1 small onion thinly sliced",
            "originalName": "onion thinly sliced",
            "amount": 1.0,
            "unit": "small",
            "meta": [
                "thinly sliced"
            ],
            "metaInformation": [
                "thinly sliced"
            ],
            "measures": {
                "us": {
                    "amount": 1.0,
                    "unitShort": "small",
                    "unitLong": "small"
                },
                "metric": {
                    "amount": 1.0,
                    "unitShort": "small",
                    "unitLong": "small"
                }
            }
        },
        {
            "id": 2009,
            "aisle": "Spices and Seasonings",
            "image": "chili-powder.jpg",
            "consistency": "solid",
            "name": "red chili powder",
            "nameClean": "chili powder",
            "original": "¼ tsp. red chili powder",
            "originalString": "¼ tsp. red chili powder",
            "originalName": "red chili powder",
            "amount": 0.25,
            "unit": "tsp",
            "meta": [
                "red"
            ],
            "metaInformation": [
                "red"
            ],
            "measures": {
                "us": {
                    "amount": 0.25,
                    "unitShort": "tsps",
                    "unitLong": "teaspoons"
                },
                "metric": {
                    "amount": 0.25,
                    "unitShort": "tsps",
                    "unitLong": "teaspoons"
                }
            }
        },
        {
            "id": 2031,
            "aisle": "Spices and Seasonings",
            "image": "chili-powder.jpg",
            "consistency": "solid",
            "name": "red pepper powder",
            "nameClean": "ground cayenne pepper",
            "original": "¼ tsp pepper powder",
            "originalString": "¼ tsp pepper powder",
            "originalName": "pepper powder",
            "amount": 0.25,
            "unit": "tsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 0.25,
                    "unitShort": "tsps",
                    "unitLong": "teaspoons"
                },
                "metric": {
                    "amount": 0.25,
                    "unitShort": "tsps",
                    "unitLong": "teaspoons"
                }
            }
        },
        {
            "id": 16124,
            "aisle": "Ethnic Foods;Condiments",
            "image": "soy-sauce.jpg",
            "consistency": "liquid",
            "name": "soya sauce",
            "nameClean": "soy sauce",
            "original": "½ tbsp. soya sauce",
            "originalString": "½ tbsp. soya sauce",
            "originalName": "soya sauce",
            "amount": 0.5,
            "unit": "tbsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 0.5,
                    "unitShort": "Tbsps",
                    "unitLong": "Tbsps"
                },
                "metric": {
                    "amount": 0.5,
                    "unitShort": "Tbsps",
                    "unitLong": "Tbsps"
                }
            }
        },
        {
            "id": 16124,
            "aisle": "Ethnic Foods;Condiments",
            "image": "soy-sauce.jpg",
            "consistency": "liquid",
            "name": "soya sauce",
            "nameClean": "soy sauce",
            "original": "¾ tbsp. soya sauce",
            "originalString": "¾ tbsp. soya sauce",
            "originalName": "soya sauce",
            "amount": 0.75,
            "unit": "tbsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 0.75,
                    "unitShort": "Tbsps",
                    "unitLong": "Tbsps"
                },
                "metric": {
                    "amount": 0.75,
                    "unitShort": "Tbsps",
                    "unitLong": "Tbsps"
                }
            }
        },
        {
            "id": 11291,
            "aisle": "Produce",
            "image": "spring-onions.jpg",
            "consistency": "solid",
            "name": "spring onions",
            "nameClean": "spring onions",
            "original": "Spring onions for garnish",
            "originalString": "Spring onions for garnish",
            "originalName": "Spring onions for garnish",
            "amount": 2.0,
            "unit": "servings",
            "meta": [
                "for garnish"
            ],
            "metaInformation": [
                "for garnish"
            ],
            "measures": {
                "us": {
                    "amount": 2.0,
                    "unitShort": "servings",
                    "unitLong": "servings"
                },
                "metric": {
                    "amount": 2.0,
                    "unitShort": "servings",
                    "unitLong": "servings"
                }
            }
        },
        {
            "id": 19335,
            "aisle": "Baking",
            "image": "sugar-in-bowl.png",
            "consistency": "solid",
            "name": "sugar",
            "nameClean": "sugar",
            "original": "½ tsp. sugar",
            "originalString": "½ tsp. sugar",
            "originalName": "sugar",
            "amount": 0.5,
            "unit": "tsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 0.5,
                    "unitShort": "tsps",
                    "unitLong": "teaspoons"
                },
                "metric": {
                    "amount": 0.5,
                    "unitShort": "tsps",
                    "unitLong": "teaspoons"
                }
            }
        },
        {
            "id": 98962,
            "aisle": "Ethnic Foods",
            "image": "fish-sauce.jpg",
            "consistency": "liquid",
            "name": "sweet chilli sauce",
            "nameClean": "sweet chili sauce",
            "original": "½ tbsp. chilli sauce",
            "originalString": "½ tbsp. chilli sauce",
            "originalName": "chilli sauce",
            "amount": 0.5,
            "unit": "tbsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 0.5,
                    "unitShort": "Tbsps",
                    "unitLong": "Tbsps"
                },
                "metric": {
                    "amount": 0.5,
                    "unitShort": "Tbsps",
                    "unitLong": "Tbsps"
                }
            }
        },
        {
            "id": 98962,
            "aisle": "Ethnic Foods",
            "image": "fish-sauce.jpg",
            "consistency": "liquid",
            "name": "sweet chilli sauce",
            "nameClean": "sweet chili sauce",
            "original": "1 tsp chilli sauce",
            "originalString": "1 tsp chilli sauce",
            "originalName": "chilli sauce",
            "amount": 1.0,
            "unit": "tsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 1.0,
                    "unitShort": "tsp",
                    "unitLong": "teaspoon"
                },
                "metric": {
                    "amount": 1.0,
                    "unitShort": "tsp",
                    "unitLong": "teaspoon"
                }
            }
        },
        {
            "id": 2053,
            "aisle": "Oil, Vinegar, Salad Dressing",
            "image": "vinegar-(white).jpg",
            "consistency": "liquid",
            "name": "vinegar",
            "nameClean": "vinegar",
            "original": "¾ tsp. vinegar",
            "originalString": "¾ tsp. vinegar",
            "originalName": "vinegar",
            "amount": 0.75,
            "unit": "tsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 0.75,
                    "unitShort": "tsps",
                    "unitLong": "teaspoons"
                },
                "metric": {
                    "amount": 0.75,
                    "unitShort": "tsps",
                    "unitLong": "teaspoons"
                }
            }
        }
    ],
    "id": 629963,
    "title": "chilli chicken",
    "author": "swasthi",
    "readyInMinutes": 160,
    "servings": 2,
    "sourceUrl": "https://spoonacular.com/-1424528841847",
    "image": "https://spoonacular.com/recipeImages/629963-556x370.jpg",
    "imageType": "jpg",
    "summary": "Chilli chicken might be just the main course you are searching for. This recipe makes 2 servings with <b>279 calories</b>, <b>31g of protein</b>, and <b>6g of fat</b> each. For <b>${'$'}1.89 per serving</b>, this recipe <b>covers 24%</b> of your daily requirements of vitamins and minerals. It can be enjoyed any time, but it is especially good for <b>The Super Bowl</b>. This recipe is typical of American cuisine. Head to the store and pick up garlic, chicken breast, vinegar, and a few other things to make it today. To use up the soya sauce you could follow this main course with the <a href=\"https://spoonacular.com/recipes/panna-cotta-with-strawberry-vin-santo-sauce-154216\">Panna Cotta with Strawberry-Vin Santo Sauce</a> as a dessert. It is a good option if you're following a <b>gluten free and dairy free</b> diet. Similar recipes include <a href=\"https://spoonacular.com/recipes/andhra-chilli-chicken-how-to-make-andhra-green-chilli-chicken-dry-666042\">Andhra Chilli Chicken , how to make andhra green chilli chicken dry</a>, <a href=\"https://spoonacular.com/recipes/chilli-chicken-one-pot-211065\">Chilli chicken one-pot</a>, and <a href=\"https://spoonacular.com/recipes/chilli-idli-indian-chinese-chilli-idli-fry-easy-lunch-box-s-for-kids-564720\">Chilli Idli – Indian Chinese Chilli Idli Fry | Easy Lunch Box s for Kids</a>.",
    "cuisines": [
        "American"
    ],
    "dishTypes": [
        "lunch",
        "main course",
        "main dish",
        "dinner"
    ],
    "diets": [
        "gluten free",
        "dairy free"
    ],
    "occasions": [
        "super bowl"
    ],
    "winePairing": {
        "pairedWines": [
            "cava",
            "grenache",
            "shiraz"
        ],
        "pairingText": "Cava, Grenache, and Shiraz are my top picks for Chili. These juicy reds don't have too much tannin (important for spicy foods), but a sparkling wine like cava can tame the heat even better. The Campo Viejo Cava Brut rosé with a 4.4 out of 5 star rating seems like a good match. It costs about 13 dollars per bottle.",
        "productMatches": [
            {
                "id": 468840,
                "title": "Campo Viejo Cava Brut Rose",
                "description": "A bright sparkling pink color. Pleasant and powerful on the nose with a strong presence of ripe red berries. With has a rounded, pleasant feel in the mouth.",
                "price": "${'$'}12.99",
                "imageUrl": "https://spoonacular.com/productImages/468840-312x231.jpg",
                "averageRating": 0.8800000000000001,
                "ratingCount": 54.0,
                "score": 0.8738650306748468,
                "link": "https://click.linksynergy.com/deeplink?id=*QCiIS6t4gA&mid=2025&murl=https%3A%2F%2Fwww.wine.com%2Fproduct%2Fcampo-viejo-cava-brut-rose%2F132863"
            }
        ]
    },
    "instructions": "<p><a href=\"http://indianhealthyrecipes.com/chilli-chicken-dry-recipe-indo-chinese-style/\">Find Complete instructions on swasthi's recipes</a><strong><br></strong></p>",
    "analyzedInstructions": [
        {
            "name": "",
            "steps": [
                {
                    "number": 1,
                    "step": "Find Complete instructions on swasthi's recipes",
                    "ingredients": [],
                    "equipment": []
                }
            ]
        }
    ],
    "originalId": null,
    "spoonacularSourceUrl": "https://spoonacular.com/chilli-chicken-629963"
}"""