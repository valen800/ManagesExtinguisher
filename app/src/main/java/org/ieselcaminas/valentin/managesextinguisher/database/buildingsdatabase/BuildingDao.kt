package org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase

import androidx.room.*

@Dao
interface BuildingDao {
    @Insert fun insertBuilding(Build: Building)

    @Update fun updateBuilding(Build: Building)

    @Delete fun deleteBuilding(Build: Building)

    @Query ("SELECT * FROM Building ORDER BY buildingId DESC")
    fun getAllBuilding(): List<Building>

    @Query("DELETE FROM Building")
    fun clearBuilding()

    @Query("SELECT * FROM Building WHERE buildingId = :nBuilding")
    fun getBuilding(nBuilding: Long): Building
}