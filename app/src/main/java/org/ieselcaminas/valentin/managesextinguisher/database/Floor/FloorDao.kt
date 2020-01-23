package org.ieselcaminas.valentin.managesextinguisher.database.Floor

import androidx.lifecycle.LiveData
import androidx.room.*
import org.ieselcaminas.valentin.managesextinguisher.database.Building.Building
import org.ieselcaminas.valentin.managesextinguisher.database.Relations.FloorWithExtinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.Relations.FloorWithFlask

@Dao
interface FloorDao {
    @Insert fun insertFloor(floor: Floor)

    @Update fun updateFloor(floor: Floor)

    @Delete fun deleteFloor(floor: Floor)

    @Query ("SELECT * FROM Floor_table ORDER BY floor_Id DESC")
    fun getAllFloors(): LiveData<List<Building>>

    @Query("DELETE FROM Floor_table")
    fun clearFloor()

    @Query("SELECT * FROM Floor_table WHERE floor_Id = :nFloor")
    fun getFloor(nFloor: Long): LiveData<Floor>

    @Transaction
    @Query("SELECT * FROM Floor_table WHERE floor_Id IN (SELECT DISTINCT(extinguisher_Id) FROM Extinguisher_table)")
    fun getExtinguishersFromFloor(): LiveData<List<FloorWithExtinguisher>>

    @Transaction
    @Query("SELECT * FROM Floor_table WHERE floor_Id IN (SELECT DISTINCT(flask_Id) FROM Flask_table)")
    fun getFlaskFromFloor(): LiveData<List<FloorWithFlask>>
}