package com.example.testapp.repositories.dataprovider.retrofit

import com.example.testapp.models.CameraResponseModel
import com.example.testapp.models.DoorsResponseModel
import retrofit2.Call
import retrofit2.http.*


interface ApiRequestClass {

    @GET("rubetek/cameras/")
    fun getCameras():Call<CameraResponseModel>

    @GET("rubetek/doors/")
    fun getDoors():Call<DoorsResponseModel>



}


