package com.nauatlakatl.rickmorty.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nauatlakatl.rickmorty.domain.characters.entity.CharactersEntity
import com.nauatlakatl.rickmorty.domain.characters.usecase.GetCharactersUseCase
import com.nauatlakatl.rickmorty.domain.common.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState.Init)
    val homeState: StateFlow<HomeState> get() = _homeState

    private val _characters = MutableStateFlow<List<CharactersEntity>>(mutableListOf())
    val characters: StateFlow<List<CharactersEntity>> get() = _characters

    private fun showLoading() {
        _homeState.value = HomeState.IsLoading(true)
    }

    private fun hideLoading() {
        _homeState.value = HomeState.IsLoading(false)
    }

    private fun showError(message: String) {
        _homeState.value = HomeState.Error(message)
    }

    fun fetchCharacters() {
        viewModelScope.launch {
            getCharactersUseCase.execute()
                .onStart { showLoading() }
                .catch { exception ->
                    hideLoading()
                    showError(exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> _characters.value = result.data
                        is BaseResult.Error -> showError(result.rawResponse.code.toString())
                    }
                }
        }
    }
}

sealed class HomeState {
    object Init : HomeState()
    data class IsLoading(val isLoading: Boolean) : HomeState()
    data class Error(val message: String) : HomeState()
}