package com.artkachenko.core_api.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FilterWrapper(val filters: Map<String, List<String>>): Parcelable