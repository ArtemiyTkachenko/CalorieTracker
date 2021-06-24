package com.artkachenko.core_api.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ConvertedAmount(
    @SerialName("sourceAmount")
    val sourceAmount: Double ?= null,
    @SerialName("sourceUnit")
    val sourceUnit: String ?= null,
    @SerialName("targetAmount")
    val targetAmount: Double ?= null,
    @SerialName("targetUnit")
    val targetUnit: String ?= null,
    @SerialName("answer")
    val answer: String ?= null,
    @SerialName("type")
    val type: String ?= null,
) : Parcelable