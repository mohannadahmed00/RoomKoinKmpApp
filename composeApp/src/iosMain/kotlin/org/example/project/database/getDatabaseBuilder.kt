package org.example.project.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.example.project.Res
import platform.Foundation.*

fun getDatabaseBuilder(): RoomDatabase.Builder<QuranDatabase> {
    Res.getUri("files/hafs_smart_v8.db")
    val resourceD = Res.getUri("files/hafs_smart_v8.db").removePrefix("file://")
    return Room.databaseBuilder<QuranDatabase>(
        name = resourceD ,
    )
}