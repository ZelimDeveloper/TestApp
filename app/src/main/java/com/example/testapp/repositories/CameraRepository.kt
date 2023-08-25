package com.example.testapp.repositories

import com.example.testapp.models.Camera
import com.example.testapp.models.CameraResponseModel
import com.example.testapp.repositories.dataprovider.retrofit.RetrofitCamerasDP
import com.example.testapp.repositories.dataprovider.room.RoomCameraDP
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CameraRepository @Inject constructor(private var retrofitCartDP: RetrofitCamerasDP, private var roomCameraDP: RoomCameraDP)
{
    fun getCameras(success: (CameraResponseModel) -> Unit) {
        retrofitCartDP.getCameras(success)
    }


    suspend fun saveCamerasToDB(items:List<Camera>) {
        roomCameraDP.saveCamerasToDB(items)
    }

    suspend fun getCamerasFromDB():List<Camera> {
       return roomCameraDP.getCameras()
    }


}