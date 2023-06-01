package com.nauatlakatl.rickmorty.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nauatlakatl.rickmorty.R
import com.nauatlakatl.rickmorty.domain.characters.entity.CharactersEntity
import com.nauatlakatl.rickmorty.ui.composables.Error
import com.nauatlakatl.rickmorty.ui.composables.LoaderIndicator

private const val EMPTY_FILTER_FIELD = ""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onClickCard: (Int) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    var openFilterDialog by remember { mutableStateOf(false) }
    val characters by homeViewModel.characters.collectAsState()
    val homeState by homeViewModel.homeState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.fetchCharacters()
    }

    if (openFilterDialog) {
        FilterDialog(
            onChangeDialogStatus = { openFilterDialog = it },
            onConfirmClicked = { name, status, gender ->
                homeViewModel.filterCharacters(
                    name,
                    status,
                    gender
                )
            }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { openFilterDialog = true }) {
                        Icon(
                            painterResource(R.drawable.ic_filter),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (homeState) {
                is HomeState.IsLoading -> {
                    if ((homeState as HomeState.IsLoading).isLoading) {
                        LoaderIndicator()
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2)
                        ) {
                            items(characters) { character ->
                                CharacterCard(character = character, onClick = onClickCard)
                            }
                        }
                    }
                }

                is HomeState.Error -> {
                    Error()
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun CharacterCard(
    character: CharactersEntity,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1F)
            .padding(4.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true),
                onClick = { onClick(character.id) }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                modifier = modifier.fillMaxSize(),
                model = character.image,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ), startY = 0F
                        )
                    )
            ) {
                Text(
                    text = character.name,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = modifier.padding(horizontal = 2.dp),
                    maxLines = 1
                )
                Text(
                    text = character.species,
                    color = Color.White,
                    modifier = modifier.padding(start = 2.dp, end = 2.dp, bottom = 2.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    onChangeDialogStatus: (Boolean) -> Unit,
    onConfirmClicked: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var filterName by remember { mutableStateOf(EMPTY_FILTER_FIELD) }
    var filterStatus by remember { mutableStateOf(EMPTY_FILTER_FIELD) }
    var filterGender by remember { mutableStateOf(EMPTY_FILTER_FIELD) }

    Dialog(onDismissRequest = {}) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                Text(
                    text = stringResource(R.string.dialog_filter_title),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = filterName,
                    onValueChange = { filterName = it },
                    label = { Text(stringResource(R.string.name_label)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                )
                ExposedDropdownMenuBox(
                    title = stringResource(R.string.dialog_status_subtitle),
                    options = stringArrayResource(id = R.array.status),
                    onOptionSelected = { filterStatus = it }
                )
                ExposedDropdownMenuBox(
                    title = stringResource(R.string.dialog_gender_subtitle),
                    options = stringArrayResource(id = R.array.gender),
                    onOptionSelected = { filterGender = it }
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onChangeDialogStatus(false) }) {
                        Text(text = stringResource(id = R.string.filter_dialog_cancel))
                    }
                    TextButton(
                        onClick = {
                            onConfirmClicked(filterName, filterStatus, filterGender)
                            onChangeDialogStatus(false)
                        },
                        enabled = filterName.isNotEmpty() || filterStatus.isNotEmpty() || filterGender.isNotEmpty()
                    ) {
                        Text(text = stringResource(id = R.string.filter_dialog_ok))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox(
    title: String,
    options: Array<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(options[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column {
            Text(text = title)
            ExposedDropdownMenuBox(
                modifier = modifier.background(color = MaterialTheme.colorScheme.primary),
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                onOptionSelected(selectedText)
                            }
                        )
                    }
                }
            }
        }
    }
}