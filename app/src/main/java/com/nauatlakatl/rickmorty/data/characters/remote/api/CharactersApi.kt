package com.nauatlakatl.rickmorty.data.characters.remote.api

import com.nauatlakatl.rickmorty.data.characters.remote.dto.CharacterDetailsResponse
import com.nauatlakatl.rickmorty.data.characters.remote.dto.CharactersResponse
import com.nauatlakatl.rickmorty.data.common.utils.ResponseListWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    suspend fun getAllCharacters(): Response<ResponseListWrapper<CharactersResponse>>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<CharacterDetailsResponse>

    @GET("character")
    suspend fun filterCharacters(
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("gender") gender: String,
    ): Response<ResponseListWrapper<CharactersResponse>>
}