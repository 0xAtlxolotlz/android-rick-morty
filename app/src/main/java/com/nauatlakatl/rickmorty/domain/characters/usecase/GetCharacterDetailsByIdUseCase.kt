package com.nauatlakatl.rickmorty.domain.characters.usecase

import com.nauatlakatl.rickmorty.data.characters.remote.dto.CharacterDetailsResponse
import com.nauatlakatl.rickmorty.data.common.utils.ResponseWrapper
import com.nauatlakatl.rickmorty.domain.characters.CharactersRepository
import com.nauatlakatl.rickmorty.domain.characters.entity.CharacterDetailsEntity
import com.nauatlakatl.rickmorty.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterDetailsByIdUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {

    suspend fun execute(id: Int): Flow<BaseResult<CharacterDetailsEntity, ResponseWrapper<CharacterDetailsResponse>>> {
        return charactersRepository.getCharacterDetailsById(id)
    }
}