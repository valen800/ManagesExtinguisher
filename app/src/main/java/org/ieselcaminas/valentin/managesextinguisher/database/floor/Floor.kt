package org.ieselcaminas.valentin.managesextinguisher.database.floor

import androidx.room.*

@Entity(tableName = "Floor_table")
data class Floor(

    @PrimaryKey(autoGenerate = true) var floorId: Long = 0L,
    var buildingFloorID: Long,

    @ColumnInfo(name = "name_floor") var nameFloor: String = "",
    @ColumnInfo(name = "n_floor") var nFloor: Long = 0L,
    @ColumnInfo(name = "amount_extinguisher") var amountExtinguisher: Int = 0
)

