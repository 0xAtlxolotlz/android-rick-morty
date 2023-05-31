package com.nauatlakatl.rickmorty.data.common.utils

import com.google.gson.annotations.SerializedName
import com.nauatlakatl.rickmorty.data.common.dto.InfoResponse

data class ResponseWrapper<T>(
    @SerializedName("info") var info: InfoResponse,
    @SerializedName("results") var data: T? = null
)
