package com.example.testapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class CameraResponseModel(
    val success:Boolean = false,
    val data: CameraModel = CameraModel()
)


data class CameraModel(
    val room: List<String> = emptyList(),
    val cameras: List<Camera> = emptyList()
)

@Entity(tableName = "camera")
data class Camera(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name:String = "",
    @ColumnInfo(name = "snapshot")
    val snapshot: String = "",
    @ColumnInfo(name = "room")
    val room: String? = "",
    @ColumnInfo(name = "favorites")
    val favorites: Boolean = false,
    @ColumnInfo(name = "rec")
    val rec: Boolean = false
)