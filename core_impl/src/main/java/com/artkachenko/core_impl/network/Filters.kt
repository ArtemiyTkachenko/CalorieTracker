package com.artkachenko.core_impl.network

object Filters {

    val vegetarianPreset = mapOf("diet" to listOf("vegetarian"))
    val italianPreset = mapOf("cuisine" to listOf("italian"))
    val indianPreset = mapOf("cuisine" to listOf("indian"))
    val quickPreset = mapOf("maxReadyTime" to listOf("20"))
    val cuisineFilters = mapOf(
        "cuisine" to listOf(
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

    val dietFilters = mapOf(
        "diet" to listOf(
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

    val intolerancesFilters = mapOf(
        "intolerances" to listOf(
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