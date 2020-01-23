package org.ieselcaminas.valentin.managesextinguisher.database.Flask

import androidx.lifecycle.LiveData
import androidx.room.*
import org.ieselcaminas.valentin.managesextinguisher.database.Extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.Flask.Flask
import org.ieselcaminas.valentin.managesextinguisher.database.Relations.FloorWithFlask

@Dao
interface FlaskDao {
    @Insert fun insertFlask(flask: Flask)

    @Update fun updateFlask(flask: Flask)

    @Delete fun deleteFlask(flask: Flask)

    @Query ("SELECT * FROM Flask_table ORDER BY flask_Id DESC")
    fun getAllFlask(): LiveData<List<Flask>>

    @Query("DELETE FROM Extinguisher_table")
    fun clearFlask()

    @Query("SELECT * FROM Extinguisher_table WHERE extinguisher_Id = :nExtinguisher")
    fun getFlask(nExtinguisher: String): LiveData<Extinguisher>

    @Transaction
    @Query("SELECT * FROM Floor_table WHERE floorId IN (SELECT DISTINCT(flask_Id) FROM Flask_table)")
    fun getFlaskFromFloor(): LiveData<List<FloorWithFlask>>
}