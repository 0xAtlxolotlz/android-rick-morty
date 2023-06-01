package com.nauatlakatl.rickmorty.data.characters.remote.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nauatlakatl.rickmorty.data.characters.remote.api.CharactersApi
import com.nauatlakatl.rickmorty.data.characters.remote.dto.CharactersResponse
import com.nauatlakatl.rickmorty.data.common.utils.ResponseListWrapper
import com.nauatlakatl.rickmorty.domain.characters.CharactersRepository
import com.nauatlakatl.rickmorty.domain.characters.entity.CharactersEntity
import com.nauatlakatl.rickmorty.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(private val charactersApi: CharactersApi) :
    CharactersRepository {

    override suspend fun getAllCharacters(): Flow<BaseResult<List<CharactersEntity>, ResponseListWrapper<CharactersResponse>>> {
        return flow {
            val response = charactersApi.getAllCharacters()
            if (response.isSuccessful) {
                val body = response.body()!!
                val characters = mutableListOf<CharactersEntity>()
                body.data?.forEach { charactersResponse ->
                    characters.add(
                        CharactersEntity(
                            charactersResponse.id,
                            charactersResponse.name,
                            charactersResponse.species,
                            charactersResponse.image
                        )
                    )
                }
                emit(BaseResult.Success(characters))
            } else {
                val type = object : TypeToken<ResponseListWrapper<CharactersResponse>>() {}.type
                val error = Gson().fromJson<ResponseListWrapper<CharactersResponse>>(
                    response.errorBody()!!.charStream(), type
                )!!
                error.code = response.code()
                emit(BaseResult.Error(error))
            }
        }
    }

}