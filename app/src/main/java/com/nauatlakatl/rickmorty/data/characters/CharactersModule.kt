package com.nauatlakatl.rickmorty.data.characters

import com.nauatlakatl.rickmorty.data.characters.remote.api.CharactersApi
import com.nauatlakatl.rickmorty.data.common.module.RetrofitModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [RetrofitModule::class])
@InstallIn(SingletonComponent::class)
class CharactersModule {

    fun provideCharactersApi(retrofit: Retrofit): CharactersApi {
        return retrofit.create(CharactersApi::class.java)
    }
}