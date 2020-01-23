package org.ieselcaminas.valentin.managesextinguisher.database.Extinguisher

import androidx.lifecycle.LiveData
import androidx.room.*
import org.ieselcaminas.valentin.managesextinguisher.database.Extinguisher.Extinguisher

@Dao
interface ExtinguisherDao {
    @Insert fun insertExt(ext: Extinguisher)

    @Update fun updateExt(ext: Extinguisher)

    @Delete fun delete(ext: Extinguisher)

    @Query ("SELECT * FROM Extinguisher_table ORDER BY extinguisher_Id DESC")
    fun getAllExtinguisher(): LiveData<List<Extinguisher>>

    @Query("DELETE FROM Extinguisher_table")
    fun clearExtinguisher()

    @Query("SELECT * FROM Extinguisher_table WHERE extinguisher_Id = :nExtinguisher")
    fun getExintinguisher(nExtinguisher: String): LiveData<Extinguisher>
}