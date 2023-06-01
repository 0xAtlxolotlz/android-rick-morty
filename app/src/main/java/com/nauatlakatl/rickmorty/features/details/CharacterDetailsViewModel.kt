package com.nauatlakatl.rickmorty.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nauatlakatl.rickmorty.domain.characters.entity.CharacterDetailsEntity
import com.nauatlakatl.rickmorty.domain.characters.usecase.GetCharacterDetailsByIdUseCase
import com.nauatlakatl.rickmorty.domain.common.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetailsByIdUseCase: GetCharacterDetailsByIdUseCase
) : ViewModel() {

    private val _characterDetailsState =
        MutableStateFlow<CharacterDetailsState>(CharacterDetailsState.Init)
    val characterDetailsState: StateFlow<CharacterDetailsState> get() = _characterDetailsState

    private val _details = MutableStateFlow<CharacterDetailsEntity?>(null)
    val details: StateFlow<CharacterDetailsEntity?> get() = _details

    private fun showLoading() {
        _characterDetailsState.value = CharacterDetailsState.IsLoading(true)
    }

    private fun hideLoading() {
        _characterDetailsState.value = CharacterDetailsState.IsLoading(false)
    }

    private fun showError(message: String) {
        _characterDetailsState.value = CharacterDetailsState.Error(message)
    }

    fun fetchCharacterDetails(id: Int) {
        viewModelScope.launch {
            getCharacterDetailsByIdUseCase.execute(id)
                .onStart { showLoading() }
                .catch { exception ->
                    hideLoading()
                    showError(exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> _details.value = result.data
                        is BaseResult.Error -> showError(result.rawResponse.code.toString())
                    }
                }
        }
    }
}

sealed class CharacterDetailsState {
    object Init : CharacterDetailsState()
    data class IsLoading(val isLoading: Boolean) : CharacterDetailsState()
    data class Error(val message: String) : CharacterDetailsState()
}