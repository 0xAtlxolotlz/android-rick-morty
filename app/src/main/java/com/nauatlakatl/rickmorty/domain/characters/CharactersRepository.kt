package com.nauatlakatl.rickmorty.domain.characters

import com.nauatlakatl.rickmorty.data.characters.remote.dto.CharacterDetailsResponse
import com.nauatlakatl.rickmorty.data.characters.remote.dto.CharactersResponse
import com.nauatlakatl.rickmorty.data.common.utils.ResponseListWrapper
import com.nauatlakatl.rickmorty.data.common.utils.ResponseWrapper
import com.nauatlakatl.rickmorty.domain.characters.entity.CharacterDetailsEntity
import com.nauatlakatl.rickmorty.domain.characters.entity.CharactersEntity
import com.nauatlakatl.rickmorty.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    suspend fun getAllCharacters(): Flow<BaseResult<List<CharactersEntity>, ResponseListWrapper<CharactersResponse>>>

    suspend fun getCharacterDetailsById(id: Int): Flow<BaseResult<CharacterDetailsEntity, ResponseWrapper<CharacterDetailsResponse>>>
}