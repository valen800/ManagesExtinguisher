package org.ieselcaminas.valentin.managesextinguisher.database.Building

import androidx.lifecycle.LiveData
import androidx.room.*
import org.ieselcaminas.valentin.managesextinguisher.database.Relations.BuildingWithFloors

@Dao
interface BuildingDao {
    @Insert fun insertBuilding(Build: Building)

    @Update fun updateBuilding(Build: Building)

    @Delete fun deleteBuilding(Build: Building)

    @Query ("SELECT * FROM Building_table ORDER BY building_Id DESC")
    fun getAllBuilding(): LiveData<List<Building>>

    @Query("DELETE FROM Building_table")
    fun clearBuilding()

    @Query("SELECT * FROM Building_table WHERE building_Id = :nBuilding")
    fun getBuilding(nBuilding: Long): LiveData<Building>

    @Transaction
    @Query("SELECT * FROM building_table WHERE building_Id IN (SELECT DISTINCT(floor_Id) FROM Floor_table)")
    fun getFloorsFromBuilding(): LiveData<List<BuildingWithFloors>>
}