package org.example.project.database

import androidx.room.RoomDatabase

expect class DatabaseBuilder {
    fun getBuilder(): RoomDatabase.Builder<QuranDatabase>
}