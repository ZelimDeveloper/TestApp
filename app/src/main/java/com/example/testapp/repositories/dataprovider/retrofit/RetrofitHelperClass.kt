package com.example.testapp.repositories.dataprovider.retrofit

import com.example.testapp.utils.API_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RetrofitHelperClass @Inject constructor()  {

    private val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
    }
    private val foodServiceResponse = AppInterceptor()
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120,TimeUnit.SECONDS)
        .addInterceptor(logging)
        .addInterceptor(foodServiceResponse)
    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    val apiClass  = Retrofit.Builder()
        .baseUrl(API_URL)
        .client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    var retrofitApi: ApiRequestClass = apiClass.create()

}
