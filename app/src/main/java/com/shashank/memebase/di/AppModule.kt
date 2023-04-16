package com.shashank.memebase.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.shashank.memebase.db.AppDatabase
import com.shashank.memebase.db.Dao
import com.shashank.memebase.globals.ApplicationConstant
import com.shashank.memebase.network.RetroService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getAppDatabase(context: Application): AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun getAppDao(appDatabase: AppDatabase): Dao {
        return appDatabase.getDao()
    }

    @Provides
    @Singleton
    fun getRetroServiceInstance(retrofit: Retrofit): RetroService {
        return retrofit.create(RetroService::class.java)
    }

    @Provides
    @Singleton
    fun getRetroInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApplicationConstant.APP_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("memeApp", Context.MODE_PRIVATE)
    }
}