package org.ieselcaminas.valentin.managesextinguisher.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExtinguisherDatabaseDao {
    @Insert
    fun insert(ext: Extinguisher)

    @Update
    fun update(ext: Extinguisher)

    @Delete
    fun delete(ext: Extinguisher)

    @Query("DELETE FROM Extinguisher")
    fun clear()

    @Query ("SELECT * FROM Extinguisher ORDER BY extinguisherId DESC")
    fun getAllExtinguisher(): LiveData<List<Extinguisher>>
}