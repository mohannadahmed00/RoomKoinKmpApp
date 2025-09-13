package org.example.project.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(entities = [AyaDto::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun getAyaDao(): AyaDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<QuranDatabase> {
    override fun initialize(): QuranDatabase
}