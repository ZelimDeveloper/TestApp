package com.example.testapp.repositories.dataprovider.room

import android.content.Context
import androidx.room.*
import com.example.testapp.models.Camera
import com.example.testapp.models.Door
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataProvider @Inject constructor(@ApplicationContext context : Context) {
    private val roomDb = Room.databaseBuilder(context, TestAppDatabase::class.java, "testapp_sql").build()
    val appDao = roomDb.testAppDao()
}


@Database(entities = [Camera::class, Door::class], version = 1)
abstract class TestAppDatabase : RoomDatabase() {
    abstract fun testAppDao():TestAppDao
}

@Dao
interface TestAppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCamerasToDB (cameraList: List<Camera>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDoorsToDB (doorsList: List<Door>)

    @Query("SELECT * FROM camera")
    suspend fun getCameras():List<Camera>

    @Query("SELECT * FROM door")
    suspend fun getDoors():List<Door>

    @Update
    suspend fun updateDoor(item:Door)

}


