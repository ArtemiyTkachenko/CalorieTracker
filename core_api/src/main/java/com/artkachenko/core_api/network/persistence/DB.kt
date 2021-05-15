package com.artkachenko.core_api.network.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.artkachenko.core_api.network.models.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Database(
    entities = [RecipeDetailModel::class, Ingredient::class, ManualDishDetail::class, Exercise::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DB.TypeConverters::class)
abstract class DB : RoomDatabase() {

    abstract fun ingredientsDao(): IngredientsDao
    abstract fun dishesDao() : DishesDao
    abstract fun exerciseDao() : ExerciseDao

    class TypeConverters {

        @TypeConverter
        fun listToString(list: List<Ingredient>) : String {
            return Json {  }.encodeToString(Json.serializersModule.serializer(), list)
        }

        @TypeConverter
        fun stringToList(listString: String): List<Ingredient>? {
            return Json {  }.decodeFromString(Json.serializersModule.serializer(), listString)
        }

        @TypeConverter
        fun stringListToList(stringList: List<String>) : String {
            return Json {  }.encodeToString(Json.serializersModule.serializer(), stringList)
        }

        @TypeConverter
        fun stringToStringList(stringList: String) : List<String> {
            return Json {  }.decodeFromString(Json.serializersModule.serializer(), stringList)
        }

        @TypeConverter
        fun nutritionToString(nutrition: Nutrition) = nutrition.toString()

        @TypeConverter
        fun stringToNutrition(nutritionString: String): Nutrition? {
            return Json {  }.decodeFromString(Json.serializersModule.serializer(), nutritionString)
        }

        @TypeConverter
        fun dateToLong(date: LocalDateTime) : Long {
            return date.toInstant(ZoneOffset.UTC).toEpochMilli()
        }

        @TypeConverter
        fun longToDate(millis: Long) : LocalDateTime {
            return  LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        }
    }
}