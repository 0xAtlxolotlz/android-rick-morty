package com.nauatlakatl.rickmorty.data.characters

import com.nauatlakatl.rickmorty.data.characters.remote.api.CharactersApi
import com.nauatlakatl.rickmorty.data.characters.remote.repository.CharactersRepositoryImpl
import com.nauatlakatl.rickmorty.data.common.module.RetrofitModule
import com.nauatlakatl.rickmorty.domain.characters.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
@InstallIn(SingletonComponent::class)
class CharactersModule {

    @Singleton
    @Provides
    fun provideCharactersApi(retrofit: Retrofit): CharactersApi {
        return retrofit.create(CharactersApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCharactersRepository(charactersApi: CharactersApi): CharactersRepository {
        return CharactersRepositoryImpl(charactersApi)
    }
}