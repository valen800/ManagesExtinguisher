package org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BuildingDao {
    @Insert fun insertBuilding(Build: Building)

    @Update fun updateBuilding(Build: Building)

    @Query ("SELECT * FROM Building ORDER BY buildingId DESC")
    fun getAllBuilding(): LiveData<List<Building>>

    @Query("DELETE FROM Building")
    fun clearBuilding()

    @Query("SELECT * FROM Building WHERE buildingId = :nBuilding")
    fun getBuildingById(nBuilding: Long): LiveData<Building>

    @Query ("DELETE FROM Building WHERE buildingId = :buildingId")
    fun deleteById(buildingId: Long)
}