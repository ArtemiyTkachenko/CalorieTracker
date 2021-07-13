package com.artkachenko.core_impl.network

import com.artkachenko.core_api.network.models.FilterItemWrapper

object Filters {
    val vegetarianPreset = mutableMapOf("diet" to mutableListOf(FilterItemWrapper("vegetarian")))
    val italianPreset = mutableMapOf("cuisine" to mutableListOf(FilterItemWrapper("italian")))
    val indianPreset = mutableMapOf("cuisine" to mutableListOf(FilterItemWrapper("indian")))
    val quickPreset = mutableMapOf("maxReadyTime" to mutableListOf(FilterItemWrapper("20")))

    val cuisineFilters = generateCuisines()

    val dietFilters = generateDiets()

    val intolerancesFilters = generateIntolerances()

    val fiveItemPreset = "number" to listOf("5")

    fun reset() {
        cuisineFilters.clear()
        cuisineFilters.putAll(generateCuisines())
        dietFilters.clear()
        dietFilters.putAll(generateDiets())
        intolerancesFilters.clear()
        intolerancesFilters.putAll(generateIntolerances())
    }

    private fun generateDiets(): MutableMap<String, MutableList<FilterItemWrapper>> {
        return mutableMapOf(
            "diet" to mutableListOf(
                FilterItemWrapper("gluten free"),
                FilterItemWrapper("ketogenic"),
                FilterItemWrapper("vegetarian"),
                FilterItemWrapper("lacto-vegetarian"),
                FilterItemWrapper("ovo-vegetarian"),
                FilterItemWrapper("vegan"),
                FilterItemWrapper("pescetarian"),
                FilterItemWrapper("paleo"),
                FilterItemWrapper("primal")
            )
        )
    }

    private fun generateCuisines(): MutableMap<String, MutableList<FilterItemWrapper>> {
        return mutableMapOf(
            "cuisine" to mutableListOf(
                FilterItemWrapper("African"),
                FilterItemWrapper("American"),
                FilterItemWrapper("British"),
                FilterItemWrapper("Cajun"),
                FilterItemWrapper("Caribbean"),
                FilterItemWrapper("Chinese"),
                FilterItemWrapper("Eastern European"),
                FilterItemWrapper("European"),
                FilterItemWrapper("French"),
                FilterItemWrapper("German"),
                FilterItemWrapper("Greek"),
                FilterItemWrapper("Indian"),
                FilterItemWrapper("Irish"),
                FilterItemWrapper("Italian"),
                FilterItemWrapper("Japanese"),
                FilterItemWrapper("Jewish"),
                FilterItemWrapper("Korean"),
                FilterItemWrapper("Latin American"),
                FilterItemWrapper("Mediterranean"),
                FilterItemWrapper("Mexican"),
                FilterItemWrapper("Middle Eastern"),
                FilterItemWrapper("Nordic"),
                FilterItemWrapper("Southern"),
                FilterItemWrapper("Spanish"),
                FilterItemWrapper("Thai"),
                FilterItemWrapper("Vietnamese")
            )
        )
    }

    private fun generateIntolerances(): MutableMap<String, MutableList<FilterItemWrapper>> {
        return mutableMapOf(
            "intolerances" to mutableListOf(
                FilterItemWrapper("dairy"),
                FilterItemWrapper("egg"),
                FilterItemWrapper("gluten"),
                FilterItemWrapper("grain"),
                FilterItemWrapper("peanut"),
                FilterItemWrapper("seafood"),
                FilterItemWrapper("sesame"),
                FilterItemWrapper("shellfish"),
                FilterItemWrapper("soy"),
                FilterItemWrapper("sulfite"),
                FilterItemWrapper("tree nut"),
                FilterItemWrapper("wheat")
            )
        )
    }
}