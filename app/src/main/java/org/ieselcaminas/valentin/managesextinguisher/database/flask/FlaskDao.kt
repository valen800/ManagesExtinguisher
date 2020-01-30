package org.ieselcaminas.valentin.managesextinguisher.database.flask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.relations.FloorWithFlask

@Dao
interface FlaskDao {
    @Insert fun insertFlask(flask: Flask)

    @Update fun updateFlask(flask: Flask)

    @Delete fun deleteFlask(flask: Flask)

    @Query ("SELECT * FROM Flask ORDER BY flaskId DESC")
    fun getAllFlask(): List<Flask>

    @Query("DELETE FROM Flask")
    fun clearFlask()

    @Query("SELECT * FROM Flask WHERE flaskId = :nFlask")
    fun getFlask(nFlask: String): Flask

    @Transaction
    @Query("SELECT * FROM Floor WHERE floorId IN (SELECT DISTINCT(flaskId) FROM Flask)")
    fun getFlaskFromFloor(): List<FloorWithFlask>
}
