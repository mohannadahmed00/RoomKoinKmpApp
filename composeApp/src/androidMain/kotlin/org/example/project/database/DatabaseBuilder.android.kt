package org.example.project.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseBuilder(private val context: Context) {
    actual fun getBuilder(): RoomDatabase.Builder<QuranDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath("quran.db")
        return Room.databaseBuilder<QuranDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        ).createFromAsset("hafs_smart_v8.db")
    }
}