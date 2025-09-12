package org.example.project.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(entities = [Person::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)

abstract class PeopleDatabase : RoomDatabase() {
    abstract fun getPeopleDao(): PeopleDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<PeopleDatabase> {
    override fun initialize(): PeopleDatabase
}