package com.example.testapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class DoorsResponseModel(
    val success:Boolean = false,
    val data: List<Door> = emptyList()
)


@Entity(tableName = "door")
data class Door(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("name")
    var name:String = "",
    @ColumnInfo("snapshot")
    val snapshot: String = "",
    @ColumnInfo("room")
    val room: String? = null,
    @ColumnInfo("favorites")
    val favorites: Boolean = false,
    @ColumnInfo("rec")
    val rec: Boolean = false
)
