package com.artkachenko.core_impl.network

object Filters {

    val vegetarianPreset = mutableMapOf("diet" to mutableListOf("vegetarian"))
    val italianPreset = mutableMapOf("cuisine" to mutableListOf("italian"))
    val indianPreset = mutableMapOf("cuisine" to mutableListOf("indian"))
    val quickPreset = mutableMapOf("maxReadyTime" to mutableListOf("20"))
    val cuisineFilters = mutableMapOf(
        "cuisine" to mutableListOf(
            "African",
            "American",
            "British",
            "Cajun",
            "Caribbean",
            "Chinese",
            "Eastern European",
            "European",
            "French",
            "German",
            "Greek",
            "Indian",
            "Irish",
            "Italian",
            "Japanese",
            "Jewish",
            "Korean",
            "Latin American",
            "Mediterranean",
            "Mexican",
            "Middle Eastern",
            "Nordic",
            "Southern",
            "Spanish",
            "Thai",
            "Vietnamese"
        )
    )

    val dietFilters = mutableMapOf(
        "diet" to mutableListOf(
            "gluten free",
            "ketogenic",
            "vegetarian",
            "lacto-vegetarian",
            "ovo-vegetarian",
            "vegan",
            "pescetarian",
            "paleo",
            "primal"
        )
    )

    val intolerancesFilters = mutableMapOf(
        "intolerances" to mutableListOf(
            "dairy",
            "egg",
            "gluten",
            "grain",
            "peanut",
            "seafood",
            "sesame",
            "shellfish",
            "soy",
            "sulfite",
            "tree nut",
            "wheat"
        )
    )

    val fiveItemPreset = "number" to listOf("5")
}