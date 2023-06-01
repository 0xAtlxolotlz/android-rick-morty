package com.nauatlakatl.rickmorty.data.characters.remote.dto

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("species") var species: String,
    @SerializedName("image") var image: String
)
