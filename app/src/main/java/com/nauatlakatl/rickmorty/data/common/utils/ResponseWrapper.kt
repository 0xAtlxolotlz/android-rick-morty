package com.nauatlakatl.rickmorty.data.common.utils

data class ResponseWrapper<T>(
    var code: Int,
    var data: T? = null
)
