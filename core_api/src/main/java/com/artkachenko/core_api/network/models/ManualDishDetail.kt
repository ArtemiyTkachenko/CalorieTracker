package com.artkachenko.core_api.network.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artkachenko.core_api.interfaces.HasId
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor

import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@ExperimentalSerializationApi
@Entity(tableName = "manual_dishes")
@Serializable(with = LocalDateSerializer::class)
@Parcelize
data class ManualDishDetail(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    val nutrition: Nutrition? = null,
    val extendedIngredients: List<Ingredient>? = null,
    val date: LocalDateTime? = null
) : Parcelable, HasId

@ExperimentalSerializationApi
@Serializer(forClass = LocalDateTime::class)
object LocalDateSerializer : KSerializer<LocalDateTime> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, obj: LocalDateTime) {
        encoder.encodeString(obj.format(formatter))
    }
    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString(), formatter)
    }
}