package org.ieselcaminas.valentin.managesextinguisher.database.extinguisher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import org.ieselcaminas.valentin.managesextinguisher.database.relations.FloorWithExtinguisher

@Dao
interface ExtinguisherDao {
    @Insert fun insertExt(ext: Extinguisher)

    @Update fun updateExt(ext: Extinguisher)

    @Delete fun delete(ext: Extinguisher)

    @Query ("SELECT * FROM Extinguisher ORDER BY extinguisherId DESC")
    fun getAllExtinguisher(): List<Extinguisher>

    @Query("DELETE FROM Extinguisher")
    fun clearExtinguisher()

    @Query("SELECT * FROM Extinguisher WHERE extinguisherId = :nExtinguisher")
    fun getExintinguisher(nExtinguisher: String): Extinguisher

    @Transaction
    @Query("SELECT * FROM Floor WHERE floorId IN (SELECT DISTINCT(extinguisherId) FROM Extinguisher)")
    fun getExtinguishersFromFloor(): List<FloorWithExtinguisher>
}