package org.ieselcaminas.valentin.managesextinguisher.database.floor

import androidx.room.*

@Entity(tableName = "Floor")
data class Floor(

    @PrimaryKey (autoGenerate = true)
    var floorId: Long = 0L,
    var buildingFloorID: Long = 0L,

    @ColumnInfo(name = "name_floor") var nameFloor: String = "",
    @ColumnInfo(name = "n_floor") var nFloor: Int = 0
)

