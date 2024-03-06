package ro.vlad.simion.softbinatior.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ro.vlad.simion.softbinatior.BuildConfig
import ro.vlad.simion.softbinatior.data.networking.PetFinderApi
import ro.vlad.simion.softbinatior.data.storage.InMemoryDataStore
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val READ_TIMEOUT = 30
private const val WRITE_TIMEOUT = 30
private const val CONNECTION_TIMEOUT = 10
private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 Mb

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return RetrofitHelper.getInstance(client)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: Interceptor
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        })
        return okHttpClientBuilder.build()
    }


    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val hasAuthBearer = InMemoryDataStore.hasAuthBearer()
            val requestBuilder = it.request().newBuilder()
            requestBuilder.addHeader("Accept", "*/*")
            requestBuilder.addHeader("Connection", "keep-alive")
            if (hasAuthBearer) {
                requestBuilder.addHeader(
                    "Authorization",
                    "Bearer ${InMemoryDataStore.getAuthBearer()}"
                )
            }
            it.proceed(requestBuilder.build())
        }
    }


    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): PetFinderApi {
        return retrofit.create(PetFinderApi::class.java)
    }

}