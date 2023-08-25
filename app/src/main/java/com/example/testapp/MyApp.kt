package com.example.testapp

import android.app.Application
import com.example.testapp.repositories.dataprovider.retrofit.RetrofitHelperClass
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp @Inject constructor(): Application() {

    lateinit var retrofit: RetrofitHelperClass

    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}