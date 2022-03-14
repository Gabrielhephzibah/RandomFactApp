package com.cherish.randomfactproject.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cherish.randomfactproject.BuildConfig
import com.cherish.randomfactproject.data.local.StoreDao
import com.cherish.randomfactproject.data.local.StoreDatabase
import com.cherish.randomfactproject.data.remote.ApiService
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
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val builder = OkHttpClient.Builder()
            .callTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG){
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideStoreDatabase(@ApplicationContext context: Context): StoreDatabase{
        return  Room.databaseBuilder(
            context,
            StoreDatabase::class.java,
            "storedatabase"
        ).fallbackToDestructiveMigration()
            .build()


    }

    @Provides
    @Singleton
    fun provideStoreDao(storeDatabase: StoreDatabase) : StoreDao = storeDatabase.storeDao()



}