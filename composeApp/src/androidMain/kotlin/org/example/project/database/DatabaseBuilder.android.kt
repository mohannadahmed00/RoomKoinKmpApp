package org.example.project.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.project.Res

actual class DatabaseBuilder(private val context: Context) {

    actual fun getBuilder(): RoomDatabase.Builder<QuranDatabase> {
        val appContext = context.applicationContext
        return Room.databaseBuilder<QuranDatabase>(
            context = appContext,
            name = "hafs_smart_v8.db"
        ).createFromAsset(
            Res.getUri(
                "files/hafs_smart_v8.db"
            ).removePrefix("file:///android_asset/")
        )
    }
}