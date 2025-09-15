package org.example.project.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*

fun getDatabaseBuilder(): RoomDatabase.Builder<QuranDatabase> {
    val dbFilePath = documentDirectory() + "/hafs_smart_v8.db"
    ensureBundledDatabaseCopied(dbFilePath)
    return Room.databaseBuilder<QuranDatabase>(
        name = dbFilePath,
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

@OptIn(ExperimentalForeignApi::class)
private fun ensureBundledDatabaseCopied(destinationPath: String) {
    val fileManager = NSFileManager.defaultManager
    if (fileManager.fileExistsAtPath(destinationPath)) return

    val bundlePath = NSBundle.mainBundle.bundlePath
    val packagedDbPath = "$bundlePath/compose-resources/composeResources/org.example.project/files/hafs_smart_v8.db"

    if (fileManager.fileExistsAtPath(packagedDbPath)) {
        fileManager.copyItemAtPath(packagedDbPath, destinationPath, error = null)
    }
}