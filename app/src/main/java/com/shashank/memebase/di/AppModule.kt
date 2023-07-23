package com.shashank.memebase.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shashank.memebase.data.NetworkChangeReceiver
import com.shashank.memebase.data.RequestInterceptorApiKey
import com.shashank.memebase.entry.data.RequestInterceptorAccessToken
import com.shashank.memebase.entry.data.UserPreferences
import com.shashank.memebase.networking.usecases.TaskyCallStatusImpl
import com.shashank.memebase.networking.usecases.TaskyLoaderImpl
import com.shashank.memebase.networking.usecases.domain.TaskyCallStatus
import com.shashank.memebase.networking.usecases.domain.TaskyLoader
import com.shashank.memebase.usecases.NetworkStatus
import com.shashank.memebase.db.AppDatabase
import com.shashank.memebase.db.Dao
import com.shashank.memebase.globals.Constants
import com.shashank.memebase.meme.network.RetroService
import com.shashank.memebase.meme.storage.SaveDataImpl
import com.shashank.memebase.meme.storage.domain.SaveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    fun providesContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun providesSaveData(@ApplicationContext applicationContext: Context) : SaveData {
        return SaveDataImpl(applicationContext)
    }

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

    @Singleton
    @Provides
    fun provideNetworkChangeReceiver(context: Context): LiveData<NetworkStatus> {
        return NetworkChangeReceiver(context)
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().setLenient().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.ApiProperties.APP_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }



    @Provides
    @Singleton
    fun getRetroServiceInstance(retrofit: Retrofit): RetroService {
        return retrofit.create(RetroService::class.java)
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideRequestInterceptorApiKey(): RequestInterceptorApiKey {
        return RequestInterceptorApiKey()
    }

    @Singleton
    @Provides
    fun provideRequestInterceptorAccessToken(userPreferences: UserPreferences): RequestInterceptorAccessToken {
        return RequestInterceptorAccessToken(userPreferences)
    }

    @Singleton
    @Provides
    fun providesTaskyLoaderImpl(context: Context): TaskyLoader {
        return TaskyLoaderImpl(context)
    }

    @Singleton
    @Provides
    fun providesTaskyCallStatusImpl(taskyLoader: TaskyLoader, context: Context): TaskyCallStatus {
        return TaskyCallStatusImpl(taskyLoader, context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(requestInterceptor: RequestInterceptorAccessToken, requestInterceptorApiKey: RequestInterceptorApiKey, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(Constants.ApiProperties.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.ApiProperties.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.ApiProperties.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(requestInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(requestInterceptorApiKey)
        return httpClient.build()
    }
}