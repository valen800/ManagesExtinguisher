package org.ieselcaminas.valentin.managesextinguisher.database.floor

import androidx.lifecycle.LiveData
import androidx.room.*
import org.ieselcaminas.valentin.managesextinguisher.database.relations.BuildingWithFloors

@Dao
interface FloorDao {
    @Insert fun insertFloor(floor: Floor)

    @Update fun updateFloor(floor: Floor)

    @Query ("SELECT * FROM Floor ORDER BY floorId DESC")
    fun getAllFloors(): LiveData<List<Floor>>

    @Query("DELETE FROM Floor")
    fun clearFloor()

    @Query("SELECT * FROM Floor WHERE buildingFloorID = :nBuilding")
    fun getFloorByBuildingId(nBuilding: Long): LiveData<List<Floor>>

    @Transaction
    @Query("SELECT * FROM Building WHERE buildingId IN (SELECT DISTINCT(floorId) FROM Floor)")
    fun getFloorsFromBuilding(): LiveData<List<BuildingWithFloors>>

    @Query ("DELETE FROM Floor WHERE floorId = :floorId")
    fun deleteById(floorId: Long)
}
