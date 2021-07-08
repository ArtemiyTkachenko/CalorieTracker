package com.artkachenko.core_impl

import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.network.models.Nutrition
import java.time.LocalDateTime

const val mockResults = """ [
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
    ]"""

const val mockDetail = """{
    "vegetarian": false,
    "vegan": false,
    "glutenFree": false,
    "dairyFree": false,
    "veryHealthy": false,
    "cheap": false,
    "veryPopular": false,
    "sustainable": false,
    "weightWatcherSmartPoints": 17,
    "gaps": "no",
    "lowFodmap": false,
    "aggregateLikes": 209,
    "spoonacularScore": 82.0,
    "healthScore": 19.0,
    "creditsText": "Full Belly Sisters",
    "license": "CC BY-SA 3.0",
    "sourceName": "Full Belly Sisters",
    "pricePerServing": 163.15,
    "extendedIngredients": [
        {
            "id": 1001,
            "aisle": "Milk, Eggs, Other Dairy",
            "image": "butter-sliced.jpg",
            "consistency": "solid",
            "name": "butter",
            "nameClean": "butter",
            "original": "1 tbsp butter",
            "originalString": "1 tbsp butter",
            "originalName": "butter",
            "amount": 1.0,
            "unit": "tbsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 1.0,
                    "unitShort": "Tbsp",
                    "unitLong": "Tbsp"
                },
                "metric": {
                    "amount": 1.0,
                    "unitShort": "Tbsp",
                    "unitLong": "Tbsp"
                }
            }
        },
        {
            "id": 10011135,
            "aisle": "Produce",
            "image": "cauliflower.jpg",
            "consistency": "solid",
            "name": "cauliflower florets",
            "nameClean": "cauliflower florets",
            "original": "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            "originalString": "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            "originalName": "about frozen cauliflower florets, thawed, cut into bite-sized pieces",
            "amount": 2.0,
            "unit": "cups",
            "meta": [
                "frozen",
                "thawed",
                "cut into bite-sized pieces"
            ],
            "metaInformation": [
                "frozen",
                "thawed",
                "cut into bite-sized pieces"
            ],
            "measures": {
                "us": {
                    "amount": 2.0,
                    "unitShort": "cups",
                    "unitLong": "cups"
                },
                "metric": {
                    "amount": 473.176,
                    "unitShort": "ml",
                    "unitLong": "milliliters"
                }
            }
        },
        {
            "id": 1041009,
            "aisle": "Cheese",
            "image": "cheddar-cheese.png",
            "consistency": "solid",
            "name": "cheese",
            "nameClean": "cheese",
            "original": "2 tbsp grated cheese (I used romano)",
            "originalString": "2 tbsp grated cheese (I used romano)",
            "originalName": "grated cheese (I used romano)",
            "amount": 2.0,
            "unit": "tbsp",
            "meta": [
                "grated",
                "(I used romano)"
            ],
            "metaInformation": [
                "grated",
                "(I used romano)"
            ],
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
            "id": 1034053,
            "aisle": "Oil, Vinegar, Salad Dressing",
            "image": "olive-oil.jpg",
            "consistency": "liquid",
            "name": "extra virgin olive oil",
            "nameClean": "extra virgin olive oil",
            "original": "1-2 tbsp extra virgin olive oil",
            "originalString": "1-2 tbsp extra virgin olive oil",
            "originalName": "extra virgin olive oil",
            "amount": 1.0,
            "unit": "tbsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 1.0,
                    "unitShort": "Tbsp",
                    "unitLong": "Tbsp"
                },
                "metric": {
                    "amount": 1.0,
                    "unitShort": "Tbsp",
                    "unitLong": "Tbsp"
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
            "original": "5-6 cloves garlic",
            "originalString": "5-6 cloves garlic",
            "originalName": "garlic",
            "amount": 5.0,
            "unit": "cloves",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 5.0,
                    "unitShort": "cloves",
                    "unitLong": "cloves"
                },
                "metric": {
                    "amount": 5.0,
                    "unitShort": "cloves",
                    "unitLong": "cloves"
                }
            }
        },
        {
            "id": 20420,
            "aisle": "Pasta and Rice",
            "image": "fusilli.jpg",
            "consistency": "solid",
            "name": "pasta",
            "nameClean": "pasta",
            "original": "6-8 ounces pasta (I used linguine)",
            "originalString": "6-8 ounces pasta (I used linguine)",
            "originalName": "pasta (I used linguine)",
            "amount": 6.0,
            "unit": "ounces",
            "meta": [
                "(I used linguine)"
            ],
            "metaInformation": [
                "(I used linguine)"
            ],
            "measures": {
                "us": {
                    "amount": 6.0,
                    "unitShort": "oz",
                    "unitLong": "ounces"
                },
                "metric": {
                    "amount": 170.097,
                    "unitShort": "g",
                    "unitLong": "grams"
                }
            }
        },
        {
            "id": 1032009,
            "aisle": "Spices and Seasonings",
            "image": "red-pepper-flakes.jpg",
            "consistency": "solid",
            "name": "red pepper flakes",
            "nameClean": "red pepper flakes",
            "original": "couple of pinches red pepper flakes, optional",
            "originalString": "couple of pinches red pepper flakes, optional",
            "originalName": "couple of red pepper flakes, optional",
            "amount": 2.0,
            "unit": "pinches",
            "meta": [
                "red"
            ],
            "metaInformation": [
                "red"
            ],
            "measures": {
                "us": {
                    "amount": 2.0,
                    "unitShort": "pinches",
                    "unitLong": "pinches"
                },
                "metric": {
                    "amount": 2.0,
                    "unitShort": "pinches",
                    "unitLong": "pinches"
                }
            }
        },
        {
            "id": 1102047,
            "aisle": "Spices and Seasonings",
            "image": "salt-and-pepper.jpg",
            "consistency": "solid",
            "name": "salt and pepper",
            "nameClean": "salt and pepper",
            "original": "salt and pepper, to taste",
            "originalString": "salt and pepper, to taste",
            "originalName": "salt and pepper, to taste",
            "amount": 2.0,
            "unit": "servings",
            "meta": [
                "to taste"
            ],
            "metaInformation": [
                "to taste"
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
            "id": 11291,
            "aisle": "Produce",
            "image": "spring-onions.jpg",
            "consistency": "solid",
            "name": "scallions",
            "nameClean": "spring onions",
            "original": "3 scallions, chopped, white and green parts separated",
            "originalString": "3 scallions, chopped, white and green parts separated",
            "originalName": "scallions, chopped, white and green parts separated",
            "amount": 3.0,
            "unit": "",
            "meta": [
                "white",
                "green",
                "separated",
                "chopped"
            ],
            "metaInformation": [
                "white",
                "green",
                "separated",
                "chopped"
            ],
            "measures": {
                "us": {
                    "amount": 3.0,
                    "unitShort": "",
                    "unitLong": ""
                },
                "metric": {
                    "amount": 3.0,
                    "unitShort": "",
                    "unitLong": ""
                }
            }
        },
        {
            "id": 14106,
            "aisle": "Alcoholic Beverages",
            "image": "white-wine.jpg",
            "consistency": "liquid",
            "name": "white wine",
            "nameClean": "white wine",
            "original": "2-3 tbsp white wine",
            "originalString": "2-3 tbsp white wine",
            "originalName": "white wine",
            "amount": 2.0,
            "unit": "tbsp",
            "meta": [
                "white"
            ],
            "metaInformation": [
                "white"
            ],
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
            "id": 99025,
            "aisle": "Pasta and Rice",
            "image": "breadcrumbs.jpg",
            "consistency": "solid",
            "name": "whole wheat bread crumbs",
            "nameClean": "whole wheat breadcrumbs",
            "original": "1/4 cup whole wheat bread crumbs (I used panko)",
            "originalString": "1/4 cup whole wheat bread crumbs (I used panko)",
            "originalName": "whole wheat bread crumbs (I used panko)",
            "amount": 0.25,
            "unit": "cup",
            "meta": [
                "whole wheat",
                "(I used panko)"
            ],
            "metaInformation": [
                "whole wheat",
                "(I used panko)"
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
        }
    ],
    "id": 716429,
    "title": "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
    "readyInMinutes": 45,
    "servings": 2,
    "sourceUrl": "http://fullbellysisters.blogspot.com/2012/06/pasta-with-garlic-scallions-cauliflower.html",
    "image": "https://spoonacular.com/recipeImages/716429-556x370.jpg",
    "imageType": "jpg",
    "summary": "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs might be just the main course you are searching for. This recipe makes 2 servings with <b>636 calories</b>, <b>21g of protein</b>, and <b>20g of fat</b> each. For <b>${'$'}1.83 per serving</b>, this recipe <b>covers 24%</b> of your daily requirements of vitamins and minerals. From preparation to the plate, this recipe takes about <b>45 minutes</b>. This recipe is liked by 209 foodies and cooks. If you have pasta, salt and pepper, cheese, and a few other ingredients on hand, you can make it. To use up the extra virgin olive oil you could follow this main course with the <a href=\"https://spoonacular.com/recipes/peach-crisp-healthy-crisp-for-breakfast-487698\">Peach Crisp: Healthy Crisp for Breakfast</a> as a dessert. All things considered, we decided this recipe <b>deserves a spoonacular score of 86%</b>. This score is tremendous. Try <a href=\"https://spoonacular.com/recipes/cauliflower-gratin-with-garlic-breadcrumbs-318375\">Cauliflower Gratin with Garlic Breadcrumbs</a>, <a href=\"https://spoonacular.com/recipes/pasta-with-cauliflower-sausage-breadcrumbs-30437\">Pasta With Cauliflower, Sausage, & Breadcrumbs</a>, and <a href=\"https://spoonacular.com/recipes/pasta-with-roasted-cauliflower-parsley-and-breadcrumbs-30738\">Pasta With Roasted Cauliflower, Parsley, And Breadcrumbs</a> for similar recipes.",
    "cuisines": [],
    "dishTypes": [
        "lunch",
        "main course",
        "main dish",
        "dinner"
    ],
    "diets": [],
    "occasions": [],
    "winePairing": {
        "pairedWines": [],
        "pairingText": "No one wine will suit every pasta dish. Pasta in a tomato-based sauce will usually work well with a medium-bodied red, such as a montepulciano or chianti. Pasta with seafood or pesto will fare better with a light-bodied white, such as a pinot grigio. Cheese-heavy pasta can pair well with red or white - you might try a sangiovese wine for hard cheeses and a chardonnay for soft cheeses. We may be able to make a better recommendation if you ask again with a specific pasta dish.",
        "productMatches": []
    },
    "instructions": "",
    "analyzedInstructions": [],
    "originalId": null,
    "spoonacularSourceUrl": "https://spoonacular.com/pasta-with-garlic-scallions-cauliflower-breadcrumbs-716429"
}"""

