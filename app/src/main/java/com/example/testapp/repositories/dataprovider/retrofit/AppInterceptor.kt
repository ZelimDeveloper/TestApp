package com.example.testapp.repositories.dataprovider.retrofit

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AppInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        request = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build()

        val response = chain.proceed(request)
        return response
    }
}



