package com.nauatlakatl.rickmorty.features.details

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nauatlakatl.rickmorty.R
import com.nauatlakatl.rickmorty.data.common.utils.ERROR_404
import com.nauatlakatl.rickmorty.ui.composables.Error
import com.nauatlakatl.rickmorty.ui.composables.LoaderIndicator
import com.nauatlakatl.rickmorty.ui.composables.NoResults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    modifier: Modifier = Modifier,
    characterDetailsViewModel: CharacterDetailsViewModel = hiltViewModel()
) {
    val details by characterDetailsViewModel.details.collectAsState()
    val charactersDetailsState by characterDetailsViewModel.characterDetailsState.collectAsState()

    LaunchedEffect(Unit) {
        characterDetailsViewModel.fetchCharacterDetails(characterId)
    }

    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            when (charactersDetailsState) {
                is CharacterDetailsState.IsLoading -> {
                    if ((charactersDetailsState as CharacterDetailsState.IsLoading).isLoading) {
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) { LoaderIndicator() }
                    } else {
                        if (details != null) {
                            Box(modifier = modifier.aspectRatio(1F)) {
                                AsyncImage(
                                    modifier = modifier.fillMaxSize(),
                                    model = details!!.image,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Text(
                                text = details!!.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                modifier = modifier.padding(8.dp)
                            )
                            CharacterDetailsRow(
                                iconResId = R.drawable.ic_heart,
                                text = details!!.status
                            )
                            CharacterDetailsRow(
                                iconResId = R.drawable.ic_paw,
                                text = details!!.species
                            )
                            CharacterDetailsRow(
                                iconResId = R.drawable.ic_gender,
                                text = details!!.gender
                            )
                            CharacterDetailsRow(
                                iconResId = R.drawable.ic_house,
                                text = details!!.origin.name
                            )
                            CharacterDetailsRow(
                                iconResId = R.drawable.ic_location,
                                text = details!!.location.name
                            )
                        }
                    }
                }

                is CharacterDetailsState.Error -> {
                    val error = charactersDetailsState as CharacterDetailsState.Error
                    when (error.message) {
                        ERROR_404 -> NoResults()
                        else -> Error()
                    }
                }

                else -> Unit
            }
        }
    }
}

@Composable
private fun CharacterDetailsRow(
    @DrawableRes iconResId: Int,
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painterResource(iconResId), contentDescription = null, modifier = modifier.size(20.dp))
        Text(text = text, fontWeight = fontWeight, modifier = modifier.padding(start = 8.dp))
    }
}