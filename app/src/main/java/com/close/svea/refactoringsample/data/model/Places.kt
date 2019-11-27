package com.close.svea.refactoringsample.data.model

import com.google.gson.annotations.SerializedName

data class Places(
    @SerializedName("place") val place: MutableList<Place>,
    @SerializedName("total") val total: Int
)