package com.shashank.memebase.meme.di

import android.content.Context

import com.shashank.memebase.db.Dao
import com.shashank.memebase.meme.domain.PopUpTaskyListDialog
import com.shashank.memebase.meme.domain.PopUpTaskyListDialogImpl
import com.shashank.memebase.meme.network.RetroService
import com.shashank.memebase.meme.repositories.NetworkRepository
import com.shashank.memebase.meme.repositories.NetworkRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AgendaModules {

    @Provides
    fun providesPopUpTaskyDialog(@ApplicationContext applicationContext: Context): PopUpTaskyListDialog {
        return PopUpTaskyListDialogImpl(applicationContext)
    }

    @Provides
    @Singleton
    fun providesNetworkRepository(retroService: RetroService, dao: Dao) : NetworkRepository{
        return NetworkRepositoryImpl(retroService, dao)
    }
}