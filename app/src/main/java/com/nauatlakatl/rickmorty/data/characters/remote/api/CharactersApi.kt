package com.nauatlakatl.rickmorty.data.characters.remote.api

import com.nauatlakatl.rickmorty.data.characters.remote.dto.CharactersResponse
import com.nauatlakatl.rickmorty.data.common.utils.ResponseListWrapper
import retrofit2.Response
import retrofit2.http.GET

interface CharactersApi {

    @GET("character")
    suspend fun getAllCharacters(): Response<ResponseListWrapper<CharactersResponse>>
}