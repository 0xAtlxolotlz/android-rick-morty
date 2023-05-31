package com.nauatlakatl.rickmorty.data.common.dto

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("count") var count: Int? = null,
    @SerializedName("pages") var pages: Int? = null,
    @SerializedName("next") var next: String? = null
)
