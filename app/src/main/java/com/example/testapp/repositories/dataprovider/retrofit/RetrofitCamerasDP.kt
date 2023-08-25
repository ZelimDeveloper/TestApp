package com.example.testapp.repositories.dataprovider.retrofit

import android.util.Log
import com.example.testapp.models.CameraResponseModel
import javax.inject.Inject
import javax.inject.Singleton
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.request

@Singleton
class RetrofitCamerasDP @Inject constructor(private var retrofit: RetrofitHelperClass) {

    fun getCameras(success: (CameraResponseModel) -> Unit) {
        retrofit.retrofitApi.getCameras().request { response -> when (response) {

            is ApiResponse.Success -> {
                success(response.data)
                Log.d("TAGG", "Success $response")
            }
            is ApiResponse.Failure.Error -> {
                Log.d("TAGG", "Error $response")
            }
            is ApiResponse.Failure.Exception -> {
                Log.d("TAGG", "Failure $response")
            }
        }
        }
    }
}