package com.artkachenko.core_api.network.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Entity(tableName = "exercise")
@Parcelize
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    val type: String,
    val duration: Long,
    val caloriesBurned: Double,
    val date: LocalDateTime = LocalDateTime.now()
) : Parcelable, HasId