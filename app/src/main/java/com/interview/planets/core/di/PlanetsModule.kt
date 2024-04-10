package com.interview.planets.core.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.interview.planets.BuildConfig
import com.interview.planets.core.database.PlanetDatabase
import com.interview.planets.data.network.PlanetService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlanetsModule {

    @Singleton
    @Provides
    fun providePlanetDataBase(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, PlanetDatabase::class.java, "planet_database")
        .build()


    @Singleton
    @Provides
    fun providePlanetDao(database: PlanetDatabase) = database.planetsDao()

    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

    @Singleton
    @Provides
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    @Singleton
    @Provides
    fun providePlanetService(okHttpClient: OkHttpClient): PlanetService = Retrofit.Builder()
        .baseUrl("https://swapi.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(PlanetService::class.java)
}