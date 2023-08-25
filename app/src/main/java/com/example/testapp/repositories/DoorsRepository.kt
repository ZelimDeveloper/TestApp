package com.example.testapp.repositories


import com.example.testapp.models.Door
import com.example.testapp.models.DoorsResponseModel
import com.example.testapp.repositories.dataprovider.retrofit.RetrofitDoorsDP
import com.example.testapp.repositories.dataprovider.room.RoomDoorDP
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DoorsRepository @Inject constructor(private var retrofitCartDP: RetrofitDoorsDP, private var roomDoorDP: RoomDoorDP) {
    fun getDoors(success: (DoorsResponseModel) -> Unit) {
        retrofitCartDP.getDoors(success)
    }

    suspend fun saveDoorsToDB(items:List<Door>) {
        roomDoorDP.saveDoorToDB(items)
    }

    suspend fun getDoorsFromDB():List<Door> {
        return roomDoorDP.getDoorsFromDB()
    }

    suspend fun updateDoor(item:Door) {
        roomDoorDP.updateDoor(item)
    }
}