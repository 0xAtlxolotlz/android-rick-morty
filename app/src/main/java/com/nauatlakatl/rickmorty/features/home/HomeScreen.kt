package com.nauatlakatl.rickmorty.features.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val characters by homeViewModel.characters.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.fetchCharacters()
    }

    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = paddingValues
        ) {
            items(characters) { character ->
                Text(text = character.name)
            }
        }
    }
}