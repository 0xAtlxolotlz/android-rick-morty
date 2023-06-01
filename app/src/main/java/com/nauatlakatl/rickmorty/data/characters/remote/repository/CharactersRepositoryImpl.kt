package com.nauatlakatl.rickmorty.data.characters.remote.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nauatlakatl.rickmorty.data.characters.remote.api.CharactersApi
import com.nauatlakatl.rickmorty.data.characters.remote.dto.CharacterDetailsResponse
import com.nauatlakatl.rickmorty.data.characters.remote.dto.CharactersResponse
import com.nauatlakatl.rickmorty.data.common.utils.ResponseListWrapper
import com.nauatlakatl.rickmorty.data.common.utils.ResponseWrapper
import com.nauatlakatl.rickmorty.domain.characters.CharactersRepository
import com.nauatlakatl.rickmorty.domain.characters.entity.CharacterDetailsEntity
import com.nauatlakatl.rickmorty.domain.characters.entity.CharactersEntity
import com.nauatlakatl.rickmorty.domain.characters.entity.LocationEntity
import com.nauatlakatl.rickmorty.domain.characters.entity.OriginEntity
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

    override suspend fun getCharacterDetailsById(id: Int): Flow<BaseResult<CharacterDetailsEntity, ResponseWrapper<CharacterDetailsResponse>>> {
        return flow {
            val response = charactersApi.getCharacterById(id)
            if (response.isSuccessful) {
                val body = response.body()!!
                val characterDetailsEntity = CharacterDetailsEntity(
                    name = body.name,
                    status = body.status,
                    species = body.species,
                    gender = body.gender,
                    origin = OriginEntity(body.originResponse.name),
                    location = LocationEntity(body.locationResponse.name),
                    image = body.image
                )
                emit(BaseResult.Success(characterDetailsEntity))
            } else {
                val type = object : TypeToken<ResponseWrapper<CharacterDetailsResponse>>() {}.type
                val error = Gson().fromJson<ResponseWrapper<CharacterDetailsResponse>>(
                    response.errorBody()!!.charStream(), type
                )!!
                error.code = response.code()
                emit(BaseResult.Error(error))
            }
        }
    }

    override suspend fun filterCharacters(
        name: String,
        status: String,
        gender: String
    ): Flow<BaseResult<List<CharactersEntity>, ResponseListWrapper<CharactersResponse>>> {
        return flow {
            val response = charactersApi.filterCharacters(name, status, gender)
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