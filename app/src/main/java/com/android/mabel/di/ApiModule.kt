package com.android.mabel.di


import com.android.mabel.restservice.FeedApi
import com.android.mabel.restservice.FeedService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val BASE_URL = "https://www.apphusetreach.no/application/119267/"
    @Provides
    fun provideUsersApi(): FeedApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(FeedApi::class.java)
    }
    @Provides
    fun provideFeedService(): FeedService {
        return FeedService()
    }
}