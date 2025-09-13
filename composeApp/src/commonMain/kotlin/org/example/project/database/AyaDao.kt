package org.example.project.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AyaDao {
    @Query("SELECT * FROM ayat WHERE sura_no = :suraNo")
    suspend fun getAyatBySura(suraNo: Int): List<AyaDto>
}