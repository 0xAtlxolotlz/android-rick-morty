package com.nauatlakatl.rickmorty.data.characters.remote.dto

import com.google.gson.annotations.SerializedName

data class CharacterDetailsResponse(
    @SerializedName("name") var name: String,
    @SerializedName("status") var status: String,
    @SerializedName("species") var species: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("origin") var originResponse: OriginResponse,
    @SerializedName("location") var locationResponse: LocationResponse,
    @SerializedName("image") var image: String
)

data class OriginResponse(
    @SerializedName("name") var name: String
)

data class LocationResponse(
    @SerializedName("name") var name: String
)