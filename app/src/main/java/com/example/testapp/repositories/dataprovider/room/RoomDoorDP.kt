package com.example.testapp.repositories.dataprovider.room

import com.example.testapp.models.Door
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomDoorDP  @Inject constructor(private val dataProvider: AppDataProvider) {

    suspend fun saveDoorToDB(doorsList:List<Door>) {
        dataProvider.appDao.saveDoorsToDB(doorsList)
    }

    suspend fun getDoorsFromDB():List<Door> {
       return dataProvider.appDao.getDoors()
    }

    suspend fun updateDoor(item:Door) {
        dataProvider.appDao.updateDoor(item)
    }
}
