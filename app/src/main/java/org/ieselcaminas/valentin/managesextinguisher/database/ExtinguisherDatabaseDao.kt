package org.ieselcaminas.valentin.managesextinguisher.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExtinguisherDatabaseDao {
    @Insert fun insertExt(ext: Extinguisher)
    @Update fun updateExt(ext: Extinguisher)
    @Delete fun delete(ext: Extinguisher)

    @Query ("SELECT * FROM Extinguisher_table ORDER BY extinguisherId DESC")
    fun getAllExtinguisher(): LiveData<List<Extinguisher>>

    @Query("DELETE FROM Extinguisher_table")
    fun clearExtinguisher()

    /*@Insert
    fun insertFlask(flask: Flask)
    @Insert
    fun insertFloor(floor: Floor)
    @Insert
    fun insertBuilding(building: Building)*/

    /*@Update
    fun updateFlask(flask: Flask)
    @Update
    fun updateFloor(floor: Floor)
    @Update
    fun updateBuilding(building: Building)*/


    /*@Delete
    fun deleteFlask(flask: Flask)
    @Delete
    fun deleteFloor(floor: Floor)
    @Delete
    fun deleteBuilding(building: Building)*/


    /*@Query ("SELECT * FROM Flask_table ORDER BY flaskId DESC")
    fun getAllFlask(): LiveData<List<Flask>>
    @Query ("SELECT * FROM Floor_table ORDER BY floorId DESC")
    fun getAllFloors(): LiveData<List<Floor>>
    @Query ("SELECT * FROM Building_table ORDER BY buildingId DESC")
    fun getAllBuildings(): LiveData<List<Building>>*/


   /* @Query("DELETE FROM Flask_table")
    fun clearFlask()
    @Query("DELETE FROM Floor_table")
    fun clearFloor()
    @Query("DELETE FROM Building_table")
    fun clearBuilding()*/

    /*@Transaction
    @Query("SELECT * FROM Building_table")
    fun getBuildingWithFloors(): List<BuildingWithFloors>

    @Transaction
    @Query("SELECT * FROM Floor_table")
    fun getFloorWithExtinguisher(): List<FloorWithExtinguisher>

    @Transaction
    @Query("SELECT * FROM Floor_table")
    fun getFloorWithFlask(): List<FloorWithFlask>*/
}