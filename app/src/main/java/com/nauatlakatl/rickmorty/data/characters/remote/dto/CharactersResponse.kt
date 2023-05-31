package com.nauatlakatl.rickmorty.data.characters.remote.dto

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("species") var species: String? = null,
    @SerializedName("image") var image: String? = null
)
