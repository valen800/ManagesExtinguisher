package org.ieselcaminas.valentin.managesextinguisher.database.Building

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BuildingDao {
    @Insert fun insertBuilding(Build: Building)

    @Update fun updateBuilding(Build: Building)

    @Delete fun deleteBuilding(Build: Building)

    @Query ("SELECT * FROM Building ORDER BY buildingId DESC")
    fun getAllBuilding(): LiveData<List<Building>>

    @Query("DELETE FROM Building")
    fun clearBuilding()

    @Query("SELECT * FROM Building WHERE buildingId = :nBuilding")
    fun getBuilding(nBuilding: Long): LiveData<Building>
}