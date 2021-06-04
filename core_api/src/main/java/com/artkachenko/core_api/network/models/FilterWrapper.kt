package com.artkachenko.core_api.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FilterWrapper(vararg val filters: Pair<String, List<String>>): Parcelable