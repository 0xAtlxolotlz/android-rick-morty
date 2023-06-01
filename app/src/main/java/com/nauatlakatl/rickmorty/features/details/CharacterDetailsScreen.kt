package com.nauatlakatl.rickmorty.features.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
fun CharacterDetailsScreen(
    characterId: Int,
    modifier: Modifier = Modifier,
    characterDetailsViewModel: CharacterDetailsViewModel = hiltViewModel()
) {
    val details by characterDetailsViewModel.details.collectAsState()

    LaunchedEffect(Unit) {
        characterDetailsViewModel.fetchCharacterDetails(characterId)
    }

    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            if (details != null) {
                Text(text = details!!.name)
                Text(text = details!!.status)
                Text(text = details!!.species)
                Text(text = details!!.gender)
                Text(text = details!!.origin.name)
                Text(text = details!!.location.name)
                Text(text = details!!.image)
            }
        }
    }
}