const val mockIngredients = """[
        {
            "id": 1001,
            "aisle": "Milk, Eggs, Other Dairy",
            "image": "butter-sliced.jpg",
            "consistency": "solid",
            "name": "butter",
            "nameClean": "butter",
            "original": "1 tbsp butter",
            "originalString": "1 tbsp butter",
            "originalName": "butter",
            "amount": 1.0,
            "unit": "tbsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 1.0,
                    "unitShort": "Tbsp",
                    "unitLong": "Tbsp"
                },
                "metric": {
                    "amount": 1.0,
                    "unitShort": "Tbsp",
                    "unitLong": "Tbsp"
                }
            }
        },
        {
            "id": 10011135,
            "aisle": "Produce",
            "image": "cauliflower.jpg",
            "consistency": "solid",
            "name": "cauliflower florets",
            "nameClean": "cauliflower florets",
            "original": "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            "originalString": "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            "originalName": "about frozen cauliflower florets, thawed, cut into bite-sized pieces",
            "amount": 2.0,
            "unit": "cups",
            "meta": [
                "frozen",
                "thawed",
                "cut into bite-sized pieces"
            ],
            "metaInformation": [
                "frozen",
                "thawed",
                "cut into bite-sized pieces"
            ],
            "measures": {
                "us": {
                    "amount": 2.0,
                    "unitShort": "cups",
                    "unitLong": "cups"
                },
                "metric": {
                    "amount": 473.176,
                    "unitShort": "ml",
                    "unitLong": "milliliters"
                }
            }
        },
        {
            "id": 1041009,
            "aisle": "Cheese",
            "image": "cheddar-cheese.png",
            "consistency": "solid",
            "name": "cheese",
            "nameClean": "cheese",
            "original": "2 tbsp grated cheese (I used romano)",
            "originalString": "2 tbsp grated cheese (I used romano)",
            "originalName": "grated cheese (I used romano)",
            "amount": 2.0,
            "unit": "tbsp",
            "meta": [
                "grated",
                "(I used romano)"
            ],
            "metaInformation": [
                "grated",
                "(I used romano)"
            ],
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
            "id": 1034053,
            "aisle": "Oil, Vinegar, Salad Dressing",
            "image": "olive-oil.jpg",
            "consistency": "liquid",
            "name": "extra virgin olive oil",
            "nameClean": "extra virgin olive oil",
            "original": "1-2 tbsp extra virgin olive oil",
            "originalString": "1-2 tbsp extra virgin olive oil",
            "originalName": "extra virgin olive oil",
            "amount": 1.0,
            "unit": "tbsp",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 1.0,
                    "unitShort": "Tbsp",
                    "unitLong": "Tbsp"
                },
                "metric": {
                    "amount": 1.0,
                    "unitShort": "Tbsp",
                    "unitLong": "Tbsp"
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
            "original": "5-6 cloves garlic",
            "originalString": "5-6 cloves garlic",
            "originalName": "garlic",
            "amount": 5.0,
            "unit": "cloves",
            "meta": [],
            "metaInformation": [],
            "measures": {
                "us": {
                    "amount": 5.0,
                    "unitShort": "cloves",
                    "unitLong": "cloves"
                },
                "metric": {
                    "amount": 5.0,
                    "unitShort": "cloves",
                    "unitLong": "cloves"
                }
            }
        },
        {
            "id": 20420,
            "aisle": "Pasta and Rice",
            "image": "fusilli.jpg",
            "consistency": "solid",
            "name": "pasta",
            "nameClean": "pasta",
            "original": "6-8 ounces pasta (I used linguine)",
            "originalString": "6-8 ounces pasta (I used linguine)",
            "originalName": "pasta (I used linguine)",
            "amount": 6.0,
            "unit": "ounces",
            "meta": [
                "(I used linguine)"
            ],
            "metaInformation": [
                "(I used linguine)"
            ],
            "measures": {
                "us": {
                    "amount": 6.0,
                    "unitShort": "oz",
                    "unitLong": "ounces"
                },
                "metric": {
                    "amount": 170.097,
                    "unitShort": "g",
                    "unitLong": "grams"
                }
            }
        },
        {
            "id": 1032009,
            "aisle": "Spices and Seasonings",
            "image": "red-pepper-flakes.jpg",
            "consistency": "solid",
            "name": "red pepper flakes",
            "nameClean": "red pepper flakes",
            "original": "couple of pinches red pepper flakes, optional",
            "originalString": "couple of pinches red pepper flakes, optional",
            "originalName": "couple of red pepper flakes, optional",
            "amount": 2.0,
            "unit": "pinches",
            "meta": [
                "red"
            ],
            "metaInformation": [
                "red"
            ],
            "measures": {
                "us": {
                    "amount": 2.0,
                    "unitShort": "pinches",
                    "unitLong": "pinches"
                },
                "metric": {
                    "amount": 2.0,
                    "unitShort": "pinches",
                    "unitLong": "pinches"
                }
            }
        },
        {
            "id": 1102047,
            "aisle": "Spices and Seasonings",
            "image": "salt-and-pepper.jpg",
            "consistency": "solid",
            "name": "salt and pepper",
            "nameClean": "salt and pepper",
            "original": "salt and pepper, to taste",
            "originalString": "salt and pepper, to taste",
            "originalName": "salt and pepper, to taste",
            "amount": 2.0,
            "unit": "servings",
            "meta": [
                "to taste"
            ],
            "metaInformation": [
                "to taste"
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
            "id": 11291,
            "aisle": "Produce",
            "image": "spring-onions.jpg",
            "consistency": "solid",
            "name": "scallions",
            "nameClean": "spring onions",
            "original": "3 scallions, chopped, white and green parts separated",
            "originalString": "3 scallions, chopped, white and green parts separated",
            "originalName": "scallions, chopped, white and green parts separated",
            "amount": 3.0,
            "unit": "",
            "meta": [
                "white",
                "green",
                "separated",
                "chopped"
            ],
            "metaInformation": [
                "white",
                "green",
                "separated",
                "chopped"
            ],
            "measures": {
                "us": {
                    "amount": 3.0,
                    "unitShort": "",
                    "unitLong": ""
                },
                "metric": {
                    "amount": 3.0,
                    "unitShort": "",
                    "unitLong": ""
                }
            }
        },
        {
            "id": 14106,
            "aisle": "Alcoholic Beverages",
            "image": "white-wine.jpg",
            "consistency": "liquid",
            "name": "white wine",
            "nameClean": "white wine",
            "original": "2-3 tbsp white wine",
            "originalString": "2-3 tbsp white wine",
            "originalName": "white wine",
            "amount": 2.0,
            "unit": "tbsp",
            "meta": [
                "white"
            ],
            "metaInformation": [
                "white"
            ],
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
            "id": 99025,
            "aisle": "Pasta and Rice",
            "image": "breadcrumbs.jpg",
            "consistency": "solid",
            "name": "whole wheat bread crumbs",
            "nameClean": "whole wheat breadcrumbs",
            "original": "1/4 cup whole wheat bread crumbs (I used panko)",
            "originalString": "1/4 cup whole wheat bread crumbs (I used panko)",
            "originalName": "whole wheat bread crumbs (I used panko)",
            "amount": 0.25,
            "unit": "cup",
            "meta": [
                "whole wheat",
                "(I used panko)"
            ],
            "metaInformation": [
                "whole wheat",
                "(I used panko)"
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
        }
    ]"""


val manualDishMock = ManualDishDetail(
    id = 1,
    nutrition = Nutrition(),
    extendedIngredients = listOf(),
    date = LocalDateTime.now()
)