package org.ieselcaminas.valentin.managesextinguisher.database.floor

import androidx.room.*
import org.ieselcaminas.valentin.managesextinguisher.database.relations.BuildingWithFloors

@Dao
interface FloorDao {
@Insert fun insertFloor(floor: Floor)

    @Update fun updateFloor(floor: Floor)

    @Delete fun deleteFloor(floor: Floor)


    @Query ("SELECT * FROM Floor ORDER BY floorId DESC")
    fun getAllFloors(): List<Floor>

    @Query("DELETE FROM Floor")
    fun clearFloor()

    @Query("SELECT * FROM Floor WHERE floorId = :nFloor")
    fun getFloor(nFloor: Long): Floor

    @Transaction
    @Query("SELECT * FROM Building WHERE buildingId IN (SELECT DISTINCT(floorId) FROM Floor)")
    fun getFloorsFromBuilding(): List<BuildingWithFloors>

}
