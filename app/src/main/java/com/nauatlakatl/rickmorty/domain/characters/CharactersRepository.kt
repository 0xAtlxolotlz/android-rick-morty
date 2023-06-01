package com.nauatlakatl.rickmorty.domain.characters

import com.nauatlakatl.rickmorty.data.characters.remote.dto.CharactersResponse
import com.nauatlakatl.rickmorty.data.common.utils.ResponseListWrapper
import com.nauatlakatl.rickmorty.domain.characters.entity.CharactersEntity
import com.nauatlakatl.rickmorty.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getAllCharacters(): Flow<BaseResult<List<CharactersEntity>, ResponseListWrapper<CharactersResponse>>>
}