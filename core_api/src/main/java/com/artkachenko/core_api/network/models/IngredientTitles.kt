package com.artkachenko.core_api.network.models

enum class IngredientTitles(val title: String) {
    CALORIES("Calories"),
    FAT("Fat"),
    FIBER("Fiber"),
    FOLATE("Folate"),
    SATURATED_FAT("Saturated Fat"),
    CARBOHYDRATES("Carbohydrates"),
    NET_CARBOHYDRATES("Net Carbohydrates"),
    SUGAR("Sugar"),
    CHOLESTEROL("Cholesterol"),
    IRON("Iron"),
    COPPER("Copper"),
    MAGNESIUM("Magnesium"),
    SODIUM("Sodium"),
    PROTEIN("Protein"),
    VITAMIN_B1("Vitamin B1"),
    VITAMIN_B2("Vitamin B2"),
    VITAMIN_B3("Vitamin B3"),
    VITAMIN_B5("Vitamin B5"),
    VITAMIN_B6("Vitamin B6"),
    VITAMIN_B12("Vitamin B12"),
    VITAMIN_C("Vitamin C"),
    VITAMIN_E("Vitamin E"),
    VITAMIN_K("Vitamin K"),
    SELENIUM("Selenium"),
    ZINC("Zinc"),
    PHOSPHORUS("Phosphorus"),
    CALCIUM("Calcium"),
    POTASSIUM("Potassium");

    companion object {
        fun mapFromString(value: String?) = IngredientTitles.values().firstOrNull { it.title.equals(value, true) }
    }
}