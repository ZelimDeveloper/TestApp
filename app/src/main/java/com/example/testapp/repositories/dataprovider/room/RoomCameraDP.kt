package com.example.testapp.repositories.dataprovider.room

import com.example.testapp.models.Camera
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomCameraDP  @Inject constructor(private val dataProvider: AppDataProvider) {

    suspend fun saveCamerasToDB(items: List<Camera>) {
        dataProvider.appDao.saveCamerasToDB(items)
    }

    suspend fun getCameras():List<Camera> {
      return dataProvider.appDao.getCameras()
    }

}