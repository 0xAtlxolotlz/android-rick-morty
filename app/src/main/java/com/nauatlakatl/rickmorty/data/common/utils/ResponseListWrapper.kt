package com.nauatlakatl.rickmorty.data.common.utils

import com.google.gson.annotations.SerializedName
import com.nauatlakatl.rickmorty.data.common.dto.InfoResponse

data class ResponseListWrapper<T>(
    var code: Int,
    @SerializedName("info") var info: InfoResponse,
    @SerializedName("results") var data: List<T>? = null
)
