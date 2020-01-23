package org.ieselcaminas.valentin.managesextinguisher.database.Floor

import androidx.room.*
import java.util.*
import java.time.LocalDateTime

@Entity(tableName = "Floor_table")
data class Floor(

    @PrimaryKey @ColumnInfo(name = "floor_Id") var floorId: Long = 0L,
    var buildingFloorID: Long,

    @ColumnInfo(name = "name_floor") var nameFloor: String = "",
    @ColumnInfo(name = "n_floor") var nFloor: Long = 0L,
    @ColumnInfo(name = "amount_extinguisher") var amountExtinguisher: Int = 0
)

