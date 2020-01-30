package org.ieselcaminas.valentin.managesextinguisher.database.floor

import androidx.room.*

@Entity(tableName = "Floor")
data class Floor(

    @PrimaryKey var floorId: Long,
    var buildingFloorID: Long,

    @ColumnInfo(name = "name_floor") var nameFloor: String,
    @ColumnInfo(name = "n_floor") var nFloor: Long,
    @ColumnInfo(name = "amount_extinguisher") var amountExtinguisher: Int
)

