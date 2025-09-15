package org.example.project.database

import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.project.Res

fun getDatabaseBuilder(): RoomDatabase.Builder<QuranDatabase> {
    return Room.databaseBuilder<QuranDatabase>(
        name = Res.getUri("files/hafs_smart_v8.db")
            .removePrefix("file://"),
    )
}