package com.nauatlakatl.rickmorty.domain.characters.entity

data class CharacterDetailsEntity(
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: OriginEntity,
    val location: LocationEntity,
    val image: String
)

data class OriginEntity(
    val name: String
)

data class LocationEntity(
    val name: String
)
